package seedu.addressbook.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.util.TestUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SortCommandTest{
    private List<ReadOnlyPerson> listWithEveryone;
    private AddressBook addressBook;
    private AddressBook sortedAddressBook;
    @Before
    public void setUp() throws Exception {
        Person aaronDoe = new Person(new Name("Aaron Doe"), new Phone("61234567", false),
                new Email("john@doe.com", false), new Address("395C Ben Road", false), new UniqueTagList());
        Person bobDoe = new Person(new Name("Bob Doe"), new Phone("91234567", false),
                new Email("jane@doe.com", false), new Address("33G Ohm Road", false), new UniqueTagList());
        Person charlieDoe = new Person(new Name("Charlie Doe"), new Phone("63345566", false),
                new Email("sam@doe.com", false), new Address("55G Abc Road", false), new UniqueTagList());
        Person davidGrant = new Person(new Name("David Grant"), new Phone("61121122", false),
                new Email("david@grant.com", false), new Address("44H Define Road", false),
                new UniqueTagList());

        listWithEveryone = TestUtil.createList(aaronDoe, bobDoe, charlieDoe, davidGrant);
        addressBook = TestUtil.createAddressBook(davidGrant, charlieDoe, bobDoe, aaronDoe);
        sortedAddressBook = TestUtil.createAddressBook(aaronDoe, bobDoe, charlieDoe, davidGrant);
    }
    @Test
    public void execute_addressBookWithThreePersons_sortedAddressBook() {
        assertSortSuccessful(addressBook, listWithEveryone);
    }

    /**
     * Executes the command, and checks that the execution was what we had expected.
     */
    private void assertCommandBehaviour(SortCommand sortCommand, String expectedMessage,
                                        AddressBook expectedAddressBook, AddressBook actualAddressBook) {

        CommandResult result = sortCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedAddressBook.getAllPersons(), actualAddressBook.getAllPersons());
    }

    /**
     * Asserts that SortCommand can sort the {@code addressBook} and verifies that the result matches
     * the persons in the expectedPersonList exactly.
     *
     */
    private void assertSortSuccessful(AddressBook addressBook, List<ReadOnlyPerson> displayList){

        AddressBook expectedAddressBook = sortedAddressBook;
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS);

        AddressBook actualAddressBook = TestUtil.clone(addressBook);

        SortCommand command = createSortCommand(actualAddressBook, displayList);
        assertCommandBehaviour(command, expectedMessage, expectedAddressBook, actualAddressBook);
    }
    /**
     * Creates a new sort command.
     *
     */
    private SortCommand createSortCommand(AddressBook addressBook, List<ReadOnlyPerson> displayList) {

        SortCommand command = new SortCommand();
        command.setData(addressBook, displayList);

        return command;
    }

}
