package databaseServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

public class Individual {
	public Species species;
	public int individualID;
	public String nickName;
	public String nature;
	public int level;
	public int ivhp;
	public int ivatk;
	public int ivdef;
	public int ivspd;
	public int ivspatk;
	public int ivspdef;
	public int evhp;
	public int evatk;
	public int evdef;
	public int evspd;
	public int evspatk;
	public int evspdef;
	public String itemName;
	public String ability;
	private ArrayList<Move> moves;
	public DatabaseConnectionService db;
	private String printMessage;

	public Individual(int individualID, String nickName, String nature, int level, int ivhp, int ivatk, int ivdef,
			int ivspd, int ivspatk, int ivspdef, int evhp, int evatk, int evdef, int evspd, int evspatk, int evspdef,
			String itemName, String ability, String speciesName, DatabaseConnectionService db) throws SQLException {
		this.db = db;
		Map<Integer, Species> specieses = new SpeciesService(db).getSpecies(speciesName, null, null, null);
		for (int key : specieses.keySet()) {
			this.species = specieses.get(key);
		}
		this.individualID = individualID;
		this.nickName = nickName;
		this.nature = nature;
		this.level = level;
		this.ivhp = ivhp;
		this.ivatk = ivatk;
		this.ivdef = ivdef;
		this.ivspd = ivspd;
		this.ivspatk = ivspatk;
		this.ivspdef = ivspdef;
		this.evhp = evhp;
		this.evatk = evatk;
		this.evdef = evdef;
		this.evspd = evspd;
		this.evspatk = evspatk;
		this.evspdef = evspdef;
		this.itemName = itemName;
		this.ability = ability;
		this.nature = nature;
		this.moves = new ArrayList<>();
	}

	public void modifyIndividual(String nickName, String nature, String level, String ivhp, String ivatk, String ivdef,
			String ivspd, String ivspatk, String ivspdef, String evhp, String evatk, String evdef, String evspd,
			String evspatk, String evspdef, String item, String ability, String move1, String move2, String move3,
			String move4) throws Exception {
		printMessage = "";
		this.level = this.tryChangeStat("level", level, this.level, 100);
		this.ivhp = this.tryChangeStat("ivhp", ivhp, this.ivhp, 31);
		this.ivatk = this.tryChangeStat("ivatk", ivatk, this.ivatk, 31);
		this.ivdef = this.tryChangeStat("ivdef", ivdef, this.ivdef, 31);
		this.ivspd = this.tryChangeStat("ivspd", ivspd, this.ivspd, 31);
		this.ivspatk = this.tryChangeStat("ivspatk", ivspatk, this.ivspatk, 31);
		this.ivspdef = this.tryChangeStat("ivspdef", ivspdef, this.ivspdef, 31);
		this.evhp = this.tryChangeStat("evhp", evhp, this.evhp, 255);
		this.evatk = this.tryChangeStat("evatk", evatk, this.evatk, 255);
		this.evdef = this.tryChangeStat("evdef", evdef, this.evdef, 255);
		this.evspd = this.tryChangeStat("evspd", evspd, this.evspd, 255);
		this.evspatk = this.tryChangeStat("evspatk", evspatk, this.evspatk, 255);
		this.evspdef = this.tryChangeStat("evspdef", evspdef, this.evspdef, 255);
		this.evhp = this.adjustevs(this.evhp);
		this.evatk = this.adjustevs(this.evatk);
		this.evdef = this.adjustevs(this.evdef);
		this.evspd = this.adjustevs(this.evspd);
		this.evspatk = this.adjustevs(this.evspatk);
		this.evspdef = this.adjustevs(this.evspdef);
		this.nickName = nickName;
		if (this.nickName == null)
			this.nickName = this.species.getValue("Name");

		if (this.nickName.length() > 10) {
			printMessage += "The nick name can have a max length of 10.\n";
			this.nickName = this.nickName.substring(0, 10);
		}
		if (printMessage.length() != 0)
			JOptionPane.showMessageDialog(null, printMessage);
		this.nature = nature;
		this.itemName = item;
		this.ability = ability;
		this.retrieveMoves(move1, move2, move3, move4);
		String query = "execute modifyIndividual ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
		PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
		stmt.setInt(1, this.individualID);
		stmt.setString(2, this.nickName);
		stmt.setString(3, this.nature);
		stmt.setInt(4, this.level);
		stmt.setInt(5, this.ivhp);
		stmt.setInt(6, this.ivatk);
		stmt.setInt(7, this.ivdef);
		stmt.setInt(8, this.ivspd);
		stmt.setInt(9, this.ivspatk);
		stmt.setInt(10, this.ivspdef);
		stmt.setInt(11, this.evhp);
		stmt.setInt(12, this.evatk);
		stmt.setInt(13, this.evdef);
		stmt.setInt(14, this.evspd);
		stmt.setInt(15, this.evspatk);
		stmt.setInt(16, this.evspdef);
		stmt.setString(17, item);
		stmt.setString(18, ability);
		if (move1 == null) {
			stmt.setInt(19, 0);
		} else {
			stmt.setInt(19, Integer.parseInt(move1));
		}
		if (move2 == null) {
			stmt.setInt(20, 0);
		} else {
			stmt.setInt(20, Integer.parseInt(move2));
		}
		if (move3 == null) {
			stmt.setInt(21, 0);
		} else {
			stmt.setInt(21, Integer.parseInt(move3));
		}
		if (move4 == null) {
			stmt.setInt(22, 0);
		} else {
			stmt.setInt(22, Integer.parseInt(move4));
		}

		stmt.executeUpdate();

	}

