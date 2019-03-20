package databaseServices;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

import databaseServices.DatabaseConnectionService;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return true;
	}

	public boolean login(String username, String password) throws SQLException {
		Connection con = this.dbService.getConnection();
		PreparedStatement stmt = null;
		String query = "select PasswordSalt, PasswordHash from [User] where Username = ?";
		stmt = con.prepareStatement(query);
		stmt.setString(1, username);
		try {
			ResultSet rs = stmt.executeQuery();

			System.out.println("executing");
			if (!rs.next()) {
				System.out.println("well..");
				JOptionPane.showMessageDialog(null, "The username or password does not exist");
				return false;
			}
			byte[] PasswordSalt = dec.decode(rs.getString(1));
			String PasswordHash = rs.getString(2);
			String hashedPassword = hashPassword(PasswordSalt, password);
			if (PasswordHash.equals(hashedPassword)) {
				return true;
			}
			JOptionPane.showMessageDialog(null, "The username or password does not exist");
			return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public boolean register(String username, String password) throws SQLException {
		byte[] newSalt = getNewSalt();
		String hashedPassword = hashPassword(newSalt, password);
		CallableStatement stmt = this.dbService.getConnection().prepareCall("{ ? = call Register(?, ?, ?)}");
		stmt.registerOutParameter(1, Types.INTEGER);
		stmt.setString(2, username);
		stmt.setString(3, getStringFromBytes(newSalt));
		stmt.setString(4, hashedPassword);
		stmt.execute();
		int returnValue = stmt.getInt(1);
		if (returnValue == 1) {
			JOptionPane.showMessageDialog(null, "Username cannot be null or empty.");
			return false;
		} else if (returnValue == 2) {
			JOptionPane.showMessageDialog(null, "PasswordSalt cannot be null or empty.");
			return false;
		} else if (returnValue == 3) {
			JOptionPane.showMessageDialog(null, "PasswordHash cannot be null or empty.");
			return false;
		} else if (returnValue == 4) {
			JOptionPane.showMessageDialog(null, "ERROR: Username already exists.");
			return false;
		}
		JOptionPane.showMessageDialog(null, "Successfully registered.");
		return true;
	}

	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}

	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
