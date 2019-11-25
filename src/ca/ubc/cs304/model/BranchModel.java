package ca.ubc.cs304.model;

import java.sql.SQLException;

/**
 * The intent for this class is to update/store information about a single branch
 */
public class BranchModel extends IModel{
	private final String location;
	private final String city;

	@Override
	public String[] getTuple() {
		String[] ret = {this.location, this.city};
		return ret;
	}

	public BranchModel(String location, String city) {
		this.location = location;
		this.city = city;
		table_name = "branch";
	}

	public String getLocation() {
		return location;
	}

	public String getCity() {
		return city;
	}
}
