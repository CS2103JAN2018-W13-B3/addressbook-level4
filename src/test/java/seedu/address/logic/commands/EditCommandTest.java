package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditActivityDescriptor;
import seedu.address.model.DeskBoard;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Task;
import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.EditActivityDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Activity editedActivity = new TaskBuilder().build();
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(editedActivity).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_ACTIVITY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new DeskBoard(model.getDeskBoard()), new UserPrefs());
        expectedModel.updateActivity(model.getFilteredActivityList().get(0), editedActivity);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredActivityList().size());
        Activity lastActivity = model.getFilteredActivityList().get(indexLastPerson.getZeroBased());

        TaskBuilder personInList = new TaskBuilder(lastActivity);
        Activity editedActivity = personInList.withName(VALID_NAME_BOB).withDateTime(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = prepareCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new DeskBoard(model.getDeskBoard()), new UserPrefs());
        expectedModel.updateActivity(lastActivity, editedActivity);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = prepareCommand(INDEX_FIRST_ACTIVITY, new EditActivityDescriptor());
        Activity editedActivity = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new DeskBoard(model.getDeskBoard()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_ACTIVITY);

        Activity activityInFilteredList = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        Activity editedActivity = new TaskBuilder(activityInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_ACTIVITY,
                new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new DeskBoard(model.getDeskBoard()), new UserPrefs());
        expectedModel.updateActivity(model.getFilteredActivityList().get(0), editedActivity);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Activity firstActivity = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(firstActivity).build();
        EditCommand editCommand = prepareCommand(INDEX_SECOND_ACTIVITY, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_ACTIVITY);

        // edit activity in filtered list into a duplicate in address book
        Activity activityInList = model.getDeskBoard().getActivityList().get(INDEX_SECOND_ACTIVITY.getZeroBased());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_ACTIVITY,
                new EditActivityDescriptorBuilder(activityInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_ACTIVITY);
        Index outOfBoundIndex = INDEX_SECOND_ACTIVITY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getDeskBoard().getActivityList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Activity editedActivity = new TaskBuilder().build();
        Activity activityToEdit = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(editedActivity).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_ACTIVITY, descriptor);
        Model expectedModel = new ModelManager(new DeskBoard(model.getDeskBoard()), new UserPrefs());

        // edit -> first activity edited
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered activity list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first activity edited again
        expectedModel.updateActivity(activityToEdit, editedActivity);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Activity} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited activity in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the activity object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Activity editedActivity = new TaskBuilder().build();
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder(editedActivity).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_ACTIVITY, descriptor);
        Model expectedModel = new ModelManager(new DeskBoard(model.getDeskBoard()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_ACTIVITY);
        Activity activityToEdit = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        // edit -> edits second activity in unfiltered activity list / first activity in filtered activity list
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered activity list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateActivity(activityToEdit, editedActivity);
        assertNotEquals(model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased()), activityToEdit);
        // redo -> edits same second activity in unfiltered activity list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditCommand standardCommand = prepareCommand(INDEX_FIRST_ACTIVITY, DESC_AMY);

        // same values -> returns true
        EditCommand.EditActivityDescriptor copyDescriptor = new EditActivityDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = prepareCommand(INDEX_FIRST_ACTIVITY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ACTIVITY, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ACTIVITY, DESC_BOB)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditCommand.EditActivityDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
