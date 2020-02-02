package com.team195.frc.auto.actions;

import com.team195.frc.constants.AutoConstants;
import com.team195.frc.subsystems.BallIntakeArm;
import com.team195.lib.util.TimeoutTimer;

public class SetBallIntakeAction implements Action {
	private static final BallIntakeArm mBallIntakeArm = BallIntakeArm.getInstance();
	private final TimeoutTimer mTimeoutTimer = new TimeoutTimer(AutoConstants.kDefaultRollerSpinUpWait);

	private double mOutputSpeed;

	public SetBallIntakeAction(double outputSpeed) {
		mOutputSpeed = outputSpeed;
	}

	@Override
	public boolean isFinished() {
		return mTimeoutTimer.isTimedOut();
	}

	@Override
	public void update() {
	}

	@Override
	public void done() {

	}

	@Override
	public void start() {
		mBallIntakeArm.setBallIntakeRollerSpeed(mOutputSpeed);
		mTimeoutTimer.reset();
	}
}