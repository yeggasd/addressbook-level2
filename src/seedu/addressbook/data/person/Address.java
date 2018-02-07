package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String EXAMPLE = "123, Clementi Ave 3, #12-34, 231534";
    public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Person addresses must be in BLOCK, STREET, UNIT, POSTAL_CODE format";
    public static final String ADDRESS_VALIDATION_REGEX = ".+,.+,.+,.+";
    public static final String SPLIT_TOKEN = ", ";

    public final int BLOCK_INDEX=0;
    public final int STREET_INDEX=1;
    public final int UNIT_INDEX=2;
    public final int POSTAL_CODE_INDEX=3;

    public final String value;
    public final Block block;
    public final Street street;
    public final Unit unit;
    public final PostalCode postalCode;

    private boolean isPrivate;


    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address, boolean isPrivate) throws IllegalValueException {
        String trimmedAddress = address.trim();
        value = trimmedAddress;
        String[] splitAddress = trimmedAddress.split(SPLIT_TOKEN);
        this.isPrivate = isPrivate;
        if (!isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.block = new Block(splitAddress[BLOCK_INDEX]);
        this.street = new Street(splitAddress[STREET_INDEX]);
        this.unit = new Unit(splitAddress[UNIT_INDEX]);
        this.postalCode = new PostalCode(splitAddress[POSTAL_CODE_INDEX]);
    }

    /**
     * Returns true if a given string is a valid person address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String address ="";
        address+=block+SPLIT_TOKEN;
        address+=street+SPLIT_TOKEN;
        address+=unit+SPLIT_TOKEN;
        address+=postalCode;

        return address;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.toString().equals(((Address) other).toString())); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    class Block {
        public final String block;

        public Block(String block) {
            this.block = block;
        }

        @Override
        public String toString() {
            return block;
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof Block
                    && this.block.equals(((Block) other).block));
        }

        @Override
        public int hashCode() {
            return block.hashCode();
        }
    }
    class Street {
        private String value;

        Street(String street) {
            this.value = street;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof Block
                    && this.value.equals(((Street) other).value));
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }

    class Unit {
        private String value;

        Unit(String unit) {
            this.value = unit;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof Block
                    && this.value.equals(((Unit) other).value));
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }

    class PostalCode {
        private String value;

        PostalCode(String postalCode) {
            this.value = postalCode;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                    || (other instanceof Block
                    && this.value.equals(((Street) other).value));
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }
}
