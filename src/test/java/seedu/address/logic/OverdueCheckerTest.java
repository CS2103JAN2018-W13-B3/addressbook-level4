package seedu.address.logic;

import static junit.framework.TestCase.assertEquals;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.testutil.TypicalActivities;

public class OverdueCheckerTest {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    //TODO
    /**
     *
     */
    public void run_markingAsOverdue_correctNumOverdueTasks() throws InterruptedException {
        ModelStub modelStub = new ModelStub();
        OverdueChecker checker = new OverdueChecker(modelStub);

        executor.schedule(checker, 0, TimeUnit.SECONDS);
        waitForRunLater();
        assertEquals(OverdueChecker.getNumOverdueTasks(), 3);
    }

    private void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(() -> semaphore.release());
        semaphore.acquire();
    }

    /**
     * Model stub
     */
    private class ModelStub implements Model {

        @Override
        public void resetData(ReadOnlyDeskBoard newData) {

        }

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
            return TypicalActivities.getTypicalTasks();
        }

        @Override
        public ObservableList<Activity> getFilteredEventList() {
            return TypicalActivities.getTypicalEvents();
        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {

        }
    }
}
