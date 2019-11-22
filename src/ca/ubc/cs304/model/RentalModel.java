package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Time;

public class RentalModel {
    private final int rid;
    private final int vlicense;
    private final int dlicense;
    private final int confNo;
    private final Date fromDate;
    private final Time fromTime;
    private final Date toDate;
    private final Time toTime;
    private final int odometer;
    private final String cardName;
    private final int cardNo;
    private final Date expDate;

    public RentalModel(int rid, int vlicense, int dlicense, int confNo, Date fromDate, Time fromTime, Date toDate,
                       Time toTime, int odometer, String cardName, int cardNo, Date expDate) {
        this.rid = rid;
        this.vlicense = vlicense;
        this.dlicense = dlicense;
        this.confNo = confNo;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.toDate = toDate;
        this.toTime = toTime;
        this.odometer = odometer;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.expDate = expDate;
    }

    public int getRid() {
        return rid;
    }

    public int getVlicense() {
        return vlicense;
    }

    public int getDlicense() {
        return dlicense;
    }

    public int getConfNo() {
        return confNo;
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

    public int getOdometer() {
        return odometer;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardNo() {
        return cardNo;
    }

    public Date getExpDate() {
        return expDate;
    }
}
