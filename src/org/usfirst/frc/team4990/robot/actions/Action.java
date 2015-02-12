package org.usfirst.frc.team4990.robot.actions;

import java.util.Date;
import java.util.List;

public abstract class Action {
	private List<Action> nextActions;
	private int delay;
	boolean actionStarted;
	
	public Action(List<Action> nextActions) {
		this.nextActions = nextActions;
		this.actionStarted = false;
	}
	
	public Action(List<Action> nextActions, int delay) {
		this.nextActions = nextActions;
		this.delay = delay;
		this.actionStarted = false;
	}
}
