package UI;

import databaseServices.Individual;
import databaseServices.IndividualService;
import databaseServices.Team;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BankFrame extends JFrame {
    private JPanel pokePanel;
    private JButton addPokemon;
    private JTable likedPokemon;

    private IndividualService is;
    private BankModel bankModel = null;
    private Team selectedTeam  =  null;
    private Individual selectedIndividual = null;
    private ListSelectionModel selectionModel = null;
    private JTable member;
    private BankModel bm;

    public BankFrame(IndividualService is, Team selectedTeam, JTable member) throws Exception {
        super();
        this.member = member;
        this.selectedTeam = selectedTeam;
        this.setSize(1000, 750);
        this.setTitle("PokeBank");
        this.pokePanel = new JPanel();
        this.addPokemon = new JButton("Add to Team");
        this.addPokemon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(selectedIndividual == null){
                        JOptionPane.showMessageDialog(null, "Please Select a Pokemon!");
                    }else{
                    selectedTeam.addPokemon(selectedIndividual);
                    System.out.println(selectedTeam.getPokemons().size());
                    bm = new BankModel(selectedTeam.getPokemons());
                    member.setModel(bm);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.is = is;

        this.pokePanel.setSize(1000, 750);
        this.pokePanel.setLayout(new BorderLayout());
        JScrollPane tablePanel = getPokemonTable();
        this.pokePanel.add(addPokemon, BorderLayout.NORTH);
        this.pokePanel.add(tablePanel, BorderLayout.CENTER);
        this.add(this.pokePanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
    }

    private JScrollPane getPokemonTable() throws Exception{
        likedPokemon = new JTable();
        JScrollPane scrollPane = new JScrollPane(likedPokemon);
        ArrayList<Individual> returnData = is.getAllIndividuals();
        bankModel = new BankModel(returnData);
        likedPokemon.setModel(bankModel);
        this.selectionModel = likedPokemon.getSelectionModel();
        this.selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting())
                    return;
                if (!selectionModel.isSelectionEmpty()) {
                    int selectedRow = selectionModel.getMinSelectionIndex();
                    selectedIndividual = bankModel.getSelectedIndividual(selectedRow);
                }
            }
        });

        return scrollPane;
    }


}
