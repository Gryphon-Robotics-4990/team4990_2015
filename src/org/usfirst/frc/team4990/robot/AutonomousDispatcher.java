package org.usfirst.frc.team4990.robot;

import java.util.HashMap;
import java.util.List;

import org.usfirst.frc.team4990.robot.actions.Action;

public class AutonomousDispatcher {
	private HashMap<String, Action> currentActions;
	
	public AutonomousDispatcher() {
		this.currentActions = new HashMap<String, Action>();
	}
	
	
}
