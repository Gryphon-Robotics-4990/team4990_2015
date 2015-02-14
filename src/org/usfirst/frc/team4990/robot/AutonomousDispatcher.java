package org.usfirst.frc.team4990.robot;

import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;

import org.usfirst.frc.team4990.robot.actions.Action;

public class AutonomousDispatcher {
	private HashMap<String, Action> currentActions;
	
	public AutonomousDispatcher() {
		this.currentActions = new HashMap<String, Action>();
	}
	
	public deserializeJSONString(String array) {
		Gson gson = new Gson();
		return gson.fromJson(array);
	}
}
