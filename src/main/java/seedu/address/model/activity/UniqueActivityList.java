package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;


/**
 * A list of activities, tasks and/or events that enforces uniqueness between its elements and
 * does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Activity#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueActivityList implements Iterable<Activity> {

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();
    //@@jasmoon
    private final ObservableList<Event> eventList = FXCollections.observableArrayList();
    private final ObservableList<Task> taskList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent activity as the given argument.
     */
    public boolean contains(Activity toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a activity to the list.
     * If activity is a task or an event, is added to its respective list.
     *
     * @throws DuplicateActivityException if the activity to add is a duplicate of an existing activity in the list.
     */
    public void add(Activity toAdd) throws DuplicateActivityException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        internalList.add(toAdd);
        //@@jasmoon
        if(toAdd.getActivityType().equals("TASK"))
            taskList.add((Task) toAdd);
        else eventList.add((Event) toAdd);
    }

    /**
     * Replaces the activity {@code target} in the list with {@code editedActivity}.
     * If activity is a task or an event, edited in its respective list.
     *
     * @throws DuplicateActivityException if the replacement is equivalent to another existing activity in the list.
     * @throws ActivityNotFoundException if {@code target} could not be found in the list.
     */
    public void setActivity(Activity target, Activity editedActivity)
            throws DuplicateActivityException, ActivityNotFoundException {
        requireNonNull(editedActivity);

        int index = internalList.indexOf(target), taskIndex, eventIndex;

        if (index == -1) {
            throw new ActivityNotFoundException();
        }

        if (!target.equals(editedActivity) && internalList.contains(editedActivity)) {
            throw new DuplicateActivityException();
        }

        internalList.set(index, editedActivity);
        //@@author jasmoon

        if(editedActivity.getActivityType().equals("TASK")) {
            taskIndex = taskList.indexOf(target);
            taskList.set(taskIndex, (Task) editedActivity);
        } else {
            eventIndex = eventList.indexOf(target);
            eventList.set(eventIndex, (Event) editedActivity);
        }

    }

    public void setActivity(UniqueActivityList replacement) {
        this.internalList.setAll(replacement.internalList);
        //@@author jasmoon
        this.eventList.setAll(replacement.eventList);
        this.taskList.setAll(replacement.taskList);
    }

    public void setActivity(List<Activity> activities) throws DuplicateActivityException {
        requireAllNonNull(activities);
        final UniqueActivityList replacement = new UniqueActivityList();
        for (final Activity activity : activities) {
            replacement.add(activity);
            //@@author jasmoon
            if(activity.getActivityType().equals("TASK"))
                replacement.taskList.add((Task) activity);
            else replacement.eventList.add((Event) activity);
        }
        setActivity(replacement);
    }

    /**
     * Removes the equivalent activity from the list and its respective task or event list.
     *
     * @throws ActivityNotFoundException if no such activity could be found in the list.
     */
    public boolean remove(Activity toRemove) throws ActivityNotFoundException {
        requireNonNull(toRemove);
        final boolean activityFoundAndDeleted = internalList.remove(toRemove);
        if (!activityFoundAndDeleted) {
            throw new ActivityNotFoundException();
            //@@author jasmoon
        } else  {
            if(toRemove.getActivityType().equals("TASK"))
                taskList.remove(toRemove);
            else    eventList.remove(toRemove);
        }
        return activityFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Activity> internalListAsObservable() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    //@@author jasmoon
    /**
     * @return the backing taskList as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> taskListAsObservable() {
        return FXCollections.unmodifiableObservableList(taskList);
    }

    /**
     * @return eventList as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> EventListAsObservable() {
        return FXCollections.unmodifiableObservableList(eventList);
    }

    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                        && this.internalList.equals(((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
