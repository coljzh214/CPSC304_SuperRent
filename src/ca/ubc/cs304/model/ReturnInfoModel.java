package ca.ubc.cs304.model;

import java.sql.Date;

public class ReturnInfoModel {
    private final int vlicense;
    private final int wrate;
    private final int drate;
    private final int hrate;
    private final Date fromDate;
    private final Date toDate;

    public ReturnInfoModel(int vlicense, int wrate, int drate, int hrate, Date fromDate, Date toDate) {
        this.vlicense = vlicense;
        this.wrate = wrate;
        this.drate = drate;
        this.hrate = hrate;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getVlicense() {
        return vlicense;
    }

    public int getWrate() {
        return wrate;
    }

    public int getDrate() {
        return drate;
    }

    public int getHrate() {
        return hrate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}
