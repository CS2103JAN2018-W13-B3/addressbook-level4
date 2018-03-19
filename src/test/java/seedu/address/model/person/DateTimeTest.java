package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.activity.DateTime;
import seedu.address.testutil.Assert;

public class DateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> DateTime.isValidDateAndTime(null));

        // invalid phone numbers
        assertFalse(DateTime.isValidDateAndTime("")); // empty string
        assertFalse(DateTime.isValidDateAndTime(" ")); // spaces only
        assertFalse(DateTime.isValidDateAndTime("91")); // less than 3 numbers
        assertFalse(DateTime.isValidDateAndTime("phone")); // non-numeric
        assertFalse(DateTime.isValidDateAndTime("9011p041")); // alphabets within digits
        assertFalse(DateTime.isValidDateAndTime("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(DateTime.isValidDateAndTime("01/08/1995 12:00")); // exactly 3 numbers
        assertTrue(DateTime.isValidDateAndTime("02/04/2018 10:00"));
        assertTrue(DateTime.isValidDateAndTime("03/03/2019 12:00")); // long phone numbers
    }
}
