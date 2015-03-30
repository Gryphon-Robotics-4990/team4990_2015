package org.usfirst.frc.team4990.robot.controllers;

import org.usfirst.frc.team4990.robot.Constants;
import org.usfirst.frc.team4990.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4990.robot.subsystems.F310Gamepad;

import edu.wpi.first.wpilibj.Joystick;

import java.util.*;

public class TeleopDriveTrainController {
	private Joystick controller;
	private DriveTrain driveTrain;
	
	private double currentThrottleMultiplier;
	
	private final boolean reverseTurningFlipped;
	private final double lowThrottle;
	private final double maxThrottle;
	
	public TeleopDriveTrainController(
			Joystick controller, 
			DriveTrain driveTrain, 
			boolean reverseTurningFlipped,
			double lowThrottle,
			double maxThrottle) {
		this.controller = controller;
		this.driveTrain = driveTrain;
		
		this.currentThrottleMultiplier = maxThrottle;
		this.reverseTurningFlipped = reverseTurningFlipped;
		this.lowThrottle = lowThrottle;
		this.maxThrottle = maxThrottle;
	}
	
	public void updateDriveTrainState() {
		if (this.controller.getRawButton(3)) {
			this.currentThrottleMultiplier = this.maxThrottle;
		} else if (this.controller.getRawButton(2)) {
			this.currentThrottleMultiplier = this.lowThrottle;
		}
		
		double xInput = -this.controller.getY();
		double yInput = this.controller.getX();
		
		double leftWheelSpeed = xInput + yInput;
		double rightWheelSpeed = xInput - yInput;
		
		if ((this.reverseTurningFlipped && xInput < 0 && yInput > 0) || 
			(!this.reverseTurningFlipped && xInput < 0 && yInput < 0) || 
			(xInput > 0 && yInput < 0)) {
			leftWheelSpeed = xInput - yInput;
			rightWheelSpeed = xInput + yInput;
		}
		
		xInput *= this.currentThrottleMultiplier;
		yInput *= this.currentThrottleMultiplier;
		
		this.driveTrain.setSpeed(leftWheelSpeed, rightWheelSpeed);
	}
}
