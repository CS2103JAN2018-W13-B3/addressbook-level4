package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Location;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.activity.Task;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.model.tag.Tag;

//@Author YuanQLLer
/**
 * Contains utility methods for populating {@code DeskBoard} with sample data.
 */
public class SampleDataUtil {
    public static Activity[] getSamplePersons() {
        return new Activity[] {
            new Task(new Name("Task 1"), new DateTime("01/01/2018 23:59"),
                    new Remark("Submit through ivle"), getTagSet("Completed")),
            new Task (new Name("Task 2"), new DateTime("02/02/2018 23:59"),
                    new Remark("Submit through ivle"), getTagSet("Completed")),
            new Task (new Name("Task 3"), new DateTime("03/03/2018 23:59"),
                    new Remark("Submit through ivle"), getTagSet("Completed")),
            new Task (new Name("Task 4"), new DateTime("04/04/2018 23:59"),
                    new Remark("Submit through ivle"), getTagSet("Uncompleted")),
            new Task (new Name("Task 5"), new DateTime("05/05/2018 23:59"),
                    new Remark("Submit through ivle"), getTagSet("Unompleted")),
            new Task (new Name("Task 6"), new DateTime("06/06/2018 23:59"),
                    new Remark("Submit through ivle"), getTagSet("Completed")),
            new Event(new Name("Event 1"), new DateTime("01/01/2018 07:00"),
                    new DateTime("01/01/2018 08:00"), new Location("TBC"), new Remark("Remark"),
                    getTagSet("Finished")),
            new Event (new Name("Event 2"), new DateTime("02/02/2018 07:00"),
                    new DateTime("02/02/2018 08:00"), new Location("TBC"), new Remark("Remark"),
                    getTagSet("Finished")),
            new Event (new Name("Event 3"), new DateTime("03/03/2018 07:00"),
                    new DateTime("03/03/2018 08:00"), new Location("TBC"), new Remark("Remark"),
                    getTagSet("Cancelled")),
            new Event (new Name("Event 4"), new DateTime("04/04/2018 07:00"),
                    new DateTime("04/04/2018 08:00"), new Location("TBC"), new Remark("Remark"),
                    getTagSet("Important")),
            new Event (new Name("Event 5"), new DateTime("05/05/2018 07:00"),
                    new DateTime("05/05/2018 08:00"), new Location("TBC"), new Remark("Remark"),
                    getTagSet("Important")),
            new Event (new Name("Event 6"), new DateTime("06/06/2018 07:00"),
                    new DateTime("06/06/2018 08:00"), new Location("TBC"), new Remark("Remark"),
                    getTagSet("Compulsory"))
        };
    }

    public static ReadOnlyDeskBoard getSampleDeskBoard() {
        try {
            DeskBoard sampleAb = new DeskBoard();
            for (Activity sampleActivity : getSamplePersons()) {
                sampleAb.addActivity(sampleActivity);
            }
            return sampleAb;
        } catch (DuplicateActivityException e) {
            throw new AssertionError("sample data cannot contain duplicate activities", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String ... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
