package seedu.address.model.activity.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Activity objects.
 */
public class DuplicateActivityException extends DuplicateDataException {
    public DuplicateActivityException() {
        super("Operation would result in duplicate activities");
    }
}
