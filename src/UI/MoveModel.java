package UI;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MoveModel extends AbstractTableModel {

	private ArrayList<String> moves;
	private String[] columnNames = { "Name", "Description", "Power", "Type", "PP", "Accuracy" };

	public MoveModel(ArrayList<String> moves) {
		this.moves = moves;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return this.moves.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return this.moves.get(row).split("\0")[column];
	}

	@Override
	public String getColumnName(int index) {
		return columnNames[index];
	}

}
