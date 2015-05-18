import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddProduct extends JFrame {

	JLabel nameLabel = new JLabel("Name Label");
	JLabel qtyLabel = new JLabel("Qty Label");
	JLabel descripLabel = new JLabel("Descrip Label");
	JTextField nameField = new JTextField();
	JTextField qtyField = new JTextField();
	JTextField descripField = new JTextField();

	JButton add = new JButton("Add");
	JButton cancel = new JButton("Cancel");

	public AddProduct() {
		super("Add New Product");
		setSize(500, 200);
		setVisible(true);
		setResizable(false);
		Dimension newdim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(newdim.width / 2 - getSize().width / 2, newdim.height / 2
				- getSize().height / 2);

		setLayout(new BorderLayout());

		// Name panel
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		nameField.setPreferredSize(new Dimension(60, 20));
		namePanel.add(nameField);

		// Description panel
		JPanel descripPanel = new JPanel();
		descripPanel.add(descripLabel);
		descripField.setPreferredSize(new Dimension(60, 20));
		descripPanel.add(descripField);

		// Quantity Panel
		JPanel qtyPanel = new JPanel();
		qtyPanel.add(qtyLabel);
		qtyField.setPreferredSize(new Dimension(60, 20));
		qtyPanel.add(qtyField);

		JPanel addProductPanel = new JPanel();
		addProductPanel.setLayout(new BorderLayout());
		addProductPanel.add(namePanel, BorderLayout.NORTH);
		addProductPanel.add(descripPanel, BorderLayout.CENTER);
		addProductPanel.add(qtyPanel, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(add);
		buttonPanel.add(cancel);

		// Putting everything in window
		add(addProductPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
	}
}
