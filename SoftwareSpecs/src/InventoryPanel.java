import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
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

public class InventoryPanel extends JPanel {

	private JButton addProduct = new JButton("Add New Product");
	private JButton addQTY = new JButton("Add Quantity");
	private JButton delProduct = new JButton("Remove Product");
	protected JTable theTable;
	private DefaultTableModel model;
	private RowSorter<DefaultTableModel> sorter;
	private String[] columns;
	private Object[][] data;

	String itemNameString;
	String itemPriceString;
	String itemQuantityString;
	String itemIDString;
	String itemNotesString;

	JTextField itemName;
	JTextField itemPrice;
	JTextField itemQuantity;
	JTextField itemID;
	JTextField itemNotes;

	public String[] newRowData = new String[5];

	public InventoryPanel() {
		setLayout(new BorderLayout());

		setColumns();
		setData();
		
		addProduct.setFocusable(false);
		addQTY.setFocusable(false);
		delProduct.setFocusable(false);
		
		// Creates buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addProduct);
		buttonPanel.add(addQTY);
		buttonPanel.add(delProduct);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, BorderLayout.SOUTH);

		// Creates inventory table
		model = new DefaultTableModel(data, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}			
		};
		// theTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// theTable.setRowSelectionAllowed(true); 

		theTable = new JTable(model);
		theTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		theTable.getTableHeader().setReorderingAllowed(false);
		
		sorter = new TableRowSorter<DefaultTableModel>(model);
		theTable.setRowSorter(sorter);

		// String s[] = {"","","","","a"};
		//
		// model.addRow(s);

		// theTable.setPreferredScrollableViewportSize(new Dimension(1000,
		// 750));
		// theTable.setFillsViewportHeight(true);

		// Creates a scroll panel for table
		JScrollPane scrollPane = new JScrollPane(theTable);
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
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
		addProduct.setBackground(new Color(200, 200, 200));
		addQTY.setBackground(new Color(200, 200, 200));
		delProduct.setBackground(new Color(200, 200, 200));

		buttonPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

		add(eastPanel, BorderLayout.EAST);
		add(westPanel, BorderLayout.WEST);

		addProduct.addActionListener(new ButtonHandler());
		addQTY.addActionListener(new ButtonHandler());
		delProduct.addActionListener(new ButtonHandler());
		
		addProduct.addMouseListener(new ButtonHandler());
		addQTY.addMouseListener(new ButtonHandler());
		delProduct.addMouseListener(new ButtonHandler());
	}

	public void setColumns() {
		columns = new String[] { "Product Name", "Price", "Quantity",
				"Product Number", "Product Description" };
	}

	public void setData() {
		SQLManager sql = new SQLManager();
		int z = sql.getInventorySize();
		//DecimalFormat df = new DecimalFormat("#.00");
		
		// Converts into USD currency format
		NumberFormat df = NumberFormat.getCurrencyInstance();
		data = new Object[z][];
		int count = 0;
		for (int i = 0; i < z; i++) {
			
			String[] itemData = sql.getInventoryRow(count + 1);
			
			if(itemData[3] != null){
				itemData[1] = df.format(Double.parseDouble(itemData[1]));
				data[i] = itemData;
			} else {
				i--;
			}
			count++;				
		}
	}

	public void addRow() {
		
		//DecimalFormat df = new DecimalFormat("#.00");
		
		// Converts into USD currency format
		NumberFormat df = NumberFormat.getCurrencyInstance();
		newRowData[0] = itemNameString;
		newRowData[1] = df.format(Double.parseDouble(itemPriceString));
		newRowData[2] = itemQuantityString;
		newRowData[3] = itemIDString;
		newRowData[4] = itemNotesString;
		
		model.addRow(newRowData);
		
		SQLManager sql = new SQLManager();
		sql.addNewItem(newRowData[0],
				Double.parseDouble(itemPriceString),
				Integer.parseInt(newRowData[2]),
				Integer.parseInt(newRowData[3]), newRowData[4]);
	}

	// public static void main(String[] args) {
	// InventoryPanel invPanel = new InventoryPanel();
	// invPanel.setVisible(true);
	// }

	private class ButtonHandler implements ActionListener, TableModelListener, MouseListener{

		public void actionPerformed(ActionEvent ae) {
			JButton tempButton = (JButton) ae.getSource();
			int row = theTable.getSelectedRow();
			if (tempButton == addProduct) {
				// new AddProduct();
				new NewProduct();//JOptionPaneMultiInput();
			} else if (tempButton == addQTY) {
				if(theTable.isRowSelected(row)) {
					// addQty button pressed
					
					int viewRow = theTable.getSelectedRow();
					int selectedRowIndex = theTable.convertRowIndexToModel(viewRow);
					int selectedColumnIndex = 3; //selects item ids
					int selectedItemID = Integer.valueOf((String) model.getValueAt(selectedRowIndex, selectedColumnIndex));					
					
					new ProductQTY(selectedItemID);
				} else {
					JOptionPane.showMessageDialog(
							null, 
							"Please select a row!", 
							"ERROR", 
							JOptionPane.ERROR_MESSAGE
					);
				}
			} else if (tempButton == delProduct) {
				if(theTable.isRowSelected(row)) {
					// delProduct button pressed
					
					int viewRow = theTable.getSelectedRow();
					int selectedRowIndex = theTable.convertRowIndexToModel(viewRow);
					int selectedColumnIndex = 3; //selects item ids
					int selectedItemID = Integer.valueOf((String) model.getValueAt(selectedRowIndex, selectedColumnIndex));
					
					new RemoveProduct(selectedItemID);
				} else {
					JOptionPane.showMessageDialog(
							null, 
							"Please select a row!", 
							"ERROR", 
							JOptionPane.ERROR_MESSAGE
					);
				}
			}
		}

		public void tableChanged(TableModelEvent tme) {
			/*int row = tme.getFirstRow();
			int col = tme.getColumn();
			DefaultTableModel model = (DefaultTableModel) tme.getSource();
			String columnName = model.getColumnName(col);
			Object newData = model.getValueAt(row, col);*/
			// model.fireTableRowsInserted(0, 0);
			// DefaultTableModel mod = new DefaultTableModel();
			
			//sorter.addRowSorterListener(theTable);
			//theTable.validate();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addProduct) {
				addProduct.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == addQTY) {
				addQTY.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == delProduct) {
				delProduct.setBackground(new Color(225, 225, 225));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addProduct) {
				addProduct.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == addQTY) {
				addQTY.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == delProduct) {
				delProduct.setBackground(new Color(200, 200, 200));
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
	
	public class NewProduct {//JOptionPaneMultiInput {
		public NewProduct() {//JOptionPaneMultiInput() {		

			SQLManager sql = new SQLManager();
			int id = sql.getNextItemID();

			JTextField itemName;
			JTextField itemPrice;
			JTextField itemQuantity;
			JTextField itemID;
			JTextField itemNotes;

			itemName = new JTextField(10);
			itemPrice = new JTextField(10);
			itemQuantity = new JTextField(10);
			itemID = new JTextField(10);
			itemNotes = new JTextField(10);
			
			itemID.setText(Integer.toString(id));
			itemID.setEditable(false);
			
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new GridLayout(5, 2));
			myPanel.add(new JLabel("Product Name:"));
			myPanel.add(itemName);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Product Price:"));
			myPanel.add(itemPrice);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Quantity:"));
			myPanel.add(itemQuantity);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Product Number:"));
			myPanel.add(itemID);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("Description:"));
			myPanel.add(itemNotes);
			myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			
			int result = JOptionPane.showConfirmDialog(
					null, 
					myPanel, 
					"Add Product", 
					JOptionPane.OK_CANCEL_OPTION
			);
			
			if (result == JOptionPane.OK_OPTION) {
				// System.out.println("x value: " + itemName.getText());
				// System.out.println("y value: " + itemPrice.getText());
				//double price;
				try {
					
					double price = Double.parseDouble(itemPrice.getText());
					int qty = Integer.parseInt(itemQuantity.getText());
					
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(
							null, 
							"You typed wrong input. Please try again!",
							"ERROR",
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}
				
				itemNameString = itemName.getText();
				itemPriceString = itemPrice.getText();
				itemQuantityString = itemQuantity.getText();
//				itemIDString = itemID.getText();
				itemIDString = Integer.toString(id);
				itemNotesString = itemNotes.getText();
				addRow();
				
				// itemName = itemName.getText();
				// itemPrice = itemName.getText();
				// itemQuantity = itemName.getText();
				// itemID = itemName.getText();
				// itemNotes = itemName.getText();

			}
		}
	}
	
	public class ProductQTY {
		public ProductQTY(int row) {
			String rowData[] = new String[5];
			SQLManager sql = new SQLManager();
			JTextField newQTY = new JTextField(10);
			
			rowData = sql.getInventoryRow(row);
			int currQTY = Integer.parseInt(rowData[2]);
			int itemID = Integer.parseInt(rowData[3]);
			
			JPanel qtyPanel = new JPanel();
			qtyPanel.setLayout(new GridLayout(4, 2));
			qtyPanel.add(new JLabel("Product Number:"));
			qtyPanel.add(new JLabel(rowData[3]));
			qtyPanel.add(new JLabel("Product Name:"));
			qtyPanel.add(new JLabel(rowData[0]));
			qtyPanel.add(new JLabel("Current Quantity:"));
			qtyPanel.add(new JLabel(rowData[2]));
			qtyPanel.add(new JLabel("Add Quantity:"));
			qtyPanel.add(newQTY);
			
			int qtyWindow = JOptionPane.showConfirmDialog(
					null, 
					qtyPanel, 
					"Add Quantity", 
					JOptionPane.OK_CANCEL_OPTION
			);
			
			if(qtyWindow == JOptionPane.OK_OPTION) {
				int addQTY;
				try {
					addQTY = Integer.parseInt(newQTY.getText());
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(
							null, 
							"You typed wrong input. Please try again!",
							"ERROR",
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}
				
				// Adds current and new quantities
				int newqty = currQTY + addQTY;
				
				// Sets new quantity to JTable
				model.setValueAt(newqty, theTable.getSelectedRow(), 2); // row-1
				
				// Sets new quantity to JTable
				//possible fix but who knows
//				for (int i = 1; i <= theTable.getRowCount(); i++){
//			                if (theTable.getValueAt(i, 3).equals(row))
//			    				model.setValueAt(newqty, i, 2);
//				}
							
				
				// Sets new quantity to database
				sql.setInventoryQuantity(itemID, newqty);
			}
		}
	}
	
	public class RemoveProduct {
		public RemoveProduct(int itemId) {
			SQLManager sql = new SQLManager();
//			String[] rowData = new String[5];
//			rowData = sql.getInventoryRow(row);
//			int itemID = Integer.parseInt(rowData[3]);
			int delWindow = JOptionPane.showConfirmDialog(
					null, 
					"WARNING: Do you still want to remove a row from table?", 
					"Remove a Product", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE
			);
			
			if(delWindow == JOptionPane.YES_OPTION) {
				// Removes the selected row from table
				model.removeRow(theTable.getSelectedRow());
				
				// Removes the selected row from database
				sql.removeInventoryRow(itemId);
			}
		}
	}
}
