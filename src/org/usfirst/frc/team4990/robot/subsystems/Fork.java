package org.usfirst.frc.team4990.robot.subsystems;

public class Fork {
	private Solenoid solenoid;
	
	public Fork(int pcmChannelNum) {
		this.solenoid = new Solenoid(pcmChannelNum);
	}
	
	public void setForkState(boolean isOpen) {
		this.solenoid.set(isOpen);
	}
}
