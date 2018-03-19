package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.activity.Activity;
import seedu.address.testutil.TaskBuilder;

public class ActivityCardTest extends GuiUnitTest {

    //TODO: TEST
    /**
     * Test
     */
    public void display() {
        // no tags
        Activity activityWithNoTags = new TaskBuilder().withTags(new String[0]).build();
        ActivityCard activityCard = new ActivityCard(activityWithNoTags, 1);
        uiPartRule.setUiPart(activityCard);
        assertCardDisplay(activityCard, activityWithNoTags, 1);

        // with tags
        Activity activityWithTags = new TaskBuilder().build();
        activityCard = new ActivityCard(activityWithTags, 2);
        uiPartRule.setUiPart(activityCard);
        assertCardDisplay(activityCard, activityWithTags, 2);
    }

    @Test
    public void equals() {
        Activity activity = new TaskBuilder().build();
        ActivityCard activityCard = new ActivityCard(activity, 0);

        // same activity, same index -> returns true
        ActivityCard copy = new ActivityCard(activity, 0);
        assertTrue(activityCard.equals(copy));

        // same object -> returns true
        assertTrue(activityCard.equals(activityCard));

        // null -> returns false
        assertFalse(activityCard.equals(null));

        // different types -> returns false
        assertFalse(activityCard.equals(0));

        // different activity, same index -> returns false
        Activity differentActivity = new TaskBuilder().withName("differentName").build();
        assertFalse(activityCard.equals(new ActivityCard(differentActivity, 0)));

        // same activity, different index -> returns false
        assertFalse(activityCard.equals(new ActivityCard(activity, 1)));
    }

    /**
     * Asserts that {@code activityCard} displays the details of {@code expectedActivity} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ActivityCard activityCard, Activity expectedActivity, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(activityCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify activity details are displayed correctly
        assertCardDisplaysPerson(expectedActivity, personCardHandle);
    }
}
