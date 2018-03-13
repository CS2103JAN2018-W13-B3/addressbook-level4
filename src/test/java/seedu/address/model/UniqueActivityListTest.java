package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.activity.UniqueActivityList;

public class UniqueActivityListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueActivityList uniqueActivityList = new UniqueActivityList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueActivityList.asObservableList().remove(0);
    }
}
