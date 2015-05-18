import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

class AppointmentPanel extends JPanel {

	// takes time as key and outputs the pixel location for placement
	private HashMap<String, Integer> timeLocation = new HashMap<String, Integer>();
	private String[] times = { "8:00 AM", "8:15 AM", "8:30 AM", "8:45 AM",
			"9:00 AM", "9:15 AM", "9:30 AM", "9:45 AM", "10:00 AM", "10:15 AM",
			"10:30 AM", "10:45 AM", "11:00 AM", "11:15 AM", "11:30 AM",
			"11:45 AM", "12:00 PM", "12:15 PM", "12:30 PM", "12:45 PM",
			"1:00 PM", "1:15 PM", "1:30 PM", "1:45 PM", "2:00 PM", "2:15 PM",
			"2:30 PM", "2:45 PM", "3:00 PM", "3:15 PM", "3:30 PM", "3:45 PM",
			"4:00 PM", "4:15 PM", "4:30 PM", "4:45 PM", "5:00 PM", "5:15 PM",
			"5:30 PM", "5:45 PM", "6:00 PM", "6:15 PM", "6:30 PM", "6:45 PM",
			"7:00 PM", "7:15 PM", "7:30 PM", "7:45 PM"};
	JPanel hoverOverPanel = new JPanel();
	JLabel currentDate;
	JPanel panels[];
	private boolean isWeekView;
	JPanel buttonContainerTop;
	JPanel buttonContainerBottom;
	JButton dayView;
	JButton weekView;
	private JButton addAppointment;
	private JButton todayButton;
	private JButton arrowBack;
	private JPanel directionButtonPanel;
	private JButton arrowForward;
	private Appointments[][] appts;
	private JPanel dateContainer;
	private JComboBox comboBox;
	private JPanel nameContainer;
	private JPanel panelContainer;
	JPanel apptArray[];
	String employees[];
	int numOfPanels;
	private Date baseDate;
	private String currentEmployee;
	private JScrollPane scrollPane;
	private Date[] days;
	private int maxNumEmployees;

	public AppointmentPanel(String lUser) {
		setBackground(new Color(70, 80, 90));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new MigLayout("", "[][grow][]",
				"[grow][grow][grow][grow][grow][]"));

		buttonContainerTop = new JPanel();
		buttonContainerTop.setLayout(new BorderLayout());
		arrowForward = new JButton("-->");
		todayButton = new JButton("Today");
		arrowBack = new JButton("<--");
		directionButtonPanel = new JPanel();
		directionButtonPanel.setLayout(new GridLayout(0, 3));
		todayButton.addActionListener(new Handler());
		arrowBack.addActionListener(new Handler());
		arrowForward.addActionListener(new Handler());
		directionButtonPanel.add(arrowBack);
		directionButtonPanel.add(todayButton);
		directionButtonPanel.add(arrowForward);

		dayView = new JButton("Day View");
		dayView.addActionListener(new Handler());

		weekView = new JButton("Week View");
		weekView.addActionListener(new Handler());

		buttonContainerTop.add(directionButtonPanel, BorderLayout.EAST);
		buttonContainerTop.add(dayView, BorderLayout.WEST);

		baseDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		currentDate = new JLabel(dateFormat.format(baseDate),
				SwingConstants.CENTER);

		SQLManager sql = new SQLManager();
		employees = sql.getEmployees();
		currentEmployee = sql.findCurrentEmployee(lUser);

		// combo box to select the current appointment views
		comboBox = new JComboBox(employees);
		comboBox.setSelectedItem(currentEmployee);
		ComboBoxHandler cbh = new ComboBoxHandler();
		comboBox.addItemListener(cbh);

		buttonContainerBottom = new JPanel();
		add(buttonContainerBottom, "cell 1 4,grow");
		add(buttonContainerTop, "cell 1 0, grow");

		// Struts for padding
		JPanel panel_0 = new JPanel();
		panel_0.setLayout(new GridLayout(0, 11, 0, 0));
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_0.add(horizontalStrut);
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel_0.add(horizontalStrut_4);

		// Scroll pane code
		scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 2 1 2,grow");
		panelContainer = new JPanel();
		scrollPane.setViewportView(panelContainer);
		add(scrollPane, "cell 1 2 1 2,grow");
		panelContainer.setLayout(new BoxLayout(panelContainer,
				BoxLayout.LINE_AXIS));

