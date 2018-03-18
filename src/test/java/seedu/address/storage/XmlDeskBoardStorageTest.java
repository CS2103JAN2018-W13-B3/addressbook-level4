package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT1;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT3;
import static seedu.address.testutil.TypicalActivities.DEMO1;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;

public class XmlDeskBoardStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlDeskBoardStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyDeskBoard> readAddressBook(String filePath) throws Exception {
        return new XmlDeskBoardStorage(filePath).readDeskBoard(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        DeskBoard original = getTypicalDeskBoard();
        XmlDeskBoardStorage xmlAddressBookStorage = new XmlDeskBoardStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveDeskBoard(original, filePath);
        ReadOnlyDeskBoard readBack = xmlAddressBookStorage.readDeskBoard(filePath).get();
        assertEquals(original, new DeskBoard(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addActivity(ASSIGNMENT3);
        original.removeActivity(ASSIGNMENT1);
        xmlAddressBookStorage.saveDeskBoard(original, filePath);
        readBack = xmlAddressBookStorage.readDeskBoard(filePath).get();
        assertEquals(original, new DeskBoard(readBack));

        //Save and read without specifying file path
        original.addActivity(DEMO1);
        xmlAddressBookStorage.saveDeskBoard(original); //file path not specified
        readBack = xmlAddressBookStorage.readDeskBoard().get(); //file path not specified
        assertEquals(original, new DeskBoard(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyDeskBoard addressBook, String filePath) {
        try {
            new XmlDeskBoardStorage(filePath).saveDeskBoard(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new DeskBoard(), null);
    }


}
