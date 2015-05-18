import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {
	Connection conn = null;

	void addNewEmployee(String employeeName, String employeeNumber,
			String employeeUsername, String employeePassword, String isAdmin) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "INSERT INTO Employees (employeeName, employeeNumber, employeeUsername, "
					+ "employeePassword, isAdmin) VALUES ('"
					+ employeeName
					+ "', '"
					+ employeeNumber
					+ "', '"
					+ employeeUsername
					+ "', '" + employeePassword + "', '" + isAdmin + "');";
			System.out.println(query);
			int rowCount = stmt.executeUpdate(query);
			System.out.println("Successfully added new user!");

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	void addNewCustomer(String clientName, String clientPhoneNumber,
			String clientEmail, String numOfTransactions, String totalSpent, String clientNotes) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "INSERT INTO Customer (clientName, clientPhoneNumber, clientEmail, "
					+ "numOfTransactions, totalSpent, clientNotes) VALUES ('"
					+ clientName
					+ "', '"
					+ clientPhoneNumber
					+ "', '"
					+ clientEmail
					+ "', '" + numOfTransactions + "', '" + totalSpent + "', '" + clientNotes + "');";
			System.out.println(query);
			int rowCount = stmt.executeUpdate(query);
			System.out.println("Successfully added new user!");

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	void addNewItem(String itemName, double itemPrice, int itemQuantity,
			int itemID, String itemNotes) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "INSERT INTO Inventory (itemName, itemID, itemPrice, itemQuantity, itemNotes) VALUES ('"
					+ itemName
					+ "', '"
					+ itemID
					+ "', '"
					+ itemPrice
					+ "', '"
					+ itemQuantity + "', '" + itemNotes + "');";
			System.out.println(query);
			int rowCount = stmt.executeUpdate(query);
			System.out.println("Successfully added new user!");

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	int addNewAppointment(String clientName, String clientNumber,
			String clientEmail, String employeeName, String employeeUsername,
			String appointmentDate, String appointmentStartTime,
			String appointmentEndTime) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "INSERT INTO Appointments (clientName, clientNumber, clientEmail, employeeName, employeeUsername, appointmentDate, "
					+ "appointmentStartTime, appointmentEndTime) VALUES ('"
					+ clientName
					+ "', '"
					+ clientNumber
					+ "', '"
					+ clientEmail
					+ "', '"
					+ employeeName
					+ "', '"
					+ employeeUsername
					+ "', '"
					+ appointmentDate
					+ "', '"
					+ appointmentStartTime
					+ "', '" + appointmentEndTime + "');";
			System.out.println(query);
			int rowCount = stmt.executeUpdate(query);
			// System.out.println("RowCount = " + rowCount);
			System.out.println("Successfully added new user!");

			stmt.close();
			conn.close();

			return getAppointmentSize();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return 0;
	}

	void updateAppointment(String clientName, String clientNumber,
			String clientEmail, String employeeName, String employeeUsername,
			String appointmentDate, String appointmentStartTime,
			String appointmentEndTime, int appointmentID) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "UPDATE Appointments SET clientName = '"
					+ clientName + "', clientNumber = '" + clientNumber
					+ "', clientEmail = '" + clientEmail
					+ "', employeeName = '" + employeeName
					+ "', employeeUsername = '" + employeeUsername
					+ "', appointmentDate = '" + appointmentDate
					+ "', appointmentStartTime = '" + appointmentStartTime
					+ "', appointmentEndTime = '" + appointmentEndTime
					+ "' WHERE appointmentID = '" + appointmentID + "';";
			System.out.println(query);
			int rowCount = stmt.executeUpdate(query);
			System.out.println("Successfully saved updates!");

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	int getAppointmentSize() {
		Statement stmt = null;
		int index = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT _rowid_ FROM Appointments ;";
			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				index++;
			}

			stmt.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return index;
	}

	boolean checkUserExistence(String username) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT * FROM Employees WHERE employeeUsername = '"
					+ username + "';";
			System.out.println(query);
			int rowCount = 0;
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				rowCount++;
			}
			stmt.close();
			rs.close();
			conn.close();

			if (rowCount == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return false;
	}

	public boolean checkLogin(String username, String password) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT * FROM Employees WHERE employeeUsername = '"
					+ username + "' AND employeePassword = '" + password + "'";
			System.out.println(query);
			int rowCount = 0;
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				rowCount++;
			}

			stmt.close();
			rs.close();
			conn.close();

			if (rowCount == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return false;
	}

	int[] getIDs(String date) {
		int[] IDs = { 0, 0 };

		return IDs;
	}

	String[] getAppointment(int appID) {
		Statement stmt = null;
		String details[] = new String[9];
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT clientName, clientNumber, clientEmail, employeeName, employeeUsername, appointmentDate, "
					+ "appointmentStartTime, appointmentEndTime, appointmentID FROM Appointments WHERE appointmentID = '"
					+ appID + "';";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			while (rs.next()) // or use if(if there is only one row in
								// resultset)
			{
				details[0] = rs.getString("clientName");
				details[1] = rs.getString("clientNumber");
				details[2] = rs.getString("clientEmail");
				details[3] = rs.getString("employeeName");
				details[4] = rs.getString("employeeUsername");
				details[5] = rs.getString("appointmentDate");
				details[6] = rs.getString("appointmentStartTime");
				details[7] = rs.getString("appointmentEndTime");
				details[8] = rs.getString("appointmentID");
			}

			for (int i = 0; i < 8; i++) {
				System.out.println(details[i]);
			}

			System.out.println("AppID = " + appID);

			stmt.close();
			rs.close();
			conn.close();

			System.out.println("Retrieved appointment info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return details;
	}

	String findCurrentEmployee(String username) {
		String employeeName = null;
		Statement stmt = null;
		int id[] = null;
		int index = 0;

		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT employeeName from employees where employeeUsername='"
					+ username + "'";
			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				employeeName = rs.getString("employeeName");
				index++;
			}

			stmt.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return employeeName;
	}

	// Takes in a string of the employee name and returns
	// the ids of the appts in an array.
	int[] employeeAppointments(String employee, String baseDate) {
		Statement stmt = null;
		int id[] = null;
		int index = 0;

		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT count(*) FROM Appointments WHERE employeeName = '"
					+ employee + "' and appointmentDate = '" + baseDate + "'";
			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int size = Integer.parseInt(rs.getString("count(*)"));
			id = new int[size];

			if (size > 0) {
				query = "SELECT _rowid_ FROM Appointments WHERE employeeName = '"
						+ employee
						+ "' and appointmentDate = '"
						+ baseDate
						+ "'";
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					id[index] = Integer.parseInt(rs.getString("appointmentID"));
					index++;
				}
			} else {
				id = new int[1];
				id[0] = -1;
			}

			stmt.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return id;
	}

	int[] appointmentSearch(String appDate, String employee) {
		Statement stmt = null;
		int id[] = null;
		int index = 0;

		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT count(*) FROM Appointments WHERE appointmentDate = '"
					+ appDate + "' and employeeName='" + employee + "'";
			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int size = Integer.parseInt(rs.getString("count(*)"));
			id = new int[size];

			if (size > 0) {
				query = "SELECT (_rowid_)appointmentID FROM Appointments WHERE appointmentDate = '"
						+ appDate + "' and employeeName='" + employee + "'";
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					id[index] = Integer.parseInt(rs.getString("appointmentID"));
					index++;
				}
			} else {
				id = new int[1];
				id[0] = -1;
			}

			stmt.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return id;
	}

	String[] getInventoryRow(int rowNum) {
		Statement stmt = null;
		String details[] = new String[5];
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT itemName, itemPrice, itemQuantity, itemID, itemNotes FROM Inventory WHERE _rowid_ = '"
					+ rowNum + "';";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			while (rs.next()) // or use if(if there is only one row in
								// resultset)
			{
				details[0] = rs.getString("itemName");
				details[1] = rs.getString("itemPrice");
				details[2] = rs.getString("itemQuantity");
				details[3] = rs.getString("itemID");
				details[4] = rs.getString("itemNotes");
			}

			for (int i = 0; i < 5; i++) {
				// System.out.println(details[i]);
			}

			stmt.close();
			rs.close();
			conn.close();

			// System.out.println("Retrieved appointment info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return details;
	}

	int getInventorySize() {
		Statement stmt = null;
		int index = 0;
		try {
			// Class.forName("org.sqlite.JDBC");
			// conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			// System.out.println("Opened database successfully");
			// stmt = conn.createStatement();
			// String query = "SELECT _rowid_ FROM Inventory ;";
			// System.out.println(query);
			//
			// ResultSet rs = stmt.executeQuery(query);
			//
			// while (rs.next()) {
			// index++;
			// }
			//
			// stmt.close();
			// rs.close();
			// conn.close();

			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT COUNT(*) AS rowcount FROM Inventory";
			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);

			rs.next();
			index = rs.getInt("rowcount");

			System.out.print("The Inventory has this many rows: " + index);

			stmt.close();
			// rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return index;
	}

	public String[] getEmployees() {
		Statement stmt = null;
		String employees[] = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT count(*) FROM Employees";
			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int size = Integer.parseInt(rs.getString("count(*)"));
			employees = new String[size];

			query = "SELECT employeeName FROM Employees";
			rs = stmt.executeQuery(query);
			int index = 0;
			while (rs.next()) {
				employees[index] = rs.getString("employeeName");
				index++;
			}

			stmt.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return employees;
	}

	public void setInventoryQuantity(int itemID, int quantityAdded) {

		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "UPDATE Inventory SET itemQuantity = '"
					+ quantityAdded + "' WHERE itemID = '" + itemID + "';";
			stmt.executeUpdate(query);
			System.out.println(query);

			stmt.close();
			// rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	public int getNextItemID() {

		Statement stmt = null;
		int nextID = 0;

		try {

			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT * FROM Inventory WHERE itemID = (SELECT MAX(itemID) FROM Inventory)";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			while (rs.next()) {
				nextID = Integer.parseInt(rs.getString("itemID"));
			}

			stmt.close();
			rs.close();
			conn.close();

			nextID++;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		return nextID;
	}

	public void removeInventoryRow(int itemID) {

		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "DELETE FROM Inventory WHERE itemID = '" + itemID
					+ "';";
			stmt.executeUpdate(query);
			System.out.println(query);

			stmt.close();
			// rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	public boolean checkAdmin(String username) {

		Statement stmt = null;
		boolean isAdmin = false;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT * FROM Employees WHERE employeeUsername = '"
					+ username + "';";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			rs.next();
			String s = rs.getString("isAdmin");

			if (s.equals("TRUE")) {
				isAdmin = true;
			}

			stmt.close();
			// rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		return isAdmin;
	}

	int getEmployeeSize() {
		Statement stmt = null;
		int index = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT _rowid_ FROM Employees ;";
			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				index++;
			}

			stmt.close();
			rs.close();
			conn.close();

			// Class.forName("org.sqlite.JDBC");
			// conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			// System.out.println("Opened database successfully");
			// stmt = conn.createStatement();
			// String query = "SELECT COUNT(*) AS rowcount FROM Inventory";
			// System.out.println(query);
			//
			// ResultSet rs = stmt.executeQuery(query);
			//
			// rs.next();
			// index = rs.getInt("rowcount") ;
			//
			// System.out.print("The Inventory has this many rows: " + index);
			//
			// stmt.close();
			// // rs.close();
			// conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return index;
	}

	public String[] getEmployeeRow(int rowNum) {
		Statement stmt = null;
		String details[] = new String[5];
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT employeeName, employeeNumber, employeeUsername, employeePassword, isAdmin FROM Employees WHERE _rowid_ = '"
					+ rowNum + "';";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			while (rs.next()) // or use if(if there is only one row in
								// resultset)
			{
				details[0] = rs.getString("employeeName");
				details[1] = rs.getString("employeeNumber");
				details[2] = rs.getString("employeeUsername");
				details[3] = rs.getString("employeePassword");
				details[4] = rs.getString("isAdmin");
			}

			for (int i = 0; i < 5; i++) {
				// System.out.println(details[i]);
			}

			stmt.close();
			rs.close();
			conn.close();

			// System.out.println("Retrieved appointment info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return details;
	}

	public String[] getEmployeeRow(String username) {
		Statement stmt = null;
		String details[] = new String[5];
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT employeeName, employeeNumber, employeeUsername, employeePassword, isAdmin FROM Employees WHERE employeeUsername = '"
					+ username + "';";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			while (rs.next()) // or use if(if there is only one row in
								// resultset)
			{
				details[0] = rs.getString("employeeName");
				details[1] = rs.getString("employeeNumber");
				details[2] = rs.getString("employeeUsername");
				details[3] = rs.getString("employeePassword");
				details[4] = rs.getString("isAdmin");
			}

			for (int i = 0; i < 5; i++) {
				// System.out.println(details[i]);
			}

			stmt.close();
			rs.close();
			conn.close();

			// System.out.println("Retrieved appointment info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return details;
	}


	int getCustomerSize() {
		Statement stmt = null;
		int index = 0;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT _rowid_ FROM Customer ;";
			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				index++;
			}

			stmt.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return index;
	}

	public String[] getCustomerRow(int rowNum) {
		Statement stmt = null;
		String details[] = new String[6];
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT clientName, clientPhoneNumber, clientEmail, numOfTransactions, totalSpent, clientNotes FROM Customer WHERE _rowid_ = '"
					+ rowNum + "';";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			while (rs.next()) // or use if(if there is only one row in
								// resultset)
			{
				details[0] = rs.getString("clientName");
				details[1] = rs.getString("clientPhoneNumber");
				details[2] = rs.getString("clientEmail");
				details[3] = rs.getString("numOfTransactions");
				details[4] = rs.getString("totalSpent");
				details[5] = rs.getString("clientNotes");
			}

			for (int i = 0; i < 5; i++) {
				// System.out.println(details[i]);
			}

			stmt.close();
			rs.close();
			conn.close();

			// System.out.println("Retrieved appointment info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return details;
	}

	public String[] getCustomerRow(String email) {
		Statement stmt = null;
		String details[] = new String[6];
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT clientName, clientPhoneNumber, clientEmail, numOfTransactions, totalSpent, clientNotes FROM Customer WHERE clientEmail = '"
					+ email + "';";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			while (rs.next()) // or use if(if there is only one row in
								// resultset)
			{
				details[0] = rs.getString("clientName");
				details[1] = rs.getString("clientPhoneNumber");
				details[2] = rs.getString("clientEmail");
				details[3] = rs.getString("numOfTransactions");
				details[4] = rs.getString("totalSpent");
				details[5] = rs.getString("clientNotes");
			}

			for (int i = 0; i < 5; i++) {
				// System.out.println(details[i]);
			}

			stmt.close();
			rs.close();
			conn.close();

			// System.out.println("Retrieved appointment info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return details;
	}

	public String[] getClientInfo(String clientEmail) {
		Statement stmt = null;
		String details[] = new String[6];
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "SELECT clientEmail, clientName, clientPhoneNumber, numOfTransactions, totalSpent, clientNotes FROM Customer WHERE clientEmail = '"
					+ clientEmail + "';";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(query);

			while (rs.next()) // or use if(if there is only one row in
								// resultset)
			{
				details[0] = rs.getString("clientEmail");
				details[1] = rs.getString("clientName");
				details[2] = rs.getString("clientPhoneNumber");
				details[3] = rs.getString("numOfTransactions");
				details[4] = rs.getString("totalSpent");
				details[5] = rs.getString("clientNotes");
			}

			for (int i = 0; i < 5; i++) {
				// System.out.println(details[i]);
			}

			stmt.close();
			rs.close();
			conn.close();

			// System.out.println("Retrieved appointment info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return details;

	}

	// TODO update customer table for the client, as long as clientEmail != ""
	public void logTransaction(int transaction, double total, String clientEmail) {
		Statement stmt = null;
		String details[] = new String[6];
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();

			// "SET itemQuantity = '" + quantityAdded + "' WHERE"

			String query = "UPDATE Customer SET numOfTransactions = '"
					+ transaction + "' , totalSpent = '" + total
					+ "'  WHERE clientEmail = '" + clientEmail + "';";
			int rowCount = stmt.executeUpdate(query);
			System.out.println(query);

			System.out.println("Successfully saved updates!");

			stmt.close();
			// rs.close();
			conn.close();

			// System.out.println("Retrieved appointment info");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

	public void removeEmployeeRow(String username) {

		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "DELETE FROM Employees WHERE employeeUsername = '"
					+ username + "';";
			stmt.executeUpdate(query);
			System.out.println(query);

			stmt.close();
			// rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	public void removeCustomerRow(String email) {

		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "DELETE FROM Customer WHERE clientEmail = '"
					+ email + "';";
			stmt.executeUpdate(query);
			System.out.println(query);

			stmt.close();
			// rs.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	void updateEmployee(String originalUsername, String employeeName,
			String employeeNumber, String employeeUsername,
			String employeePassword, String isAdmin) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "UPDATE Employees SET employeeName = '"
					+ employeeName + "', employeeNumber = '" + employeeNumber
					+ "', employeeUsername = '" + employeeUsername
					+ "', employeePassword = '" + employeePassword
					+ "', isAdmin = '" + isAdmin
					+ "' WHERE employeeUsername = '" + originalUsername + "';";
			System.out.println(query);
			int rowCount = stmt.executeUpdate(query);
			System.out.println("Successfully added new user!");

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	void updateCustomer(String originalEmail, String clientName,
			String clientPhoneNumber, String clientEmail,
			String numOfTransactions, String totalSpent, String clientNotes) {
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:DataBase");
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String query = "UPDATE Customer SET clientName = '"
					+ clientName + "', clientPhoneNumber = '" + clientPhoneNumber
					+ "', clientEmail = '" + clientEmail
					+ "', numOfTransactions = '" + numOfTransactions
					+ "', totalSpent = '" + totalSpent
					+ "', clientNotes = '" + clientNotes
					+ "' WHERE clientEmail = '" + originalEmail + "';";
			System.out.println(query);
			int rowCount = stmt.executeUpdate(query);
			System.out.println("Successfully added new user!");

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
}