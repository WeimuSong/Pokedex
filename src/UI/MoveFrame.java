package UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import databaseServices.DatabaseConnectionService;
import databaseServices.IndividualService;

public class MoveFrame extends JFrame {
	private String pokeID;
	private JPanel movePanel;
	private DatabaseConnectionService db;
	private JButton addPokemon;
	private JLabel pokelable;
	// private IndividualService is;

	public MoveFrame(DatabaseConnectionService db, String pokeID, String username) throws Exception {
		super();
		this.pokeID = pokeID;
		this.db = db;
		IndividualService is = new IndividualService(db, username);
		this.setSize(1000, 750);
		this.setTitle("Moves");
		this.movePanel = new JPanel();
		this.addPokemon = new JButton("Like this Pokemon");
		this.addPokemon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					is.addNewIndividual(pokeID);
					JOptionPane.showMessageDialog(null, "Successfully Sent to Bank");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		this.movePanel.setSize(1000, 750);
		this.movePanel.setLayout(new BorderLayout());
		JScrollPane tablePanel = getMoveTable();
		if(Integer.parseInt(pokeID) <= 721){
			this.pokelable = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/icons/"+ pokeID +".png")));
		}else{
			this.pokelable = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/icons/egg.png")));
		}
		this.movePanel.add(addPokemon, BorderLayout.NORTH);
		this.movePanel.add(pokelable, BorderLayout.CENTER);
		this.movePanel.add(tablePanel, BorderLayout.SOUTH);
		this.add(this.movePanel);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
	}

	private JScrollPane getMoveTable() throws Exception {
		JTable resultTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(resultTable);
		ArrayList<String> moves = this.getMoves();
		MoveModel tableModel = new MoveModel(moves);
		resultTable.setModel(tableModel);
		resultTable.setFillsViewportHeight(true);
		return scrollPane;
	}

	public ArrayList<String> getMoves() throws Exception {
		String query = "execute getMove ?";
		PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
		stmt.setString(1, this.pokeID);
		ResultSet rs = stmt.executeQuery();
		ArrayList<String> moves = new ArrayList<>();
		int name = rs.findColumn("Name");
		int description = rs.findColumn("Description");
		int power = rs.findColumn("Power");
		int type = rs.findColumn("type");
		int pp = rs.findColumn("pp");
		int accuracy = rs.findColumn("accuracy");
		while (rs.next()) {
			String newMove = rs.getString(name) + "\0" + rs.getString(description) + "\0" + rs.getString(power) + "\0"
					+ rs.getString(type) + "\0" + rs.getString(pp) + "\0" + rs.getString(accuracy);
			moves.add(newMove);
		}
		return moves;
	}
}
