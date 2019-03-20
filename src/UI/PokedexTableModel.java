package UI;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import databaseServices.Species;

public class PokedexTableModel extends AbstractTableModel{

    private ArrayList<Species> data;
    private String[] columnNames = {"PokeID", "Name", "HP", "ATK",
            "DEF", "SPD", "SP.DEF", "SP.ATK","Type1","Type2", "AbilityName"};

    public PokedexTableModel(ArrayList<Species> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //return this.data.get(rowIndex).getValue(this.columnNames[columnIndex]);
        Species sData = data.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return sData.getValue("PokeID");
            case 1:
                return sData.getValue("Name");
            case 2:
                return sData.getValue("HP");
            case 3:
                return sData.getValue("ATK");
            case 4:
                return sData.getValue("DEF");
            case 5:
                return sData.getValue("SPD");
            case 6:
                return sData.getValue("SP.DEF");
            case 7:
                return sData.getValue("SP.ATK");
            case 8:
                return sData.getValue("Type1");
            case 9:
                return sData.getValue("Type2");
            case 10:
                return sData.getValue("AbilityName");
        }
        return null;

    }

    @Override
    public String getColumnName(int index) {
        return this.columnNames[index];
    }


}
