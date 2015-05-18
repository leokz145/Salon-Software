import java.util.Arrays;

public class SQLTest {

	public SQLTest() {

		SQLManager s = new SQLManager();
//		s.addNewEmployee("Kyle Z", "2391234567", "", "");
//		s.addNewItem("Head & Shoulders Conditioner", 1, 19.99, 256, "None");
		System.out.print("Added new appointment to rowID: "
				+ s.addNewAppointment("Joe W", "2391112222", "bob@s.com",
						"Kyle Z", "kaz", "03/13/15", "2:00 PM", "3:00 PM"));

		System.out.println();
		
//		Appointments a = new Appointments(10);

		// s.updateAppointment("Bob S", "2391112222", "bob@s.com", "Kyle Z",
		// "kaz", "03/07/15", "1400", "1800", 4);

		// s.getAppointment(4);

		// System.out.println(Arrays.toString(s.appointmentSearch("03/07/15")));
	}

}
