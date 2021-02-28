package com.team195.frc.subsystems;

import com.team195.frc.constants.CalConstants;
import com.team195.frc.constants.DeviceIDConstants;
import com.team195.frc.loops.ILooper;
import com.team195.frc.loops.Loop;
import com.team195.frc.reporters.ReflectingLogDataGenerator;
import com.team195.lib.drivers.motorcontrol.*;
import com.team195.lib.util.*;
import edu.wpi.first.wpilibj.Solenoid;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class Intake extends Subsystem {

	private static Intake mInstance = new Intake();

	private final CKTalonFX mOuterIntakeMotor;
	private final CKTalonFX mInnerIntakeMotor;

	private IntakeControlMode mIntakeControlMode = IntakeControlMode.OFF;
	private FeederControlMode mFeederControlMode = FeederControlMode.OFF;

	private final Solenoid mIntakePushSol;
	private final Solenoid mBallSol;

	private PeriodicIO mPeriodicIO;
	private ReflectingLogDataGenerator<PeriodicIO> mLogDataGenerator = new ReflectingLogDataGenerator<>(PeriodicIO.class);

	private final ElapsedTimer loopTimer = new ElapsedTimer();

	private Intake() {
		mPeriodicIO = new PeriodicIO();

		mOuterIntakeMotor = new CKTalonFX(DeviceIDConstants.kOuterIntakeMotorId, false, PDPBreaker.B30A);
		mOuterIntakeMotor.setInverted(true);
		mOuterIntakeMotor.configCurrentLimit(CalConstants.kIntakeContinuousCurrentLimit, CalConstants.kIntakePeakCurrentThreshold, CalConstants.kIntakePeakCurrentThresholdExceedDuration);

		mInnerIntakeMotor = new CKTalonFX(DeviceIDConstants.kInnerIntakeMotorId, false, PDPBreaker.B30A);
		mInnerIntakeMotor.setInverted(false);
		mInnerIntakeMotor.configCurrentLimit(CalConstants.kFeederContinuousCurrentLimit, CalConstants.kFeederPeakCurrentThreshold, CalConstants.kFeederPeakCurrentThresholdExceedDuration);

		mBallSol = new Solenoid(DeviceIDConstants.kBallSolenoid);
		mIntakePushSol = new Solenoid(DeviceIDConstants.kIntakePushSolenoid);

		zeroSensors();
	}

	public static Intake getInstance() {
		return mInstance;
	}

	@Override
	public void stop() {
		mOuterIntakeMotor.set(MCControlMode.PercentOut, 0, 0, 0);
		mInnerIntakeMotor.set(MCControlMode.PercentOut, 0, 0, 0);
	}

	@Override
	public synchronized boolean isSystemFaulted() {
		return false;
	}

	@Override
	public boolean runDiagnostics() {
		return false;
	}

	@Override
	public synchronized List<Object> generateReport() {
		loopTimer.start();
		mTmpHandle = mLogDataGenerator.generateData(mPeriodicIO);
		mPeriodicIO.intake_loop_time += loopTimer.hasElapsed();
		return mTmpHandle;
	}
	private List<Object> mTmpHandle;

	@Override
	public void zeroSensors() {

	}

	@Override
	public void registerEnabledLoops(ILooper in) {
		in.register(mLoop);
	}

	private final Loop mLoop = new Loop() {
		@Override
		public void onFirstStart(double timestamp) {
			synchronized (Intake.this) {
				zeroSensors();
			}
		}

		@Override
		public void onStart(double timestamp) {
			synchronized (Intake.this) {

			}
		}

		@SuppressWarnings("Duplicates")
		@Override
		public void onLoop(double timestamp) {
			loopTimer.start();
			synchronized (Intake.this) {
				switch (mIntakeControlMode) {
					case FORWARD:
						mOuterIntakeMotor.set(MCControlMode.PercentOut, 1, 0, 0);
						break;
					case REVERSE:
						mOuterIntakeMotor.set(MCControlMode.PercentOut, -1, 0, 0);
						break;
					case OFF:
						mOuterIntakeMotor.set(MCControlMode.Disabled, 0, 0, 0);
						break;
				}
			}

			switch (mFeederControlMode) {
				case FORWARD:
					mInnerIntakeMotor.set(MCControlMode.PercentOut, 1, 0, 0);
					break;
				case REVERSE:
					mInnerIntakeMotor.set(MCControlMode.PercentOut, -1, 0, 0);
					break;
				case OFF:
					mInnerIntakeMotor.set(MCControlMode.Disabled, 0, 0, 0);
					break;
			}

			mPeriodicIO.intake_loop_time += loopTimer.hasElapsed();
		}

		@Override
		public void onStop(double timestamp) {
			stop();
		}

		@Override
		public String getName() {
			return "Intake";
		}
	};

	public synchronized void setPushSolenoid(boolean on) {
		mIntakePushSol.set(on);
	}

	public synchronized void setBallSolenoid(boolean on) {
		mBallSol.set(on);
	}

	public synchronized void setIntakeControlMode(IntakeControlMode intakeControlMode) {
		if (mIntakeControlMode != intakeControlMode)
			mIntakeControlMode = intakeControlMode;
	}

	public synchronized void setFeederControlMode(FeederControlMode feederControlMode) {
		if (mFeederControlMode != feederControlMode)
			mFeederControlMode = feederControlMode;
	}


	public enum IntakeControlMode {
		FORWARD,
		REVERSE,
		OFF
	}

	public enum FeederControlMode {
		FORWARD,
		REVERSE,
		OFF
	}

	@Override
	public synchronized void readPeriodicInputs() {
		loopTimer.start();
		mPeriodicIO.intake_loop_time = loopTimer.hasElapsed();
	}

	@Override
	public synchronized void writePeriodicOutputs() {
		loopTimer.start();
		mPeriodicIO.intake_loop_time += loopTimer.hasElapsed();
	}

	@SuppressWarnings("WeakerAccess")
	public static class PeriodicIO {
		// Outputs
		public double intake_loop_time;
	}
}