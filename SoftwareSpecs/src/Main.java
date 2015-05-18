import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
	public static void main(String args[]) {

		// SQLTest sql = new SQLTest();

		// SQLManager sql = new SQLManager();
		// sql.getInventorySize();

		JFrame loginFrame = new LoginFrame();
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(600, 400);
		loginFrame.setResizable(false);
		loginFrame.setVisible(true);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		loginFrame.setLocation(dim.width / 2 - loginFrame.getSize().width / 2,
				dim.height / 2 - loginFrame.getSize().height / 2);

	}
}