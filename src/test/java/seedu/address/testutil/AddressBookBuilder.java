package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Calendar;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Calendar ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private Calendar addressBook;

    public AddressBookBuilder() {
        addressBook = new Calendar();
    }

    public AddressBookBuilder(Calendar addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Activity} to the {@code Calendar} that we are building.
     */
    public AddressBookBuilder withPerson(Activity activity) {
        try {
            addressBook.addActivity(activity);
        } catch (DuplicateActivityException dpe) {
            throw new IllegalArgumentException("activity is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code Calendar} that we are building.
     */
    public AddressBookBuilder withTag(String tagName) {
        try {
            addressBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public Calendar build() {
        return addressBook;
    }
}
