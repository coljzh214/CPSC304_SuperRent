package ca.ubc.cs304.model;

public class CustomerModel {
    private final String address;
    private final String name;
    private final int dlicense;
    private final int phonenumber;

    public CustomerModel(int dlicense, String name, String address, int phonenumber) {
        this.address = address;
        this.name = name;
        this.dlicense = dlicense;
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getDlicense() {
        return dlicense;
    }

    public int getPhonenumber() {
        return phonenumber;
    }
}
