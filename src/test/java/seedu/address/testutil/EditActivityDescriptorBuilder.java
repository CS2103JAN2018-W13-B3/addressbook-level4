package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditActivityDescriptor;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditActivityDescriptor objects.
 */
public class EditActivityDescriptorBuilder {

    private EditActivityDescriptor descriptor;

    public EditActivityDescriptorBuilder() {
        descriptor = new EditActivityDescriptor();
    }

    public EditActivityDescriptorBuilder(EditCommand.EditActivityDescriptor descriptor) {
        this.descriptor = new EditActivityDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditActivityDescriptor} with fields containing {@code activity}'s details
     */
    public EditActivityDescriptorBuilder(Activity activity) {
        descriptor = new EditActivityDescriptor();
        descriptor.setName(activity.getName());
        descriptor.setDateTime(activity.getDateTime());
        descriptor.setRemark(activity.getRemark());
        descriptor.setTags(activity.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withPhone(String phone) {
        descriptor.setDateTime(new DateTime(phone));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withAddress(String address) {
        descriptor.setRemark(new Remark(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditActivityDescriptor}
     * that we are building.
     */
    public EditActivityDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditActivityDescriptor build() {
        return descriptor;
    }
}
