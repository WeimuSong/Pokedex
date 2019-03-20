package databaseServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionService {

	private final String SampleURL = "jdbc:sqlserver://golem.csse.rose-hulman.edu;databaseName=pokedex;user=SodaBaseUserzhouy929;password={Password123}";

	private Connection connection = null;

	public DatabaseConnectionService() {
	}

	public boolean connect() {
		String URL = SampleURL;
		try {
			connection = DriverManager.getConnection(URL);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeConnection() {
		if (this.connection != null) {
			try {
				System.out.println("The connection is closed");
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
