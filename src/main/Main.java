package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;

import UI.Frame;
import databaseServices.*;
import UI.LoginPrompt;
import UI.WindowCloseListener;
import UI.LoginComplete;

public class Main {

	private static String serverUsername = null;
	private static String serverPassword = null;
	private static DatabaseConnectionService dbService = null;
	private static LoginPrompt lp = null;
	private static WindowCloseListener wcl = null;
	private static String username = null;
	// private static EncryptionService es = new EncryptionService();

	public static void main(String[] args) {
		dbService = new DatabaseConnectionService();
		// System.out.println(dbService.connect());
		wcl = new WindowCloseListener(dbService);
		final UserService userService = new UserService(dbService);
		LoginComplete lc = new LoginComplete() {
			@Override
			public void login(String u, String p) throws SQLException {
				if (userService.login(u, p)) {
					try {
						username = u;
						loginSucceeded();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Login unsuccessful.");
				}
			}

			@Override
			public void register(String u, String p) throws SQLException {
				if (userService.register(u, p)) {
					try {
						username = u;
						loginSucceeded();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Registration unsuccessful.");
				}
			}
		};

		if (dbService.connect() == false) {
			JOptionPane.showMessageDialog(null, "Connection to database could not be made.");
		} else {
			if (userService.useApplicationLogins()) {

				try {
					lp = new LoginPrompt(lc);
					lp.addWindowListener(wcl);
				} catch (Exception eee) {
					eee.printStackTrace();
				}
			} else {
				try {
					loginSucceeded();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void loginSucceeded() throws Exception {
		if (lp != null) {
			lp.setVisible(false);
			lp.dispose();
		}
		SpeciesService sService = new SpeciesService(dbService);
		IndividualService iService = new IndividualService(dbService, username);
		ItemAndNatureService ins = new ItemAndNatureService(dbService);
		TeamService ts = new TeamService(dbService, username);
		JFrame PokeFrame = new Frame(sService, iService, ins, ts, username);
		PokeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PokeFrame.addWindowListener(wcl);

	}

}
