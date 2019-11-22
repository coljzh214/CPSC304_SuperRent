package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Time;

public class RentReturnModel {
    private final int rid;
    private final Date returnDate;
    private final Time returnTime;
    private final int odometer;
    private final boolean fulltank;
    private final int value;

    public RentReturnModel(int rid, Date returnDate, Time returnTime, int odometer, boolean fulltank, int value) {
        this.rid = rid;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.odometer = odometer;
        this.fulltank = fulltank;
        this.value = value;
    }

    public int getRid() {
        return rid;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Time getReturnTime() {
        return returnTime;
    }

    public int getOdometer() {
        return odometer;
    }

    public boolean isFulltank() {
        return fulltank;
    }

    public int getValue() {
        return value;
    }
}
