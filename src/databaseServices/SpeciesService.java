package databaseServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;

public class SpeciesService {
	private DatabaseConnectionService dbService = null;

	public SpeciesService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public Map<Integer, Species> getSpecies(String name, String ability, String move, String type) throws SQLException {
		String query = "select distinct s.PokeID, s.Name, s.HP, s.ATK, s.DEF, s.SPD, s.[SP.ATK], s.[SP.DEF], s.Type1, s.Type2, a.Name as AbilityName "
				+ "from Species s join CanHave ch on s.PokeID = ch.SpeciesID "
				+ "join Ability a on ch.AbilityID = a.id join Can_learn cl on s.PokeID = cl.PokeID\r\n"
				+ "join Move m on m.id = cl.moveid ";
		int count = 0;
		ArrayList<String> pieces = new ArrayList<>();
		if (name != null) {
			count++;
			query += "where s.Name = ? ";
			pieces.add(name.split(";")[0]);
		}
		if (ability != null) {
			query += (count > 0) ? "and a.Name = ? " : "where a.Name = ? ";
			pieces.add(ability.split(";")[0]);
			count++;
		}
		if (move != null) {
			query += (count > 0) ? "and m.Name = ? " : "where m.Name = ? ";
			pieces.add(move.split(";")[0]);
			count++;
		}
		if (type != null) {
			query += (count > 0) ? "and (s.type1 = ? or s.type2 = ?) " : "where s.type1 = ? or s.type2 = ? ";
			pieces.add(type.split(";")[0]);
			pieces.add(type.split(";")[0]);
			count += 2;
		}
		query += "order by s.pokeid asc";
		PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
		for (int i = 0; i < count; i++) {
			stmt.setString(i + 1, pieces.get(i));
		}
		ResultSet rs = stmt.executeQuery();
		return parseResults(rs);
	}

	private Map<Integer, Species> parseResults(ResultSet rs) {
		Map<Integer, Species> species = new TreeMap<>();
		try {
			while (rs.next()) {
				int id = rs.getInt("PokeID");
				if (species.containsKey(id)) {
					species.get(id).addAbility(rs.getString("AbilityName"));
				} else {
					species.put(id,
							new Species(rs.getInt("PokeID"), rs.getString("Name"), rs.getString("Type1"),
									rs.getString("Type2"), rs.getString("AbilityName"), rs.getInt("HP"),
									rs.getInt("ATK"), rs.getInt("DEF"), rs.getInt("SPD"), rs.getInt("SP.ATK"),
									rs.getInt("SP.DEF"), this.dbService));
				}
			}

			return species;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DatabaseConnectionService getDB() {
		return this.dbService;
	}
}
