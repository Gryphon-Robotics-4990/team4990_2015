package org.usfirst.frc.team4990.robot.actions;

import java.util.List;
//import java.org.json.JSONObject;

import org.usfirst.frc.team4990.robot.lib.MotionProfile;

public class DriveAction extends Action {
	private MotionProfile motionProfile;
	
	public DriveAction(List<Action> nextActions, double distanceToTravel, double velocity, double acceleration) {
		super(nextActions);
		this.motionProfile = new MotionProfile(distanceToTravel, velocity, acceleration);
	}

	public DriveAction(List<Action> nextActions, int delay, double distanceToTravel, double velocity, double acceleration) {
		super(nextActions, delay);
		this.motionProfile = new MotionProfile(distanceToTravel, velocity, acceleration);
	}
	
	/*public MotionProfile.ProfileValues next() {
		if (!this.actionStarted) {
			this.actionStarted = true;
		}
	}*/
}
