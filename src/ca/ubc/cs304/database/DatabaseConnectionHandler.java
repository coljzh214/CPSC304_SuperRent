package ca.ubc.cs304.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import ca.ubc.cs304.model.*;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void deleteBranch(String location, String city) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE location = ? AND city = ?");
        ps.setString(1, location);
        ps.setString(2, city);

        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Branch " + location + " " + city + " does not exist!");
        }

        connection.commit();

        ps.close();
    }


    public void deleteVehicleType(String vtname) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM VehicleType WHERE vtname = ? ");
        ps.setString(1, vtname);

        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " VechicleType " + vtname + " does not exist!");
        }

        connection.commit();

        ps.close();
    }

    public void deleteVehicle(String vlicense) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM Vehicle WHERE vlicense = ? ");
        ps.setString(1, vlicense);

        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Vechicle " + vlicense + " does not exist!");
        }

        connection.commit();

        ps.close();
    }

    public void deleteReservation(int confNo) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM Reservation WHERE confNo = ? ");
        ps.setInt(1, confNo);

        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Customer " + confNo + " does not exist!");
        }

        connection.commit();

        ps.close();
    }

    public void deleteRental(int rid) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM Rental WHERE rid = ? ");
        ps.setInt(1, rid);

        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Customer " + rid + " does not exist!");
        }

        connection.commit();

        ps.close();
    }

    public void deleteRentReturn(int rid) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM RentReturn WHERE rid = ? ");
        ps.setInt(1, rid);

        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Customer " + rid + " does not exist!");
        }

        connection.commit();

        ps.close();
    }

    public void insertBranch(BranchModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?)");
        ps.setString(1, model.getLocation());
        ps.setString(2, model.getCity());

        ps.executeUpdate();
        connection.commit();

        ps.close();
    }

    public String[] getTableInfo() throws SQLException {
        ArrayList<String> result = new ArrayList<String>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT table_name FROM user_tables");

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            String table = rs.getString("table_name");
            result.add(table);
        }

        rs.close();
        stmt.close();
        return result.toArray(new String[result.size()]);
    }

    public RentReturnModel[] getRentReturnInfo() throws SQLException {
        ArrayList<RentReturnModel> result = new ArrayList<RentReturnModel>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM RentReturn");

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            RentReturnModel model = new RentReturnModel(rs.getInt("rid"),
                    rs.getDate("returnDate"),
                    rs.getInt("odometer"),
                    rs.getString("fullTank"),
                    rs.getInt("value"), -1, -1);
            result.add(model);
        }

        rs.close();
        stmt.close();
        return result.toArray(new RentReturnModel[result.size()]);
    }

    public RentalModel[] getRentalInfo() throws SQLException {
        ArrayList<RentalModel> result = new ArrayList<RentalModel>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Rental");

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            RentalModel model = new RentalModel(rs.getInt("rid"),
                    rs.getInt("vlicense"),
                    rs.getInt("dlicense"),
                    rs.getInt("confNo"),
                    rs.getDate("fromDate"),
                    rs.getDate("toDate"),
                    rs.getInt("odometer"),
                    rs.getString("cardName"),
                    rs.getInt("cardNo"),
                    rs.getDate("expDate"));
            result.add(model);
        }

        rs.close();
        stmt.close();
        return result.toArray(new RentalModel[result.size()]);
    }

    public ReservationModel[] getReservationInfo() throws SQLException {
        ArrayList<ReservationModel> result = new ArrayList<ReservationModel>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Reservation");

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            ReservationModel model = new ReservationModel(rs.getInt("confNo"),
                    rs.getString("vtname"),
                    rs.getInt("dlicense"),
                    rs.getDate("fromDate"),
                    rs.getDate("toDate"));
            result.add(model);
        }

        rs.close();
        stmt.close();

        return result.toArray(new ReservationModel[result.size()]);
    }

    public CustomerModel[] getCustomerInfo() throws SQLException {
        ArrayList<CustomerModel> result = new ArrayList<CustomerModel>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Customer");

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            CustomerModel model = new CustomerModel(rs.getInt("dlicense"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getInt("phonenumber"));
            result.add(model);
        }

        rs.close();
        stmt.close();

        return result.toArray(new CustomerModel[result.size()]);
    }

    public VehicleTypeModel[] getVehicleTypeInfo() throws SQLException {
        ArrayList<VehicleTypeModel> result = new ArrayList<VehicleTypeModel>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM VehicleType");

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            VehicleTypeModel model = new VehicleTypeModel(rs.getString("vtname"),
                    rs.getString("features"),
                    rs.getInt("wrate"),
                    rs.getInt("drate"),
                    rs.getInt("hrate"),
                    rs.getInt("wirate"),
                    rs.getInt("dirate"),
                    rs.getInt("hirate"),
                    rs.getInt("krate"));
            result.add(model);
        }

        rs.close();
        stmt.close();

        return result.toArray(new VehicleTypeModel[result.size()]);
    }

    public VehicleModel[] getVehicleInfo() throws SQLException {
        ArrayList<VehicleModel> result = new ArrayList<VehicleModel>();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicle");

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            VehicleModel model = new VehicleModel(rs.getInt("vlicense"),
                    rs.getInt("vid"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getString("color"),
                    rs.getInt("odometer"),
                    rs.getString("status"),
                    rs.getString("vtname"),
                    rs.getString("location"),
                    rs.getString("city"));
            result.add(model);
        }

        rs.close();
        stmt.close();
        return result.toArray(new VehicleModel[result.size()]);
    }

    public BranchModel[] getBranchInfo() throws SQLException {
        ArrayList<BranchModel> result = new ArrayList<BranchModel>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM branch");

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            BranchModel model = new BranchModel(rs.getString("location"),
                    rs.getString("city"));
            result.add(model);
        }

        rs.close();
        stmt.close();
        return result.toArray(new BranchModel[result.size()]);
    }

    public VehicleModel[] getVehicleQuery(String vType, String location, String startDateString, String endDateString) throws IllegalArgumentException, SQLException {
        ArrayList<VehicleModel> result = new ArrayList<VehicleModel>();
        java.sql.Date endDate = null;
        java.sql.Date startDate = null;
        try {
            if (startDateString != null && startDateString.length() > 0)
                startDate = Date.valueOf(startDateString);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        try {
            if (endDateString != null && endDateString.length() > 0)
                endDate = Date.valueOf(endDateString);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        String sqlQuery = "SELECT DISTINCT v.* FROM Vehicle v, VehicleType vt" +
                " WHERE v.vtname = vt.vtname ";
        if (location.length() > 0) {
            sqlQuery += " AND v.location = '" + location + "'";
        }
        if (vType.length() > 0) {
            sqlQuery += " AND vt.vtname = '" + vType + "'";
        }

        if (startDate != null && endDate != null) {
            if (startDate.getTime() > endDate.getTime()) {
                throw new IllegalArgumentException("StartDate must be less than endDate");
            }
            sqlQuery += " AND NOT EXISTS  (SELECT * FROM Rental ren WHERE " +
                    " v.vlicense = ren.vlicense AND (to_date('" + startDate + "'), to_date('" + endDate + "')) OVERLAPS (ren.fromDate, ren.toDate))";
            sqlQuery += " AND NOT EXISTS  (SELECT * FROM Rental ren, Reservation res WHERE " +
                    " v.vlicense = ren.vlicense AND ren.confNo = ren.confNo AND (to_date('" + startDate + "'), to_date('" + endDate + "')) OVERLAPS (res.fromDate, res.toDate))";
        }
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sqlQuery);

        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        System.out.println(" ");

        // display column names;
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            // get column name and print it
            System.out.printf("%-15s", rsmd.getColumnName(i + 1));
        }

        while (rs.next()) {
            VehicleModel model = new VehicleModel(rs.getInt("vlicense"),
                    rs.getInt("vid"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getString("color"),
                    rs.getInt("odometer"),
                    rs.getString("status"),
                    rs.getString("vtname"),
                    rs.getString("location"),
                    rs.getString("city")
            );
            result.add(model);
        }

        rs.close();
        stmt.close();

        return result.toArray(new VehicleModel[result.size()]);
    }


    public void updateBranch(String location, String city, BranchModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE branch SET location = ?, city = ? WHERE location = ? AND city =?");
        ps.setString(1, model.getLocation());
        ps.setString(2, model.getCity());
        ps.setString(3, location);
        ps.setString(4, city);

        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Branch " + location + ", " + city + " does not exist!");
        }

        connection.commit();

        ps.close();
    }

    public void updateVehicleType(String vtname, VehicleTypeModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE vehicle SET vtname = ?, feature = ?, wrate = ?," +
                " drate = ?, hrate = ?, wirate = ?, dirate = ?, hirate = ?, krate = ?" +
                " WHERE vtname = ?");
        ps.setString(1, model.getVtname());
        ps.setString(2, model.getFeatures());
        ps.setInt(3, model.getWrate());
        ps.setInt(4, model.getDrate());
        ps.setInt(5, model.getHrate());
        ps.setInt(6, model.getWirate());
        ps.setInt(7, model.getDirate());
        ps.setInt(8, model.getHirate());
        ps.setInt(9, model.getKrate());
        ps.setString(10, vtname);

        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " VehicleType " + vtname + " does not exist!");
        }

        connection.commit();

        ps.close();
    }

    public void insertReservation(ReservationModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Reservation VALUES (?,?,?,?,?)");
        ps.setInt(1, model.getConfNo());
        ps.setString(2, model.getVtname());
        // ps.setInt(3, model.getPhonenumber());
        ps.setDate(4, model.getFromDate());
        ps.setDate(5, model.getToDate());

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void updateReservation(int confNo, ReservationModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE Reservation SET confNo = ?, vtname = ?," +
                " dlicense = ?, fromDate = ?, toDate = ? WHERE confNo = ?");
        ps.setInt(1, model.getConfNo());
        ps.setString(2, model.getVtname());
        // ps.setInt(3, model.getPhonenumber());
        ps.setDate(4, model.getFromDate());
        ps.setDate(5, model.getToDate());
        ps.setInt(6, confNo);

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void insertVehicleType(VehicleTypeModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO VehicleType VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, model.getVtname());
        ps.setString(2, model.getFeatures());
        ps.setInt(3, model.getWrate());
        ps.setInt(4, model.getDrate());
        ps.setInt(5, model.getHrate());
        ps.setInt(6, model.getWirate());
        ps.setInt(7, model.getDirate());
        ps.setInt(8, model.getHirate());
        ps.setInt(9, model.getKrate());

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void insertVehicle(VehicleModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Vehicle VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, model.getVlicense());
        ps.setInt(2, model.getVid());
        ps.setString(3, model.getMake());
        ps.setString(4, model.getModel());
        ps.setInt(5, model.getYear());
        ps.setString(6, model.getColor());
        ps.setInt(7, model.getOdometer());
        ps.setString(8, model.getStatus());
        ps.setString(9, model.getVtname());
        ps.setString(10, model.getLocation());
        ps.setString(11, model.getCity());

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void updateVehicle(int vlicense, VehicleModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE Vehicle SET vlicense = ?, " +
                " vid = ?, make = ? , model = ?, year = ?, color = ?, odometer =?, status = ?, vtname = ?," +
                " location = ?, city = ? WHERE vlicense = ?");
        ps.setInt(1, model.getVlicense());
        ps.setInt(2, model.getVid());
        ps.setString(3, model.getMake());
        ps.setString(4, model.getModel());
        ps.setInt(5, model.getYear());
        ps.setString(6, model.getColor());
        ps.setInt(7, model.getOdometer());
        ps.setString(8, model.getStatus());
        ps.setString(9, model.getVtname());
        ps.setString(10, model.getLocation());
        ps.setString(11, model.getCity());
        ps.setInt(12, vlicense);

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void insertRental(RentalModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Rental VALUES (?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, model.getRid());
        ps.setInt(2, model.getVlicense());
        ps.setInt(3, model.getDlicense());
        ps.setInt(4, model.getConfNo());
        ps.setDate(5, model.getFromDate());
        ps.setDate(6, model.getToDate());
        ps.setString(7, model.getCardName());
        ps.setInt(8, model.getCardNo());
        ps.setDate(9, model.getExpDate());

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void updateRental(int rid, RentalModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UDPATE Rental SET rid = ?, vlicense = ?, dlicense = ?," +
                " confNo = ?, fromDate = ?, toDate = ?, cardName = ?, cardNo = ? expDate = ? WHERE rid = ?");
        ps.setInt(1, model.getRid());
        ps.setInt(2, model.getVlicense());
        ps.setInt(3, model.getDlicense());
        ps.setInt(4, model.getConfNo());
        ps.setDate(5, model.getFromDate());
        ps.setDate(6, model.getToDate());
        ps.setString(7, model.getCardName());
        ps.setInt(8, model.getCardNo());
        ps.setDate(9, model.getExpDate());
        ps.setInt(10, model.getRid());

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void insertRentReturn(RentReturnModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO RentReturn VALUES (?,?,?,?,?)");
        ps.setInt(1, model.getRid());
        ps.setDate(2, model.getReturnDate());
        ps.setInt(3, model.getOdometer());
        ps.setString(4, model.isFulltank());
        ps.setInt(5, model.getValue());

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void insertRentReturn(int rid, RentReturnModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE RentReturn SET rid = ?, returnDate = ?," +
                " odometer = ?, fullTank = ?, value = ? WHERE rid = ?");
        ps.setInt(1, model.getRid());
        ps.setDate(2, model.getReturnDate());
        ps.setInt(3, model.getOdometer());
        ps.setString(4, model.isFulltank());
        ps.setInt(5, model.getValue());
        ps.setInt(1, rid);

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void insertCustomer(CustomerModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
        ps.setInt(1, model.getDlicense());
        ps.setString(2, model.getName());
        ps.setString(3, model.getAddress());
        ps.setInt(4, model.getPhonenumber());

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void updateCustomer(int dlicense, CustomerModel model) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE Customer SET dlicense = ?, name = ?," +
                " address = ?, phonenumber = ? WHERE dlicense = ?");
        ps.setInt(1, model.getDlicense());
        ps.setString(2, model.getName());
        ps.setString(3, model.getAddress());
        ps.setInt(4, model.getPhonenumber());
        ps.setInt(5, dlicense);

        ps.executeUpdate();
        connection.commit();
        ps.close();
    }


    public void deleteCustomer(int dLicense) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM customer WHERE dLicense = ?");
        ps.setInt(1, dLicense);
        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Customer with license " + dLicense + " does not exist!");
        }
        connection.commit();
        ps.close();
    }

    public void updateCustomer() {
        return;
    }

    public ReservationModel createReservation(ReservationModel res, CustomerModel Customer) throws Exception, SQLException {
        Statement stmt = connection.createStatement();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Customer WHERE dlicense = ?");
        ps.setInt(1, Customer.getDlicense());

        VehicleModel[] availablevehicles = getVehicleQuery(res.getVtname(), "", res.getFromDate().toString(), res.getToDate().toString());
        if (availablevehicles.length <= 0) {
            throw new Exception("There are no vehicles of that type available");
        }

        ResultSet rs = ps.executeQuery();
        // get info on ResultSet
        ResultSetMetaData rsmd = rs.getMetaData();

        // decides whether to create Customer or not
        if (!rs.next()) {
            insertCustomer(Customer);
        }
        insertReservation(res);
        rs.close();
        stmt.close();
        return res;
    }

    public void updateVehicleStatus(String status, int vlicense) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE Vehicle SET status = ? WHERE vlicense = ?");
        ps.setString(1, status);
        ps.setInt(2, vlicense);
        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Vehicle " + vlicense + " does not exist!");
        }
        connection.commit();
        ps.close();
    }

	public void updateVehicleStatus(String status, int odometer, int vlicense) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE Vehicle SET status = ?, odometer = ? " +
                "WHERE vlicense = ?");
        ps.setString(1, status);
        ps.setInt(2, odometer);
        ps.setInt(3, vlicense);
        int rowCount = ps.executeUpdate();
        if (rowCount == 0) {
            System.out.println(WARNING_TAG + " Vehicle " + vlicense + " does not exist!");
        }
        connection.commit();
        ps.close();
	}

	public ReservationModel getReservation(int confNo) throws SQLException {
		ReservationModel r = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM reservation WHERE confNo = ?");
        ps.setInt(1, confNo);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            r = new ReservationModel(rs.getInt("confNo"),
                    rs.getString("vtname"),
                    rs.getInt("dlicense"),
                    rs.getDate("fromDate"),
                    rs.getDate("toDate"));
        }
        ps.executeUpdate();
        connection.commit();
        ps.close();
	    return r;
    }

	public RentalModel processRentalWithReservation(int confNo, String cardName, int cardNo, String expDateString) throws Exception {
		java.sql.Date expDate = Date.valueOf(expDateString);
		ReservationModel rm = this.getReservation(confNo);
		if (rm == null) {
			throw new Exception("Reservation with given confNo does not exist.");
		}
		VehicleModel vm = this.getVehicleForRent(rm.getVtname());
		if (vm == null) {
			throw new Exception("No vehicle with given vehicle type is available.");
		}

        String str = "INSERT INTO Rental (vlicense, dlicense, confNo, fromDate, toDate, odometer, cardName, cardNo, expDate) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(str);
        ps.setInt(1, vm.getVlicense());
        ps.setInt(2, rm.getDlicense());
        ps.setInt(3, confNo);
        ps.setDate(4, rm.getFromDate());
        ps.setDate(5, rm.getToDate());
        ps.setInt(6, vm.getOdometer());
        ps.setString(7, cardName);
        ps.setInt(8, cardNo);
        ps.setDate(9, expDate);

        ps.executeUpdate();

        connection.commit();
        ps.close();
        this.updateVehicleStatus("rented", vm.getVlicense());

        PreparedStatement ridps = connection.prepareStatement("SELECT rent_seq.currval FROM dual");
        ResultSet rs = ridps.executeQuery();
        int rid = 0;
        while (rs.next()) {
            rid = rs.getInt("currval");
        }
		return new RentalModel(rid, vm.getVlicense(), rm.getDlicense(), confNo, rm.getFromDate(), rm.getToDate(),
                vm.getOdometer(), cardName, cardNo, expDate);
	}

    public RentalModel processRentalWithoutReservation(String vtname, int dlicense, String fromDateString, String toDateString,
											String cardName, int cardNo, String expDateString) throws Exception {
		java.sql.Date startDate = Date.valueOf(fromDateString);
		java.sql.Date endDate = Date.valueOf(toDateString);
		java.sql.Date expDate = Date.valueOf(expDateString);
        if (endDate.getTime() - startDate.getTime() <= 0) {
            throw new Exception("Start time is later than end time");
        } else if (!this.isCustomer(dlicense)) {
			throw new Exception("Customer with invalid dlicense");
		}
        VehicleModel vm = this.getVehicleForRent(vtname);
        if (vm == null) {
            throw new Exception("No available vehicles found ");
        }
        String str = "INSERT INTO Rental (vlicense, dlicense, confNo, fromDate, toDate, odometer, cardName, cardNo, expDate) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(str);
        ps.setInt(1, vm.getVlicense());
        ps.setInt(2, dlicense);
        ps.setNull(3, java.sql.Types.INTEGER);
        ps.setDate(4, startDate);
        ps.setDate(5, endDate);
        ps.setInt(6, vm.getOdometer());
        ps.setString(7, cardName);
        ps.setInt(8, cardNo);
        ps.setDate(9, expDate);

        ps.executeUpdate();
        connection.commit();
        ps.close();
        this.updateVehicleStatus("rented", vm.getVlicense());

        PreparedStatement ridps = connection.prepareStatement("SELECT rent_seq.currval FROM dual");
        ResultSet rs = ridps.executeQuery();
        int rid = 0;
        while (rs.next()) {
            rid = rs.getInt("currval");
        }
        return new RentalModel(rid, vm.getVlicense(), dlicense, -1, startDate, endDate, vm.getOdometer(),
                cardName, cardNo, expDate);
    }

    public boolean isCustomer(int dlicense) throws SQLException {
		boolean exists = true;
        String str = "SELECT * " +
                        "FROM customer " +
                        "WHERE dlicense = ?";
        PreparedStatement ps = connection.prepareStatement(str);
        ps.setInt(1, dlicense);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            exists = false;
        }
        rs.close();
        ps.close();
		return exists;
	}

    public VehicleModel getVehicleForRent(String vtname) throws SQLException {
		VehicleModel model = null;
        String str = "SELECT * " +
                "FROM (SELECT * FROM vehicle ORDER BY odometer) " +
                "WHERE vtname = ? AND status = 'available' AND rownum = 1";
        PreparedStatement ps = connection.prepareStatement(str);
        ps.setString(1, vtname);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            model = new VehicleModel(rs.getInt("vlicense"),
                    rs.getInt("vid"),
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getString("color"),
                    rs.getInt("odometer"),
                    rs.getString("status"),
                    rs.getString("vtname"),
                    rs.getString("location"),
                    rs.getString("city"));
        }
        rs.close();
        ps.close();
		return model;
	}

	public RentReturnModel processReturn(int rid, String returnDateString, int odometer, String fullTank) throws Exception {
		java.sql.Date returnDate = Date.valueOf(returnDateString);
        ReturnInfoModel rm;

        rm = this.getReturnInfo(rid);
        if (rm == null) {
            throw new Exception("Rental ID does not exist.");
        }

        long diff = returnDate.getTime() - rm.getFromDate().getTime();
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        int value = days * rm.getDrate();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO RentReturn VALUES (?,?,?,?,?)");
        ps.setInt(1, rid);
        ps.setDate(2, returnDate);
        ps.setInt(3, odometer);
        ps.setString(4, fullTank);
        ps.setInt(5, value);

        ps.executeUpdate();
        connection.commit();
        ps.close();
        this.updateVehicleStatus("available", odometer, rm.getVlicense());

        return new RentReturnModel(rid, returnDate, odometer, fullTank, value, days, rm.getDrate());
	}

	public ReturnInfoModel getReturnInfo(int rid) throws SQLException {
		ReturnInfoModel model = null;
        String str = "SELECT v.vlicense, vt.wrate, vt.drate, vt.hrate, r.fromDate, r.toDate " +
                        "FROM vehicle v, rental r, vehicleType vt " +
                        "WHERE v.vlicense = r.vlicense AND v.vtname = vt.vtname " +
                        "AND r.rid = ? AND v.status = 'rented' ";
        // String s = "SELECT v.vlicense, vt.wrate, vt.drate, vt.hrate, r.fromDate, r.toDate FROM vehicle v, rental r, vehicleType vt WHERE v.vlicense = r.vlicense AND v.vtname = vt.vtname AND r.rid = ? ";
        PreparedStatement ps = connection.prepareStatement(str);
        ps.setInt(1, rid);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            model = new ReturnInfoModel(rs.getInt("vlicense"),
                    rs.getInt("wrate"),
                    rs.getInt("drate"),
                    rs.getInt("hrate"),
                    rs.getDate("fromDate"),
                    rs.getDate("toDate")
            );
        }
        rs.close();
        ps.close();
		return model;
	}

	public ReportModel generateRentalReport() throws SQLException {
		List l1 = new ArrayList();
		List l2 = new ArrayList();
		List l3 = new ArrayList();
		List l4 = new ArrayList();
		long millis = System.currentTimeMillis();
		java.sql.Date currentDate = new java.sql.Date(millis);

        String select_str1 = "SELECT r.rid, v.location, v.city, v.vtname, v.vlicense, r.dlicense, r.confNo, " +
                "r.fromDate, r.toDate " +
                "FROM vehicle v, rental r " +
                "WHERE r.vlicense = v.vlicense AND r.fromDate = ? " +
                "ORDER BY v.location, v.city, v.vtname";
        String select_str2 = "SELECT v.vtname, count(*) as count " +
                "FROM vehicle v, rental r " +
                "WHERE r.vlicense = v.vlicense AND r.fromDate = ? " +
                "GROUP BY v.vtname";
        String select_str3 = "SELECT v.location, v.city, count(*) as count " +
                "r.fromDate, r.toDate " +
                "FROM vehicle v, rental r " +
                "WHERE r.vlicense = v.vlicense AND r.fromDate = ? " +
                "GROUP BY v.location, v.city, v.vtname";
        String select_str4 = "SELECT count(*) as count " +
                "FROM vehicle v, rental r " +
                "WHERE r.vlicense = v.vlicense AND r.fromDate = ? ";
        PreparedStatement ps1 = connection.prepareStatement(select_str1);
        PreparedStatement ps2 = connection.prepareStatement(select_str2);
        PreparedStatement ps3 = connection.prepareStatement(select_str3);
        PreparedStatement ps4 = connection.prepareStatement(select_str4);
        ps1.setDate(1, currentDate);
        ps2.setDate(1, currentDate);
        ps3.setDate(1, currentDate);
        ps4.setDate(1, currentDate);
        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
        ResultSet rs3 = ps3.executeQuery();
        ResultSet rs4 = ps4.executeQuery();
        while (rs1.next()) {
            Object[] o = {rs1.getString(0), rs1.getString(1), rs1.getString(2),
                    rs1.getString(3), rs1.getInt(4), rs1.getInt(5), rs1.getInt(6),
                    rs1.getDate(7), rs1.getDate(8)};
            l1.add(o);
        }
        while (rs2.next()) {
            Object[] o = {rs1.getString(0), rs1.getInt(1)};
            l2.add(o);
        }
        while (rs3.next()) {
            Object[] o = {rs1.getString(0), rs1.getString(1), rs1.getInt(2)};
            l3.add(o);
        }
        while (rs4.next()) {
            Object[] o = {rs1.getInt(0)};
            l4.add(o);
        }
		return new ReportModel(l1, l2, l3 ,l4);
	}

	public BranchReportModel generateRentalReport(String location, String city) throws SQLException {
		List l1 = new ArrayList();
		List l2 = new ArrayList();
		List l3 = new ArrayList();
		long millis = System.currentTimeMillis();
		java.sql.Date currentDate = new java.sql.Date(millis);
        String select_str1 = "SELECT r.rid, v.location, v.city, v.vtname, v.vlicense, r.dlicense, r.confNo, " +
                "r.fromDate, r.toDate " +
                "FROM vehicle v, rental r " +
                "WHERE r.vlicense = v.vlicense AND r.fromDate = ? " +
                "AND v.location = ? AND v.city = ? " +
                "ORDER BY v.vtname";
        String select_str2 = "SELECT v.vtname, count(*) as count " +
                "FROM vehicle v, rental r " +
                "WHERE r.vlicense = v.vlicense AND r.fromDate = ? " +
                "AND v.location = ? AND v.city = ? " +
                "GROUP BY v.vtname";
        String select_str3 = "SELECT count(*) as count " +
                "FROM vehicle v, rental r " +
                "WHERE r.vlicense = v.vlicense AND r.fromDate = ? " +
                "AND v.location = ? AND v.city = ? ";
        PreparedStatement ps1 = connection.prepareStatement(select_str1);
        PreparedStatement ps2 = connection.prepareStatement(select_str2);
        PreparedStatement ps3 = connection.prepareStatement(select_str3);
        ps1.setDate(1, currentDate);
        ps1.setString(2, location);
        ps1.setString(3, city);
        ps2.setDate(1, currentDate);
        ps2.setString(2, location);
        ps2.setString(3, city);
        ps3.setDate(1, currentDate);
        ps3.setString(2, location);
        ps3.setString(3, city);
        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
        ResultSet rs3 = ps3.executeQuery();
        while (rs1.next()) {
            Object[] o = {rs1.getString(0), rs1.getString(1), rs1.getString(2),
                    rs1.getString(3), rs1.getInt(4), rs1.getInt(5), rs1.getInt(6),
                    rs1.getDate(7), rs1.getDate(8)};
            l1.add(o);
        }
        while (rs2.next()) {
            Object[] o = {rs1.getString(0), rs1.getInt(1)};
            l2.add(o);
        }
        while (rs3.next()) {
            Object[] o = {rs1.getInt(0)};
            l3.add(o);
        }
		return new BranchReportModel(l1, l2, l3);
	}

	public ReportModel generateReturnReport() throws SQLException {
		List l1 = new ArrayList();
		List l2 = new ArrayList();
		List l3 = new ArrayList();
		List l4 = new ArrayList();
		long millis = System.currentTimeMillis();
		java.sql.Date currentDate = new java.sql.Date(millis);
        String select_str1 = "SELECT r.rid, v.location, v.city, v.vtname, v.vlicense, r.dlicense, rt.returnDate, " +
                "rt.value, rt.odometer, rt.fullTank " +
                "FROM vehicle v, rental r, rentreturn rt " +
                "WHERE r.rid = rt.rid AND r.vlicense = v.vlicense AND rt.returnDate = ? " +
                "ORDER BY v.location, v.city, v.vtname";
        String select_str2 = "SELECT v.vtname, count(*), sum(rt.value) " +
                "FROM vehicle v, rental r, rentreturn rt " +
                "WHERE r.rid = rt.rid AND r.vlicense = v.vlicense AND rt.returnDate = ? " +
                "GROUP BY v.vtname";
        String select_str3 = "SELECT v.location, v.city, count(*), sum(rt.value) " +
                "FROM vehicle v, rental r, rentreturn rt " +
                "WHERE r.rid = rt.rid AND r.vlicense = v.vlicense AND rt.returnDate = ? " +
                "GROUP BY v.location, v.city";
        String select_str4 = "SELECT count(*), sum(rt.value) " +
                "FROM vehicle v, rental r, rentreturn rt " +
                "WHERE r.rid = rt.rid AND r.vlicense = v.vlicense AND rt.returnDate = ? ";
        PreparedStatement ps1 = connection.prepareStatement(select_str1);
        PreparedStatement ps2 = connection.prepareStatement(select_str2);
        PreparedStatement ps3 = connection.prepareStatement(select_str3);
        PreparedStatement ps4 = connection.prepareStatement(select_str4);
        ps1.setDate(1, currentDate);
        ps2.setDate(1, currentDate);
        ps3.setDate(1, currentDate);
        ps4.setDate(1, currentDate);
        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
        ResultSet rs3 = ps3.executeQuery();
        ResultSet rs4 = ps4.executeQuery();
        while (rs1.next()) {
            Object[] o = {rs1.getInt(0), rs1.getString(1), rs1.getString(2),
                    rs1.getString(3), rs1.getInt(4), rs1.getInt(5), rs1.getDate(6),
                    rs1.getInt(7), rs1.getInt(8), rs1.getString(9)};
            l1.add(o);
        }
        while (rs2.next()) {
            Object[] o = {rs1.getString(0), rs1.getInt(1), rs1.getInt(2)};
            l2.add(o);
        }
        while (rs3.next()) {
            Object[] o = {rs1.getString(0), rs1.getString(1), rs1.getInt(2), rs1.getInt(3)};
            l3.add(o);
        }
        while (rs4.next()) {
            Object[] o = {rs1.getInt(0), rs1.getInt(1)};
            l4.add(o);
        }
		return new ReportModel(l1, l2, l3, l4);
	}

	public BranchReportModel generateReturnReport(String location, String city) throws SQLException {
		List l1 = new ArrayList();
		List l2 = new ArrayList();
		List l3 = new ArrayList();
		long millis = System.currentTimeMillis();
		java.sql.Date currentDate = new java.sql.Date(millis);
        String select_str1 = "SELECT r.rid, v.vtname, v.vlicense, r.dlicense, rt.returnDate, " +
                "rt.value, rt.odometer, rt.fullTank " +
                "FROM vehicle v, rental r, rentreturn rt " +
                "WHERE r.rid = rt.rid AND r.vlicense = v.vlicense AND rt.returnDate = ? " +
                "AND v.location = ? AND v.city = ? " +
                "ORDER BY v.vtname";
        String select_str2 = "SELECT v.vtname, count(*), sum(rt.value) " +
                "FROM vehicle v, rental r, rentreturn rt " +
                "WHERE r.rid = rt.rid AND r.vlicense = v.vlicense AND rt.returnDate = ? " +
                "AND v.location = ? AND v.city = ? " +
                "GROUP BY v.vtname";
        String select_str3 = "SELECT count(*), sum(rt.value) " +
                "FROM vehicle v, rental r, rentreturn rt " +
                "WHERE r.rid = rt.rid AND r.vlicense = v.vlicense AND rt.returnDate = ? " +
                "AND v.location = ? AND v.city = ?";
        PreparedStatement ps1 = connection.prepareStatement(select_str1);
        PreparedStatement ps2 = connection.prepareStatement(select_str2);
        PreparedStatement ps3 = connection.prepareStatement(select_str3);
        ps1.setDate(1, currentDate);
        ps1.setString(2, location);
        ps1.setString(3, city);
        ps2.setDate(1, currentDate);
        ps2.setString(2, location);
        ps2.setString(3, city);
        ps3.setDate(1, currentDate);
        ps3.setString(2, location);
        ps3.setString(3, city);
        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
        ResultSet rs3 = ps3.executeQuery();
        while (rs1.next()) {
            Object[] o = {rs1.getInt(0), rs1.getString(1), rs1.getString(2),
                    rs1.getString(3), rs1.getInt(4), rs1.getInt(5), rs1.getDate(6),
                    rs1.getInt(7), rs1.getInt(8), rs1.getString(9)};
            l1.add(o);
        }
        while (rs2.next()) {
            Object[] o = {rs1.getString(0), rs1.getInt(1), rs1.getInt(2)};
            l2.add(o);
        }
        while (rs3.next()) {
            Object[] o = {rs1.getInt(0), rs1.getInt(1)};
            l3.add(o);
        }
		return new BranchReportModel(l1, l2, l3);
	}
}
