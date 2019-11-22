package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Time;

public class ReservationModel {
    private final int confNo;
    private final String vtname;
    private final int phonenumber;
    private final Date fromDate;
    private final Time fromTime;
    private final Date toDate;
    private final Time toTime;

    public ReservationModel(int confNo, String vtname, int phonenumber, Date fromDate, Time fromTime,
                            Date toDate, Time toTime) {
        this.confNo = confNo;
        this.vtname = vtname;
        this.phonenumber = phonenumber;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
    }

    public int getConfNo() {
        return confNo;
    }

    public String getVtname() {
        return vtname;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Time getFromTime() {
        return fromTime;
    }

    public Date getToDate() {
        return toDate;
    }

    public Time getToTime() {
        return toTime;
    }
}
