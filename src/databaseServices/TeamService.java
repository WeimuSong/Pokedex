package databaseServices;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.sql.Types;

public class TeamService {
	private DatabaseConnectionService db;
	private String username;
	private ArrayList<Team> teams;

	public TeamService(DatabaseConnectionService db, String username) {
		this.db = db;
		this.username = username;
		this.teams = new ArrayList<>();
	}

	public void addNewTeam(String teamname) throws Exception {
		String query = "{ ? = call addNewTeam(?, ?)}";
		CallableStatement stmt = this.db.getConnection().prepareCall(query);
		stmt.registerOutParameter(1, Types.INTEGER);
		stmt.setString(2, this.username);
		stmt.setString(3, teamname);
		stmt.execute();
		int result = stmt.getInt(1);
		if (result == 1) {
			JOptionPane.showMessageDialog(null, "The user does not exist!");
		} else if (result == 2) {
			JOptionPane.showMessageDialog(null, "The team already exists!");
		} else if (result == 3) {
			JOptionPane.showMessageDialog(null, "The team name cannot be empty!");
		} else {
			Team newTeam = new Team(teamname, this.username, db);
			this.teams.add(newTeam);
			JOptionPane.showMessageDialog(null, "Successfully add the new team " + teamname);
		}
	}

	public ArrayList<Team> getTeams() throws Exception {
		if (this.teams.size() == 0) {
			this.retrieveTeams();
		}
		return this.teams;
	}

	private void retrieveTeams() throws Exception {
		String query = "execute getAllTeams ?";
		PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
		stmt.setString(1, this.username);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Team newTeam = new Team(rs.getString("TeamName"), rs.getString("username"), this.db);
			this.teams.add(newTeam);
		}
	}

	public void deleteTeam(String teamName) throws SQLException {
		String query = "{ ? = call deleteTeam(?, ?)}";
		CallableStatement stmt = this.db.getConnection().prepareCall(query);
		stmt.registerOutParameter(1, Types.INTEGER);
		stmt.setString(2, this.username);
		stmt.setString(3, teamName);
		stmt.execute();
		int returnValue = stmt.getInt(1);
		if (returnValue == 1) {
			JOptionPane.showMessageDialog(null, "The team does not exist any more!");
			return;
		}
		for (int i = 0; i < this.teams.size(); i++) {
			if (this.teams.get(i).getTeamname().equals(teamName)) {
				this.teams.remove(i);
				break;
			}
		}
		JOptionPane.showMessageDialog(null, "Successfully remove the team!");
	}
}
