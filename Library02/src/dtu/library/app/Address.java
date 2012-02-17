package dtu.library.app;

public class Address {
    private String street;
    private int postNumber;
    private String town;

    public Address(String street, int postNumber, String town) {
        this.street = street;
        this.postNumber = postNumber;
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public int getPostnumber() {
        return postNumber;
    }

    public String getTown() {
        return town;
    }
}
