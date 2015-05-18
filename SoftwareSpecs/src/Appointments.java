public class Appointments {
	String clientName;
	String clientNumber;
	String clientEmail;
	String employeeName;
	String employeeUsername;
	String appointmentDate;
	String appointmentStartTime;
	String appointmentEndTime;
	int appointmentID;

	public Appointments() {

	}

	public Appointments(int appID) {
		appointmentID = appID;
		SQLManager sql = new SQLManager();

		String[] s = sql.getAppointment(appID);

		clientName = s[0];
		clientNumber = s[1];
		clientEmail = s[2];
		employeeName = s[3];
		employeeUsername = s[4];
		appointmentDate = s[5];
		appointmentStartTime = s[6];
		appointmentEndTime = s[7];
	}

	public void addAppointment(String clientName, String clientNumber,
			String clientEmail, String employeeName, String employeeUsername,
			String appointmentDate, String appointmentStartTime,
			String appointmentEndTime, int appointmentID) {

		SQLManager sql = new SQLManager();
		this.appointmentID = sql.addNewAppointment(clientName, clientNumber,
				clientEmail, employeeName, employeeUsername, appointmentDate,
				appointmentStartTime, appointmentEndTime);

		this.clientName = clientName;
		this.clientNumber = clientNumber;
		this.clientEmail = clientEmail;
		this.employeeName = employeeName;
		this.employeeUsername = employeeUsername;
		this.appointmentDate = appointmentDate;
		this.appointmentStartTime = appointmentStartTime;
		this.appointmentEndTime = appointmentEndTime;
	}

	public void updateAppointment(String clientName, String clientNumber,
			String clientEmail, String employeeName, String employeeUsername,
			String appointmentDate, String appointmentStartTime,
			String appointmentEndTime, int appointmentID) {

		SQLManager sql = new SQLManager();
		sql.updateAppointment(clientName, clientNumber, clientEmail,
				employeeName, employeeUsername, appointmentDate,
				appointmentStartTime, appointmentEndTime, appointmentID);

		this.clientName = clientName;
		this.clientNumber = clientNumber;
		this.clientEmail = clientEmail;
		this.employeeName = employeeName;
		this.employeeUsername = employeeUsername;
		this.appointmentDate = appointmentDate;
		this.appointmentStartTime = appointmentStartTime;
		this.appointmentEndTime = appointmentEndTime;
		this.appointmentID = appointmentID;
	}

	public String getClientName() {
		return clientName;
	}

	public String getClientNumber() {
		return clientNumber;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getEmployeeUsername() {
		return employeeUsername;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public String getAppointmentStartTime() {
		return appointmentStartTime;
	}

	public String getAppointmentEndTime() {
		return appointmentEndTime;
	}

	public int getAppointmentID() {
		return appointmentID;
	}
}
