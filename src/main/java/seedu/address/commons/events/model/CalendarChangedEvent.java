package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCalendar;

/** Indicates the Calendar in the model has changed*/
public class CalendarChangedEvent extends BaseEvent {

    public final ReadOnlyCalendar data;

    public CalendarChangedEvent(ReadOnlyCalendar data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
