package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Time;

public class ReservationModel {
    private final int confNo;
    private final String vtname;
    private final int phonenumber;
    private final Date fromDate;
    private final Date toDate;

    public ReservationModel(int confNo, String vtname, int phonenumber, Date fromDate,
                            Date toDate) {
        this.confNo = confNo;
        this.vtname = vtname;
        this.phonenumber = phonenumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public Date getToDate() {
        return toDate;
    }
}
