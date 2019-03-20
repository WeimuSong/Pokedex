package databaseServices;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class IndividualService {
	private DatabaseConnectionService dbService = null;
	private String username;

	public IndividualService(DatabaseConnectionService dbService, String username) {
		this.dbService = dbService;
		this.username = username;
	}

	public ArrayList<Individual> getAllIndividuals() throws Exception {
		String query = "execute getAllIndividual ?";
		PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
		stmt.setString(1, this.username);
		ResultSet rs = stmt.executeQuery();
		return parseResults(rs);
	}

	public void addNewIndividual(String pokeID) throws Exception {
		String query = "execute createIndividual ?, ?";
		PreparedStatement stmt = this.dbService.getConnection().prepareStatement(query);
		stmt.setString(1, pokeID);
		stmt.setString(2, this.username);
		stmt.executeUpdate();

	}

	public ArrayList<Individual> parseResults(ResultSet rs) {
		Map<Integer, Individual> individuals = new TreeMap<>();
		try {
			while (rs.next()) {
				int id = rs.getInt("IndividualID");
				Move newMove = new Move(rs.getInt("moveID"), rs.getString("moveName"), rs.getString("moveDes"),
						rs.getInt("movePower"), rs.getString("moveType"), rs.getInt("movePP"), rs.getInt("moveAccu"));
				if (individuals.containsKey(id)) {
					individuals.get(id).addMove(newMove);
				} else {
					Individual newIndividual = new Individual(id, rs.getString("NickName"), rs.getString("Nature"), rs.getInt("Level"),
							rs.getInt("ivhp"), rs.getInt("ivatk"), rs.getInt("ivdef"), rs.getInt("ivspd"),
							rs.getInt("ivsp.atk"), rs.getInt("ivsp.def"), rs.getInt("evhp"), rs.getInt("evatk"),
							rs.getInt("evdef"), rs.getInt("evspd"), rs.getInt("evsp.atk"),
							rs.getInt("evsp.def"), rs.getString("ItemName"), rs.getString("AbilityName"),
							rs.getString("SpeciesName"), this.dbService);
					newIndividual.addMove(newMove);
					individuals.put(id, newIndividual);

				}
			}
			ArrayList<Individual> result = new ArrayList<>();
			for (int key : individuals.keySet()) {
				result.add(individuals.get(key));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteIndividual(String individualID) throws Exception {
		String query = "{ ? = call deleteIndividual(?, ?)}";
		CallableStatement stmt = this.dbService.getConnection().prepareCall(query);
		stmt.registerOutParameter(1, Types.INTEGER);
		stmt.setString(2, individualID);
		stmt.setString(3, this.username);
		stmt.execute();
		int result = stmt.getInt(1);
		if (result == 1) {
			JOptionPane.showMessageDialog(null, "The pokemon does not belong to you any more");
		}
//		else {
//			JOptionPane.showMessageDialog(null, "The pokemon is released, bye bye!");
//		}
	}
}
