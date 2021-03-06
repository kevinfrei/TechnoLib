package org.firstinspires.ftc.teamcode.newcode.commands.lift;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.teamcode.newcode.subsystems.LiftSubsystem;

public class LiftToLastBrickHeightCommand extends Command {
    public LiftSubsystem subsystem;
    public int targetHeight;

    public LiftToLastBrickHeightCommand(LiftSubsystem s) {
        addRequirements(s);
        subsystem = s;
    }

    @Override
    public void init() {
        int temp = subsystem.lastPlacedBrickHeight + 1;
        targetHeight = (temp >= 0 && temp < subsystem.liftPositions.length) ?
                temp : subsystem.getHeight();
    }

    @Override
    public boolean isFinished() {
        return subsystem.goToHeight(targetHeight);
    }

    @Override
    public void end(boolean cancel) {
        subsystem.setHeightValue(targetHeight);
        subsystem.lastPlacedBrickHeight = targetHeight;
    }
}