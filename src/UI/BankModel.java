package UI;

import databaseServices.Individual;
import databaseServices.Species;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BankModel extends AbstractTableModel {

    private ArrayList<Individual> data = null;
    private String[] columnNames = {"Pokemon", "HP", "ATK", "DEF", "SPD", "SP_ATK", "SP_DEF"};

    public BankModel(ArrayList<Individual> data) {
        this.data = data;
    }
    public BankModel(){
    }

    @Override
    public int getRowCount() {
        if(data == null){
            return 0;
        }
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(data == null){
            return null;
        }
        Individual iData = data.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return iData.getValue("Pokemon");
            case 1:
                return iData.getValue("HP");
            case 2:
                return iData.getValue("ATK");
            case 3:
                return iData.getValue("DEF");
            case 4:
                return iData.getValue("SPD");
            case 5:
                return iData.getValue("SP_ATK");
            case 6:
                return iData.getValue("SP_DEF");
            case 7:
                return iData.getValue("nature");
            case 8:
                return iData.getValue("ivhp");
            case 9:
                return iData.getValue("ivatk");
            case 10:
                return iData.getValue("ivdef");
            case 11:
                return iData.getValue("ivspd");
            case 12:
                return iData.getValue("ivspatk");
            case 13:
                return iData.getValue("ivspdef");
            case 14:
                return iData.getValue("evhp");
            case 15:
                return iData.getValue("evatk");
            case 16:
                return iData.getValue("evdef");
            case 17:
                return iData.getValue("evspd");
            case 18:
                return iData.getValue("evspatk");
            case 19:
                return iData.getValue("evspdef");
            case 20:
                return iData.getValue("item");
            case 21:
                return iData.getValue("AbilityName");
            case 22:
                return iData.getValue("level");
            case 23:
                return iData.getValue("individualID");
        }
        return null;
    }

    public Individual getSelectedIndividual(int rowIndex) {
        Individual currentIndividual = data.get(rowIndex);
        return currentIndividual;
    }




    @Override
    public String getColumnName(int index) {
        return this.columnNames[index];
    }
}
