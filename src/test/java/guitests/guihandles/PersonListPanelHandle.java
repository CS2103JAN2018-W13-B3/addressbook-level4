package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.activity.Activity;
import seedu.address.ui.ActivityCard;

/**
 * Provides a handle for {@code ActivityListPanel} containing the list of {@code ActivityCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<ActivityCard>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    private Optional<ActivityCard> lastRememberedSelectedPersonCard;

    public PersonListPanelHandle(ListView<ActivityCard> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public PersonCardHandle getHandleToSelectedCard() {
        List<ActivityCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() != 1) {
            throw new AssertionError("Activity list size expected 1.");
        }

        return new PersonCardHandle(personList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<ActivityCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the activity.
     */
    public void navigateToCard(Activity activity) {
        List<ActivityCard> cards = getRootNode().getItems();
        Optional<ActivityCard> matchingCard = cards.stream().filter(card -> card.activity.equals(activity)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Activity does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the activity card handle of a activity associated with the {@code index} in the list.
     */
    public PersonCardHandle getPersonCardHandle(int index) {
        return getPersonCardHandle(getRootNode().getItems().get(index).activity);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code activity} in the list.
     */
    public PersonCardHandle getPersonCardHandle(Activity activity) {
        Optional<PersonCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.activity.equals(activity))
                .map(card -> new PersonCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Activity does not exist."));
    }

    /**
     * Selects the {@code ActivityCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code ActivityCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<ActivityCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPersonCard = Optional.empty();
        } else {
            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ActivityCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<ActivityCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPersonCard.isPresent();
        } else {
            return !lastRememberedSelectedPersonCard.isPresent()
                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
