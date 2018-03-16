package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.tag.Tag;

//@@author YuanQQLer
/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyDeskBoard {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Activity> getActivityList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
