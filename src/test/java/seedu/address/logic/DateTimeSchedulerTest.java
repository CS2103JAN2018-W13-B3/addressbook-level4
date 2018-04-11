package seedu.address.logic;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

public class DateTimeSchedulerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void initialise_validModel_success() {
        ModelStub modelStub = new ModelStub();
        DateTimeScheduler.initialise(modelStub);
    }

    @Test
    public void initialise_nullModel_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        DateTimeScheduler.initialise(null);
    }

    /**
     * Methods in this Model Stub have no implementation.
     */
    private class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyDeskBoard newData) { }

        @Override
        public ReadOnlyDeskBoard getDeskBoard() {
            return null;
        }

        @Override
        public void deleteActivity(Activity target) throws ActivityNotFoundException {

        }

        @Override
        public void addActivity(Activity activity) throws DuplicateActivityException {

        }

        @Override
        public void addActivities(ReadOnlyDeskBoard deskBoard) {

        }

        @Override
        public void updateActivity(Activity target, Activity editedActivity)
                throws DuplicateActivityException, ActivityNotFoundException {

        }

        @Override
        public ObservableList<Activity> getFilteredActivityList() {
            return null;
        }

        @Override
        public ObservableList<Activity> getFilteredTaskList() {
            return null;
        }

        @Override
        public ObservableList<Activity> getFilteredEventList() {
            return null;
        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {

        }
    }
}
