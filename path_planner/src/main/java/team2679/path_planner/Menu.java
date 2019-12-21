package team2679.path_planner;

import team2679.core.Spline;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar implements ActionListener {

    // Members:
    MenuItems menuItems;
    JMenu file, edit, statistics, spline, changeSpline;

    // File Items:
    public static JMenuItem loadMap, save, load, bSpline, hermiteSpline, deleteAll, disableSpline;

    public Menu(MenuItems menuItems) {

        // Add listener:
        this.menuItems = menuItems;

        // Set Menus:
        // File menu:
        file = new JMenu("File");
        loadMap = new JMenuItem("Load Map");
        save = new JMenuItem("Save Spline");
        load = new JMenuItem("Load Spline");
        file.add(loadMap);
        file.add(save);
        file.add(load);

        // Edit menu:
        edit = new JMenu("Edit");
        deleteAll = new JMenuItem("Delete All");
        edit.add(deleteAll);

        // Statistics menu:
        statistics = new JMenu("Statistics");

        // Spline menu:
        spline = new JMenu("Spline");
        changeSpline = new JMenu("Change Spline");
        bSpline = new JMenuItem("BSpline");
        hermiteSpline = new JMenuItem("Hermite Spline");
        disableSpline = new JMenuItem("Disable");
        changeSpline.add(bSpline);
        changeSpline.add(hermiteSpline);
        spline.add(disableSpline);
        spline.add(changeSpline);

        // Add listeners:
        loadMap.addActionListener(this);
        save.addActionListener(this);
        load.addActionListener(this);
        bSpline.addActionListener(this);
        hermiteSpline.addActionListener(this);
        deleteAll.addActionListener(this);
        disableSpline.addActionListener(this);

        // Disabling some items:
        hermiteSpline.setEnabled(false);

        // Add Menus to the MenuBar:
        this.add(file);
        this.add(edit);
        this.add(statistics);
        this.add(spline);
    }

    public static void main(String[] args) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        menuItems.onItemClicked(e);
    }
}
