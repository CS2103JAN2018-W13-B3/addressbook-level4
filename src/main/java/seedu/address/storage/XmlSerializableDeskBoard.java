package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;

/**
 * An Immutable DeskBoard that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableDeskBoard {

    @XmlElement
    private List<XmlAdaptedActivity> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableDeskBoard.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableDeskBoard() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableDeskBoard(ReadOnlyDeskBoard src) {
        this();
        persons.addAll(src.getActivityList().stream().map(XmlAdaptedActivity::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code DeskBoard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedActivity} or {@code XmlAdaptedTag}.
     */
    public DeskBoard toModelType() throws IllegalValueException {
        DeskBoard deskBoard = new DeskBoard();
        for (XmlAdaptedTag t : tags) {
            deskBoard.addTag(t.toModelType());
        }
        for (XmlAdaptedActivity p : persons) {
            deskBoard.addActivity(p.toModelType());
        }
        return deskBoard;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableDeskBoard)) {
            return false;
        }

        XmlSerializableDeskBoard otherAb = (XmlSerializableDeskBoard) other;
        return persons.equals(otherAb.persons) && tags.equals(otherAb.tags);
    }
}
