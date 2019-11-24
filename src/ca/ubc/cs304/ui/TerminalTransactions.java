package ca.ubc.cs304.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.BranchModel;

/**
 * The class is only responsible for handling terminal text inputs. 
 */
public class TerminalTransactions {
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";
	private static final int INVALID_INPUT = Integer.MIN_VALUE;
	private static final int EMPTY_INPUT = 0;
	
	private BufferedReader bufferedReader = null;
	private TerminalTransactionsDelegate delegate = null;

	public TerminalTransactions() {
	}

	/**
	 * Displays simple text interface
	 */ 
	public void showMainMenu(TerminalTransactionsDelegate delegate) {
		this.delegate = delegate;
		
	    bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = INVALID_INPUT;
		
		while (choice != 5) {
			System.out.println();
			System.out.println("1. Insert branch");
			System.out.println("2. Delete branch");
			// System.out.println("3. Update branch name");
			System.out.println("4. Show branch");
			System.out.println("5. Quit");
			System.out.println("6. CustomerQuery");
			System.out.print("Please choose one of the above 5 options: ");

			choice = readInteger(false);

			System.out.println(" ");

			if (choice != INVALID_INPUT) {
				switch (choice) {
				case 1:  
					handleInsertOption(); 
					break;
				case 2:  
					handleDeleteOption(); 
					break;
				case 3: 
					// handleUpdateOption();
					break;
				case 4:  
					delegate.showBranch(); 
					break;
				case 5:
					handleQuitOption();
					break;
				case 6:
					handleCustomerQuery();
					break;
				default:
					System.out.println(WARNING_TAG + " The number that you entered was not a valid option.");
					break;
				}
			}
		}		
	}
	
	private void handleDeleteOption() {
		String location = null;
		while (location == null || location.length() <= 0) {
			System.out.print("Please enter the branch location you wish to insert: ");
			location = readLine().trim();
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the branch city you wish to insert: ");
			city = readLine().trim();
		}

		delegate.deleteBranch(location, city);
	}

	private void handleCustomerQuery() {
		String location = null;
		while (location == null) {
			System.out.print("Please enter the branch location to query: ");
			location = readLine().trim();
		}
		String carType = null;
		while (carType == null) {
			System.out.print("Please enter carType to query: ");
			carType = readLine().trim();
		}
		String TimeStartInterval = null;
		while (TimeStartInterval == null) {
			System.out.print("Please enter the start of the time interval to query (YYYY-MM-DD): ");
			TimeStartInterval = readLine().trim();
		}
		String TimeEndInterval = null;
		while (TimeEndInterval == null) {
			System.out.print("Please enter the end of the time interval to query (YYYY-MM-DD): ");
			TimeEndInterval = readLine().trim();
		}
		delegate.vehicleQuery(carType, location, TimeStartInterval, TimeEndInterval);
	}
	
	private void handleInsertOption() {
		String location = null;
		while (location == null || location.length() <= 0) {
			System.out.print("Please enter the branch name you wish to insert: ");
			location = readLine().trim();
		}

		String city = null;
		while (city == null || city.length() <= 0) {
			System.out.print("Please enter the branch city you wish to insert: ");
			city = readLine().trim();
		}

		
		BranchModel model = new BranchModel(location,
											city);
		delegate.insertBranch(model);
	}
	
	private void handleQuitOption() {
		System.out.println("Good Bye!");
		
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("IOException!");
			}
		}
		
		delegate.terminalTransactionsFinished();
	}
	
//	private void handleUpdateOption() {
//		int id = INVALID_INPUT;
//		while (id == INVALID_INPUT) {
//			System.out.print("Please enter the branch ID you wish to update: ");
//			id = readInteger(false);
//		}
//
//		String name = null;
//		while (name == null || name.length() <= 0) {
//			System.out.print("Please enter the branch name you wish to update: ");
//			name = readLine().trim();
//		}
//
//		delegate.updateBranch(id, name);
//	}
	
	private int readInteger(boolean allowEmpty) {
		String line = null;
		int input = INVALID_INPUT;
		try {
			line = bufferedReader.readLine();
			input = Integer.parseInt(line);
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		} catch (NumberFormatException e) {
			if (allowEmpty && line.length() == 0) {
				input = EMPTY_INPUT;
			} else {
				System.out.println(WARNING_TAG + " Your input was not an integer");
			}
		}
		return input;
	}
	
	private String readLine() {
		String result = null;
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}
}
