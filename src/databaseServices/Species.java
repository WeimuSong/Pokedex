package databaseServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Species {
	public int id;
	public String name;
	public String type1;
	public String type2;
	public ArrayList<String> abilities;
	public int hp;
	public int atk;
	public int def;
	public int spd;
	public int spdef;
	public int spatk;
	private ArrayList<Move> moves;
	private DatabaseConnectionService db;

	public Species(int id, String name, String type1, String type2, String ability, int hp, int atk, int def, int spd,
			int spdef, int spatk, DatabaseConnectionService db) {
		abilities = new ArrayList<>();
		this.id = id;
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.abilities.add(ability);
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.spd = spd;
		this.spdef = spdef;
		this.spatk = spatk;
		this.db = db;
		moves = new ArrayList<>();
	}

	public String getValue(String propertyName) {
		switch (propertyName) {
		case "PokeID":
			return this.id + "";
		case "Name":
			return this.name;
		case "Type1":
			return this.type1;
		case "Type2":
			return this.type2;
		case "HP":
			return this.hp + "";
		case "ATK":
			return this.atk + "";
		case "DEF":
			return this.def + "";
		case "SPD":
			return this.spd + "";
		case "SP.DEF":
			return this.spdef + "";
		case "SP.ATK":
			return this.spatk + "";
		case "AbilityName":
			return this.abilities.toString();
		default:
			break;
		}
		return propertyName;
	}

	public ArrayList<String> getAbilities() {
		return this.abilities;
	}

	public void addAbility(String ability) {
		if (!this.abilities.contains(ability))
			this.abilities.add(ability);
	}

	public ArrayList<Move> getMoves() throws Exception {
		if (this.moves.size() == 0)
			this.retreveMoves();
		return this.moves;
	}

	private void retreveMoves() throws Exception {
		String query = "execute getMove ?";
		PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
		stmt.setInt(1, this.id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Move newMove = new Move(rs.getInt("id"), rs.getString("Name"), rs.getString("Description"),
					rs.getInt("Power"), rs.getString("type"), rs.getInt("pp"), rs.getInt("accuracy"));
			this.moves.add(newMove);
		}
	}

}
