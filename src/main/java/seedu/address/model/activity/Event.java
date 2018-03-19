package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.model.tag.Tag;


//@@author YuanQQLer
/**
 * Represents a Task in the desk board.
 * The field contains 3 field, name, start date/time, end date/time and
 *      (Optional)location (Optional)remark.
 * The following example would illustrate one example
 * ******** Example ******************************* *
 * NAME : CS2103 Exam
 * START DATE/TIME: 21/03/2018 23:59
 * END DATE/TIME:
 * LOCATION: TBC
 * REMARK: Submit through a pull request in git hub.
 * ************************************************ *
 */
public class Event extends Activity {

    private static final String  ACTIVITY_TYPE = "EVENT";

    private final DateTime endDateTime;
    private final Location location;

    /**
     * Every field must be present and not null.
     */
    public Event(
            Name name, DateTime startDateTime, DateTime endDateTime, Location location, Remark remark, Set<Tag> tags) {
        super(name, startDateTime, remark, tags);
        requireAllNonNull(endDateTime);
        this.endDateTime = endDateTime;
        this.location = location;
    }

    @Override
    public Name getName() {
        return super.getName();
    }

    public DateTime getStartDateTime() {
        return super.getDateTime();
    }

    public DateTime getEndDateTime() {
        return this.endDateTime;
    }

    public Location getLocation() {
        return this.location;
    }
    @Override
    public Remark getRemark() {
        return super.getRemark();
    }

    @Override
    public String getActivityType() {
        return ACTIVITY_TYPE;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return super.getTags();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(this.getName())
                && otherEvent.getStartDateTime().equals(this.getStartDateTime())
                && otherEvent.getEndDateTime().equals(this.getEndDateTime())
                && otherEvent.getLocation().equals(this.getLocation())
                && otherEvent.getRemark().equals(this.getRemark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return super.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event Name")
                .append(getName())
                .append("Start Date/Time: ")
                .append(getDateTime())
                .append("End Date/Time")
                .append(getEndDateTime())
                .append("Venue")
                .append(getLocation())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

