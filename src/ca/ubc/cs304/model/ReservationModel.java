package ca.ubc.cs304.model;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

import java.sql.Date;

public class ReservationModel extends IModel{
    private final int confNo;
    private final String vtname;
    private final int dlicense;
    private final Date fromDate;
    private final Date toDate;

    @Override
    public String[] getTuple() {
        String[] ret = {Integer.toString(this.confNo), vtname, Integer.toString(this.dlicense), fromDate.toString(),
                toDate.toString()};
        return ret;
    }

    public ReservationModel(int confNo, String vtname, int dlicense, Date fromDate, Date toDate) {
        this.confNo = confNo;
        this.vtname = vtname;
        this.dlicense = dlicense;
        this.fromDate = fromDate;
        this.toDate = toDate;
        table_name = "Reservation";
    }

    public ReservationModel(String vtname, int dlicense, Date fromDate, Date toDate) {
        this.confNo = getNewConfNo();
        this.vtname = vtname;
        this.dlicense = dlicense;
        this.fromDate = fromDate;
        this.toDate = toDate;
        table_name = "Reservation";
    }

    private int getNewConfNo() {
        DatabaseConnectionHandler dbh = new DatabaseConnectionHandler();
        return dbh.getNewConfNo();
    }

    public int getConfNo() {
        return confNo;
    }

    public String getVtname() {
        return vtname;
    }

    public int getDlicense() {
        return dlicense;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}
