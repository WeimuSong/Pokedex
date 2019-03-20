package UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import databaseServices.Species;
import databaseServices.SpeciesService;

public class PokedexPanel extends JPanel {
	private JTable resultTable = null;
	private JTextField pokemonField = null;
	private JTextField typeField = null;
	private JTextField abilityField = null;
	private JTextField moveField = null;
	private JButton searchButton = null;
	private SpeciesService sService;
	private PokedexTableModel pokedexTableModel = null;
	private ListSelectionModel selectionModel = null;
	private String username = null;

	public PokedexPanel(SpeciesService sService, String username) {
		this.sService = sService;
		this.username = username;
		this.setLayout(new BorderLayout());
		JPanel searchPanel = generateSearchPanel();
		JScrollPane tablePane = generateResultTable();
		this.add(searchPanel, BorderLayout.NORTH);

		this.add(tablePane, BorderLayout.CENTER);
	}

	private JScrollPane generateResultTable() {
		this.resultTable = new JTable();
		this.selectionModel = this.resultTable.getSelectionModel();
		this.selectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting())
					return;
				if (!selectionModel.isSelectionEmpty()) {
					int selectedRow = selectionModel.getMinSelectionIndex();
					try {
						JFrame MoveFrame = new MoveFrame(sService.getDB(),
								(String) pokedexTableModel.getValueAt(selectedRow, 0), username);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(this.resultTable);
		this.resultTable.setFillsViewportHeight(true);
		return scrollPane;
	}

	private JPanel generateSearchPanel() {

		JPanel searchPanel = new JPanel();

		FlowLayout layout = new FlowLayout();
		layout.setHgap(15);

		this.pokemonField = new JTextField();
		this.typeField = new JTextField();
		this.abilityField = new JTextField();
		this.moveField = new JTextField();

		searchPanel.setLayout(layout);
		this.pokemonField.setPreferredSize(new Dimension(200, 30));
		this.typeField.setPreferredSize(new Dimension(200, 30));
		this.abilityField.setPreferredSize(new Dimension(200, 30));
		this.moveField.setPreferredSize(new Dimension(200, 30));

		searchPanel.add(new JLabel("Pokemon"));
		searchPanel.add(pokemonField);
		searchPanel.add(new JLabel("Type"));
		searchPanel.add(typeField);
		searchPanel.add(new JLabel("Ability"));
		searchPanel.add(abilityField);
		searchPanel.add(new JLabel("Move"));
		searchPanel.add(moveField);

		this.searchButton = new JButton("Search");
		searchPanel.add(searchButton);

		this.searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PokedexPanel.this.resultTable.setModel(PokedexPanel.this.search());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		return searchPanel;
	}

	private PokedexTableModel search() throws SQLException {
		String name = null;
		String type = null;
		String ability = null;
		String move = null;
		if (!pokemonField.getText().isEmpty()) {
			name = this.pokemonField.getText();
			// System.out.println(name);
		}
		if (!typeField.getText().isEmpty()) {
			type = this.typeField.getText();
		}
		if (!abilityField.getText().isEmpty()) {
			ability = this.abilityField.getText();
		}
		if (!moveField.getText().isEmpty()) {
			move = this.moveField.getText();
		}
		Map<Integer, Species> data = this.sService.getSpecies(name, ability, move, type);
		ArrayList<Species> returnData = new ArrayList<>(data.values());

		pokedexTableModel = new PokedexTableModel(returnData);
		return pokedexTableModel;
	}

}
