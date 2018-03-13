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
import seedu.address.model.ReadOnlyCalendar;

/**
 * A class to access Calendar data stored as an xml file on the hard disk.
 */
public class XmlCalendarStorage implements CalendarStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCalendarStorage.class);

    private String filePath;

    public XmlCalendarStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getCalendarFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {
        return readCalendar(filePath);
    }

    /**
     * Similar to {@link #readCalendar()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCalendar> readCalendar(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("Calendar file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        XmlSerializableCalendar xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyCalendar addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyCalendar)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyCalendar addressBook, String filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableCalendar(addressBook));
    }

    @Override
    public void backupAddressBook(ReadOnlyCalendar addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + ".backup");
    }
}
