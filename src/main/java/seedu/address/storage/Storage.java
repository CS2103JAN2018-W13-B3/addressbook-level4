package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.DeskBoardChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends DeskBoardStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getDeskBoardFilePath();

    @Override
    Optional<ReadOnlyDeskBoard> readDeskBoard() throws DataConversionException, IOException;

    @Override
    void saveDeskBoard(ReadOnlyDeskBoard deskBoard) throws IOException;

    /**
     * Saves the current version of the Remark Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleDeskBoardChangedEvent(DeskBoardChangedEvent dbce);
}
