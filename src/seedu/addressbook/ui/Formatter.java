package seedu.addressbook.ui;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.ArrayList;
import java.util.List;

import static seedu.addressbook.common.Messages.*;

public class Formatter {

    /** A decorative prefix added to the beginning of lines printed by AddressBook */
    private static final String LINE_PREFIX = "|| ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();

    private static final String DIVIDER = "===================================================";

    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";


    /** Offset required to convert between 1-indexing and 0-indexing.  */
    public static final int DISPLAYED_INDEX_OFFSET = 1;

    /** Format of a comment input line. Comment lines are silently consumed when reading user input. */
    private static final String COMMENT_LINE_FORMAT_REGEX = "#.*";


    /**
     * Returns true if the user input line should be ignored.
     * Input should be ignored if it is parsed as a comment, is only whitespace, or is empty.
     *
     * @param rawInputLine full raw user input line.
     * @return true if the entire user input line should be ignored.
     */
    protected boolean shouldIgnore(String rawInputLine) {
        return rawInputLine.trim().isEmpty() || isCommentLine(rawInputLine);
    }

    /**
     * Returns true if the user input line is a comment line.
     *
     * @param rawInputLine full raw user input line.
     * @return true if input line is a comment.
     */
    protected boolean isCommentLine(String rawInputLine) {
        return rawInputLine.trim().matches(COMMENT_LINE_FORMAT_REGEX);
    }

    public String getUserRequestInputString(){
        return new String(LINE_PREFIX + "Enter command: ");
    }
    public String getRequestStringRepeated(String fullInputLine){
        return formatMessage("[Command entered:" + fullInputLine + "]");
    }

    public String getWelcomeMessage(String storageFilePath, String version ){
        String storageFileInfo = String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath);
        return formatMessage(DIVIDER,
                DIVIDER,
                MESSAGE_WELCOME,
                version,
                MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE,
                storageFileInfo,
                DIVIDER);
    }
    public String getGoodbyeMessage(){
        return formatMessage(MESSAGE_GOODBYE, DIVIDER, DIVIDER);
    }
    public String getInitFailedMessage(){
        return formatMessage(MESSAGE_INIT_FAILED, DIVIDER, DIVIDER);
    }
    public String getResultMessage(CommandResult result){
        return formatMessage(result.feedbackToUser, DIVIDER);
    }
    public List<String> formatPersons(List<? extends ReadOnlyPerson> persons){
        final List<String> formattedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : persons) {
            formattedPersons.add(person.getAsTextHidePrivate());
        }
        return formattedPersons;
    }
    /** Formats a list of strings as a viewable indexed list. */
    public String getIndexedListForViewing(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatMessage(formatted.toString());
    }

    /**
     * Formats a string as a viewable indexed list item.
     *
     * @param visibleIndex visible index for this listing
     */
    public String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }
    public String formatMessage(String... message){
        final StringBuilder sb = new StringBuilder();
        for (String m : message) {
            sb.append(LINE_PREFIX + m.replace("\n", LS + LINE_PREFIX) + LS);
        }
        return sb.toString();
    }
}
