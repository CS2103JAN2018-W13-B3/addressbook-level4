package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;

/**
 * Represents a storage for {@link DeskBoard}.
 */
public interface DeskBoardStorage {

    /**
     * Returns the file path of the data file.
     */
    String getDeskBoardFilePath();

    /**
     * Returns DeskBoard data as a {@link ReadOnlyDeskBoard}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDeskBoard> readDeskBoard() throws DataConversionException, IOException;

    /**
     * @see #getDeskBoardFilePath()
     */
    Optional<ReadOnlyDeskBoard> readDeskBoard(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDeskBoard} to the storage.
     * @param deskBoard cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDeskBoard(ReadOnlyDeskBoard deskBoard) throws IOException;

    /**
     * @see #saveDeskBoard(ReadOnlyDeskBoard)
     */
    void saveDeskBoard(ReadOnlyDeskBoard deskBoard, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyDeskBoard} to the fixed temporary location.
     * @param deskBoard cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupDeskBoard(ReadOnlyDeskBoard deskBoard) throws IOException;
}
