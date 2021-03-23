package com.technototes.library.command;


import com.qualcomm.robotcore.util.ElapsedTime;
import com.technototes.library.subsystem.Subsystem;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/** The root Command class
 * @author Alex Stedman
 */
public class Command implements Runnable {

    protected ElapsedTime commandRuntime;
    protected CommandState commandState;
    protected Set<Subsystem> requirements;

    /** Make a Command
     *
     */
    public Command() {
        commandState = CommandState.RESET;
        commandRuntime = new ElapsedTime();
        requirements = new HashSet<>();
        commandRuntime.reset();
    }

    /** Add requirement subsystems to command
     *
     * @param requirements The subsystems
     * @return this
     */
    public final Command addRequirements(Subsystem... requirements) {
        this.requirements.addAll(Arrays.asList(requirements));
        return this;
    }

    /** Init the command
     *
     */
    public void init() {

    }

    /** Execute the command
     *
     */
    public void execute() {

    }

    /** Return if the command is finished
     *
     * @return Is command finished
     */
    public boolean isFinished() {
        return true;
    }

    /** End the command
     *
     * @param cancel If the command was cancelled or ended naturally
     */
    public void end(boolean cancel) {

    }

    //run a command after
    public final Command then(Command c){
        CommandScheduler.getInstance().scheduleAfterOther(this, c);
        return c;
    }
    //wait a time
    public final Command wait(double sec){
        return then(new WaitCommand(sec));
    }
    public final Command wait(DoubleSupplier sup){
        return then(new WaitCommand(sup));
    }

    //await a condition
    public final Command await(BooleanSupplier condition) {
        return then(new ConditionalCommand(condition));
    }

    //run a command in parallel
    public final Command with(Command c){
        CommandScheduler.getInstance().scheduleWithOther(this, c);
        return c;
    }

    /** Run the commmand
     *
     */
    @Override
    public void run() {
        switch (commandState) {
            case RESET:
                commandRuntime.reset();
                commandState = CommandState.INITILAIZING;
                return;
            case INITILAIZING:
                init();
                commandState = CommandState.EXECUTING;
                //THERE IS NO RETURN HERE SO IT FALLS THROUGH TO POST-INITIALIZATION
            case EXECUTING:
                execute();
                commandState = isFinished() ? CommandState.FINISHED : CommandState.EXECUTING;
                if (isFinished()) end(false);
                //allow one cycle to run so other dependent commands can schedule
                return;
            case FINISHED:
                commandState = CommandState.RESET;
                return;
        }
    }

    /** The command state enum
     *
     */
    public enum CommandState {
        RESET, INITILAIZING, EXECUTING, FINISHED
    }


    /** Return the command runtime
     *
     * @return The runtime as an {@link ElapsedTime}
     */
    public ElapsedTime getRuntime() {
        return commandRuntime;
    }

    /** Return the command state
     *
     * @return The state as an {@link CommandState}
     */
    public CommandState getState() {
        return commandState;
    }

    /** Return the subsystem requirements for this command
     *
     * @return The {@link Subsystem} requirements
     */
    public Set<Subsystem> getRequirements() {
        return requirements;
    }

    /** Creates a conditional command out of this
     *
     * @param condition The condition to run the command under
     * @return the conditional command
     */
    public ConditionalCommand asConditional(BooleanSupplier condition){
        return new ConditionalCommand(condition, this);
    }


    public final boolean justFinished(){
        return commandState == CommandState.FINISHED;
    }
    public final boolean justStarted() {
        return commandState == CommandState.INITILAIZING;
    }

}
