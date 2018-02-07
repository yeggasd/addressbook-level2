package seedu.addressbook.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.addressbook.util.TestUtil.getSize;
import static seedu.addressbook.util.TestUtil.isEmpty;
import static seedu.addressbook.util.TestUtil.isIdentical;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

public class AddressBookTest {
    private Tag tagPrizeWinner;
    private Tag tagScientist;
    private Tag tagMathematician;
    private Tag tagEconomist;

    private Person aliceBetsy;
    private Person bobChaplin;
    private Person charlieDouglas;
    private Person davidElliot;

    private AddressBook defaultAddressBook;
    private AddressBook emptyAddressBook;


    @Before
    public void setUp() throws Exception {
        tagPrizeWinner   = new Tag("prizewinner");
        tagScientist     = new Tag("scientist");
        tagMathematician = new Tag("mathematician");
        tagEconomist     = new Tag("economist");

        aliceBetsy     = new Person(new Name("Alice Betsy"),
                                    new Phone("91235468", false),
                                    new Email("alice@nushackers.org", false),
                                    new Address("8, Computing Drive, 123-123, 777777", false),
                                    new UniqueTagList(tagMathematician));

        bobChaplin     = new Person(new Name("Bob Chaplin"),
                                    new Phone("94321500", false),
                                    new Email("bob@nusgreyhats.org", false),
                                    new Address("9, Computing Drive, 123-123, 777777", false),
                                    new UniqueTagList(tagMathematician));

        charlieDouglas = new Person(new Name("Charlie Douglas"),
                                    new Phone("98751365", false),
                                    new Email("charlie@nusgdg.org", false),
                                    new Address("10, Science Drive, 123-123, 777777", false),
                                    new UniqueTagList(tagScientist));

        davidElliot    = new Person(new Name("David Elliot"),
                                    new Phone("84512575", false),
                                    new Email("douglas@nuscomputing.com", false),
                                    new Address("11, Arts Link, 123-123, 777777", false),
                                    new UniqueTagList(tagEconomist, tagPrizeWinner));

        emptyAddressBook = new AddressBook();
        defaultAddressBook = new AddressBook(new UniquePersonList(aliceBetsy, bobChaplin),
                                             new UniqueTagList(tagMathematician, tagScientist));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addPerson_emptyAddressBook() throws Exception {
        emptyAddressBook.addPerson(bobChaplin);
        emptyAddressBook.addPerson(charlieDouglas);

        UniqueTagList expectedTagList = new UniqueTagList(tagMathematician, tagScientist);
        assertTrue(isIdentical(expectedTagList, emptyAddressBook.getAllTags()));

        assertTrue(isTagObjectInAddressBookList(tagMathematician, emptyAddressBook));
        assertTrue(isTagObjectInAddressBookList(tagScientist, emptyAddressBook));

    }

    @Test
    public void addPerson_someTagsNotInTagList() throws Exception {
        assertFalse(isTagObjectInAddressBookList(tagEconomist, defaultAddressBook));
        assertFalse(isTagObjectInAddressBookList(tagPrizeWinner, defaultAddressBook));
        defaultAddressBook.addPerson(davidElliot);
        assertTrue(isTagObjectInAddressBookList(tagEconomist, defaultAddressBook));
        assertTrue(isTagObjectInAddressBookList(tagPrizeWinner, defaultAddressBook));
    }

    @Test
    public void addPerson_personAlreadyInList_throwsDuplicatePersonException() throws Exception {
        thrown.expect(DuplicatePersonException.class);
        defaultAddressBook.addPerson(aliceBetsy);
    }

    @Test
    public void addPerson_personAlreadyInListButHasTagNotInList_tagNotAdded() throws Exception {
        aliceBetsy.setTags(new UniqueTagList(tagMathematician, tagPrizeWinner));

        try {
            defaultAddressBook.addPerson(aliceBetsy);
        } catch (DuplicatePersonException e) {
            // ignore expected exception
        }

        assertFalse(isTagObjectInAddressBookList(tagPrizeWinner, defaultAddressBook));
    }

    @Test
    public void containsPerson() throws Exception {
        UniquePersonList personsWhoShouldBeIn = new UniquePersonList(aliceBetsy, bobChaplin);
        UniquePersonList personsWhoShouldNotBeIn = new UniquePersonList(charlieDouglas, davidElliot);

        for (Person personWhoShouldBeIn : personsWhoShouldBeIn) {
            assertTrue(defaultAddressBook.containsPerson(personWhoShouldBeIn));
        }
        for (Person personWhoShouldNotBeIn : personsWhoShouldNotBeIn) {
            assertFalse(defaultAddressBook.containsPerson(personWhoShouldNotBeIn));
        }

        UniquePersonList allPersons = new UniquePersonList(aliceBetsy, bobChaplin, charlieDouglas, davidElliot);

        for (Person person : allPersons) {
            assertFalse(emptyAddressBook.containsPerson(person));
        }
    }

    @Test
    public void removePerson_personExists_removesNormally() throws Exception {
        int numberOfPersonsBeforeRemoval = getSize(defaultAddressBook.getAllPersons());
        defaultAddressBook.removePerson(aliceBetsy);

        assertFalse(defaultAddressBook.containsPerson(aliceBetsy));

        int numberOfPersonsAfterRemoval = getSize(defaultAddressBook.getAllPersons());
        assertTrue(numberOfPersonsAfterRemoval == numberOfPersonsBeforeRemoval - 1);

    }

    @Test
    public void removePerson_personNotExists_throwsPersonNotFoundException() throws Exception {
        thrown.expect(PersonNotFoundException.class);
        defaultAddressBook.removePerson(charlieDouglas);
    }

    @Test
    public void clear() throws Exception {
        defaultAddressBook.clear();

        assertTrue(isEmpty(defaultAddressBook.getAllPersons()));
        assertTrue(isEmpty(defaultAddressBook.getAllTags()));
    }

    @Test
    public void getAllPersons() throws Exception {
        UniquePersonList allPersons = defaultAddressBook.getAllPersons();
        UniquePersonList personsToCheck = new UniquePersonList(aliceBetsy, bobChaplin);

        assertTrue(isIdentical(allPersons, personsToCheck));
    }

    @Test
    public void getAllTags() throws Exception {
        UniqueTagList allTags = defaultAddressBook.getAllTags();
        UniqueTagList tagsToCheck = new UniqueTagList(tagMathematician, tagScientist);

        assertTrue(isIdentical(allTags, tagsToCheck));
    }

    /**
     * Returns true if the given Tag object is found in the tag list of the given AddressBook.
     */
    private boolean isTagObjectInAddressBookList(Tag tagToCheck, AddressBook addressBook) {
        for (Tag tag : addressBook.getAllTags()) {
            if (tag == tagToCheck) {
                return true;
            }
        }
        return false;
    }
}
