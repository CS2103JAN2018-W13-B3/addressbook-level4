package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Location;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;


/**
 * This the class to build event
 */
public class EventBuilder implements ActivityBuilder {

    public static final String DEFAULT_NAME = "CIP";
    public static final String DEFAULT_START_DATETIME = "04/04/2018 08:10";
    public static final String DEFAULT_END_DATETIME = "04/04/2018 10:00";
    public static final String DEFAULT_LOCATION = "123, Jurong West Ave 6";
    public static final String DEFAULT_REMARK = "nil";
    public static final String DEFAULT_TAGS = "optional";

    private Name name;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Location location;
    private Remark remark;
    private Set<Tag> tags;

    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        startDateTime = new DateTime(DEFAULT_START_DATETIME);
        endDateTime = new DateTime(DEFAULT_END_DATETIME);
        location = new Location(DEFAULT_LOCATION);
        remark = new Remark(DEFAULT_REMARK);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the EventBuilder with the data of {@code activityToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        startDateTime = eventToCopy.getStartDateTime();
        endDateTime = eventToCopy.getEndDateTime();
        location = eventToCopy.getLocation();
        remark = eventToCopy.getRemark();
        tags = new HashSet<>(eventToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Activity} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Activity} that we are building.
     */
    public EventBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Activity} that we are building.
     */
    public EventBuilder withStartDateTime(String dateTime) {
        this.startDateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Activity} that we are building.
     */
    public EventBuilder withEndDateTime(String dateTime) {
        this.endDateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Activity} that we are building.
     */
    public EventBuilder withLocation (String location) {
        this.location = new Location(location);
        return this;
    }
    public Activity build() {
        return new Event(name, startDateTime, endDateTime, location, remark, tags);
    }

}