	public String getValue(String propertyName) {
		switch (propertyName) {
		case "individualID":
			return this.individualID + "";
		case "Pokemon":
			return this.nickName;
		case "nature":
			return this.nature;
		case "ivhp":
			return this.ivhp + "";
		case "ivatk":
			return this.ivatk + "";
		case "ivdef":
			return this.ivdef + "";
		case "ivspd":
			return this.ivspd + "";
		case "ivspatk":
			return this.ivspatk + "";
		case "ivspdef":
			return this.ivspdef + "";
		case "evhp":
			return this.evhp + "";
		case "evatk":
			return this.evatk + "";
		case "evdef":
			return this.evdef + "";
		case "evspd":
			return this.evspd + "";
		case "evspatk":
			return this.evspatk + "";
		case "evspdef":
			return this.evspdef + "";
		case "item":
			return this.itemName;
		case "AbilityName":
			return this.ability;
		case "level":
			return this.level + "";
		case "move1":
			if (this.moves.size() > 0 && this.moves.get(0) != null)
				return this.moves.get(0).getValue("name");
			return null;
		case "move2":
			if (this.moves.size() > 1 && this.moves.get(1) != null)
				return this.moves.get(1).getValue("name");
			return null;
		case "move3":
			if (this.moves.size() > 2 && this.moves.get(2) != null)
				return this.moves.get(2).getValue("name");
			return null;
		case "move4":
			if (this.moves.size() > 3 && this.moves.get(3) != null)
				return this.moves.get(3).getValue("name");
			return null;
		case "Remove":
			return "x";
		case "HP":
			return (int) Math.floor(
					((double) (2 * this.species.hp + this.ivhp + this.evhp) * this.level) / 100 + this.level + 10) + "";
		case "ATK":
			return this.calculateStat("atk", this.species.atk, this.ivatk, this.evatk) + "";
		case "DEF":
			return this.calculateStat("def", this.species.def, this.ivdef, this.evdef) + "";
		case "SPD":
			return this.calculateStat("spd", this.species.spd, this.ivspd, this.evspd) + "";
		case "SP_ATK":
			return this.calculateStat("spatk", this.species.spatk, this.ivspatk, this.evspatk) + "";
		case "SP_DEF":
			return this.calculateStat("spdef", this.species.spdef, this.ivspdef, this.evspdef) + "";
		default:
			break;
		}
		return null;
	}

