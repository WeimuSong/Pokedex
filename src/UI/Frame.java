package UI;

import databaseServices.IndividualService;
import databaseServices.ItemAndNatureService;
import databaseServices.SpeciesService;
import databaseServices.TeamService;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Frame extends JFrame {

	private JPanel addPanel;
	private JPanel likedPanel;
	private JPanel containerPanel;
	private JPanel currentPanel = null;
	private TextArea resultDisplay;
	private PokedexPanel pokedexPanel;
	private JMenu menu;
	private JMenuItem team;
	private JMenuItem pokedex;
	private JMenuItem LikedPokemon;
	private PokeBankPanel pokeBankPanel;
	private TeamPanel teamPanel;
	private String username;

	public Frame(SpeciesService sService, IndividualService iService, ItemAndNatureService ins, TeamService ts, String username)
			throws Exception {
		super();
		this.username = username;
		this.setSize(1000, 750);
		this.setResizable(false);
		this.setTitle("Pokedex");

		this.containerPanel = new JPanel();
		this.containerPanel.setSize(1000, 750);
		this.pokedexPanel = new PokedexPanel(sService, this.username);
		this.addPanel = this.getAddPanel(ts, iService);
		this.currentPanel = this.pokedexPanel;
		this.addPanel.setVisible(false);
		this.likedPanel = this.getLikedPanel(iService, ins);
		this.likedPanel.setVisible(false);

		this.menu = new JMenu("Screens");
		this.pokedex = new JMenuItem("Pokedex");

		this.pokedex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Frame.this.switchToResult();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		this.team = new JMenuItem("Team");
		this.team.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToTeam();
			}
		});

		this.LikedPokemon = new JMenuItem(("PokeBank"));
		this.LikedPokemon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToLiked();
				pokeBankPanel.updateBank();
			}
		});

		this.menu.add(this.pokedex);
		this.menu.add(this.team);
		this.menu.add(this.LikedPokemon);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(this.menu);

		this.add(menuBar, "North");
		this.add(containerPanel, "Center");
		containerPanel.add(this.pokedexPanel);
		containerPanel.add(this.addPanel);
		containerPanel.add(this.likedPanel);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.pack();
	}

	private JPanel getAddPanel(TeamService ts, IndividualService iService) throws Exception {
		JPanel toReturn = new JPanel();
		BorderLayout layout = (new BorderLayout());
		toReturn.setLayout(layout);
		teamPanel = new TeamPanel(ts, iService);
		toReturn.add(teamPanel);
		return toReturn;

	}

	private JPanel getLikedPanel(IndividualService iService, ItemAndNatureService ins) throws Exception {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BorderLayout());
		pokeBankPanel = new PokeBankPanel(iService, ins);
		toReturn.add(pokeBankPanel);
		return toReturn;
	}

	private void switchPanel(Component toRemove1, Component toRemove2) {
		toRemove1.setVisible(false);
		toRemove2.setVisible(false);
		this.currentPanel.setVisible(true);
	}

	private void switchToResult() throws SQLException {
		if (this.pokedexPanel != this.currentPanel) {
			this.currentPanel = this.pokedexPanel;
			this.switchPanel(this.addPanel, this.likedPanel);
		}
	}

	private void switchToTeam() {
		if (this.addPanel != this.currentPanel) {
			this.currentPanel = this.addPanel;
			this.switchPanel(this.pokedexPanel, this.likedPanel);
		}
	}

	private void switchToLiked() {
		if (this.likedPanel != this.currentPanel) {
			this.currentPanel = this.likedPanel;
			this.switchPanel(this.pokedexPanel, this.addPanel);
		}
	}

}
