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

public class CustomerPanel extends JPanel {

	private JButton addCustomer = new JButton("Add Customer");
	private JButton editCustomer = new JButton("Edit Customer");
	private JButton delCustomer = new JButton("Remove Customer");
	protected JTable theTable;
	private DefaultTableModel model;
	private RowSorter<DefaultTableModel> sorter;
	private String[] columns;
	private Object[][] data;

	String clientNameString;
	String clientNumberString;
	String clientEmailString;
	String numOfTransactionsString;
	String totalSpentString;
	String clientNotesString;

	JTextField clientName;
	JTextField clientNumber;
	JTextField clientEmail;
	JTextField numOfTransactions;
	JTextField totalSpent;
	JTextField clientNotes;

	public String[] newRowData = new String[6];

	public CustomerPanel() {
		setLayout(new BorderLayout());

		setColumns();
		setData();

		// Creates buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addCustomer);
		buttonPanel.add(editCustomer);
		buttonPanel.add(delCustomer);
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
		addCustomer.setBackground(new Color(200, 200, 200));
		editCustomer.setBackground(new Color(200, 200, 200));
		delCustomer.setBackground(new Color(200, 200, 200));

		buttonPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

		add(eastPanel, BorderLayout.EAST);
		add(westPanel, BorderLayout.WEST);

		addCustomer.addActionListener(new ButtonHandler());
		editCustomer.addActionListener(new ButtonHandler());
		delCustomer.addActionListener(new ButtonHandler());

		addCustomer.addMouseListener(new ButtonHandler());
		editCustomer.addMouseListener(new ButtonHandler());
		delCustomer.addMouseListener(new ButtonHandler());
	}

	public void setColumns() {
		columns = new String[] { "Name", "Phone Number", "Email", "# of Transactions", "Total Spent", "Special Comments" };
	}

	public void setData() {
		SQLManager sql = new SQLManager();
		int z = sql.getCustomerSize();
				
		data = new Object[z][];

		int count = 0;
		for (int i = 0; i < z; i++) {

			if(i < 0)
				break;
			
			String[] customerData = sql.getCustomerRow(count + 1);

			if (customerData[0] != null) {
				data[i] = customerData;
			} else
				i--;

			count++;

		}
	}

