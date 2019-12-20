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
    public static JMenuItem save, bSpline, hermiteSpline;

    public Menu(MenuItems menuItems) {

        // Add listener:
        this.menuItems = menuItems;

        // Set Menus:
        // File menu:
        file = new JMenu("File");
        save = new JMenuItem("Save");
        file.add(save);

        // Edit menu:
        edit = new JMenu("Edit");

        // Statistics menu:
        statistics = new JMenu("Statistics");

        // Spline menu:
        spline = new JMenu("Spline");
        changeSpline = new JMenu("Change Spline");
        bSpline = new JMenuItem("BSpline");
        hermiteSpline = new JMenuItem("Hermite Spline");
        changeSpline.add(bSpline);
        changeSpline.add(hermiteSpline);
        spline.add(changeSpline);

        // Add listeners:
        save.addActionListener(this);
        bSpline.addActionListener(this);
        hermiteSpline.addActionListener(this);

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