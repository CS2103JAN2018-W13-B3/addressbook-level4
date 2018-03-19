package seedu.address.model.activity;

import java.util.Set;

import seedu.address.model.tag.Tag;

//@@author YuanQQLer
/**
 * Represents a Task in the desk board.
 * The field contains 3 field, name, due date and (Optional)remark.
 * The following example would illustrate one example
 * ******** Example ******************************* *
 * NAME : CS2103 Project
 * DUE DATE/TIME: 21/03/2018 23:59
 * REMARK: Submit through a pull request in git hub.
 * ************************************************ *
 */
public class Task extends Activity {

    private static final String  ACTIVITY_TYPE = "TASK";

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DateTime dueDateTime, Remark remark, Set<Tag> tags) {
        super(name, dueDateTime, remark, tags);


    }

    @Override
    public Name getName() {
        return super.getName();
    }

    public DateTime getDueDateTime() {
        return super.getDateTime();
    }

    @Override
    public Remark getRemark() {
        return super.getRemark();
    }

    @Override
    public String getActivityType() {
        return ACTIVITY_TYPE;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return super.getTags();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(this.getName())
                && otherTask.getDueDateTime().equals(this.getDueDateTime())
                && otherTask.getRemark().equals(this.getRemark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return super.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Task Name")
                .append(getName())
                .append(" Due Date/Time: ")
                .append(getDateTime())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
