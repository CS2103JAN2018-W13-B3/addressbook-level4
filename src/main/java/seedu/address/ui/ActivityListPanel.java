package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ActivityPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of persons.
 */
public class ActivityListPanel extends UiPart<Region> {
    private static final String FXML = "ActivityListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private ListView<ActivityCard> activityListView;

    public ActivityListPanel(ObservableList<Activity> activityList) {
        super(FXML);
        setConnections(activityList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Activity> activityList) {
        ObservableList<ActivityCard> mappedList = EasyBind.map(
                activityList, (activity) -> new ActivityCard(activity, activityList.indexOf(activity) + 1));
        activityListView.setItems(mappedList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        activityListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in activity list panel changed to : '" + newValue + "'");
                        raise(new ActivityPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ActivityCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            activityListView.scrollTo(index);
            activityListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ActivityCard}.
     */
    class ActivityListViewCell extends ListCell<ActivityCard> {

        @Override
        protected void updateItem(ActivityCard activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(activity.getRoot());
            }
        }
    }

}
