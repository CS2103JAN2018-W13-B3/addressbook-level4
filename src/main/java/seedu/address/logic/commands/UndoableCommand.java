package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ACTIVITY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyDeskBoard previousDeskBoard;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveDeskBoardSnapshot() {
        requireNonNull(model);
        this.previousDeskBoard = new DeskBoard(model.getDeskBoard());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the DeskBoard to the state before this command
     * was executed and updates the filtered activity list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousDeskBoard);
        model.resetData(previousDeskBoard);
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITY);
    }

    /**
     * Executes the command and updates the filtered activity
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITY);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveDeskBoardSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
