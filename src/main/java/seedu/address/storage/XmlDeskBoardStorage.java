package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyDeskBoard;

/**
 * A class to access DeskBoard data stored as an xml file on the hard disk.
 */
public class XmlDeskBoardStorage implements DeskBoardStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDeskBoardStorage.class);

    private String filePath;

    public XmlDeskBoardStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getDeskBoardFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDeskBoard> readDeskBoard() throws DataConversionException, IOException {
        return readDeskBoard(filePath);
    }

    /**
     * Similar to {@link #readDeskBoard()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDeskBoard> readDeskBoard(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File deskBoardFile = new File(filePath);

        if (!deskBoardFile.exists()) {
            logger.info("DeskBoard file "  + deskBoardFile + " not found");
            return Optional.empty();
        }

        XmlSerializableDeskBoard xmlDeskBoard = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlDeskBoard.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + deskBoardFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDeskBoard(ReadOnlyDeskBoard deskBoard) throws IOException {
        saveDeskBoard(deskBoard, filePath);
    }

    /**
     * Similar to {@link #saveDeskBoard(ReadOnlyDeskBoard)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveDeskBoard(ReadOnlyDeskBoard deskBoard, String filePath) throws IOException {
        requireNonNull(deskBoard);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableDeskBoard(deskBoard));
    }

    @Override
    public void backupDeskBoard(ReadOnlyDeskBoard deskBoard) throws IOException {
        saveDeskBoard(deskBoard, filePath + ".backup");
    }
}