		// Container for time labels
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.PAGE_AXIS));
		panelContainer.add(Box.createRigidArea(new Dimension(20, 0)));
		panelContainer.add(timePanel);
		panelContainer.add(Box.createRigidArea(new Dimension(20, 0)));

		// Time labels
		JLabel[] timeLabels = new JLabel[48];
		for (int i = 0; i < 48; i++) {
			timeLabels[i] = new JLabel(times[i]);
			timePanel.add(timeLabels[i]);
		}

		addAppointment = new JButton("Add Appointment");
		addAppointment.addActionListener(new Handler());
		buttonContainerBottom.setLayout(new GridLayout(0, 2, 0, 0));

		buttonContainerBottom.add(addAppointment);
		buttonContainerBottom.add(comboBox);
		
		// Builds appointments array
		isWeekView = true;
		buildWeekAppointments();
	}

	void redrawPanels() {
		// Different panels every other panel has a different color
		panels = new JPanel[numOfPanels];
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel();
			if (i % 2 == 0) {
				panels[i].setBackground(new Color(255, 250, 205));
			} else {
				panels[i].setBackground(Color.WHITE);
			}
			DateFormat df = new SimpleDateFormat("MM/dd/yy");
			boolean isToday;
			if(isWeekView){
				isToday = df.format(days[i]).equals(df.format(new Date())) ? true
					: false;
				if (!days[i].after(new Date()) && isWeekView) {
					if (!isToday) {

						if (i % 2 == 0) {
							panels[i].setBackground(Color.LIGHT_GRAY);
						} else {
							panels[i].setBackground(Color.CYAN);
						}
					}
				}
			} else {
				isToday = df.format(baseDate).equals(df.format(new Date())) ? true
						: false;
				if (!baseDate.after(new Date()) && isWeekView) {
					if (!isToday) {

						if (i % 2 == 0) {
							panels[i].setBackground(Color.LIGHT_GRAY);
						} else {
							panels[i].setBackground(Color.CYAN);
						}
					}
				}
			}
			 

			isToday = df.format(baseDate).equals(df.format(new Date())) ? true
					: false;
			if (!isWeekView && !baseDate.after(new Date())) {
				if (!isToday) {
					if (i % 2 == 0) {
						panels[i].setBackground(Color.LIGHT_GRAY);
					} else {
						panels[i].setBackground(Color.CYAN);
					}
				}
			}
			panelContainer.add(panels[i]);
			panels[i].setLayout(null);
		}
		/*
		 * These are the separators for the rows. The if-else selects every
		 * other line to be dashed. The loop inside paintComponent makes them
		 * dashed.
		 */
		JSeparator[][] separators = new JSeparator[numOfPanels][24];
		for (int i = 0; i < separators.length; i++) {
			for (int j = 0; j < separators[0].length; j++) {
				if (j % 2 == 0) {
					separators[i][j] = new JSeparator();
				} else {
					separators[i][j] = new JSeparator() {
						private static final long serialVersionUID = 1L;

						public void paintComponent(Graphics g) {
							for (int x = 0; x < 1000; x += 15)
								g.drawLine(x, 0, x + 10, 0);
						}

					};
				}
				separators[i][j].setBounds(0, j * 32, 1000, 2);
				panels[i].add(separators[i][j]);
			}
		}
	}

	public class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JPanel idPanel = (JPanel) e.getSource();
			JLabel idLabel = (JLabel) idPanel.getComponent(2);
			int id = Integer.parseInt(idLabel.getText());
			Appointments app = new Appointments(id);
			DateFormat format = new SimpleDateFormat("MM/dd/yy");
			Date date = null;
			try {
				date = format.parse(app.appointmentDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			boolean isToday = app.appointmentDate.equals(format.format(new Date()));
			if (date.before(new Date()) && !isToday) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Cannot edit passed appointments!");
			} else {
				removeAll();
				setLayout(new FlowLayout());
				JPanel editAppointments = new EditAppointmentPanel(app, employees, times);
				add(editAppointments);
			}
			revalidate();
			repaint();
		}
	}

	public class ComboBoxHandler implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			System.out.println("Changed STATE");
			currentEmployee = (String) e.getItem();
			System.out.println(currentEmployee);
			removePanels();
			remove(dateContainer);
			buildWeekAppointments();
			revalidate();
		}

	}

	public class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addAppointment) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
						JCalendarTest newAppointment = new JCalendarTest(
								employees, times);
						newAppointment
								.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						newAppointment.setResizable(false);
						newAppointment.setVisible(true);

						Dimension dim = Toolkit.getDefaultToolkit()
								.getScreenSize();
						newAppointment.setLocation(dim.width / 2
								- newAppointment.getSize().width / 2,
								dim.height / 2
										- newAppointment.getSize().height / 2);
					}
				});

			} else if (e.getSource() == dayView) {
				isWeekView = false;
				switchView();
			} else if (e.getSource() == todayButton) {
				switchDay(0);
			} else if (e.getSource() == arrowBack) {
				switchDay(-1);
			} else if (e.getSource() == arrowForward) {
				switchDay(1);
			} else if (e.getSource() == weekView) {
				isWeekView = true;
				switchView();
			}
		}
	}

	// Takes a 2-d array of appointment objects. The first index matches
	// up with the five day range, 0-4 respectively. The appointment object
	// contains all the information about the appointment.
	private void placeAppointmentPanels(Color[] colors) {
		for (int i = 0; i < appts.length; i++) {
			apptArray = new JPanel[appts[i].length];
			for (int j = 0; j < appts[i].length; j++) {
				apptArray[j] = new JPanel();
				if (appts[i][j] != null) {
					JLabel nameLabel = new JLabel(appts[i][j].getClientName());
					JLabel timeLabel = new JLabel(
							appts[i][j].getAppointmentStartTime() + " - "
									+ appts[i][j].getAppointmentEndTime());
					int x = timeLocation.get(appts[i][j]
							.getAppointmentStartTime());
					int height = timeLocation.get(appts[i][j]
							.getAppointmentEndTime())
							- timeLocation.get(appts[i][j]
									.getAppointmentStartTime());
					JLabel idLabel = new JLabel(Integer.toString(appts[i][j]
							.getAppointmentID()));
					idLabel.setVisible(false);
					apptArray[j].setBounds(10, x, 150, height);
					if (colors != null) {
						apptArray[j].setBackground(colors[i]);
					} else {
						apptArray[j].setBackground(Color.RED);
					}
					apptArray[j].setLayout(new BorderLayout());
					apptArray[j].add(nameLabel, BorderLayout.NORTH);
					apptArray[j].add(timeLabel, BorderLayout.CENTER);
					apptArray[j].add(idLabel, BorderLayout.SOUTH);
					apptArray[j].addMouseListener(new MouseHandler());
					panels[i].add(apptArray[j]);
				}
			}
		}
	}

	public void removePanels() {
		for (int i = 0; i < panels.length; i++) {
			panelContainer.remove(panels[i]);
		}
	}

	void switchDay(int i) {
		// comboBox.setSelectedItem(currentEmployee);
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate);
		if (i == -1) {
			c.add(Calendar.DATE, -1);
			baseDate = c.getTime();
		} else if (i == 0) {
			baseDate = new Date();
		} else if (i == 1) {
			c.add(Calendar.DATE, 1);
			baseDate = c.getTime();
		}
		DateFormat df = new SimpleDateFormat("MM/dd/yy");
		System.out.println("New base date is:" + df.format(baseDate));
		removePanels();
		if (isWeekView) {
			remove(dateContainer);
			buildWeekAppointments();
			revalidate();
			repaint();
		} else {
			remove(dateContainer);
			remove(nameContainer);
			buildDayAppointments();
			revalidate();
			repaint();
		}
	}

	void switchView() {
		int ids[][] = null;
		System.out.println("is Week view? " + isWeekView);
		removePanels();

		if (isWeekView) {
			if (maxNumEmployees > employees.length) {
				remove(nameContainer);
			}
			buttonContainerTop.remove(weekView);
			buttonContainerTop.remove(currentDate);
			System.out.println("Added combo box");
			buttonContainerBottom.add(comboBox);
			buttonContainerTop.add(dayView, BorderLayout.WEST);
			buildWeekAppointments();
			revalidate();
			repaint();
		} else {
			remove(dateContainer);
			buttonContainerTop.remove(dayView);
			buttonContainerTop.add(weekView, BorderLayout.WEST);
			System.out.println("Removed combo box");
			buttonContainerBottom.remove(comboBox);
			buttonContainerBottom.repaint();
			buttonContainerBottom.validate();
			buildDayAppointments();
			revalidate();
			repaint();

		}
	}

	private void buildDayAppointments() {
		maxNumEmployees = 24;
		Color[] colors = new Color[maxNumEmployees];
		colors[0] = new Color(255, 0, 0);
		colors[1] = new Color(255, 63, 0);
		colors[2] = new Color(255, 125, 0);
		colors[3] = new Color(255, 187, 0);
		colors[4] = new Color(255, 255, 0);
		colors[5] = new Color(187, 255, 0);
		colors[6] = new Color(125, 255, 0);
		colors[7] = new Color(63 , 255, 0);
		colors[8] = new Color(0, 255, 0);
		colors[9] = new Color(0, 255, 63);
		colors[10] = new Color(0, 255, 125);
		colors[11] = new Color(0, 255, 187);
		colors[12] = new Color(0, 255, 255);
		colors[13] = new Color(0, 187, 255);
		colors[14] = new Color(0, 125, 255);
		colors[15] = new Color(0, 63, 255);
		colors[16] = new Color(0, 0, 255);
		colors[17] = new Color(63, 0, 255);
		colors[18] = new Color(125, 0, 255);
		colors[19] = new Color(187, 0, 255);
		colors[20] = new Color(255, 0, 255);
		colors[21] = new Color(255, 0, 187);
		colors[22] = new Color(255, 0, 125);
		colors[23] = new Color(255, 0, 63);
		

		// clear any previous appts
		appts = null;
		// as many panels as employees
		numOfPanels = employees.length;

		if (numOfPanels > maxNumEmployees) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please remove an employee! Maximum allowed is "
							+ maxNumEmployees);
		} else {
			// redraw after setting number of panels
			redrawPanels();

			nameContainer = new JPanel();
			add(nameContainer, "cell 1 1, grow");

			nameContainer.setLayout(new GridLayout(0, numOfPanels + 1));
			JLabel[] employeeLabels = new JLabel[numOfPanels];
			JPanel emptyPanel = new JPanel();
			nameContainer.add(emptyPanel);
			for (int i = 0; i < numOfPanels; i++) {
				employeeLabels[i] = new JLabel(employees[i]);
				nameContainer.add(employeeLabels[i]);
			}

			SQLManager sql = new SQLManager();
			int ids[][] = new int[numOfPanels][];
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
			currentDate.setText(dateFormat.format(baseDate));
			buttonContainerTop.add(currentDate, BorderLayout.CENTER);
			for (int i = 0; i < numOfPanels; i++) {
				ids[i] = sql.employeeAppointments(employees[i],
						dateFormat.format(baseDate));
			}
			appts = new Appointments[numOfPanels][];

			for (int i = 0; i < ids.length; i++) {
				appts[i] = new Appointments[ids[i].length];
				for (int j = 0; j < ids[i].length; j++) {
					if (ids[i][j] == -1) {
						appts[i][j] = null;
					} else {
						appts[i][j] = new Appointments(ids[i][j]);
					}
				}

			}

			placeAppointmentPanels(colors);
		}
	}

	void buildWeekAppointments() {
		// clearing any previous appts
		appts = null;

		// week view of 5 days
		numOfPanels = 5;

		// Panel to hold all of the labels
		dateContainer = new JPanel();
		add(dateContainer, "cell 1 1,grow");
		dateContainer.setLayout(new GridLayout(0, 6, 0, 0));

		// Array for the different days starting with the current day
		days = new Date[numOfPanels];

		// The initial date for which the days will be based off of
		days[0] = baseDate;

		// The format that we want it to be returned
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		Calendar c = Calendar.getInstance();
		c.setTime(days[0]);

		// Starts at 2nd day since first has already
		// been initialized. This loop builds the dates.
		for (int i = 1; i < days.length; i++) {
			c.add(Calendar.DATE, 1);
			days[i] = c.getTime();
		}

		// redraw after setting number of panels
		// and after days[] has been built
		redrawPanels();

		// empty Panel for padding
		JPanel emptyPanel0 = new JPanel();
		dateContainer.add(emptyPanel0);

		// Date labels
		JLabel[] dateLabels = new JLabel[numOfPanels];
		for (int i = 0; i < dateLabels.length; i++) {
			dateLabels[i] = new JLabel(dateFormat.format(days[i]));
			dateLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			dateContainer.add(dateLabels[i]);
		}

		// empty Panel for padding
		JPanel emptyPanel1 = new JPanel();
		dateContainer.add(emptyPanel1);

		// Day of the week labels, just change format
		dateFormat = new SimpleDateFormat("EEEE");
		JLabel[] dayLabels = new JLabel[numOfPanels];
		for (int i = 0; i < dayLabels.length; i++) {
			dayLabels[i] = new JLabel(dateFormat.format(days[i]));
			dayLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			dateContainer.add(dayLabels[i]);
		}

		// SQL manager is used for queries
		SQLManager sql = new SQLManager();

		dateFormat = new SimpleDateFormat("MM/dd/yy");

		// This queries the db for the appointment ids for the appointments
		// that are occurring within the next five days
		int[][] ids = new int[numOfPanels][];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = sql.appointmentSearch(dateFormat.format(days[i]),
					currentEmployee);
		}

		for (int i = 0; i < 48; i++) {
			timeLocation.put(times[i], i * 16);
		}

		appts = new Appointments[numOfPanels][];

		for (int i = 0; i < ids.length; i++) {
			appts[i] = new Appointments[ids[i].length];
			for (int j = 0; j < ids[i].length; j++) {
				if (ids[i][j] == -1) {
					appts[i][j] = null;
				} else {
					appts[i][j] = new Appointments(ids[i][j]);
				}
			}

		}
		Color[] colors = null;
		placeAppointmentPanels(colors);
	}
}
