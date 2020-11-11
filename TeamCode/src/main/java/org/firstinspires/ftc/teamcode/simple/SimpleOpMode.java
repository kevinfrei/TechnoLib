package org.firstinspires.ftc.teamcode.simple;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.InstantCommand;
import com.technototes.library.command.simple.TankDriveCommand;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.subsystem.simple.SimpleServoSubsystem;
import com.technototes.library.subsystem.simple.SimpleTankDrivebaseSubsystem;
@Disabled
@TeleOp(name = "Simple Teleop")
public class SimpleOpMode extends CommandOpMode {
    public SimpleTankDrivebaseSubsystem drivebaseSubsystem;
    public SimpleServoSubsystem gripperSubsystem;
    @Override
    public void uponInit() {
        drivebaseSubsystem = new SimpleTankDrivebaseSubsystem(new Motor<DcMotor>("leftMotor"), new Motor<DcMotor>("rightMotor"));;
        gripperSubsystem = new SimpleServoSubsystem(new Servo("gripper"));
        driverGamepad.a.whenActivated(new InstantCommand(() -> gripperSubsystem.setPosition(0)));
        driverGamepad.b.whenActivated(new InstantCommand(() -> gripperSubsystem.setPosition(1)));
        CommandScheduler.getInstance().schedule(new TankDriveCommand(drivebaseSubsystem, driverGamepad.leftStick));
    }
}
