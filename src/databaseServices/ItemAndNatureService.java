package databaseServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemAndNatureService {
	private DatabaseConnectionService db;
	private ArrayList<String> items;
	private String[] natures = { "Hardy", "Lonely", "Adamant", "Naughty", "Brave", "Bold", "Docile", "Impish", "Lax",
			"Relaxed", "Modest", "Mild", "Bashful", "Rash", "Quiet", "Calm", "Gentle", "Careful", "Quirky", "Sassy",
			"Timid", "Hasty", "Jolly", "Naive", "Serious" };

	public ItemAndNatureService(DatabaseConnectionService db) {
		this.db = db;
		this.items = new ArrayList<>();
	}

	public void getAllItems() throws Exception {
		String query = "execute getAllItems";
		PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			this.items.add(rs.getString("ItemName"));
		}

	}

	public ArrayList<String> getItems() throws Exception {
		if (this.items.size() == 0) {
			this.getAllItems();
		}
		return this.items;
	}

	public ArrayList<String> getNatures() {
		ArrayList<String> naturesToReturn = new ArrayList<>();
		for (int i = 0; i < natures.length; i++) {
			naturesToReturn.add(natures[i]);
		}
		return naturesToReturn;
	}
}
