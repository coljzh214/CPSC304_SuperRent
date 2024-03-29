package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.BranchModel;

import java.sql.SQLException;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
	public void deleteBranch(String location, String city) throws SQLException;
	public void insertBranch(BranchModel model) throws SQLException;
	public void showBranch() throws SQLException;
	public void vehicleQuery(String carType, String location, String startDate, String endDate);
	// public void updateBranch(int branchId, String name);

	public void terminalTransactionsFinished();
}
