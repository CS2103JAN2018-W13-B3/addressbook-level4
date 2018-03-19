//package seedu.address.logic.commands;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
//import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
//
//import org.junit.Test;
//
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.UserPrefs;
//import seedu.address.model.activity.Activity;
//import seedu.address.model.activity.exceptions.ActivityNotFoundException;
//
//public class UndoableCommandTest {
//    private final Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
//    private final DummyCommand dummyCommand = new DummyCommand(model);
//
//    private Model expectedModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
//
//    @Test
//    public void executeUndo() throws Exception {
//        dummyCommand.execute();
//        deleteFirstPerson(expectedModel);
//        assertEquals(expectedModel, model);
//
//        showPersonAtIndex(model, INDEX_FIRST_ACTIVITY);
//
//        // undo() should cause the model's filtered list to show all persons
//        dummyCommand.undo();
//        expectedModel = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
//        assertEquals(expectedModel, model);
//    }
//
//    @Test
//    public void redo() {
//        showPersonAtIndex(model, INDEX_FIRST_ACTIVITY);
//
//        // redo() should cause the model's filtered list to show all persons
//        dummyCommand.redo();
//        deleteFirstPerson(expectedModel);
//        assertEquals(expectedModel, model);
//    }
//
//    /**
//     * Deletes the first activity in the model's filtered list.
//     */
//    class DummyCommand extends UndoableCommand {
//        DummyCommand(Model model) {
//            this.model = model;
//        }
//
//        @Override
//        public CommandResult executeUndoableCommand() throws CommandException {
//            Activity activityToDelete = model.getFilteredActivityList().get(0);
//            try {
//                model.deleteActivity(activityToDelete);
//            } catch (ActivityNotFoundException pnfe) {
//                fail("Impossible: activityToDelete was retrieved from model.");
//            }
//            return new CommandResult("");
//        }
//    }
//}
