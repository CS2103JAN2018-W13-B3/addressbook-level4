package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author YuanQQLer
/**
 * This clas is to store location info in an event
 */
public class Location {
    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Activity location should not be blank, or start with white space";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param location A valid location.
     */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidName(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid activity name.
     */
    public static boolean isValidName(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.value.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
