//package seedu.address.logic.commands;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
//import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
//import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.CommandHistory;
//import seedu.address.logic.UndoRedoStack;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.activity.Activity;
//
///**
// * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
// * {@code DeleteCommand}.
// */
//public class DeleteCommandTest {
//
//    private Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() throws Exception {
//        Activity activityToDelete = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
//        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_ACTIVITY);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, activityToDelete);
//
//        ModelManager expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
//        expectedModel.deleteActivity(activityToDelete);
//
//        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
//        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_validIndexFilteredList_success() throws Exception {
//        showPersonAtIndex(model, INDEX_FIRST_ACTIVITY);
//
//        Activity activityToDelete = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
//        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_ACTIVITY);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, activityToDelete);
//
//        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
//        expectedModel.deleteActivity(activityToDelete);
//        showNoPerson(expectedModel);
//
//        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_throwsCommandException() {
//        showPersonAtIndex(model, INDEX_FIRST_ACTIVITY);
//
//        Index outOfBoundIndex = INDEX_SECOND_ACTIVITY;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getDeskBoard().getActivityList().size());
//
//        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
//        UndoRedoStack undoRedoStack = new UndoRedoStack();
//        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
//        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
//        Activity activityToDelete = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
//        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_ACTIVITY);
//        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
//
//        // delete -> first activity deleted
//        deleteCommand.execute();
//        undoRedoStack.push(deleteCommand);
//
//        // undo -> reverts addressbook back to previous state and filtered activity list to show all persons
//        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        // redo -> same first activity deleted again
//        expectedModel.deleteActivity(activityToDelete);
//        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
//        UndoRedoStack undoRedoStack = new UndoRedoStack();
//        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
//        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
//        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
//
//        // execution failed -> deleteCommand not pushed into undoRedoStack
//        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
//
//        // no commands in undoRedoStack -> undoCommand and redoCommand fail
//        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
//        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
//    }
//
//    /**
//     * 1. Deletes a {@code Activity} from a filtered list.
//     * 2. Undo the deletion.
//     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted activity in the
//     * unfiltered list is different from the index at the filtered list.
//     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the activity object regardless of indexing.
//     */
//    @Test
//    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
//        UndoRedoStack undoRedoStack = new UndoRedoStack();
//        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
//        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
//        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_ACTIVITY);
//        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
//
//        showPersonAtIndex(model, INDEX_SECOND_ACTIVITY);
//        Activity activityToDelete = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
//        // delete -> deletes second activity in unfiltered activity list / first activity in filtered activity list
//        deleteCommand.execute();
//        undoRedoStack.push(deleteCommand);
//
//        // undo -> reverts addressbook back to previous state and filtered activity list to show all persons
//        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        expectedModel.deleteActivity(activityToDelete);
//        assertNotEquals(activityToDelete, model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased()));
//        // redo -> deletes same second activity in unfiltered activity list
//        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void equals() throws Exception {
//        DeleteCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_ACTIVITY);
//        DeleteCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_ACTIVITY);
//
//        // same object -> returns true
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
//
//        // same values -> returns true
//        DeleteCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_ACTIVITY);
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
//
//        // one command preprocessed when previously equal -> returns false
//        deleteFirstCommandCopy.preprocessUndoableCommand();
//        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(deleteFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(deleteFirstCommand.equals(null));
//
//        // different activity -> returns false
//        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
//    }
//
//    /**
//     * Returns a {@code DeleteCommand} with the parameter {@code index}.
//     */
//    private DeleteCommand prepareCommand(Index index) {
//        DeleteCommand deleteCommand = new DeleteCommand(index);
//        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
//        return deleteCommand;
//    }
//
//    /**
//     * Updates {@code model}'s filtered list to show no one.
//     */
//    private void showNoPerson(Model model) {
//        model.updateFilteredActivityList(p -> false);
//
//        assertTrue(model.getFilteredActivityList().isEmpty());
//    }
//}
