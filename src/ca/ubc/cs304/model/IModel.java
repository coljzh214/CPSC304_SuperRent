package ca.ubc.cs304.model;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

import java.sql.*;
import java.util.ArrayList;

/**
 * The intent for this class is to update/store information about a single branch
 */
public abstract class IModel {

	protected String table_name;
	protected static DatabaseConnectionHandler dbh;

	public abstract String[] getTuple();

	public String[] getColumns() throws SQLException {
		String[] ret = dbh.getAbstractTableInfo(table_name);
		return ret;
	}
}
