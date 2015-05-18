import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;
import org.jbundle.thin.base.screen.jcalendarbutton.JTimeButton;

public class EditAppointmentPanel extends JPanel {

	private JLabel clientNameLabel;
	private JTextField clientNameTextField;
	private JLabel dateLabel;
	private JTextField dateTextField;
	private JLabel startTimeLabel;
	private JComboBox startTimeComboBox;
	private JLabel endTimeLabel;
	private JComboBox endTimeComboBox;
	private JLabel phoneLabel;
	private JTextField phoneTextField;
	private JLabel emailLabel;
	private JTextField emailTextField;
	private JComboBox hairdresserComboBox;
	private JCalendarButton calendarButton;
	private JButton editButton;
	private JButton saveButton;
	public static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
	private Appointments app;

	public EditAppointmentPanel(Appointments lApp, String[] employees,
			String[] times) {
		app = lApp;
		clientNameLabel = new JLabel("Client Name:");
		clientNameTextField = new JTextField(app.getClientName());
		dateLabel = new JLabel("Date:");
		dateTextField = new JTextField(app.getAppointmentDate());
		dateTextField.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				String date = dateTextField.getText();
				setDate(date);
			}
		});
		startTimeLabel = new JLabel("Start Time:");
		startTimeComboBox = new JComboBox(times);
		startTimeComboBox.setSelectedItem(app.getAppointmentStartTime());
		endTimeLabel = new JLabel("End Time:");
		endTimeComboBox = new JComboBox(times);
		endTimeComboBox.setSelectedItem(app.getAppointmentEndTime());
		phoneLabel = new JLabel("Phone Number:");
		phoneTextField = new JTextField(app.getClientNumber());
		emailLabel = new JLabel("Email:");
		emailTextField = new JTextField(app.getClientEmail());

		hairdresserComboBox = new JComboBox(employees);
		hairdresserComboBox.setSelectedItem(app.getEmployeeName());

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
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"), }));

		JPanel panel_1 = new JPanel();
		add(panel_1, "2, 2, fill, fill");

		add(clientNameLabel, "10, 4, right, default");
		add(clientNameTextField, "12, 4, fill, default");
		clientNameTextField.setColumns(10);

		add(dateLabel, "10, 6, right, default");
		add(dateTextField, "12, 6, fill, default");
		dateTextField.setColumns(10);
		add(calendarButton, "14, 6");

		add(startTimeLabel, "10, 8, right, default");
		add(startTimeComboBox, "12, 8, fill, default");

		add(endTimeLabel, "10, 10, right, default");
		add(endTimeComboBox, "12, 10, fill, default");

		add(phoneLabel, "10, 12, right, default");
		add(phoneTextField, "12, 12, fill, default");
		phoneTextField.setColumns(10);

		add(emailLabel, "10, 14, right, default");
		add(emailTextField, "12, 14, fill, default");
		emailTextField.setColumns(10);

		editButton = new JButton("Edit Appointment");
		editButton.addActionListener(new Handler());
		saveButton = new JButton("Save Appointment");
		saveButton.addActionListener(new Handler());

		add(hairdresserComboBox, "10, 16, 3, 1");

		add(editButton, "10, 18, right, default");
		add(saveButton, "12, 18, fill, default");

		JPanel panel = new JPanel();
		add(panel, "24, 20, fill, fill");

		setUneditable();
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

	void setEditable() {
		clientNameTextField.setEditable(true);
		dateTextField.setEditable(true);
		startTimeComboBox.setEnabled(true);
		endTimeComboBox.setEnabled(true);
		phoneTextField.setEditable(true);
		emailTextField.setEditable(true);
		hairdresserComboBox.setEnabled(true);
	}

	void setUneditable() {
		clientNameTextField.setEditable(false);
		dateTextField.setEditable(false);
		startTimeComboBox.setEnabled(false);
		endTimeComboBox.setEnabled(false);
		phoneTextField.setEditable(false);
		emailTextField.setEditable(false);
		hairdresserComboBox.setEnabled(false);
	}

	public void setDate(String dateString) {
		Date date = null;
		try {
			if ((dateString != null) && (dateString.length() > 0)) {
			}
			date = dateFormat.parse(dateString);
		} catch (Exception e) {
			date = null;
		}
		this.setDate(date);
	}

	public class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == editButton) {
				setEditable();
			} else if (e.getSource() == saveButton) {
				Date date = null;
				if (!dateTextField.getText().equals("")) {
					Calendar c = Calendar.getInstance();
					int year = Integer.parseInt(dateTextField.getText()
							.substring(6)) + 2000;
					int month = Integer.parseInt(dateTextField.getText()
							.substring(0, 2)) - 1;
					int day = Integer.parseInt(dateTextField.getText()
							.substring(3, 5));
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
				} else if (startTimeComboBox.getSelectedIndex() >= endTimeComboBox
						.getSelectedIndex()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"End time must be after the start time!");
				} else if (!emailTextField.getText().contains("@")
						|| !(emailTextField.getText().endsWith(".com") || emailTextField
								.getText().endsWith(".edu"))) {
					JOptionPane
							.showMessageDialog(new JFrame(),
									"Email must be in 'email@domain.com' or 'email@domain.edu' format!");
				} else if ((phoneTextField.getText().length() != 10 && phoneTextField
						.getText().length() != 12)
						|| (phoneTextField.getText().length() == 12 && (phoneTextField
								.getText().charAt(3) != '-' || phoneTextField
								.getText().charAt(7) != '-'))) {
					JOptionPane
							.showMessageDialog(new JFrame(),
									"Phone number must be in '123-456-7890' or '1234567890' format! 2");
				} else {
					SQLManager sql = new SQLManager();
					String s = phoneTextField.getText();
					if (s.length() == 12) {
						s.replace("-", "");
					}
					app.updateAppointment(clientNameTextField.getText(), s,
							emailTextField.getText(), hairdresserComboBox
									.getSelectedItem().toString(), "test",
							dateTextField.getText(), (String) startTimeComboBox
									.getSelectedItem(),
							(String) endTimeComboBox.getSelectedItem(), app
									.getAppointmentID());
				}
				setUneditable();
			}
		}
	}
}
