package org.usfirst.frc.team4990.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;

import org.usfirst.frc.team4990.robot.controllers.AutoDriveTrainController;
import org.usfirst.frc.team4990.robot.controllers.TeleopDriveTrainController;
import org.usfirst.frc.team4990.robot.controllers.TeleopForkliftController;
import org.usfirst.frc.team4990.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4990.robot.subsystems.F310Gamepad;
import org.usfirst.frc.team4990.robot.subsystems.Forklift;
import org.usfirst.frc.team4990.robot.subsystems.motors.TalonMotorController;
import org.usfirst.frc.team4990.robot.subsystems.motors.TalonSRXMotorController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Preferences prefs;
	private Logger logger;
	
	private F310Gamepad driveGamepad;
	private DriveTrain driveTrain;
	
	private Joystick forkliftJoystick;
	private Forklift forklift;
	
	private AutoDriveTrainController autoDriveTrainController;
	
	private boolean eStopTriggered;
	
	private TeleopDriveTrainController teleopDriveTrainController;
	private TeleopForkliftController teleopForkliftController;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	this.prefs = Preferences.getInstance();
    	this.logger = new Logger(
    			
    			//prefs for logger
    			"/home/lvuser/logs/log.txt");
    	
    	this.driveGamepad = new F310Gamepad(1);
    	this.forkliftJoystick = new Joystick(2);
    	
    	this.driveTrain = new DriveTrain( 
    		new TalonMotorController(0),
    		new TalonMotorController(1),
    		new TalonMotorController(2),
    		new TalonMotorController(3),
    		0, 1, 2, 3);

    	this.forklift = new Forklift(
    			new TalonMotorController(4), 
    			0, 
    			4, // top switch
    			this.prefs.getInt("topSwitchCounterSensitivity", 4),
    			5, // bottom switch
    			this.prefs.getInt("bottomSwitchCounterSensitivity", 4));
    	
    	this.eStopTriggered = false;
    	
    	//checks and runs the start up text for logger
    	//this.logger.logInit();
    }

    public void autonomousInit() {
    	this.autoDriveTrainController = new AutoDriveTrainController(
    			this.driveTrain);
    	
    	this.autoDriveTrainController.setMotionProfile(
    			this.prefs.getInt("autoDelay", 0), 
    			this.prefs.getInt("autoDriveTime", 0), 
    			this.prefs.getDouble("autoVelocity", 0));
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	this.autoDriveTrainController.updateDriveTrainState();
    	
    	this.driveTrain.update();
    	
    	//this.logger.profileDriveTrain(this.driveTrain);
    }
    
    public void teleopInit() {
    	this.teleopDriveTrainController = new TeleopDriveTrainController(
        		this.driveGamepad, 
        		this.driveTrain, 
        		this.prefs.getDouble("maxTurnRadius", Constants.defaultMaxTurnRadius),
        		this.prefs.getBoolean("reverseTurningFlipped", true),
        		this.prefs.getDouble("smoothDriveAccTime", Constants.defaultAccelerationTime),
        		this.prefs.getDouble("lowThrottleMultiplier", .25),
        		this.prefs.getDouble("maxThrottle", 1.0),
        		this.prefs.getBoolean("usingStraightPID", false),
        		this.prefs.getDouble("straightPIDKp", 0.5),
        		this.prefs.getDouble("straightPIDKd", 0.5));
    	
    	this.teleopForkliftController = new TeleopForkliftController(
    			this.forkliftJoystick, 
    			this.forklift,
    			this.prefs.getDouble("maxPowerPercent", 0.4));
    }
     
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	if (this.forkliftJoystick.getRawButton(11)) {
    		this.eStopTriggered = true;
    	}
    	
    	if (!this.eStopTriggered) {
	        this.teleopDriveTrainController.updateDriveTrainState();
	        this.teleopForkliftController.updateForkliftState();
    	} else {
    		this.driveTrain.setSpeed(0.0, 0.0);
    		this.forklift.setElevatorPower(0.0);
    	}
    	
    	this.driveTrain.update();
        this.forklift.update();
        
        this.logger.profileDriveTrain(this.driveTrain);
    }
}
