package seedu.addressbook.commands;


/**
 * Sorts the address book according to the names of the people.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the people according to their name\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book sorted.";

    @Override
    public CommandResult execute() {
        addressBook.sortByName();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
