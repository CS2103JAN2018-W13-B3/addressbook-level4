package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.TaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Kyomian
/**
 * Parses user input.
 */
public class DeskBoardParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case TaskCommand.COMMAND_WORD:
            return new TaskCommandParser().parse(arguments);


        case CompleteCommand.COMMAND_WORD:
            return new CompleteCommandParser().parse(arguments);

        case EventCommand.COMMAND_WORD:
            return new EventCommandParser().parse(arguments);


        //case EditCommand.COMMAND_WORD:
            //return new EditCommandParser().parse(arguments);

        //case SelectCommand.COMMAND_WORD:
            //return new SelectCommandParser().parse(arguments);
        case DeleteCommand.ALIAS:
            // Fallthrough
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        //case FindCommand.COMMAND_WORD:
            //return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ListCommand.COMMAND_ALIAS:
            return new ListCommandParser().parse(arguments);

        //case HistoryCommand.COMMAND_WORD:
            //return new HistoryCommand();

        //case ExitCommand.COMMAND_WORD:
            //return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommandParser().parse(arguments);

        //case UndoCommand.COMMAND_WORD:
            //return new UndoCommand();

        //case RedoCommand.COMMAND_WORD:
            //return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}