	public void addRow() {

		newRowData[0] = clientNameString;
		newRowData[1] = clientNumberString;
		newRowData[2] = clientEmailString;
		newRowData[3] = numOfTransactionsString;
		newRowData[4] = totalSpentString;
		newRowData[5] = clientNotesString;

		model.addRow(newRowData);

		SQLManager sql = new SQLManager();

		sql.addNewCustomer(newRowData[0], newRowData[1], newRowData[2],
				newRowData[3], newRowData[4], newRowData[5]);
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
			if (tempButton == addCustomer) {
				// new addCustomer();
				new AddCustomer();
			} else if (tempButton == editCustomer) {
				if (theTable.isRowSelected(row)) {
					// editCustomer button pressed

					int viewRow = theTable.getSelectedRow();

					new EditCustomer(viewRow);
				} else {
					JOptionPane.showMessageDialog(null, "Please select a row!",
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			} else if (tempButton == delCustomer) {
				if (theTable.isRowSelected(row)) {
					// delCustomer button pressed

					int viewRow = theTable.getSelectedRow();
					int selectedRowIndex = theTable
							.convertRowIndexToModel(viewRow);
					int selectedColumnIndex = 2; // selects item ids
					String username = (String) model.getValueAt(
							selectedRowIndex, selectedColumnIndex);

					new RemoveCustomer(username);
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

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == addCustomer) {
				addCustomer.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == editCustomer) {
				editCustomer.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == delCustomer) {
				delCustomer.setBackground(new Color(225, 225, 225));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == addCustomer) {
				addCustomer.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == editCustomer) {
				editCustomer.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == delCustomer) {
				delCustomer.setBackground(new Color(200, 200, 200));
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}
	}

	public class AddCustomer {
		public AddCustomer() {

			// SQLManager sql = new SQLManager();
			// int id = sql.getNextItemID();

			JTextField clientName;
			JTextField clientNumber;
			JTextField clientEmail;
			JTextField numOfTransactions;
			JTextField totalSpent;
			JTextField clientNotes;
			String[] adminBoxString = { "FALSE", "TRUE" };

			clientName = new JTextField(10);
			clientNumber = new JTextField(10);
			clientEmail = new JTextField(10);
			numOfTransactions = new JTextField(10);
			totalSpent = new JTextField(10);
			
			clientNotes = new JTextField(10);

			 numOfTransactions.setText("0");
			 numOfTransactions.setEditable(false);
			 
			 totalSpent.setText("0");
			 totalSpent.setEditable(false);

			JPanel myPanel = new JPanel();
			myPanel.setLayout(new GridLayout(6, 2));
			myPanel.add(new JLabel("Customer Name:"));
			myPanel.add(clientName);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Customer Number:"));
			myPanel.add(clientNumber);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Customer Email:"));
			myPanel.add(clientEmail);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("# of Transactions:"));
			myPanel.add(numOfTransactions);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Total Spent:"));
			myPanel.add(totalSpent);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Special Comments:"));
			myPanel.add(clientNotes);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer

			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Add Customer", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {

				clientNameString = clientName.getText();
				clientNumberString = clientNumber.getText();
				clientEmailString = clientEmail.getText();
				numOfTransactionsString = numOfTransactions.getText();
				// numOfTransactionsString = Integer.toString(id);
				totalSpentString = (String) totalSpent.getText();
				clientNotesString = (String) clientNotes.getText();

				addRow();

			}
		}
	}

	public class EditCustomer {
		public EditCustomer(int row) {
			// SQLManager sql = new SQLManager();
			// int id = sql.getNextItemID();

			SQLManager sql = new SQLManager();
			String[] clientInfo = new String[6];

			String originalEmail = (String) theTable.getValueAt(row, 2);

			clientInfo = sql.getCustomerRow(originalEmail);

			JTextField clientName;
			JTextField clientNumber;
			JTextField clientEmail;
			JTextField numOfTransactions;
			JTextField totalSpent;
			String[] adminBoxString = { "FALSE", "TRUE" };

			JTextField clientNotes;
			
			clientName = new JTextField(10);
			clientNumber = new JTextField(10);
			clientEmail = new JTextField(10);
			numOfTransactions = new JTextField(10);
			totalSpent = new JTextField(10);

			clientNotes = new JTextField(10);
			
			// clientEmail.setEditable(false);

			clientName.setText(clientInfo[0]);
			clientNumber.setText(clientInfo[1]);
			clientEmail.setText(clientInfo[2]);
			numOfTransactions.setText(clientInfo[3]);

			System.out.println("TESTING*** " + clientInfo[0]);

//			if (clientInfo[4].equals("TRUE"))
//				totalSpent.setSelectedIndex(1);
//			else
//				totalSpent.setSelectedIndex(0);
			
			totalSpent.setText(clientInfo[4]);
			
			clientNotes.setText(clientInfo[5]);

			// numOfTransactions.setText(Integer.toString(id));
			// numOfTransactions.setEditable(false);

			JPanel myPanel = new JPanel();
			myPanel.setLayout(new GridLayout(6, 2));
			myPanel.add(new JLabel("Customer Name:"));
			myPanel.add(clientName);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Customer Number:"));
			myPanel.add(clientNumber);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Customer Email:"));
			myPanel.add(clientEmail);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("# of Transactions:"));
			myPanel.add(numOfTransactions);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Total Spent:"));
			myPanel.add(totalSpent);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Special Comments:"));
			myPanel.add(clientNotes);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer


			numOfTransactions.setEditable(false);			 
			totalSpent.setEditable(false);
			
			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Edit Customer", JOptionPane.OK_CANCEL_OPTION);

			if (result == JOptionPane.OK_OPTION) {

				clientNameString = clientName.getText();
				clientNumberString = clientNumber.getText();
				clientEmailString = clientEmail.getText();
				numOfTransactionsString = numOfTransactions.getText();
				// numOfTransactionsString = Integer.toString(id);
				totalSpentString = (String) totalSpent.getText();

				clientNotesString = (String) clientNotes.getText();

				// TODO: Locally update rows with new info

				theTable.setValueAt(clientNameString, row, 0);
				theTable.setValueAt(clientNumberString, row, 1);
				theTable.setValueAt(clientEmailString, row, 2);

				sql.updateCustomer(originalEmail, clientNameString,
						clientNumberString, clientEmailString,
						numOfTransactionsString, totalSpentString, clientNotesString);

			}
		}
	}

	public class RemoveCustomer {
		public RemoveCustomer(String username) {
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
				// sql.removeInventoryRow(numOfTransactions);
				sql.removeCustomerRow(username);
			}
		}
	}
}
