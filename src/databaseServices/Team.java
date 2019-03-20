package databaseServices;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Team {

	private ArrayList<Individual> pokemons;
	private String teamname;
	private String username;
	private DatabaseConnectionService db;
	private IndividualService is;

	public Team(String teamname, String username, DatabaseConnectionService db) {
		this.teamname = teamname;
		this.username = username;
		this.db = db;
		this.is = new IndividualService(this.db, this.username);
		this.pokemons = new ArrayList<>();
	}

	public String getTeamname() {
		return this.teamname;
	}

	public ArrayList<Individual> getPokemons() throws Exception {
		//if (this.pokemons.size() == 0) {
			this.retrievePokemons();
		//}
		return this.pokemons;
	}

	private void retrievePokemons() throws Exception {
		String query = "execute getIndividualInTeam ?, ?";
		PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
		stmt.setString(1, this.username);
		stmt.setString(2, this.teamname);
		ResultSet rs = stmt.executeQuery();
		this.pokemons = this.is.parseResults(rs);
	}

	public void addPokemon(Individual pokemon) throws Exception {
		String query = "{ ? = call addIndividualToTeam(?, ?, ?)}";
		CallableStatement stmt = this.db.getConnection().prepareCall(query);
		stmt.registerOutParameter(1, Types.INTEGER);
		stmt.setString(2, this.username);
		stmt.setString(3, this.teamname);
		stmt.setString(4, pokemon.getValue("individualID"));
		stmt.execute();
		int result = stmt.getInt(1);
		if (result == 1)
			JOptionPane.showMessageDialog(null, "The pokemon is already in the team!");
		else if (result == 2)
			JOptionPane.showMessageDialog(null, "There are already 6 pokemons in this team!");
		else
			this.pokemons.add(pokemon);
	}

	public void deletePokemon(Individual pokemon) throws Exception {
		String query = "execute removeIndividualFromTeam ?, ?, ?";
		PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
		stmt.setString(1, this.username);
		stmt.setString(2, this.teamname);
		stmt.setString(3, pokemon.getValue("individualID"));
		stmt.executeUpdate();
		for (int i = 0; i < this.pokemons.size(); i++) {
			if (pokemon.getValue("individualID").equals(this.pokemons.get(i).getValue("individualID"))) {
				this.pokemons.remove(i);
				break;
			}
		}
	}
}