import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import com.sun.javafx.geom.BoxBounds;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * Class CheckoutPanel
 * This class creates the checkout panel of the program.
 */
public class CheckoutPanel extends JPanel {
	// Labels to be used.
	private final JLabel searchLabel;
	private final JLabel nameLabel;
	private final JLabel numberLabel;
	private final JLabel quantityLabel;
	private final JLabel priceLabel;
	private final JLabel descriptionLabel;

	// Text Fields to be used.
	private final JTextField searchTextField;
	private final JTextField nameTextField;
	private final JTextField numberTextField;
	private final JTextField priceTextField;
	private final JTextField quantityTextField;
	private final JTextArea descriptionArea;

	// Buttons to be used.
	private final JButton searchButton;
	private final JButton customerButton;
	private final JButton priceButton;
	private final JButton addButton;
	private final JButton removeAllButton;
	private final JButton removeButton;
	private final JButton checkoutButton;
	private final JButton clearButton;

	// Items to be used for the table.
	public Object[][] data;
	public String[] colNames = { "Name", "Product ID", "Quantity", "Price" };
	private final DefaultTableModel model;
	private final JTable table;

	// Different panels used on the main panel.
	private final JComponent productInfoPanel;
	private final JPanel middleButtPanel;
	private final JPanel searchPanel;
	private final JPanel tablePanel;
	private final JPanel buttonsPanel;

	// Static variable to keep track of the rows used in the table.
	static int rows = 0;

	/*
	 * Constructor.
	 */
	public CheckoutPanel() {
		// Set Layout
		setLayout(new BorderLayout());

		// Add search panel at the top
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout());
		searchLabel = new JLabel();
		searchLabel.setText("Product Number:");
		searchTextField = new JTextField(20);
		searchButton = new JButton("Seach");
		searchPanel.add(searchLabel);
		searchPanel.add(searchTextField);
		searchPanel.add(searchButton);
		searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		add(searchPanel, BorderLayout.NORTH);

		// Add product info panel in west of frame.
		productInfoPanel = new JPanel();
		GroupLayout layout = new GroupLayout(productInfoPanel);
		productInfoPanel.setLayout(layout);
		nameLabel = new JLabel();
		nameLabel.setText("Name: ");
		numberLabel = new JLabel();
		numberLabel.setText("Product #: ");
		quantityLabel = new JLabel();
		quantityLabel.setText("Quantity: ");
		priceLabel = new JLabel();
		priceLabel.setText("Price: ");
		descriptionLabel = new JLabel();
		descriptionLabel.setText("Description: ");
		nameTextField = new JTextField(20);
		numberTextField = new JTextField(20);
		priceTextField = new JTextField(20);
		quantityTextField = new JTextField(20);
		JScrollPane scroll;
		descriptionArea = new JTextArea();
		scroll = new JScrollPane(descriptionArea);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		productInfoPanel.setBorder(BorderFactory
				.createEmptyBorder(40, 0, 80, 0));
		clearButton = new JButton("Clear");

		// Order elements in the panel.
		// Create horizontal group.
		GroupLayout.SequentialGroup horGroup = layout.createSequentialGroup();
		horGroup.addGroup(layout.createParallelGroup().addComponent(nameLabel)
				.addComponent(priceLabel).addComponent(quantityLabel)
				.addComponent(numberLabel).addComponent(descriptionLabel));
		horGroup.addGroup(layout.createParallelGroup()
				.addComponent(nameTextField).addComponent(priceTextField)
				.addComponent(quantityTextField).addComponent(numberTextField)
				.addComponent(scroll).addComponent(clearButton));
		layout.setHorizontalGroup(horGroup);

