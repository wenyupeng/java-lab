package com.energyrating;

import com.energyrating.ui.MainFrame;
import com.energyrating.util.DatabaseManager;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        DatabaseManager.initialiseDatabase();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
