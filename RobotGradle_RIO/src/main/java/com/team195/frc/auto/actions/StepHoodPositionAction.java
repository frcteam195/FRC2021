package com.team195.frc.auto.actions;

import com.team195.frc.constants.CalConstants;
import com.team195.frc.subsystems.Turret;
import com.team195.frc.subsystems.positions.TurretPositions;
import com.team195.lib.util.TimeoutTimer;

public class StepHoodPositionAction implements Action {
	private static final Turret mTurret = Turret.getInstance();
	private final TimeoutTimer mTimeoutTimer = new TimeoutTimer(3);

	private double mPosition;

	public StepHoodPositionAction(boolean increment) {
		mPosition = mTurret.getHoodSetpoint() + (increment ? 5 : -5);
	}

	@Override
	public boolean isFinished() {
		return mTimeoutTimer.isTimedOut() || mTurret.isHoodAtSetpoint(TurretPositions.HoodPositionDelta);
	}

	@Override
	public void update() {
	}

	@Override
	public void done() {

	}

	@Override
	public void start() {
		mTurret.setHoodControlMode(Turret.HoodControlMode.POSITION);
		mTurret.setHoodPosition(Math.min(Math.max(mPosition, CalConstants.kTurretHoodMinDegrees), CalConstants.kTurretHoodMaxDegrees));
		mTimeoutTimer.reset();
	}
}
