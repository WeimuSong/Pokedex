package UI;

import databaseServices.Individual;
import databaseServices.IndividualService;
import databaseServices.ItemAndNatureService;
import databaseServices.Move;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PokeBankPanel extends JPanel {

	// JTable LikedPokemon = null;
	private JPanel modifyPanel;
	private JPanel modifyPanel1;
	private JPanel modifyPanel2;
	private JPanel modifyPanel3;
	private JPanel modifyPanel4;
	private JPanel modifyPanel5;
	private JTextField nickName = null;
	private JComboBox<String> nature = null;
	private JComboBox<String> item = null;
	private JComboBox<String> ability = null;
	private JComboBox<String> move1 = null;
	private JComboBox<String> move2 = null;
	private JComboBox<String> move3 = null;
	private JComboBox<String> move4 = null;
	private JTextField level = null;
	private JTextField IVATK;
	private JTextField IVDEF;
	private JTextField IVHP;
	private JTextField IVSPD;
	private JTextField IVSP_DEF;
	private JTextField IVSP_ATK;
	private JTextField EVATK;
	private JTextField EVDEF;
	private JTextField EVHP;
	private JTextField EVSPD;
	private JTextField EVSP_DEF;
	private JTextField EVSP_ATK;
	private JButton confirm = null;
	private JButton remove = null;
	private Individual currentIndividual = null;

	private IndividualService is;
	private ItemAndNatureService ins;
	private BankModel bankModel = null;
	private ListSelectionModel selectionModel = null;
    private JTable likedPokemon;

	public PokeBankPanel(IndividualService is, ItemAndNatureService ins) throws Exception {
		this.setLayout(new BorderLayout());
		this.is = is;
		this.ins = ins;
		JScrollPane likedPokemonTable = this.generateLikedPokemonTable();
		this.modifyPanel = generateModifyPanel();
		this.modifyPanel1 = generateModifyFirstRow();
		this.modifyPanel2 = generateModifySecondRow();
		this.modifyPanel3 = generateModifyThirdRow();
		this.modifyPanel4 = generateModifyFourthRow();
		this.modifyPanel5 = generateModifyFifhRow();
		this.modifyPanel.add(modifyPanel1);
		this.modifyPanel.add(modifyPanel2);
		this.modifyPanel.add(modifyPanel3);
		this.modifyPanel.add(modifyPanel4);
		this.modifyPanel.add(modifyPanel5);
		this.add(modifyPanel, BorderLayout.CENTER);
		this.modifyPanel.setVisible(false);
		this.add(likedPokemonTable, BorderLayout.LINE_START);
	}

	private void populateNature() {
		this.nature.addItem(null);
	}

	private void populateItem() {
		this.item.addItem(null);
	}

	private void populateAbility() {
		this.ability.addItem(null);
	}

	private void populateMoves() {
		this.move1.addItem(null);
		this.move2.addItem(null);
		this.move3.addItem(null);
		this.move4.addItem(null);
	}

	private JScrollPane generateLikedPokemonTable() throws Exception {

		likedPokemon = new JTable();
		JScrollPane scrollPane = new JScrollPane(likedPokemon);
		ArrayList<Individual> returnData = is.getAllIndividuals();
		bankModel = new BankModel(returnData);

		likedPokemon.setModel(bankModel);
		likedPokemon.setFillsViewportHeight(true);

		this.selectionModel = likedPokemon.getSelectionModel();
		this.selectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting())
					return;
				if (!selectionModel.isSelectionEmpty()) {
					modifyPanel.setVisible(true);
					int selectedRow = selectionModel.getMinSelectionIndex();
					try {
					    nature.removeAllItems();
					    ability.removeAllItems();
					    item.removeAllItems();
					    move1.removeAllItems();
                        move2.removeAllItems();
                        move3.removeAllItems();
                        move4.removeAllItems();

						currentIndividual = bankModel.getSelectedIndividual(selectedRow);
						nickName.setText((String) bankModel.getValueAt(selectedRow, 0));
                        for (int i = 0; i < ins.getNatures().size(); i++) {
                            nature.addItem(ins.getNatures().get(i));
                        }

						nature.setSelectedItem(bankModel.getValueAt(selectedRow, 7));
						level.setText((String) bankModel.getValueAt(selectedRow, 22));
						IVHP.setText((String) bankModel.getValueAt(selectedRow, 8));
						IVATK.setText((String) bankModel.getValueAt(selectedRow, 9));
						IVDEF.setText((String) bankModel.getValueAt(selectedRow, 10));
						IVSPD.setText((String) bankModel.getValueAt(selectedRow, 11));
						IVSP_ATK.setText((String) bankModel.getValueAt(selectedRow, 12));
						IVSP_DEF.setText((String) bankModel.getValueAt(selectedRow, 13));
						EVHP.setText((String) bankModel.getValueAt(selectedRow, 14));
						EVATK.setText((String) bankModel.getValueAt(selectedRow, 15));
						EVDEF.setText((String) bankModel.getValueAt(selectedRow, 16));
						EVSPD.setText((String) bankModel.getValueAt(selectedRow, 17));
						EVSP_ATK.setText((String) bankModel.getValueAt(selectedRow, 18));
						EVSP_DEF.setText((String) bankModel.getValueAt(selectedRow, 19));

                        for (int i = 0; i < ins.getItems().size(); i++) {
                            item.addItem(ins.getItems().get(i));
                        }
						item.setSelectedItem(bankModel.getValueAt(selectedRow, 20));

						for (int i = 0; i < bankModel.getSelectedIndividual(selectedRow).species.getAbilities()
								.size(); i++) {
							ability.addItem(bankModel.getSelectedIndividual(selectedRow).species.getAbilities().get(i));
						}
						ability.setSelectedItem(bankModel.getValueAt(selectedRow, 21));

						for (int i = 0; i < bankModel.getSelectedIndividual(selectedRow).species.getMoves().size(); i++) {
							move1.addItem(bankModel.getSelectedIndividual(selectedRow).species.getMoves().get(i)
									.getValue("name"));
							move2.addItem(bankModel.getSelectedIndividual(selectedRow).species.getMoves().get(i)
									.getValue("name"));
							move3.addItem(bankModel.getSelectedIndividual(selectedRow).species.getMoves().get(i)
									.getValue("name"));
							move4.addItem(bankModel.getSelectedIndividual(selectedRow).species.getMoves().get(i)
									.getValue("name"));
						}

						    if(bankModel.getSelectedIndividual(selectedRow).getMoves().get(0) != null){
                            move1.setSelectedItem(bankModel.getSelectedIndividual(selectedRow).getMoves().get(0).getValue("name"));}
                            if(bankModel.getSelectedIndividual(selectedRow).getMoves().get(1) != null){
                            move2.setSelectedItem(bankModel.getSelectedIndividual(selectedRow).getMoves().get(1).getValue("name"));}
                            if(bankModel.getSelectedIndividual(selectedRow).getMoves().get(2) != null){
                            move3.setSelectedItem(bankModel.getSelectedIndividual(selectedRow).getMoves().get(2).getValue("name"));}
                            if(bankModel.getSelectedIndividual(selectedRow).getMoves().get(3) != null){
                            move4.setSelectedItem(bankModel.getSelectedIndividual(selectedRow).getMoves().get(3).getValue("name"));}

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		return scrollPane;

	}

	private JPanel generateModifyPanel() {
		JPanel toReturn = new JPanel();
		GridLayout layout = new GridLayout(5, 1);
		toReturn.setLayout(layout);
		layout.setVgap(30);
		toReturn.setPreferredSize(new Dimension(750, 200));
		return toReturn;
	}

	private JPanel generateModifyFirstRow() {
		JPanel toReturn = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(15);
		toReturn.setLayout(layout);
		this.nickName = new JTextField("None");
		this.nature = new JComboBox<>();
		this.level = new JTextField();
		this.nickName.setPreferredSize(new Dimension(100, 30));
		this.level.setPreferredSize(new Dimension(100, 30));
		this.nature.setPreferredSize(new Dimension(100, 30));
		this.populateNature();
		toReturn.add(new JLabel("NickName"));
		toReturn.add(this.nickName);
		toReturn.add(new JLabel("Nature"));
		toReturn.add(this.nature);
		toReturn.add(new JLabel("Level"));
		toReturn.add(this.level);
		return toReturn;
	}

	private JPanel generateModifySecondRow() {
		JPanel toReturn = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(10);
		toReturn.setLayout(layout);
		IVATK = new JTextField("0");
		IVDEF = new JTextField("0");
		IVHP = new JTextField("0");
		IVSPD = new JTextField("0");
		IVSP_DEF = new JTextField("0");
		IVSP_ATK = new JTextField("0");
		IVHP.setPreferredSize(new Dimension(50, 30));
		IVATK.setPreferredSize(new Dimension(50, 30));
		IVDEF.setPreferredSize(new Dimension(50, 30));
		IVSP_ATK.setPreferredSize(new Dimension(50, 30));
		IVSP_DEF.setPreferredSize(new Dimension(50, 30));
		IVSPD.setPreferredSize(new Dimension(50, 30));
		toReturn.add(new JLabel("IVs: "));
		toReturn.add(new JLabel("HP"));
		toReturn.add(IVHP);
		toReturn.add(new JLabel("ATK"));
		toReturn.add(IVATK);
		toReturn.add(new JLabel("DEF"));
		toReturn.add(IVDEF);
		toReturn.add(new JLabel("SPD"));
		toReturn.add(IVSPD);
		toReturn.add(new JLabel("SP.ATK"));
		toReturn.add(IVSP_ATK);
		toReturn.add(new JLabel("SP.DEF"));
		toReturn.add(IVSP_DEF);
		return toReturn;
	}

	private JPanel generateModifyThirdRow() {
		JPanel toReturn = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(10);
		toReturn.setLayout(layout);
		EVATK = new JTextField("0");
		EVDEF = new JTextField("0");
		EVHP = new JTextField("0");
		EVSPD = new JTextField("0");
		EVSP_DEF = new JTextField("0");
		EVSP_ATK = new JTextField("0");
		EVHP.setPreferredSize(new Dimension(50, 30));
		EVATK.setPreferredSize(new Dimension(50, 30));
		EVDEF.setPreferredSize(new Dimension(50, 30));
		EVSP_ATK.setPreferredSize(new Dimension(50, 30));
		EVSP_DEF.setPreferredSize(new Dimension(50, 30));
		EVSPD.setPreferredSize(new Dimension(50, 30));
		toReturn.add(new JLabel("EVs: "));
		toReturn.add(new JLabel("HP"));
		toReturn.add(EVHP);
		toReturn.add(new JLabel("ATK"));
		toReturn.add(EVATK);
		toReturn.add(new JLabel("DEF"));
		toReturn.add(EVDEF);
		toReturn.add(new JLabel("SPD"));
		toReturn.add(EVSPD);
		toReturn.add(new JLabel("SP.ATK"));
		toReturn.add(EVSP_ATK);
		toReturn.add(new JLabel("SP.DEF"));
		toReturn.add(EVSP_DEF);
		return toReturn;
	}

	private JPanel generateModifyFourthRow() {
		JPanel toReturn = new JPanel();
		JPanel movePanel = new JPanel();
		FlowLayout layout = new FlowLayout();
		GridLayout moveLayout = new GridLayout(2, 2);
		moveLayout.setHgap(10);
		layout.setHgap(15);
		toReturn.setLayout(layout);
		movePanel.setLayout(moveLayout);
		this.item = new JComboBox<>();
		this.item.setPreferredSize(new Dimension(100, 30));
		this.populateItem();
		this.ability = new JComboBox<>();
		this.ability.setPreferredSize(new Dimension(100, 30));
		this.populateAbility();
		this.move1 = new JComboBox<>();
		this.move1.setPreferredSize(new Dimension(100, 30));
		this.move2 = new JComboBox<>();
		this.move2.setPreferredSize(new Dimension(100, 30));
		this.move3 = new JComboBox<>();
		this.move3.setPreferredSize(new Dimension(100, 30));
		this.move4 = new JComboBox<>();
		this.move4.setPreferredSize(new Dimension(100, 30));
		this.populateMoves();
		movePanel.add(move1);
		movePanel.add(move2);
		movePanel.add(move3);
		movePanel.add(move4);
		toReturn.add(new JLabel("Item"));
		toReturn.add(this.item);
		toReturn.add(new JLabel("Ability"));
		toReturn.add(this.ability);
		toReturn.add(new JLabel("Moves"));
		toReturn.add(movePanel);

		return toReturn;
	}

	private JPanel generateModifyFifhRow() {
		JPanel toReturn = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(15);
		toReturn.setLayout(layout);
		this.confirm = new JButton("Save");
		this.confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					currentIndividual.modifyIndividual(nickName.getText(), (String) nature.getSelectedItem(),
							level.getText(), IVHP.getText(), IVATK.getText(), IVDEF.getText(), IVSPD.getText(),
							IVSP_ATK.getText(), IVSP_DEF.getText(), EVHP.getText(), EVATK.getText(), EVDEF.getText(),
							EVSPD.getText(), EVSP_ATK.getText(), EVSP_DEF.getText(), (String) item.getSelectedItem(),
							(String) ability.getSelectedItem(), getMoveID((String) move1.getSelectedItem()),
							getMoveID((String) move2.getSelectedItem()), getMoveID((String) move3.getSelectedItem()),
							getMoveID((String) move4.getSelectedItem()));
					updateBank();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		this.remove = new JButton("Remove");
		this.remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    is.deleteIndividual(currentIndividual.getValue("individualID"));

                    updateBank();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });

		toReturn.add(this.confirm);
        toReturn.add(this.remove);
		return toReturn;
	}

	private String getMoveID(String moveName) throws Exception {
		ArrayList<Move> moves = currentIndividual.species.getMoves();
		for (int i = 0; i < moves.size(); i++) {
			if (moves.get(i).getValue("name").equals(moveName)) {
				return moves.get(i).getValue("moveID");
			}
		}
		return null;
	}

	public void updateBank(){
        ArrayList<Individual> returnData = null;
        try {
            returnData = is.getAllIndividuals();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bankModel = new BankModel(returnData);
        likedPokemon.setModel(bankModel);
    }

}
