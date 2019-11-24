package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.BranchModel;

/**
 * This interface uses the delegation design pattern where instead of having
 * the UiTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * UiTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface UiTransactionsDelegate {
	public void deleteBranch(String location, String city) throws Exception;
	public void insertBranch(BranchModel model) throws Exception;
	public void showBranch() throws Exception;
    public void updateBranch(String location, String city, BranchModel model) throws Exception;
    public void vehicleQuery(String carType, String location, String startDate, String endDate) throws Exception;
	
	public void uiTransactionsFinished();
}