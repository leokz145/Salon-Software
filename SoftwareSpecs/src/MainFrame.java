import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MainFrame extends JFrame {

	static Color color = Color.BLACK;
	static JPanel currentPanel;
	JButton logoButton;
	JButton appointmentsButton;
	JButton inventoryButton;
	JButton employeeButton;
	JButton customerButton;
	JButton checkoutButton;
	String user;
	private JTextField searchBar;

	public MainFrame(String username, boolean isAdmin) {

		setLayout(new BorderLayout());

		setBackground(Color.DARK_GRAY);

		user = username;
		JPanel toolBar = new JPanel();
		JPanel headerBar = new JPanel();
		JPanel navigationBar = new JPanel();
		JPanel blank = new JPanel();

		headerBar.setBackground(new Color(30, 40, 50));
		navigationBar.setBackground(new Color(30, 40, 50));

		Border borderColor = new LineBorder(color.BLACK, 1);

		logoButton = new JButton("Michael's Salon!");
		logoButton.setPreferredSize(new Dimension(120, 100));
		logoButton.setBorder(borderColor);

		searchBar = new JTextField("");

		headerBar.setBorder(new EmptyBorder(10, 10, 0, 10));
		toolBar.setPreferredSize(new Dimension(1000, 100));
		searchBar.setPreferredSize(new Dimension(200, 24));

		appointmentsButton = new JButton("Appointments");
		appointmentsButton.setPreferredSize(new Dimension(100, 50));
		appointmentsButton.setBorder(borderColor);

		inventoryButton = new JButton("Inventory");
		inventoryButton.setPreferredSize(new Dimension(100, 50));
		inventoryButton.setBorder(borderColor);

		employeeButton = new JButton("Employee");
		employeeButton.setPreferredSize(new Dimension(100, 50));
		employeeButton.setBorder(borderColor);
		
		customerButton = new JButton("Customer");
		customerButton.setPreferredSize(new Dimension(100, 50));
		customerButton.setBorder(borderColor);

		checkoutButton = new JButton("Checkout");
		checkoutButton.setPreferredSize(new Dimension(100, 50));
		checkoutButton.setBorder(borderColor);

		toolBar.setLayout(new BorderLayout());
		headerBar.setLayout(new BorderLayout());
		navigationBar.setLayout(new FlowLayout());

		// headerBar.add(blank, BorderLayout.NORTH);
		headerBar.add(logoButton, BorderLayout.WEST);
		// headerBar.add(searchBar, BorderLayout.EAST);

		navigationBar.add(appointmentsButton);

		if (isAdmin) {
			navigationBar.add(inventoryButton);
			navigationBar.add(employeeButton);
			navigationBar.add(customerButton);
		}

		navigationBar.add(checkoutButton);

		toolBar.add(headerBar, BorderLayout.CENTER);
		toolBar.add(navigationBar, BorderLayout.SOUTH);

		add(toolBar, BorderLayout.NORTH);

		Handler handler = new Handler();

		appointmentsButton.addActionListener(handler);
		inventoryButton.addActionListener(handler);
		employeeButton.addActionListener(handler);
		customerButton.addActionListener(handler);
		checkoutButton.addActionListener(handler);
		logoButton.addActionListener(handler);

		logoButton.setBackground(new Color(200, 200, 200));
		appointmentsButton.setBackground(new Color(200, 200, 200));
		inventoryButton.setBackground(new Color(200, 200, 200));
		employeeButton.setBackground(new Color(200, 200, 200));
		customerButton.setBackground(new Color(200, 200, 200));
		checkoutButton.setBackground(new Color(200, 200, 200));

		appointmentsButton.addMouseListener(handler);
		inventoryButton.addMouseListener(handler);
		employeeButton.addMouseListener(handler);
		customerButton.addMouseListener(handler);
		checkoutButton.addMouseListener(handler);

		JPanel apps = new AppointmentPanel(user);
		addPanels(apps);

	}

	public void addPanels(JPanel panel) {
		JPanel temp;
		if (currentPanel != null) {
			temp = currentPanel;
			remove(currentPanel);
			revalidate();
		}
		currentPanel = panel;
		add(panel, BorderLayout.CENTER);

		validate();
	}

	public void buttonManager(String buttonPressed) {

		switch (buttonPressed) {
		case "Appointments":
			JPanel appPanel = new AppointmentPanel(user);
			addPanels(appPanel);
			break;
		case "Inventory":
			JPanel inventoryPanel = new InventoryPanel();
			addPanels(inventoryPanel);
			break;
		case "Employee":
			JPanel employeePanel = new EmployeePanel();
			addPanels(employeePanel);
			break;
		case "Customer":
			JPanel customerPanel = new CustomerPanel();
			addPanels(customerPanel);
			break;
		case "Checkout":
			JPanel checkoutPanel = new CheckoutPanel();
			addPanels(checkoutPanel);
			break;
		}
	}

	public void logout() {
		this.dispose();

		JFrame loginFrame = new LoginFrame();
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(600, 400);
		loginFrame.setResizable(false);
		loginFrame.setVisible(true);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		loginFrame.setLocation(dim.width / 2 - loginFrame.getSize().width / 2,
				dim.height / 2 - loginFrame.getSize().height / 2);

	}

	public class Handler implements ActionListener, MouseListener {

		public void actionPerformed(ActionEvent e) {

			String navButtonPressed = e.getActionCommand();

			if (e.getSource() == appointmentsButton)
				buttonManager("Appointments");
			else if (e.getSource() == inventoryButton)
				buttonManager("Inventory");
			else if (e.getSource() == employeeButton)
				buttonManager("Employee");
			else if (e.getSource() == customerButton)
				buttonManager("Customer");
			else if (e.getSource() == checkoutButton)
				buttonManager("Checkout");
			else if (e.getSource() == logoButton) {
				logout();
			}

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

			if (e.getSource() == checkoutButton) {
				checkoutButton.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == appointmentsButton) {
				appointmentsButton.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == inventoryButton) {
				inventoryButton.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == employeeButton) {
				employeeButton.setBackground(new Color(225, 225, 225));
			}
			if (e.getSource() == customerButton) {
				customerButton.setBackground(new Color(225, 225, 225));
			}

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

			if (e.getSource() == checkoutButton) {
				checkoutButton.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == appointmentsButton) {
				appointmentsButton.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == inventoryButton) {
				inventoryButton.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == employeeButton) {
				employeeButton.setBackground(new Color(200, 200, 200));
			}
			if (e.getSource() == customerButton) {
				customerButton.setBackground(new Color(200, 200, 200));
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
}
