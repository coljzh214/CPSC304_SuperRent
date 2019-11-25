package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Time;

public class RentalModel extends IModel{
    private final int rid;
    private final int vlicense;
    private final int dlicense;
    private final int confNo;
    private final Date fromDate;
    private final Date toDate;
    private final int odometer;
    private final String cardName;
    private final int cardNo;
    private final Date expDate;

    @Override
    public String[] getTuple() {
        String[] ret = {Integer.toString(this.rid), Integer.toString(this.vlicense), Integer.toString(this.dlicense), Integer.toString(confNo), fromDate.toString(),
            toDate.toString(), Integer.toString(this.odometer), cardName, Integer.toString(cardNo), expDate.toString()};
        return ret;
    }

    public RentalModel(int rid, int vlicense, int dlicense, int confNo, Date fromDate, Date toDate,
                       int odometer, String cardName, int cardNo, Date expDate) {
        this.rid = rid;
        this.vlicense = vlicense;
        this.dlicense = dlicense;
        this.confNo = confNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.odometer = odometer;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.expDate = expDate;
        this.table_name = "Rental";
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

    public Date getToDate() {
        return toDate;
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
