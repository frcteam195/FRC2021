package com.team195.frc.auto.actions;

import com.team195.frc.subsystems.Intake;

public class SetBallSolenoidAction implements Action {
	private static final Intake mIntake = Intake.getInstance();

	private final boolean mOn;

	public SetBallSolenoidAction(boolean on) {
		mOn = on;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public void update() {

	}

	@Override
	public void done() {

	}

	@Override
	public void start() {
		mIntake.setBallSolenoid(mOn);
	}
}