		// Create vertical group.
		GroupLayout.SequentialGroup verGroup = layout.createSequentialGroup();
		verGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(nameLabel).addComponent(nameTextField));
		verGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(priceLabel).addComponent(priceTextField));
		verGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(quantityLabel).addComponent(quantityTextField));
		verGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(numberLabel).addComponent(numberTextField));
		verGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(descriptionLabel).addComponent(scroll));
		verGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
				.addComponent(clearButton));
		layout.setVerticalGroup(verGroup);

		// Add the panel.
		add(productInfoPanel, BorderLayout.WEST);

		// Add middle buttons in the middle of the frame.
		middleButtPanel = new JPanel();
		BoxLayout box = new BoxLayout(middleButtPanel, BoxLayout.Y_AXIS);
		middleButtPanel.setLayout(box);
		addButton = new JButton("Add >>>");
		removeButton = new JButton("Remove");
		removeAllButton = new JButton("Remove All");
		addButton.setPreferredSize(new Dimension(90, 40));
		removeButton.setPreferredSize(new Dimension(90, 40));
		removeAllButton.setPreferredSize(new Dimension(100, 40));
		addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middleButtPanel.add(addButton);
		middleButtPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		middleButtPanel.add(removeButton);
		middleButtPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		middleButtPanel.add(removeAllButton);
		middleButtPanel
				.setBorder(BorderFactory.createEmptyBorder(160, 0, 0, 0));
		add(middleButtPanel, BorderLayout.CENTER);

		// Add table panel in east.
		tablePanel = new JPanel();
		data = new Object[25][colNames.length];
		// model = new DefaultTableModel(data, colNames);

		// might need to edit out
		model = new DefaultTableModel(data, colNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 2)
					return true;
				else
					return false;
			}
		};

		table = new JTable(model);
		JScrollPane tableScroll = new JScrollPane(table);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		// Center columns
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < colNames.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		table.setShowGrid(true);
		tablePanel.add(tableScroll);
		tablePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		add(tablePanel, BorderLayout.EAST);

		// Add buttons panel at the bottom.
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		customerButton = new JButton("Customer Lookup");
		priceButton = new JButton("Price Check");
		checkoutButton = new JButton("Check Out");
		buttonsPanel.add(customerButton);
		buttonsPanel.add(priceButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(625, 0)));
		buttonsPanel.add(checkoutButton);
		add(buttonsPanel, BorderLayout.SOUTH);

		// Create handler for buttons.
		ButtonHandler handler = new ButtonHandler();
		customerButton.addActionListener(handler);
		searchButton.addActionListener(handler);
		priceButton.addActionListener(handler);
		checkoutButton.addActionListener(handler);
		clearButton.addActionListener(handler);
		addButton.addActionListener(handler);
		removeButton.addActionListener(handler);
		removeAllButton.addActionListener(handler);
		searchTextField.addActionListener(handler);

	}

	/*
	 * Class ButtonHandler This private inner class handles of the buttons.
	 */
	private class ButtonHandler implements ActionListener {
		/*
		 * Method actonPerformed. This method handles the buttons.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			// Handle searchButton.
			if (event.getSource() == searchButton
					|| event.getSource() == searchTextField) {
				// If nothing is entered or if the string does not contains only numbers,
				// display error message.
				if (searchTextField.getText().equals("") || 
						!searchTextField.getText().matches("-?\\d+(\\.\\d+)?")){
					JOptionPane.showMessageDialog(null,
							"Please enter a valid product number!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				// Else, load data from database.
				else {
					String[] rowData = new String[5];
					SQLManager sql = new SQLManager();
					rowData = sql.getInventoryRow(Integer
							.parseInt(searchTextField.getText()));

					// If a wrong product numbered is entered, display error.
					if (rowData[0] == null && rowData[1] == null
							&& rowData[2] == null && rowData[3] == null
							&& rowData[4] == null) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid product number!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					// Else, load data into fields.
					else {
						nameTextField.setText(rowData[0]);
						priceTextField.setText(rowData[1]);
						quantityTextField.setText(rowData[2]);
						numberTextField.setText(rowData[3]);
						descriptionArea.setText(rowData[4]);
					}
				}
			}

			// Handle customerBut.
			if (event.getSource() == customerButton) {
				// Ask user to enter the customer email.
				String input = JOptionPane
						.showInputDialog("Enter customer Email:");

				// Verify input.
				if(input != null){
					// Get data from database.
					String[] custInfo = new String[6];
					SQLManager sql = new SQLManager();
	
					/*
					 * custInfo = sql.getClientInfo(input);
					 * 
					 * // Display data. JOptionPane.showMessageDialog(null, "Name: "
					 * + custInfo[1] "\n" + "Phone Number: " + custInfo[2] "\n" +
					 * "Email: " + custInfo[0] "\n" + "Transactions: " + custInfo[3]
					 * "\n" + "Total Spent: " + custInfo[4] "\n" + "Notes: " +
					 * custInfo[5] "\n", "Client Information",
					 * JOptionPane.INFORMATION_MESSAGE);
					 */
					
					
					 custInfo = sql.getClientInfo(input);
					 
					 // If the email does not exist or if email is not in the correct format,
					 // display error message.
					 if(custInfo[0] == null || !input.matches(
							"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
							+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){			
						 JOptionPane.showMessageDialog(null, "Please enter a valid email!", "Error",
								 JOptionPane.ERROR_MESSAGE);
					 // Else, get data from database.		 
					 }else{
						 double total = Double.parseDouble(custInfo[4]);
						 
						 JOptionPane.showMessageDialog(null, "Name: "
						 + custInfo[1] + "\n" + "Phone Number: " + custInfo[2] + "\n" +
						 "Email: " + custInfo[0] + "\n" + "Transactions: " + custInfo[3]
						 + "\n" + "Total Spent: " + String.format("$%.2f", total) + "\n" + "Notes: " +
						 custInfo[5] + "\n", "Client Information",
						 JOptionPane.INFORMATION_MESSAGE);
						 
					 }
					 
				}
				 
			}

			// Handle priceBut.
			if (event.getSource() == priceButton) {
				// Ask user to enter product number.
				String input = JOptionPane
						.showInputDialog("Enter product number:");

				if(input != null){
				
					// If nothing is entered or if the string does not contains only numbers,
					// display error.
					if (input == null || input.isEmpty() || 
							!input.matches("-?\\d+(\\.\\d+)?")) {
						JOptionPane.showMessageDialog(null,
								"Enter a valid product number", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					// Else, get data from database.
					else {
						String[] rowData = new String[5];
						String price;
						SQLManager sql = new SQLManager();
						rowData = sql.getInventoryRow(Integer.parseInt(input));
	
						// If a wrong product number is entered, display error.
						if (rowData[1] == null) {
							JOptionPane.showMessageDialog(null,
									"Enter a valid product number", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
						// Else, display the price of the product.
						else {
							price = rowData[1];
							JOptionPane.showMessageDialog(null, "The price is: $"
									+ price);
						}
					}
				
				}
			}

			// Handle clearBut.
			if (event.getSource() == clearButton) {
				// Clear fields.
				nameTextField.setText("");
				numberTextField.setText("");
				quantityTextField.setText("");
				descriptionArea.setText("");
				priceTextField.setText("");
			}

			// Handle addButton.
			if (event.getSource() == addButton) {
				// Get text from text fields and put the on one row in the table
				String nameText = nameTextField.getText();
				model.setValueAt(nameText, rows, 0);
				String numberText = numberTextField.getText();
				model.setValueAt(numberText, rows, 1);
				String priceText = priceTextField.getText();
				model.setValueAt(priceText, rows, 3);

				// Set quantity to one.
				// User will be able to edit this value if quantity is
				// greater than one.
				int quantity = 1;
				model.setValueAt(quantity, rows, 2);

				// Move to the next row.
				rows++;
			}

			// Handle removeButton.
			if (event.getSource() == removeButton) {
				// Move back one row.
				rows--;

				// Display error message if no row is selected.
				if (table.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null,
							"Please select a row to remove!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				// Delete rows.
				if (rows >= 0) {
					for (int i = 0; i < 4; i++) {
						model.setValueAt("", table.getSelectedRow(), i);
					}
				}

				// Variable to count the number of rows to be moved.
				int count = 0;

				// Move data from rows below the one that was just erased up.
				for (int j = table.getSelectedRow(); j < rows + 1; j++) {
					for (int i = 0; i < 4; i++) {
						model.setValueAt(
								model.getValueAt(table.getSelectedRow()
										+ (count + 1), i),
								table.getSelectedRow() + count, i);
					}
					count++;
				}
			}

			// Handle removeAllButton
			if (event.getSource() == removeAllButton) {
				// Make sure rows never go below zero.
				if (rows >= 0) {
					// Clear all rows.
					for (int j = 0; j < rows; j++) {
						for (int i = 0; i < 4; i++) {
							model.setValueAt("", j, i);
						}
					}
				}

				// Reset rows to 0
				rows = 0;
			}

			// Handle checkoutBut.
			if (event.getSource() == checkoutButton) {
				// Variables to calculate the total.
				double subTotal = 0.0;
				double total;
				double salesTax;
				boolean error = false;

				// Get data from the table.
				for (int i = 0; i < rows; i++) {
					double price = Double.parseDouble(String.valueOf(model
							.getValueAt(i, 3)));
					double quantity = 1;
					try{
					quantity = Double.parseDouble(String.valueOf(model
							.getValueAt(i, 2)));
					}catch(NumberFormatException e){
						
						JOptionPane.showMessageDialog(null,
								"Please enter a valid quantity!", "Error",
								JOptionPane.ERROR_MESSAGE);
						error = true;
						continue;
					}
					
					subTotal = price * quantity + subTotal;
				}

				if(!error){
					// Calculate taxes and total
					salesTax = subTotal * .06;
					total = subTotal + salesTax;
	
					// Display total.
	//				JOptionPane.showMessageDialog(
	//						null,
	//						"   Subtotal:  " + String.format("$%.2f", subTotal)
	//								+ "\n" + "Sales Tax:  "
	//								+ String.format("$%.2f", salesTax) + "\n"
	//								+ "------------------------\n"
	//								+ "          Total:  "
	//								+ String.format("$%.2f", total), "Total",
	//						JOptionPane.INFORMATION_MESSAGE);
					
	
					// Display total.
	
					JPanel panel = new JPanel();
					JPanel panel1 = new JPanel();
					JPanel panel2 = new JPanel();
					panel1.setLayout(new GridLayout(7,1));
	//				panel2.setLayout(new GridLayout(1,2));
					panel.setLayout(new GridLayout(2,1));
	
					JLabel label1 = new JLabel("   Subtotal:  " + String.format("$%.2f", subTotal));
					JLabel label2 = new JLabel("Sales Tax:  "+ String.format("$%.2f", salesTax));
					JLabel label3 = new JLabel("------------------------");
					JLabel label4 = new JLabel("          Total:  "	+ String.format("$%.2f", total));
	
					JLabel label5 = new JLabel("");
					JLabel label6 = new JLabel("Label email? (optional)");
					JTextField eText = new JTextField(20);
	
					JLabel label7 = new JLabel("");
	//				eText.setPreferredSize(new Dimension(60, 20));
	//				eText.setMaximumSize(new Dimension (15, 10));
					
					panel1.add(label1);
					panel1.add(label2);
					panel1.add(label3);
					panel1.add(label4);
					panel1.add(label5);
					panel1.add(label6);
					panel2.add(eText);
	//				panel2.add(label7);
	
					panel.add(panel1);
					panel.add(panel2);
					
					int result = JOptionPane.showConfirmDialog(
							null,
							panel, "Total",
							JOptionPane.OK_CANCEL_OPTION);
					
					SQLManager sql = new SQLManager();
	
					boolean optionFilled = false;
					
					if(!eText.getText().equals("")){		
						
						if(!sql.checkUserExistence(eText.getText())){
							JOptionPane.showMessageDialog(null,
									"Please enter a valid customer email! Order not logged.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}else{
							String[] s = sql.getClientInfo(eText.getText());
							
							if(s[0] != null){
		
								double newTotal = total + Double.parseDouble(s[4]);
								int newTransaction = Integer.parseInt(s[3]) + 1;
								
								
								sql.logTransaction(newTransaction, newTotal, eText.getText());
								
							
								int itemID = 0;
								int newQuantity = 0;
								
//								for(int i = 0; i < table.getRowCount(); i++){
//									
//									itemID = Integer.parseInt((String) table.getValueAt(i, 1));
//									newQuantity = Integer.parseInt((String) table.getValueAt(i, 2));
//
//									sql.setInventoryQuantity(itemID, newQuantity);
//								}
								
								optionFilled = true;
							}
						}
					}				
					
					//TODO: fix this. ClassCastException errors everywhere
					if(!optionFilled){
						
//						int itemID = 0;
//						int newQuantity = 0;
//						
//						for(int i = 0; i < table.getRowCount(); i++){
//							
//							itemID = Integer.parseInt((String) table.getValueAt(i, 1));
//							newQuantity = Integer.parseInt((String) table.getValueAt(i, 2));
//
//							sql.setInventoryQuantity(itemID, newQuantity);
//						}
						
					}
					
					//TODO: get eTextField entry and call logTransactions
				
				}
			}
		}
	}
}
