package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyCalendar;

/**
 * An Immutable Calendar that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableCalendar {

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableCalendar.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCalendar() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCalendar(ReadOnlyCalendar src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code Calendar} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public Calendar toModelType() throws IllegalValueException {
        Calendar addressBook = new Calendar();
        for (XmlAdaptedTag t : tags) {
            addressBook.addTag(t.toModelType());
        }
        for (XmlAdaptedPerson p : persons) {
            addressBook.addActivity(p.toModelType());
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCalendar)) {
            return false;
        }

        XmlSerializableCalendar otherAb = (XmlSerializableCalendar) other;
        return persons.equals(otherAb.persons) && tags.equals(otherAb.tags);
    }
}
