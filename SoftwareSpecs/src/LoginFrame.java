import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {
	JButton loginButton;
	private JTextField userText;
	private JPasswordField passText;

	public LoginFrame() {
		super("Login");
		setLayout(new BorderLayout());

		JLabel background = new JLabel();

		// login panel is created
		JPanel login = new JPanel();
		login.setLayout(new GridLayout(14, 1));
		login.setBorder(new EmptyBorder(30, 20, 10, 50));

		JPanel logEmpty1 = new JPanel();
		JPanel logEmpty2 = new JPanel();
		JPanel logEmpty3 = new JPanel();

		JLabel loginLabel = new JLabel("Sign In");
		loginLabel.setFont(new Font(getName(), Font.BOLD, 20));
		JLabel loginLabel2 = new JLabel("Employees Only");
		loginLabel2.setFont(new Font(getName(), Font.PLAIN, 10));

		JLabel loginLabel3 = new JLabel("Username");
		JLabel loginLabel4 = new JLabel("Password");
		userText = new JTextField();
		passText = new JPasswordField();

		loginButton = new JButton("Log In");

		login.setSize(350, 400);
		login.setLocation(150, 10);

		login.add(loginLabel);
		login.add(loginLabel2);
		login.add(logEmpty1);
		login.add(loginLabel3);
		login.add(userText);
		login.add(logEmpty2);
		login.add(loginLabel4);
		login.add(passText);
		login.add(logEmpty3);
		login.add(loginButton);

		background.add(login);
		add(background);

		Handler handler = new Handler();
		loginButton.addActionListener(handler);
		userText.addActionListener(handler);
		passText.addActionListener(handler);
	}

	public boolean checkCredentials() {
		SQLManager userData = new SQLManager();
		String strPassword = new String(passText.getPassword());
		if (userData.checkUserExistence(userText.getText())) {
			if (userData.checkLogin(userText.getText(), strPassword)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		// return false;
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// if user tries to log in
			// check username and password against database
			if (checkCredentials()) {
				System.out.println("Login was pressed.");

				SQLManager sql = new SQLManager();
				boolean isAdmin = sql.checkAdmin(userText.getText());

				System.out.println("TESTING**** isAdmin = " + isAdmin);

				JFrame mainFrame = new MainFrame(userText.getText(), isAdmin);

				LoginFrame.this.dispose();

				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setSize(1000, 750);
				mainFrame.setResizable(true);
				mainFrame.setVisible(true);

				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width
						/ 2, dim.height / 2 - mainFrame.getSize().height / 2);

			} else { // if the username or password is incorrect
				JOptionPane.showMessageDialog(LoginFrame.this,
						"Incorrect username or password", "Login Failed",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}
}
