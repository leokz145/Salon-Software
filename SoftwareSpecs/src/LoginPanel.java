import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LoginPanel extends JPanel {
	JPanel LoginPanel;
	CardLayout cl;
	JButton loginButton;
	JButton regButton;
	private JTextField userText;
	private JPasswordField passText;

	public LoginPanel() {
		LoginPanel = this;
		cl = new CardLayout();
		setLayout(cl);
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
		JLabel loginLabel2 = new JLabel("Using an existing account");
		loginLabel2.setFont(new Font(getName(), Font.PLAIN, 10));

		JLabel loginLabel3 = new JLabel("Username");
		JLabel loginLabel4 = new JLabel("Password");
		userText = new JTextField();
		passText = new JPasswordField();

		loginButton = new JButton("Log In");

		// register panel is created
		JPanel register = new JPanel();
		register.setLayout(new GridLayout(14, 1));
		register.setBorder(new EmptyBorder(30, 30, 10, 40));

		JPanel regEmpty1 = new JPanel();
		JPanel regEmpty2 = new JPanel();
		JPanel regEmpty3 = new JPanel();

		JLabel regLabel = new JLabel("Register");
		regLabel.setFont(new Font(getName(), Font.BOLD, 20));
		JLabel regLabel2 = new JLabel("A new account for free");
		regLabel2.setFont(new Font(getName(), Font.PLAIN, 10));

		JLabel regLabel3 = new JLabel("Why Join?");
		JLabel regLabel4 = new JLabel(
				"Gain access to thousands of games on multiple");
		JLabel regLabel5 = new JLabel(
				"systems. Rent, play, and rate titles and share");
		JLabel regLabel6 = new JLabel("them with your friends!");

		regLabel4.setFont(new Font(getName(), Font.PLAIN, 11));
		regLabel5.setFont(new Font(getName(), Font.PLAIN, 11));
		regLabel6.setFont(new Font(getName(), Font.PLAIN, 11));

		regButton = new JButton("Register");

		// login.setSize(345, 400);
		login.setSize(350, 400);
		login.setLocation(50, 40);
		// login.setLocation(175, 40);

		register.setSize(350, 400);
		register.setLocation(401, 40);
		// register.setLocation(400, 40);
		// register.setLocation(525, 40);

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

		register.add(regLabel);
		register.add(regLabel2);
		register.add(regEmpty1);
		register.add(regLabel3);
		register.add(regLabel4);
		register.add(regLabel5);
		register.add(regLabel6);
		register.add(regEmpty2);
		register.add(regEmpty3);
		register.add(regButton);

		background.add(login);
		background.add(register);
		add(background);

		cl.show(LoginPanel, "1");
	}

}
