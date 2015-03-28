package org.usfirst.frc.team4990.robot.controllers;

import org.usfirst.frc.team4990.robot.Constants;
import org.usfirst.frc.team4990.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4990.robot.subsystems.F310Gamepad;

import edu.wpi.first.wpilibj.Joystick;

import java.util.*;

public class TeleopDriveTrainController {
	private Joystick controller;
	private DriveTrain driveTrain;
	
	private boolean lastDpiToggleInput = false;
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
		boolean dpiTogglePressed = this.controller.getRawButton(2);
		
		if (dpiTogglePressed && !this.lastDpiToggleInput) {
			if (this.currentThrottleMultiplier == this.maxThrottle) {
				this.currentThrottleMultiplier = this.lowThrottle;
			} else {
				this.currentThrottleMultiplier = this.maxThrottle;
			}
		}
		
		this.lastDpiToggleInput = dpiTogglePressed;
		
		double throttleInput = -this.controller.getY();
		double turnSteepnessInput = this.controller.getX();
		
		double throttle = throttleInput * this.currentThrottleMultiplier;
		double turnSteepness = turnSteepnessInput;
		
		double leftWheelSpeed = throttle;
		double rightWheelSpeed = calculateInsideWheelSpeed(throttle, turnSteepness);
		
		if ((this.reverseTurningFlipped && throttle < 0 && turnSteepness > 0) || 
			(!this.reverseTurningFlipped && throttle < 0 && turnSteepness < 0) || 
			(throttle > 0 && turnSteepness < 0)) {
			leftWheelSpeed = calculateInsideWheelSpeed(throttle, turnSteepness);
			rightWheelSpeed = throttle;
			
		}
		
		this.driveTrain.setSpeed(leftWheelSpeed, rightWheelSpeed);
	}
	
	public double calculateInsideWheelSpeed(double throttle, double turnSteepness) {
		return throttle * (1 - turnSteepness);
	}
}
