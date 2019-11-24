package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Time;

public class RentReturnModel {
    private final int rid;
    private final Date returnDate;
    private final int odometer;
    private final String fulltank;
    private final int value;
    private final int duration;
    private final int rate;

    public RentReturnModel(int rid, Date returnDate, int odometer, String fulltank, int value,
                           int duration, int rate) {
        this.rid = rid;
        this.returnDate = returnDate;
        this.odometer = odometer;
        this.fulltank = fulltank;
        this.value = value;
        this.duration = duration;
        this.rate = rate;
    }

    public int getRid() {
        return rid;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public int getOdometer() {
        return odometer;
    }

    public String isFulltank() {
        return fulltank;
    }

    public int getValue() {
        return value;
    }

    public int getDuration() {
        return duration;
    }

    public int getRate() {
        return rate;
    }
}
