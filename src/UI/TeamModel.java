package UI;

import databaseServices.Team;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TeamModel extends AbstractTableModel {

    private ArrayList<Team> data;
    private String[] columnNames = {"Team"};

    public TeamModel(ArrayList<Team> data){
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
        Team tData = data.get(rowIndex);
        switch (columnIndex){
            case 0:
                return tData.getTeamname();
        }
        return null;
    }

    @Override
    public String getColumnName(int index) {
        return this.columnNames[index];
    }

    public Team getSelectedTeam(int rowIndex){
        Team currentTeam = data.get(rowIndex);
        return currentTeam;
    }
}
