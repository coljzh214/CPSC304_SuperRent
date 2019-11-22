package ca.ubc.cs304.model;

public class VehicleModel {
    private final int vlicense;
    private final int vid;
    private final String make;
    private final String model;
    private final int year;
    private final String color;
    private final int odometer;
    private final String status;
    private final String vtname;
    private final String location;
    private final String city;

    public VehicleModel(int vlicense, int vid, String make, String model, int year, String color, int odometer,
                        String status, String vtname, String location, String city) {
        this.vlicense = vlicense;
        this.vid = vid;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.odometer = odometer;
        this.status = status;
        this.vtname = vtname;
        this.location = location;
        this.city = city;
    }

    public int getVlicense() {
        return vlicense;
    }

    public int getVid() {
        return vid;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public int getOdometer() {
        return odometer;
    }

    public String getStatus() {
        return status;
    }

    public String getVtname() {
        return vtname;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }
}
