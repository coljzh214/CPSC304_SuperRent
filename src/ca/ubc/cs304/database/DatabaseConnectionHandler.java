package ca.ubc.cs304.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

	public void initializeDatabase() throws SQLException, IOException {
		BufferedReader reader = null;
		Statement stmt;
		try {
			stmt = connection.createStatement();
			reader = new BufferedReader(new FileReader("create_tables.sql"));
			StringBuilder b = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				b.append(line);
			}
			String[] statements = b.toString().split(";");
			for (String statement: statements) {
				if (statement.trim().isEmpty()) {
					continue;
				}
				stmt.execute(statement);
			}
		} catch (Exception e) {
				e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
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
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void deleteBranch(String location, String city) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM branch WHERE location = ? AND city = ?");
			ps.setString(1, location);
			ps.setString(2, city);
			
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + location +" " + city + " does not exist!");
			}
			
			connection.commit();
	
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	public void deleteVehicleType(String vtname) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM VehicleType WHERE vtname = ? ");
			ps.setString(1, vtname);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " VechicleType " + vtname + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteVehicle(String vlicense) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Vehicle WHERE vlicense = ? ");
			ps.setString(1, vlicense);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Vechicle " + vlicense + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteReservation(int confNo) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Reservation WHERE confNo = ? ");
			ps.setInt(1, confNo);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Customer " + confNo + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteRental(int rid) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM Rental WHERE rid = ? ");
			ps.setInt(1, rid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Customer " + rid + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteRentReturn(int rid) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM RentReturn WHERE rid = ? ");
			ps.setInt(1, rid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Customer " + rid + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertBranch(BranchModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?)");
			ps.setString(1, model.getLocation());
			ps.setString(2, model.getCity());

			ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public String[] getTableInfo() {
		ArrayList<String> result = new ArrayList<String>();

		try {
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

			while(rs.next()) {
				String table = rs.getString("table_name");
				result.add(table);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new String[result.size()]);
	}

	public RentReturnModel[] getRentReturnInfo() {
		ArrayList<RentReturnModel> result = new ArrayList<RentReturnModel>();

		try {
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

			while(rs.next()) {
				RentReturnModel model = new RentReturnModel(rs.getInt("rid"),
						rs.getDate("returnDate"),
						rs.getInt("odometer"),
						rs.getString("fullTank") == "1",
						rs.getInt("value"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new RentReturnModel[result.size()]);
	}

	public RentalModel[] getRentalInfo() {
		ArrayList<RentalModel> result = new ArrayList<RentalModel>();

		try {
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

			while(rs.next()) {
				RentalModel model = new RentalModel(rs.getInt("rid"),
						rs.getInt("vlicense"),
						rs.getInt("dlicense"),
						rs.getInt("confNo"),
						rs.getDate("fromDate"),
						rs.getDate("toDate"),
						rs.getString("cardName"),
						rs.getInt("cardNo"),
						rs.getDate("expDate"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new RentalModel[result.size()]);
	}

	public ReservationModel[] getReservationInfo() {
		ArrayList<ReservationModel> result = new ArrayList<ReservationModel>();

		try {
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

			while(rs.next()) {
				ReservationModel model = new ReservationModel(rs.getInt("confNo"),
						rs.getString("vtname"),
						rs.getInt("dlicense"),
						rs.getDate("fromDate"),
						rs.getDate("toDate"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new ReservationModel[result.size()]);
	}

	public CustomerModel[] getCustomerInfo() {
		ArrayList<CustomerModel> result = new ArrayList<CustomerModel>();

		try {
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

			while(rs.next()) {
				CustomerModel model = new CustomerModel(rs.getInt("dlicense"),
						rs.getString("name"),
						rs.getString("address"),
						rs.getInt("phonenumber"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new CustomerModel[result.size()]);
	}

	public VehicleTypeModel[] getVehicleTypeInfo() {
		ArrayList<VehicleTypeModel> result = new ArrayList<VehicleTypeModel>();

		try {
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

			while(rs.next()) {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new VehicleTypeModel[result.size()]);
	}

	public VehicleModel[] getVehicleInfo() {
		ArrayList<VehicleModel> result = new ArrayList<VehicleModel>();

		try {
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

			while(rs.next()) {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new VehicleModel[result.size()]);
	}

	public BranchModel[] getBranchInfo() {
		ArrayList<BranchModel> result = new ArrayList<BranchModel>();
		
		try {
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
			
			while(rs.next()) {
				BranchModel model = new BranchModel(rs.getString("location"),
													rs.getString("city"));
				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		
		return result.toArray(new BranchModel[result.size()]);
	}

	public VehicleModel[] getVehicleQuery(String vType, String location, String startDateString, String endDateString) throws IllegalArgumentException{
		ArrayList<VehicleModel> result = new ArrayList<VehicleModel>();
		java.sql.Date endDate = null;
		java.sql.Date startDate = null;
		try {
			if(startDateString != null && startDateString.length() > 0)
				startDate = Date.valueOf(startDateString);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		try {
			if(endDateString != null && endDateString.length() > 0)
				endDate = Date.valueOf(endDateString);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		try {
			String sqlQuery = "SELECT DISTINCT v.* FROM Vehicle v, VehicleType vt" +
					" WHERE v.vtname = vt.vtname ";
			if(location.length() > 0) {
				sqlQuery += " AND v.location = '" + location + "'";
			}
			if(vType.length() > 0) {
				sqlQuery += " AND vt.vtname = '" + vType + "'";
			}

			if(startDate != null && endDate != null) {
				if (startDate.getTime() > endDate.getTime()) {
					throw new IllegalArgumentException("StartDate must be less than endDate");
				}
				sqlQuery += " AND NOT EXISTS  (SELECT * FROM Rental ren WHERE " +
						" v.vlicense = ren.vlicense AND (to_date('"+ startDate +"'), to_date('" + endDate + "')) OVERLAPS (ren.fromDate, ren.toDate))";
				sqlQuery += " AND NOT EXISTS  (SELECT * FROM Rental ren, Reservation res WHERE " +
						" v.vlicense = ren.vlicense AND ren.confNo = ren.confNo AND (to_date('"+ startDate +"'), to_date('" + endDate + "')) OVERLAPS (res.fromDate, res.toDate))";
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

			while(rs.next()) {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new VehicleModel[result.size()]);
	}


	public void updateBranch(String location, String city, BranchModel model) {
		try {
		  	PreparedStatement ps = connection.prepareStatement("UPDATE branch SET location = ?, city = ? WHERE location = ? AND city =?");
			ps.setString(1, model.getLocation());
			ps.setString(2, model.getCity());
		  	ps.setString(3, location);
		  	ps.setString(4, city);

		  	int rowCount = ps.executeUpdate();
		  	if (rowCount == 0) {
		      System.out.println(WARNING_TAG + " Branch " + location +", "+ city + " does not exist!");
		  }

		  connection.commit();

		  ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateVehicleType(String vtname, VehicleTypeModel model) {
		try {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertReservation(ReservationModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Reservation VALUES (?,?,?,?,?)");
			ps.setInt(1, model.getConfNo());
			ps.setString(2, model.getVtname());
			ps.setInt(3, model.getPhonenumber());
			ps.setDate(4, model.getFromDate());
			ps.setDate(5, model.getToDate());

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
	public void updateReservation(int confNo, ReservationModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Reservation SET confNo = ?, vtname = ?," +
					" dlicense = ?, fromDate = ?, toDate = ? WHERE confNo = ?");
			ps.setInt(1, model.getConfNo());
			ps.setString(2, model.getVtname());
			ps.setInt(3, model.getPhonenumber());
			ps.setDate(4, model.getFromDate());
			ps.setDate(5, model.getToDate());
			ps.setInt(6, confNo);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertVehicleType(VehicleTypeModel model) {
		try {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertVehicle(VehicleModel model) {
		try {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateVehicle(int vlicense, VehicleModel model) {
		try {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertRental(RentalModel model) {
		try {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateRental(int rid, RentalModel model) {
		try {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertRentReturn(RentReturnModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO RentReturn VALUES (?,?,?,?,?)");
			ps.setInt(1, model.getRid());
			ps.setDate(2, model.getReturnDate());
			ps.setInt(3, model.getOdometer());
			ps.setBoolean(4, model.isFulltank());
			ps.setInt(5, model.getValue());

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertRentReturn(int rid, RentReturnModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE RentReturn SET rid = ?, returnDate = ?," +
					" odometer = ?, fullTank = ?, value = ? WHERE rid = ?");
			ps.setInt(1, model.getRid());
			ps.setDate(2, model.getReturnDate());
			ps.setInt(3, model.getOdometer());
			ps.setBoolean(4, model.isFulltank());
			ps.setInt(5, model.getValue());
			ps.setInt(1, rid);

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertCustomer(CustomerModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
			ps.setInt(1, model.getDlicense());
			ps.setString(2, model.getName());
			ps.setString(3, model.getAddress());
			ps.setInt(4, model.getPhonenumber());

			ps.executeUpdate();
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateCustomer(int dlicense, CustomerModel model) {
		try {
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
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	public void deleteCustomer(int dLicense) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM customer WHERE dLicense = ?");
			ps.setInt(1, dLicense);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Customer with license " + dLicense + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateCustomer() {
		return;
	}

	public ReservationModel createReservation(ReservationModel res, CustomerModel Customer) throws Exception {
		try {
			Statement stmt = connection.createStatement();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Customer WHERE dlicense = ?");
			ps.setInt(1, Customer.getDlicense());

			VehicleModel[] availablevehicles = getVehicleQuery(res.getVtname(), "", res.getFromDate().toString(), res.getToDate().toString());
			if(availablevehicles.length <= 0) {
				throw new Exception("There are no vehicles of that type available");
			}

			ResultSet rs = ps.executeQuery();
			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// decides whether to create Customer or not
			if(!rs.next()) {
				insertCustomer(Customer);
			}
			insertReservation(res);
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return res;
	}

	public void updateVehicleStatus(String status, int vlicense) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE Vehicle SET status = ? WHERE vlicense = ?");
			ps.setString(1, status);
			ps.setInt(2, vlicense);
			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Vehicle " + vlicense + " does not exist!");
			}
			connection.commit();
			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void processRental() {
		return;
	}

	public void processReturn() {
		return;
	}

	public void generateRentalReport() {
		return;
	}

	public void generateRentalReport(String location, String city) {
		return;
	}

	public void generateReturnReport() {
		return;
	}

	public void generateReturnReport(String location, String city) {
		return;
	}
}
