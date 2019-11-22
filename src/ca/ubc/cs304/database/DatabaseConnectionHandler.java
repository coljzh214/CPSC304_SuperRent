package ca.ubc.cs304.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.CustomerModel;
import ca.ubc.cs304.model.VehicleModel;

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
			ps.setString(1, city);
			
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

	public VehicleModel[] getVehicleQuery(String vType, String location, String startDateString, String endDatestring) throws IllegalArgumentException{
		ArrayList<VehicleModel> result = new ArrayList<VehicleModel>();
		java.sql.Date endDate = null;
		java.sql.Date startDate = null;
		try {
			boolean a = startDateString != null && startDateString != "";
			boolean b = startDateString != null;
			boolean c = startDateString.length() > 0;
			if(startDateString != null && startDateString.length() > 0)
				startDate = Date.valueOf(startDateString);
		} catch (IllegalArgumentException e) {
			System.out.print("Please enter the start time in the format (YYYY-MM-DD): ");
			throw e;
		}
		try {
			if(endDatestring != null && endDatestring.length() > 0)
				endDate = Date.valueOf(endDatestring);
		} catch (IllegalArgumentException e) {
			System.out.print("Please enter the end time in the format (YYYY-MM-DD): ");
			throw e;
		}
		try {
			String sqlQuery = "SELECT DISTINCT v.* FROM Vehicle v, VehicleType vt" +
					" WHERE v.vtname = vt.vtname ";
			if(location.length() > 0) {
				sqlQuery += " AND v.location = " + location;
			}
			if(vType.length() > 0) {
				sqlQuery += " AND vt.vtname = " + vType;
			}

			if(startDate != null && endDate != null) {
				if (startDate.getTime() > endDate.getTime()) {
					throw new IllegalArgumentException("StartDate must be less than endDate");
				}
				sqlQuery += " AND NOT EXISTS  (SELECT * FROM Rental ren WHERE " +
						" v.vlicense = ren.vlisense AND "+ startDate +" > ren.startDate " +
						" AND "+endDate +" < ren.endDate)";
				sqlQuery += " AND NOT EXISTS  (SELECT * FROM Rental ren, Reservation res WHERE " +
						" v.vlicense = ren.vlisense AND ren.confNo = ren.confNo AND "+ startDate +" > res.startDate " +
						" AND "+endDate +" < res.endDate)";
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


//	public void updateBranch(String location, String city) {
//		try {
//		  PreparedStatement ps = connection.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");
//		  ps.setString(1, name);
//		  ps.setInt(2, id);
//
//		  int rowCount = ps.executeUpdate();
//		  if (rowCount == 0) {
//		      System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
//		  }
//
//		  connection.commit();
//
//		  ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//			rollbackConnection();
//		}
//	}


	public void insertCustomer(CustomerModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
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

	public void updateCustomerInfo() {
		return;
	}

	public VehicleModel[] viewAvailableVehicles(String vtname, String location) {
		ArrayList<VehicleModel> result = new ArrayList<VehicleModel>();
		try {
			List array = new ArrayList();
			if (vtname != null) {
				array.add("v.vtname = " + vtname);
			}
			if (location != null) {
				array.add("v.location = " + location);
			}
			String condition_str = String.join(" AND ", array);
			String result_str = "SELECT * " +
									"FROM Vehicle v, VehicleType vt " +
									"WHERE " + condition_str + " AND v.status = 'available' " +
									"AND v.vtname = vt.vtname";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM branch");
			while(rs.next()) {
				// TODO: replace with VehicleModel
//				VehicleModel model = new VehicleModel(rs.getString("branch_addr"),
//						rs.getString("branch_city"),
//						rs.getInt("branch_id"),
//						rs.getString("branch_name"),
//						rs.getInt("branch_phone"));
//				result.add(model);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return null;
	}

	public void createReservation() {
		return;
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
