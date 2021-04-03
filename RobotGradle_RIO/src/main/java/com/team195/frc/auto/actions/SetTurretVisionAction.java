package com.team195.frc.auto.actions;

import com.team195.frc.subsystems.Turret;
import com.team195.frc.subsystems.VisionTracker;
import com.team195.frc.subsystems.positions.TurretPositions;
import com.team195.lib.util.TimeoutTimer;

public class SetTurretVisionAction implements Action {
	private static final Turret mTurret = Turret.getInstance();

	private boolean mEnabled;

	public SetTurretVisionAction(boolean enabled) {
		mEnabled = enabled;
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
		if (mEnabled) {
			VisionTracker.getInstance().setVisionEnabled(true);
			mTurret.setTurretControlMode(Turret.TurretControlMode.VISION_TRACK);
		}
		else {
			VisionTracker.getInstance().setVisionEnabled(false);
			mTurret.setTurretControlMode(Turret.TurretControlMode.POSITION);
		}
	}
}
