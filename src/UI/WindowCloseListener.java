package UI;


import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import databaseServices.DatabaseConnectionService;

public class WindowCloseListener implements WindowListener {

    private DatabaseConnectionService dbService;

    public WindowCloseListener(DatabaseConnectionService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosing(WindowEvent e) {
        dbService.closeConnection();

    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

}

