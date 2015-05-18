import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class EmployeePanel extends JPanel {

	private JButton addEmployee = new JButton("Add Employee");
	private JButton editEmployee = new JButton("Edit Employee");
	private JButton delEmployee = new JButton("Remove Employee");
	protected JTable theTable;
	private DefaultTableModel model;
	private RowSorter<DefaultTableModel> sorter;
	private String[] columns;
	// private Object[][] data = {
	// {"Jay", "Human", 1,0,0},
	// {"Cat", "Pet Animal", 20,0,0},
	// {"Dog", "Pet Animal", 30,0,0},
	// {"Racoon", "Wild Animal", 10,0,0},
	// }
	private Object[][] data;

	String employeeNameString;
	String employeeNumberString;
	String employeeUsernameString;
	String employeePasswordString;
	String isAdminString;

	JTextField employeeName;
	JTextField employeeNumber;
	JTextField employeeUsername;
	JTextField employeePassword;
	JTextField isAdmin;

	public String[] newRowData = new String[5];

	public EmployeePanel() {
		setLayout(new BorderLayout());

		setColumns();
		setData();

		// Creates buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addEmployee);
		buttonPanel.add(editEmployee);
		buttonPanel.add(delEmployee);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, BorderLayout.SOUTH);

		// Creates inventory table
		model = new DefaultTableModel(data, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		theTable = new JTable(model);
		theTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		theTable.getTableHeader().setReorderingAllowed(false);

		sorter = new TableRowSorter<DefaultTableModel>(model);
		theTable.setRowSorter(sorter);

		// Creates a scroll panel for table
		JScrollPane scrollPane = new JScrollPane(theTable);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBackground(Color.WHITE);
		add(scrollPane, BorderLayout.CENTER);

		JPanel eastPanel = new JPanel();
		JPanel westPanel = new JPanel();

		eastPanel.setPreferredSize(new Dimension(40, 0));
		westPanel.setPreferredSize(new Dimension(40, 0));

		eastPanel.setBackground(new Color(70, 80, 90));
		westPanel.setBackground(new Color(70, 80, 90));
		buttonPanel.setBackground(new Color(70, 80, 90));
		// eastPanel.setBackground(new Color(30, 40, 50));
		// westPanel.setBackground(new Color(30, 40, 50));
		// buttonPanel.setBackground(new Color(30, 40, 50));
		addEmployee.setBackground(new Color(200, 200, 200));
		editEmployee.setBackground(new Color(200, 200, 200));
		delEmployee.setBackground(new Color(200, 200, 200));

		buttonPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

		add(eastPanel, BorderLayout.EAST);
		add(westPanel, BorderLayout.WEST);

		addEmployee.addActionListener(new ButtonHandler());
		editEmployee.addActionListener(new ButtonHandler());
		delEmployee.addActionListener(new ButtonHandler());

		addEmployee.addMouseListener(new ButtonHandler());
		editEmployee.addMouseListener(new ButtonHandler());
		delEmployee.addMouseListener(new ButtonHandler());
	}

	public void setColumns() {
		columns = new String[] { "Name", "Phone Number", "Username" };
	}

	public void setData() {
		SQLManager sql = new SQLManager();
		int z = sql.getEmployeeSize();

		data = new Object[z][];

		int count = 1;
		for (int i = 0; i < z; i++) {

			// String[] itemData = sql.getEmployeeRow(count + 1);
			//
			// if (itemData[3] != null) {
			// data[i] = itemData;
			// } else
			// i--;
			//
			// count++;

			String[] itemData = sql.getEmployeeRow(count + 1);

			if (itemData[0] != null) {
				data[i] = itemData;
			} else
				i--;

			// data[i] = itemData;

			count++;

		}
	}

	public void addRow() {

		newRowData[0] = employeeNameString;
		newRowData[1] = employeeNumberString;
		newRowData[2] = employeeUsernameString;
		newRowData[3] = employeePasswordString;
		newRowData[4] = isAdminString;

		model.addRow(newRowData);

		SQLManager sql = new SQLManager();

		sql.addNewEmployee(newRowData[0], newRowData[1], newRowData[2],
				newRowData[3], newRowData[4]);
	}

	// public static void main(String[] args) {
	// InventoryPanel invPanel = new InventoryPanel();
	// invPanel.setVisible(true);
	// }

	private class ButtonHandler implements ActionListener, TableModelListener,
			MouseListener {

		public void actionPerformed(ActionEvent ae) {
			JButton tempButton = (JButton) ae.getSource();
			int row = theTable.getSelectedRow();
			if (tempButton == addEmployee) {
				// new addEmployee();
				new AddEmployee();
			} else if (tempButton == editEmployee) {
				if (theTable.isRowSelected(row)) {
					// editEmployee button pressed

					int viewRow = theTable.getSelectedRow();
					// int selectedRowIndex = theTable
					// .convertRowIndexToModel(viewRow);
					// int selectedColumnIndex = 3; // selects item ids
					// int selectedItemID = Integer.valueOf((String) model
					// .getValueAt(selectedRowIndex, selectedColumnIndex));

					new EditEmployee(viewRow);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a row!",
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			} else if (tempButton == delEmployee) {
				if (theTable.isRowSelected(row)) {
					// delEmployee button pressed

					int viewRow = theTable.getSelectedRow();
					int selectedRowIndex = theTable
							.convertRowIndexToModel(viewRow);
					int selectedColumnIndex = 2; // selects item ids
					String username = (String) model.getValueAt(
							selectedRowIndex, selectedColumnIndex);

					new RemoveEmployee(username);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a row!",
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		public void tableChanged(TableModelEvent tme) {
			/*
			 * int row = tme.getFirstRow(); int col = tme.getColumn();
			 * DefaultTableModel model = (DefaultTableModel) tme.getSource();
			 * String columnName = model.getColumnName(col); Object newData =
			 * model.getValueAt(row, col);
			 */
			// model.fireTableRowsInserted(0, 0);
			// DefaultTableModel mod = new DefaultTableModel();

			// sorter.addRowSorterListener(theTable);
			// theTable.validate();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addEmployee) {
				addEmployee.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == editEmployee) {
				editEmployee.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == delEmployee) {
				delEmployee.setBackground(new Color(225, 225, 225));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addEmployee) {
				addEmployee.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == editEmployee) {
				editEmployee.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == delEmployee) {
				delEmployee.setBackground(new Color(200, 200, 200));
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public class AddEmployee {
		public AddEmployee() {

			// SQLManager sql = new SQLManager();
			// int id = sql.getNextItemID();

			JTextField employeeName;
			JTextField employeeNumber;
			JTextField employeeUsername;
			JTextField employeePassword;
			JComboBox isAdmin;
			String[] adminBoxString = { "FALSE", "TRUE" };

			employeeName = new JTextField(10);
			employeeNumber = new JTextField(10);
			employeeUsername = new JTextField(10);
			employeePassword = new JTextField(10);
			isAdmin = new JComboBox(adminBoxString);

			// employeePassword.setText(Integer.toString(id));
			// employeePassword.setEditable(false);

			JPanel myPanel = new JPanel();
			myPanel.setLayout(new GridLayout(5, 2));
			myPanel.add(new JLabel("Employee Name:"));
			myPanel.add(employeeName);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Employee Number:"));
			myPanel.add(employeeNumber);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Employee Username:"));
			myPanel.add(employeeUsername);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Employee Password:"));
			myPanel.add(employeePassword);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Admin Privileges:"));
			myPanel.add(isAdmin);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer

			if (isAdminString !=null && (employeePasswordString.equals("")
						|| employeeUsernameString.equals(""))) {
				employeeName.setText(employeeNameString);
				employeeNumber.setText(employeeNumberString);
				employeeUsername.setText(employeeUsernameString);
				employeePassword.setText(employeePasswordString);
	
				if (isAdminString.equals("TRUE"))
					isAdmin.setSelectedIndex(1);
				else
					isAdmin.setSelectedIndex(0);
			}
			
			
			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Add Employee", JOptionPane.OK_CANCEL_OPTION);

			
			if (result == JOptionPane.OK_OPTION) {

				employeeNameString = employeeName.getText();
				employeeNumberString = employeeNumber.getText();
				employeeUsernameString = employeeUsername.getText();
				employeePasswordString = employeePassword.getText();
				// employeePasswordString = Integer.toString(id);
				isAdminString = (String) isAdmin.getSelectedItem();
				
				if (employeePasswordString.equals("")
						|| employeeUsernameString.equals("")) {
					JOptionPane
							.showMessageDialog(
									null,
									"Username and password fields must be filled. Please try again!",
									"ERROR", JOptionPane.ERROR_MESSAGE);

				} else {

					addRow();
				}
			}
		}
	}

	public class EditEmployee {
		public EditEmployee(int row) {
			// SQLManager sql = new SQLManager();
			// int id = sql.getNextItemID();

			SQLManager sql = new SQLManager();
			String[] employeeInfo = new String[5];

			String originalUsername = (String) theTable.getValueAt(row, 2);

			employeeInfo = sql.getEmployeeRow(originalUsername);

			JTextField employeeName;
			JTextField employeeNumber;
			JTextField employeeUsername;
			JTextField employeePassword;
			JComboBox isAdmin;
			String[] adminBoxString = { "FALSE", "TRUE" };

			employeeName = new JTextField(10);
			employeeNumber = new JTextField(10);
			employeeUsername = new JTextField(10);
			employeePassword = new JTextField(10);
			isAdmin = new JComboBox(adminBoxString);

			// employeeUsername.setEditable(false);

			employeeName.setText(employeeInfo[0]);
			employeeNumber.setText(employeeInfo[1]);
			employeeUsername.setText(employeeInfo[2]);
			employeePassword.setText(employeeInfo[3]);

			System.out.println("TESTING*** " + employeeInfo[0]);

			if (employeeInfo[4].equals("TRUE"))
				isAdmin.setSelectedIndex(1);
			else
				isAdmin.setSelectedIndex(0);

			// employeePassword.setText(Integer.toString(id));
			// employeePassword.setEditable(false);

			JPanel myPanel = new JPanel();
			myPanel.setLayout(new GridLayout(5, 2));
			myPanel.add(new JLabel("Employee Name:"));
			myPanel.add(employeeName);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Employee Number:"));
			myPanel.add(employeeNumber);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Employee Username:"));
			myPanel.add(employeeUsername);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Employee Password:"));
			myPanel.add(employeePassword);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Admin Privileges:"));
			myPanel.add(isAdmin);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer

			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Edit Employee", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {

				employeeNameString = employeeName.getText();
				employeeNumberString = employeeNumber.getText();
				employeeUsernameString = employeeUsername.getText();
				employeePasswordString = employeePassword.getText();
				// employeePasswordString = Integer.toString(id);
				isAdminString = (String) isAdmin.getSelectedItem();

				// TODO: Locally update rows with new info

				theTable.setValueAt(employeeNameString, row, 0);
				theTable.setValueAt(employeeNumberString, row, 1);
				theTable.setValueAt(employeeUsernameString, row, 2);

				sql.updateEmployee(originalUsername, employeeNameString,
						employeeNumberString, employeeUsernameString,
						employeePasswordString, isAdminString);

			}
		}
	}

	public class RemoveEmployee {
		public RemoveEmployee(String username) {
			SQLManager sql = new SQLManager();
			int delWindow = JOptionPane.showConfirmDialog(null,
					"WARNING: Do you still want to remove a row from table?",
					"Remove a Product", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (delWindow == JOptionPane.YES_OPTION) {
				// Removes the selected row from table
				// model.removeRow(row-1);
				model.removeRow(theTable.getSelectedRow());
				// Removes the selected row from database
				// sql.removeInventoryRow(employeePassword);
				sql.removeEmployeeRow(username);
			}
		}
	}
}
