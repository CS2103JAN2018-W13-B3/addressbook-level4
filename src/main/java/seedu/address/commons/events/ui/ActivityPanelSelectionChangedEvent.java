package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.ActivityCard;
//@@author YuanQQLer
/**
 * Represents a selection change in the Activity List Panel
 */
public class ActivityPanelSelectionChangedEvent extends BaseEvent {


    private final ActivityCard newSelection;

    public ActivityPanelSelectionChangedEvent(ActivityCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ActivityCard getNewSelection() {
        return newSelection;
    }
}
