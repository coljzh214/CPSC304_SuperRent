package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.delegates.UiTransactionsDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalTransactions;
import ca.ubc.cs304.ui.UiTransactions;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class SuperRent implements LoginWindowDelegate, UiTransactionsDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;

	public SuperRent() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void init() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}
	
	/**
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
     */ 
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();

			UiTransactions transaction = new UiTransactions();
			//TerminalTransactions transaction = new TerminalTransactions();
			transaction.showMainMenu(this);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}
	
	/**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Insert a branch with the given info
	 */
    public void insertBranch(BranchModel model) throws SQLException {
    	dbHandler.insertBranch(model);
    }

    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Delete branch with given branch ID.
	 */ 
    public void deleteBranch(String location, String city) throws SQLException {
    	dbHandler.deleteBranch(location, city);
    }
    
    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Update the branch name for a specific ID
	 */

	public void updateBranch(String location, String city, BranchModel model) throws Exception{
    	dbHandler.updateBranch(location, city, model);
    }

    /**
	 * TermainalTransactionsDelegate Implementation
	 * 
	 * Displays information about varies bank branches.
	 */
    public void showBranch() throws SQLException {
    	BranchModel[] models = dbHandler.getBranchInfo();
		System.out.printf("There are " + models.length + " results");
		System.out.println();
    	for (int i = 0; i < models.length; i++) {
    		BranchModel model = models[i];

    		// simplified output formatting; truncation may occur
    		System.out.printf("%-10.10s", model.getLocation());
    		System.out.printf("%-20.20s", model.getCity());
    		System.out.println();
    	}
    }
	
    /**
	 * TerminalTransactionsDelegate Implementation
	 * 
     * The TerminalTransaction instance tells us that it is done with what it's 
     * doing so we are cleaning up the connection since it's no longer needed.
     */ 
    public void terminalTransactionsFinished() {
    	dbHandler.close();
    	dbHandler = null;
    	
    	System.exit(0);
    }

    public void vehicleQuery(String carType, String location, String startDate, String endDate)  throws Exception {
			VehicleModel[] models = dbHandler.getVehicleQuery(carType, location, startDate, endDate);
			System.out.println();
			for (int i = 0; i < models.length; i++) {
				VehicleModel model = models[i];

				// simplified output formatting; truncation may occur
				System.out.printf("%-10.10s", model.getVlicense());
				System.out.printf("%-20.20s", model.getVid());
				if (model.getMake() == null) {
					System.out.printf("%-20.20s", " ");
				} else {
					System.out.printf("%-20.20s", model.getMake());
				}
				System.out.printf("%-15.15s", model.getCity());
				if (model.getMake() == null) {
					System.out.printf("%-15.15s", " ");
				} else {
					System.out.printf("%-15.15s", model.getMake());
				}
			}
	}

	public RentalModel processRentalwithReservation(int confNo, String cardName, int cardNo, String expDateString) throws Exception {
    	return dbHandler.processRentalWithReservation(confNo, cardName, cardNo, expDateString);
	}

	public RentalModel processRentalwithoutReservation(String vtname, int dlicense, String fromDateString, String toDateString,
													   String cardName, int cardNo, String expDateString) throws Exception {
		return dbHandler.processRentalWithoutReservation(vtname, dlicense, fromDateString, toDateString, cardName, cardNo, expDateString);
	}

	public RentReturnModel processReturn(int rid, String returnDateString, int odometer, String fullTank) throws Exception {
    	return dbHandler.processReturn(rid, returnDateString, odometer, fullTank);
    }

    public ReportModel generateRentalReport() throws SQLException {
    	return dbHandler.generateRentalReport();
	}

	public BranchReportModel generateRentalReport(String location, String city) throws SQLException {
    	return dbHandler.generateReturnReport(location, city);
	}

	public ReportModel generateReturnReport() throws SQLException {
		return dbHandler.generateReturnReport();
	}

	public BranchReportModel generateReturnReport(String location, String city) throws SQLException {
		return dbHandler.generateReturnReport(location, city);
	}

	public String[] getBranches() {
    	try {
			ArrayList<String> result = new ArrayList<String>();
			result.add("");
			BranchModel[] models = dbHandler.getBranchInfo();
			for (BranchModel m: models) {
				result.add(m.getLocation() + "-" + m.getCity());
			}
			return result.toArray(new String[result.size()]);
		} catch (SQLException e) {
			ArrayList<String> result = new ArrayList<String>();
			result.add("");
    		return result.toArray(new String[result.size()]);
		}
	}

	/**
	 * transaction returns vehicleTypes
	 */
	public String[] getVehicleTypes() {
		try {
			VehicleTypeModel[] models = dbHandler.getVehicleTypeInfo();
			ArrayList<String> ret = new ArrayList<String>();
			System.out.printf("There are " + models.length + " results");
			System.out.println();
			//add empty entry
			ret.add("");
			for (int i = 0; i < models.length; i++) {
				VehicleTypeModel model = models[i];
				ret.add(model.getVtname());
			}
			return ret.toArray(new String[ret.size()]);
		} catch (SQLException e) {
			String[] ret = {""};
			return ret;
		}
	}


	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		SuperRent SuperRent = new SuperRent();
		SuperRent.init();
	}

	public void uiTransactionsFinished() {
    	dbHandler.close();
    	dbHandler = null;
    	
    	System.exit(0);
	}


}