	public void retrieveMoves(String move1, String move2, String move3, String move4) throws Exception {
		String[] newMoves = { move1, move2, move3, move4 };
		this.moves = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			if (newMoves[i] != null) {
				String query = "execute getMoveByID ?";
				PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
				stmt.setInt(1, Integer.parseInt(newMoves[i]));
				ResultSet rs = stmt.executeQuery();
				Move newMove = null;
				while (rs.next()) {
					newMove = new Move(rs.getInt("id"), rs.getString("Name"), rs.getString("Description"),
							rs.getInt("Power"), rs.getString("type"), rs.getInt("pp"), rs.getInt("Accuracy"));
				}
				this.moves.add(newMove);
			}
		}
	}

	public ArrayList<Move> getMoves() {
		int size = this.moves.size();
		for (int i = 0; i < 4 - size; i++) {

			this.moves.add(null);

		}
		return this.moves;
	}

	public void addMove(Move newMove) {
		this.moves.add(newMove);
	}

	public int calculateStat(String statType, int b, int i, int e) {
		double result = Math.floor(((double) (2 * b + i + e)) * this.level / 100 + 5);
		switch (statType) {
		case "atk":
			if (this.nature.equals("Lonely") || this.nature.equals("Adamant") || this.nature.equals("Naughty")
					|| this.nature.equals("Brave")) {
				result = Math.floor(result * 1.1);
			} else if (this.nature.equals("Bold") || this.nature.equals("Modest") || this.nature.equals("Calm")
					|| this.nature.equals("Timid")) {
				result = Math.floor(result * 0.9);
			}
			break;
		case "def":
			if (this.nature.equals("Bold") || this.nature.equals("Impish") || this.nature.equals("Lax")
					|| this.nature.equals("Relaxed")) {
				result = Math.floor(result * 1.1);
			} else if (this.nature.equals("Lonely") || this.nature.equals("Mild") || this.nature.equals("Gentle")
					|| this.nature.equals("Hasty")) {
				result = Math.floor(result * 0.9);
			}
			break;
		case "spd":
			if (this.nature.equals("Timid") || this.nature.equals("Hasty") || this.nature.equals("Jolly")
					|| this.nature.equals("Naive")) {
				result = Math.floor(result * 1.1);
			} else if (this.nature.equals("Brave") || this.nature.equals("Relaxed") || this.nature.equals("Quiet")
					|| this.nature.equals("Sassy")) {
				result = Math.floor(result * 0.9);
			}
			break;
		case "spatk":
			if (this.nature.equals("Modest") || this.nature.equals("Mild") || this.nature.equals("Rash")
					|| this.nature.equals("Quiet")) {
				result = Math.floor(result * 1.1);
			} else if (this.nature.equals("Adamant") || this.nature.equals("Impish") || this.nature.equals("Careful")
					|| this.nature.equals("Jolly")) {
				result = Math.floor(result * 0.9);
			}
			break;
		case "spdef":
			if (this.nature.equals("Calm") || this.nature.equals("Gentle") || this.nature.equals("Careful")
					|| this.nature.equals("Sassy")) {
				result = Math.floor(result * 1.1);
			} else if (this.nature.equals("Naughty") || this.nature.equals("Lax") || this.nature.equals("Rash")
					|| this.nature.equals("Naive")) {
				result = Math.floor(result * 0.9);
			}
			break;
		}
		return (int) result;
	}

	public int tryChangeStat(String statTriedToChange, String value, int originalValue, int maxValue) {
		try {
			int result = Integer.parseInt(value);
			if (result < 0 || result > maxValue) {
				printMessage = printMessage + "The value for " + statTriedToChange + " should be between 0 and "
						+ maxValue + "\n";
				return originalValue;
			}
			return result;
		} catch (NumberFormatException e) {
			printMessage = printMessage + "The other statuses are changed except for " + statTriedToChange + ".\n"
					+ statTriedToChange + " should be an integer!\n";
			return originalValue;
		}
	}

	public int adjustevs(int toChange) {
		int evall = this.evhp + this.evatk + this.evdef + this.evspd + this.evspatk + this.evspdef;

		if (evall > 510) {
			if (evall - 510 > toChange) {
				evall -= toChange;
				return 0;
			} else {
				printMessage = printMessage + "The total value for ev should be less than 510\n";
				return this.evhp + 510 - evall;
			}
		}
		return toChange;
	}

}
