package UI;

import databaseServices.Individual;
import databaseServices.IndividualService;
import databaseServices.Team;
import databaseServices.TeamService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamPanel extends JPanel {
	private TeamService ts;
	private JTextField newTeam = null;
	private JTable team = null;
	private JTable member = null;
	private TeamModel teamModel = null;
	private BankModel memberModel = null;
	private JButton addTeam = null;
	private JButton removeTeam = null;
	private JButton addMember = null;
	private JButton removeMember = null;

	private JTextPane nickName;
	private JTextPane nature;
	private JTextPane level;
	private JTextPane IVATK;
	private JTextPane IVDEF;
	private JTextPane IVHP;
	private JTextPane IVSPD;
	private JTextPane IVSP_DEF;
	private JTextPane IVSP_ATK;
	private JTextPane EVATK;
	private JTextPane EVDEF;
	private JTextPane EVHP;
	private JTextPane EVSPD;
	private JTextPane EVSP_DEF;
	private JTextPane EVSP_ATK;
	private JTextPane item;
	private JTextPane ability;
	private JTextPane move1;
	private JTextPane move2;
	private JTextPane move3;
	private JTextPane move4;

	private Team selectedTeam = null;
	private Individual selectedPokemon = null;
	private IndividualService is;

	public TeamPanel(TeamService ts, IndividualService is) throws Exception {
		this.ts = ts;
		this.is = is;
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		layout.setHgap(10);
		JPanel teamPart = this.createTeamPanel();
		teamPart.setPreferredSize(new Dimension(300, 200));
		JPanel memberPart = this.createMemberPanel();
		// memberPart.setPreferredSize(new Dimension(300, 300));
		JPanel infoPart = this.createInfoPanel();
		this.add(teamPart, BorderLayout.LINE_START);
		this.add(memberPart, BorderLayout.CENTER);
		this.add(infoPart, BorderLayout.EAST);
	}

	private JPanel createInfoPanel() {
		JPanel toReturn = new JPanel();
		GridLayout layout = new GridLayout(5, 1);
		toReturn.setLayout(layout);
		layout.setVgap(30);
		toReturn.setPreferredSize(new Dimension(400, 200));
		JPanel infoRow1 = generateInfoFirstRow();
		JPanel infoRow2 = generateInfoSecondRow();
		JPanel infoRow3 = generateInfoThirdRow();
		JPanel infoRow4 = generateInfoFourthRow();
		JPanel infoRow5 = generateInfoFifthRow();
		toReturn.add(infoRow1);
		toReturn.add(infoRow2);
		toReturn.add(infoRow3);
		toReturn.add(infoRow4);
		toReturn.add(infoRow5);
		return toReturn;
	}

	private JPanel generateInfoFirstRow() {
		JPanel toReturn = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(10);
		toReturn.setLayout(layout);
		toReturn.add(new JLabel("NickName:"));
		this.nickName = new JTextPane();
		this.nickName.setEditable(false);
		this.nickName.setPreferredSize(new Dimension(50, 20));
		toReturn.add(this.nickName);
		toReturn.add(new JLabel("Nature:"));
		this.nature = new JTextPane();
		this.nature.setEditable(false);
		this.nature.setPreferredSize(new Dimension(50, 20));
		toReturn.add(this.nature);
		toReturn.add(new JLabel("Level:"));
		this.level = new JTextPane();
		this.level.setEditable(false);
		this.level.setPreferredSize(new Dimension(50, 20));
		toReturn.add(this.level);
		return toReturn;
	}

	private JPanel generateInfoSecondRow() {
		JPanel toReturn = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(3);
		toReturn.setLayout(layout);
		toReturn.add(new JLabel("IVs:"));
		toReturn.add(new JLabel("HP"));
		this.IVHP = new JTextPane();
		this.IVHP.setEditable(false);
		this.IVHP.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.IVHP);
		toReturn.add(new JLabel("ATK"));
		this.IVATK = new JTextPane();
		this.IVATK.setEditable(false);
		this.IVATK.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.IVATK);
		toReturn.add(new JLabel("DEF"));
		this.IVDEF = new JTextPane();
		this.IVDEF.setEditable(false);
		this.IVDEF.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.IVDEF);
		toReturn.add(new JLabel("SPD"));
		this.IVSPD = new JTextPane();
		this.IVSPD.setEditable(false);
		this.IVSPD.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.IVSPD);
		toReturn.add(new JLabel("SP.ATK"));
		this.IVSP_ATK = new JTextPane();
		this.IVSP_ATK.setEditable(false);
		this.IVSP_ATK.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.IVSP_ATK);
		toReturn.add(new JLabel("SP.DEF"));
		this.IVSP_DEF = new JTextPane();
		this.IVSP_DEF.setEditable(false);
		this.IVSP_DEF.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.IVSP_DEF);
		return toReturn;
	}

	private JPanel generateInfoThirdRow() {
		JPanel toReturn = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(3);
		toReturn.setLayout(layout);
		toReturn.add(new JLabel("EVs:"));
		toReturn.add(new JLabel("HP"));
		this.EVHP = new JTextPane();
		this.EVHP.setEditable(false);
		this.EVHP.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.EVHP);
		toReturn.add(new JLabel("ATK"));
		this.EVATK = new JTextPane();
		this.EVATK.setEditable(false);
		this.EVATK.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.EVATK);
		toReturn.add(new JLabel("DEF"));
		this.EVDEF = new JTextPane();
		this.EVDEF.setEditable(false);
		this.EVDEF.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.EVDEF);
		toReturn.add(new JLabel("SPD"));
		this.EVSPD = new JTextPane();
		this.EVSPD.setEditable(false);
		this.EVSPD.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.EVSPD);
		toReturn.add(new JLabel("SP.ATK"));
		this.EVSP_ATK = new JTextPane();
		this.EVSP_ATK.setEditable(false);
		this.EVSP_ATK.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.EVSP_ATK);
		toReturn.add(new JLabel("SP.DEF"));
		this.EVSP_DEF = new JTextPane();
		this.EVSP_DEF.setEditable(false);
		this.EVSP_DEF.setPreferredSize(new Dimension(25, 20));
		toReturn.add(this.EVSP_DEF);
		return toReturn;
	}

	private JPanel generateInfoFourthRow() {
		JPanel toReturn = new JPanel();
		JPanel movePanel = new JPanel();
		FlowLayout layout = new FlowLayout();
		GridLayout moveLayout = new GridLayout(2, 2);
		moveLayout.setHgap(10);
		layout.setHgap(15);

		toReturn.setLayout(layout);
		movePanel.setLayout(moveLayout);
		this.item = new JTextPane();
		this.item.setEditable(false);
		this.item.setPreferredSize(new Dimension(100, 20));
		toReturn.add(new JLabel("Item:"));
		toReturn.add(this.item);
		this.ability = new JTextPane();
		this.ability.setEditable(false);
		this.ability.setPreferredSize(new Dimension(100, 20));
		toReturn.add(new JLabel("Ability:"));
		toReturn.add(this.ability);
		return toReturn;
	}

	private JPanel generateInfoFifthRow() {
		JPanel toReturn = new JPanel();
		JPanel movePanel = new JPanel();
		FlowLayout layout = new FlowLayout();
		GridLayout moveLayout = new GridLayout(2, 2);
		moveLayout.setHgap(10);
		moveLayout.setVgap(10);
		layout.setHgap(15);
		toReturn.setLayout(layout);
		movePanel.setLayout(moveLayout);
		this.move1 = new JTextPane();
		this.move1.setPreferredSize(new Dimension(150, 20));
		this.move1.setEditable(false);
		this.move2 = new JTextPane();
		this.move2.setPreferredSize(new Dimension(150, 20));
		this.move2.setEditable(false);
		this.move3 = new JTextPane();
		this.move3.setPreferredSize(new Dimension(150, 20));
		this.move3.setEditable(false);
		this.move4 = new JTextPane();
		this.move4.setPreferredSize(new Dimension(150, 20));
		this.move4.setEditable(false);
		movePanel.add(move1);
		movePanel.add(move2);
		movePanel.add(move3);
		movePanel.add(move4);
		toReturn.add(new JLabel("Move:"));
		toReturn.add(movePanel);
		return toReturn;
	}

	private JPanel createTeamPanel() throws Exception {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BorderLayout());
		JScrollPane teamTable = this.generateTeamTable();
		teamTable.setPreferredSize(new Dimension(300, 380));
		JPanel modifyPanel = this.createModifyTeamPanel();
		toReturn.add(teamTable, BorderLayout.NORTH);
		toReturn.add(modifyPanel, BorderLayout.SOUTH);
		return toReturn;
	}

	private JScrollPane generateTeamTable() throws Exception {
		team = new JTable();
		JScrollPane scrollPane = new JScrollPane(team);
		// scrollPane.setPreferredSize(new Dimension(200,200));
		ArrayList<Team> returnData = ts.getTeams();
		teamModel = new TeamModel(returnData);
		team.setModel(teamModel);

		ListSelectionModel teamSelectionModel = team.getSelectionModel();
		teamSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting())
					return;
				if (!teamSelectionModel.isSelectionEmpty()) {
					int selectedRow = teamSelectionModel.getMinSelectionIndex();
					selectedTeam = teamModel.getSelectedTeam(selectedRow);
					try {
						memberModel = new BankModel(selectedTeam.getPokemons());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					member.setModel(memberModel);
				}
			}
		});
		return scrollPane;
	}

	private JPanel createModifyTeamPanel() {
		JPanel toReturn = new JPanel();
		GridLayout layout = new GridLayout(2, 1);
		toReturn.setLayout(layout);
		this.newTeam = new JTextField();
		this.newTeam.setPreferredSize(new Dimension(100, 30));
		toReturn.add(this.newTeam);
		addTeam = new JButton("Add Team");
		toReturn.add(addTeam);
		removeTeam = new JButton("Remove Team");
		toReturn.add(new JPanel());
		toReturn.add(removeTeam);
		addTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String t = newTeam.getText();
					System.out.println(t);
					ts.addNewTeam(newTeam.getText());
					updateTeam();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		removeTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ts.deleteTeam(selectedTeam.getTeamname());
					updateTeam();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		return toReturn;
	}

	private boolean addTeam() {
		return false;
	}

	private JPanel createMemberPanel() throws Exception {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BorderLayout());
		JScrollPane memberTable = this.generateMemberTable();
		JPanel modifyPanel = this.createEditMemberPanel();
		toReturn.add(memberTable, BorderLayout.NORTH);
		toReturn.add(modifyPanel, BorderLayout.SOUTH);
		return toReturn;
	}

	private JScrollPane generateMemberTable() throws Exception {
		member = new JTable();
		JScrollPane scrollPane = new JScrollPane(member);
		ListSelectionModel memberSelectionModel = member.getSelectionModel();
		memberSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting())
					return;
				if (!memberSelectionModel.isSelectionEmpty()) {
					int selectedRow = memberSelectionModel.getMinSelectionIndex();
					System.out.println(selectedRow);
					//selectedPokemon = memberModel.getSelectedIndividual(selectedRow);
					try {
						selectedPokemon = selectedTeam.getPokemons().get(selectedRow);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					nickName.setText(selectedPokemon.nickName);
					nature.setText(selectedPokemon.getValue("nature"));
					level.setText(selectedPokemon.level + "");
					IVHP.setText(selectedPokemon.ivhp + "");
					IVATK.setText(selectedPokemon.ivatk + "");
					IVDEF.setText(selectedPokemon.ivdef + "");
					IVSPD.setText(selectedPokemon.ivspd + "");
					IVSP_ATK.setText(selectedPokemon.ivspatk + "");
					IVSP_DEF.setText(selectedPokemon.ivspdef + "");
					EVHP.setText(selectedPokemon.evhp + "");
					EVATK.setText(selectedPokemon.evatk + "");
					EVDEF.setText(selectedPokemon.evdef + "");
					EVSPD.setText(selectedPokemon.evspd + "");
					EVSP_ATK.setText(selectedPokemon.evspatk + "");
					EVSP_DEF.setText(selectedPokemon.evspdef + "");
					item.setText(selectedPokemon.itemName);
					ability.setText(selectedPokemon.ability);
					move1.setText(selectedPokemon.getValue("move1"));
					move2.setText(selectedPokemon.getValue("move2"));
					move3.setText(selectedPokemon.getValue("move3"));
					move4.setText(selectedPokemon.getValue("move4"));
				}
			}
		});
		return scrollPane;
	}

	private JPanel createEditMemberPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
		addMember = new JButton("Add Pokemon");
		addMember.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (selectedTeam == null) {
						JOptionPane.showMessageDialog(null, "Please Select a Team!");
					} else {
						JFrame bankFrame = new BankFrame(is, selectedTeam, member);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		removeMember = new JButton("Remove Pokemon");
		removeMember.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedPokemon == null) {
					JOptionPane.showMessageDialog(null, "Please Select a Pokemon!");
				} else {
					try {
						selectedTeam.deletePokemon(selectedPokemon);
						memberModel = new BankModel(selectedTeam.getPokemons());
						member.setModel(memberModel);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		toReturn.add(addMember);
		toReturn.add(removeMember);
		return toReturn;
	}

	public void updateTeam() {
		ArrayList<Team> returnData = null;
		try {
			returnData = ts.getTeams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		teamModel = new TeamModel(returnData);
		team.setModel(teamModel);
	}

}
