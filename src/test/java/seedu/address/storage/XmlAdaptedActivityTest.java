package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedActivity.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.testutil.Assert;

public class XmlAdaptedActivityTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = ASSIGNMENT2.getName().toString();
    private static final String VALID_PHONE = ASSIGNMENT2.getDateTime().toString();
    private static final String VALID_ADDRESS = ASSIGNMENT2.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = ASSIGNMENT2.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedActivity person = new XmlAdaptedActivity(ASSIGNMENT2);
        assertEquals(ASSIGNMENT2, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedActivity person =
                new XmlAdaptedActivity(INVALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedActivity person = new XmlAdaptedActivity(null, VALID_PHONE, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedActivity person =
                new XmlAdaptedActivity(VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedActivity person = new XmlAdaptedActivity(VALID_NAME, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedActivity person =
                new XmlAdaptedActivity(VALID_NAME, VALID_PHONE, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Remark.MESSAGE_REMARK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedActivity person = new XmlAdaptedActivity(VALID_NAME, VALID_PHONE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedActivity person =
                new XmlAdaptedActivity(VALID_NAME, VALID_PHONE, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
