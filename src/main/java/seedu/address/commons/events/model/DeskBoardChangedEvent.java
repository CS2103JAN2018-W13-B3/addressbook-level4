package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyDeskBoard;
//@@author YuanQQLer
/** Indicates the DeskBoard in the model has changed*/
public class DeskBoardChangedEvent extends BaseEvent {

    public final ReadOnlyDeskBoard data;

    public DeskBoardChangedEvent(ReadOnlyDeskBoard data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of activities " + data.getActivityList().size() + ", number of tags " + data.getTagList().size();
    }
}
