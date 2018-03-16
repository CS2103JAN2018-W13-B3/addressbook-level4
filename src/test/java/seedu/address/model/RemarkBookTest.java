package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.tag.Tag;

public class RemarkBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DeskBoard addressBook = new DeskBoard();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getActivityList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        DeskBoard newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Activity> newActivities = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        DeskBoardStub newData = new DeskBoardStub(newActivities, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getActivityList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyDeskBoard whose activities and tags lists can violate interface constraints.
     */
    private static class DeskBoardStub implements ReadOnlyDeskBoard {
        private final ObservableList<Activity> activities = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        DeskBoardStub(Collection<Activity> activities, Collection<? extends Tag> tags) {
            this.activities.setAll(activities);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Activity> getActivityList() {
            return activities;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
