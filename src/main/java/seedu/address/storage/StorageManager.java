package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CalendarChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Calendar data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CalendarStorage calendarStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CalendarStorage calendarStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.calendarStorage = calendarStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Calendar methods ==============================

    @Override
    public String getCalendarFilePath() {
        return calendarStorage.getCalendarFilePath();
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {
        return readCalendar(calendarStorage.getCalendarFilePath());
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return calendarStorage.readCalendar(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyCalendar addressBook) throws IOException {
        saveAddressBook(addressBook, calendarStorage.getCalendarFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyCalendar addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        calendarStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void backupAddressBook(ReadOnlyCalendar addressBook) throws IOException {
        calendarStorage.backupAddressBook(addressBook);
    }

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(CalendarChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
