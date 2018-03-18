package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code DeskBoard ab = new DeskBoardBuilder().withActivity("John", "Doe").withTag("Friend").build();}
 */
public class DeskBoardBuilder {

    private DeskBoard deskBoard;

    public DeskBoardBuilder() {
        deskBoard = new DeskBoard();
    }

    public DeskBoardBuilder(DeskBoard deskBoard) {
        this.deskBoard = deskBoard;
    }

    /**
     * Adds a new {@code Activity} to the {@code DeskBoard} that we are building.
     */
    public DeskBoardBuilder withActivity(Activity activity) {
        try {
            deskBoard.addActivity(activity);
        } catch (DuplicateActivityException dpe) {
            throw new IllegalArgumentException("activity is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code DeskBoard} that we are building.
     */
    public DeskBoardBuilder withTag(String tagName) {
        try {
            deskBoard.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public DeskBoard build() {
        return deskBoard;
    }
}
