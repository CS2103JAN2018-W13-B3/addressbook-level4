package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.CalendarChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CalendarStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getCalendarFilePath();

    @Override
    Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyCalendar addressBook) throws IOException;

    /**
     * Saves the current version of the Remark Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(CalendarChangedEvent abce);
}
