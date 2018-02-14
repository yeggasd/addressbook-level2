package seedu.addressbook.data.person;

public abstract class Contact {

    public String value;
    protected boolean isPrivate;

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    /**
     * Returns true if the given string is a valid contact.
     */
    public abstract boolean isValid(String value);

}
