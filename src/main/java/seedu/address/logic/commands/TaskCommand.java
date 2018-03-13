package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

//@@author Kyomian
/**
 * Adds a task to CLIndar
 */
public class TaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the CLIndar. "
            + "Parameters: "
            + "NAME "
            + "DATETIME "
            + "REMARK\n"
            + "EXAMPLE: " + COMMAND_WORD + " "
            + "CS2103T "
            + "18-04-2018 "
            + "Update Developer Guide";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in CLIndar";

    private final Activity toAdd;

    public TaskCommand(Activity task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addActivity(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateActivityException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof AddCommand // instanceof handles nulls
//                && toAdd.equals(((AddCommand) other).toAdd));
//    }
}
