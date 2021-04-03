package com.team195.frc.constants;

public class DeviceIDConstants {
	// Do not change anything after this line unless you rewire the robot and
	// update the spreadsheet!
	// Port assignments should match up with the spreadsheet here:
	// https://docs.google.com/spreadsheets/d/179YszqnEWPWInuHUrYJnYL48LUL7LUhZrnvmNu1kujE/edit#gid=0

	/* I/O */
	// (Note that if multiple talons are dedicated to a mechanism, any sensors
	// are attached to the master)

	// Drive
	public static final int kRightDriveMasterId = 1;
	public static final int kRightDriveSlaveAId = 2;
	public static final int kRightDriveSlaveBId = 3;
	public static final int kLeftDriveMasterId = 4;
	public static final int kLeftDriveSlaveAId = 5;
	public static final int kLeftDriveSlaveBId = 6;

	//Climber
	public static final int kClimberWinchMotor = 11;
	public static final int kClimberArmMotor = 10;



	//Turret
	public static final int kTurretMotorId = 8;
	public static final int kHoodMotorId = 12;
	public static final int kLeftShooterMotorId = 13;
	public static final int kRightShooterMotorId = 14;

	//Intake Feeder
	public static final int kInnerIntakeMotorId = 9;
	public static final int kOuterIntakeMotorId = 7;


	// Solenoids
	public static final int kBallSolenoid = 0;
	public static final int kIntakePushSolenoid = 1;
	public static final int kClimberSolenoid = 2;

	public static final int kCANifierLEDId = 30;
	public static final int kNeoPixelPWMPort = 0;
}
