/* * A manual example using JCalendarButton. * JCalendarTest.java * Created on Aug 24, 2009, 10:47:11 PM * Copyright © 2012 jbundle.org. All rights reserved. */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;
import org.jbundle.thin.base.screen.jcalendarbutton.JTimeButton;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/** * * @author don */
public class JCalendarTest extends javax.swing.JFrame {
	/**
	 * Creates new form NewJFrame
	 * 
	 * @param employees
	 */
	String[] employees;
	private String[] times;

	public JCalendarTest(String[] lEmployees, String[] lTimes) {
		employees = lEmployees;
		times = lTimes;
		initComponents();
	}

	private void initComponents() {
		clientNameLabel = new JLabel("Client Name:");
		clientNameTextField = new JTextField();
		dateLabel = new JLabel("Date:");
		dateTextField = new JTextField();
		dateTextField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				String date = dateTextField.getText();
				setDate(date);
			}
		});
		startTimeLabel = new JLabel("Start Time:");
		startTimeComboBox = new JComboBox(times);
		endTimeLabel = new JLabel("End Time:");
		endTimeComboBox = new JComboBox(times);
		phoneLabel = new JLabel("Phone Number:");
		phoneTextField = new JTextField();
		emailLabel = new JLabel("Email:");
		emailTextField = new JTextField();

		submitButton = new JButton("Add Appointment");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == submitButton) {
					Date date = null;
					if(!dateTextField.getText().equals("")){
						Calendar c = Calendar.getInstance();
						int year = Integer.parseInt(dateTextField.getText().substring(6))+2000;
						int month = Integer.parseInt(dateTextField.getText().substring(0, 2))-1;
						int day = Integer.parseInt(dateTextField.getText().substring(3, 5));
						c.set(year, month, day);
						date = c.getTime();
					}
					
					
					if (clientNameTextField.getText().equals("")
							|| dateTextField.getText().equals("")
							|| phoneTextField.getText().equals("")) {

						JOptionPane.showMessageDialog(new JFrame(),
								"Please fill out all the fields.");
					
					} else if (date.before(new Date())) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Cannot create appointment for a passed date!");
					}  else if (startTimeComboBox.getSelectedIndex() >= endTimeComboBox
							.getSelectedIndex()) {
						JOptionPane.showMessageDialog(new JFrame(),
								"End time must be after the start time!");
					} else if (!emailTextField.getText().contains("@")
							|| !(emailTextField.getText().endsWith(".com") || emailTextField
									.getText().endsWith(".edu"))) {
						JOptionPane
								.showMessageDialog(new JFrame(),
										"Email must be in 'email@domain.com' or 'email@domain.edu' format!");
					} else if ((phoneTextField.getText().length() != 10
							&& phoneTextField.getText().length() != 12) || (phoneTextField.getText().length() == 12
							&& (phoneTextField.getText().charAt(3) != '-' ||
							phoneTextField.getText().charAt(7) != '-'))) {
						JOptionPane
								.showMessageDialog(new JFrame(),
										"Phone number must be in '123-456-7890' or '1234567890' format! 2");
					} else {
						SQLManager sql = new SQLManager();
						String s = phoneTextField.getText();
						if (s.length() == 12) {
							s.replace("-", "");
						}

						sql.addNewAppointment(clientNameTextField.getText(), s,
								emailTextField.getText(), hairdresserComboBox
										.getSelectedItem().toString(), "test",
								dateTextField.getText(),
								(String) startTimeComboBox.getSelectedItem(),
								(String) endTimeComboBox.getSelectedItem());

						// untested code
						JCalendarTest.this.dispose();
					}
				}

			}
		});
		hairdresserComboBox = new JComboBox(employees);

		calendarButton = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
		calendarButton
				.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
					public void propertyChange(
							java.beans.PropertyChangeEvent evt) {
						if (evt.getNewValue() instanceof Date)
							setDate((Date) evt.getNewValue());
					}
				});

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "2, 2, fill, fill");

		getContentPane().add(clientNameLabel, "10, 4, right, default");
		clientNameTextField = new JTextField();
		getContentPane().add(clientNameTextField, "12, 4, fill, default");
		clientNameTextField.setColumns(10);

		getContentPane().add(dateLabel, "10, 6, right, default");
		getContentPane().add(dateTextField, "12, 6, fill, default");
		dateTextField.setColumns(10);
		getContentPane().add(calendarButton, "14, 6");

		getContentPane().add(startTimeLabel, "10, 8, right, default");
		getContentPane().add(startTimeComboBox, "12, 8, fill, default");

		getContentPane().add(endTimeLabel, "10, 10, right, default");
		getContentPane().add(endTimeComboBox, "12, 10, fill, default");

		getContentPane().add(phoneLabel, "10, 12, right, default");
		getContentPane().add(phoneTextField, "12, 12, fill, default");
		phoneTextField.setColumns(10);

		getContentPane().add(emailLabel, "10, 14, right, default");
		getContentPane().add(emailTextField, "12, 14, fill, default");
		emailTextField.setColumns(10);

		getContentPane().add(hairdresserComboBox, "10, 16, right, default");
		getContentPane().add(submitButton, "12, 16, fill, default");

		JPanel panel = new JPanel();
		getContentPane().add(panel, "24, 18, fill, fill");
	}

	/**
	 * * Validate and set the datetime field on the screen given a datetime
	 * string. * @param dateTime The datetime string
	 */
	public void setDate(String dateString) {
		Date date = null;
		try {
			if ((dateString != null) && (dateString.length() > 0))
				date = dateFormat.parse(dateString);
		} catch (Exception e) {
			date = null;
		}
		this.setDate(date);
	}

	/**
	 * * Validate and set the datetime field on the screen given a date. * @param
	 * dateTime The datetime object
	 */
	public void setDate(Date date) {
		String dateString = "";
		if (date != null)
			dateString = dateFormat.format(date);
		dateTextField.setText(dateString);
		calendarButton.setTargetDate(date);
	}

	public boolean refreshPanels() {
		return true;
	}

	// Variables declaration
	private JLabel clientNameLabel;
	private JLabel dateLabel;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;
	private JLabel phoneLabel;
	private JLabel emailLabel;

	private JTextField clientNameTextField;
	private JTextField dateTextField;
	private JComboBox startTimeComboBox;
	private JComboBox endTimeComboBox;
	private JTextField phoneTextField;
	private JTextField emailTextField;

	private JComboBox hairdresserComboBox;
	private JButton submitButton;

	private JCalendarButton calendarButton;
	private JTimeButton startTimeButton;
	private JTimeButton endTimeButton;
	public static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
	// public static DateFormat dateFormat = DateFormat
	// .getDateInstance(DateFormat.MEDIUM);
	public static DateFormat timeFormat = DateFormat
			.getTimeInstance(DateFormat.SHORT);
	public static DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(
			DateFormat.MEDIUM, DateFormat.SHORT);
}