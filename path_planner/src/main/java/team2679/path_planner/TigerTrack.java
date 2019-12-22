package team2679.path_planner;

import team2679.core.MappingProvider;
import team2679.core.WindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class TigerTrack extends JFrame implements MenuItems {

    FilesNavigation fileNav;
    SplinePanel sp;

    public TigerTrack() {
        super("TigerTrack");
        sp = new SplinePanel();
        fileNav = new FilesNavigation(sp);
        Menu menu = new Menu(this);

        add(sp);
        setSize(sp.map.getWidth(sp), sp.map.getHeight(sp) + menu.getHeight() + sp.TOP_PAD*2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setJMenuBar(menu);

        sp.repaint();
    }

    public static void main(String[] args) {
        new TigerTrack();
    }

    @Override
    public void onItemClicked(ActionEvent e) {
        if (e.getSource() == Menu.loadMap) {
            File file = fileNav.openFileChooserDialog();
            if (file != null) {
                sp.map = Toolkit.getDefaultToolkit().getImage(file.getPath());
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        setSize(sp.map.getWidth(sp), sp.map.getHeight(sp) + sp.TOP_PAD*2);
                    }
                }.start();
            }
        } else if (e.getSource() == Menu.save) {
            File file = fileNav.openFolderChooserDialog();
            if (file != null)
                System.out.println(file.getPath());
        } else if (e.getSource() == Menu.load) {
            File file = fileNav.openFileChooserDialog();
            if (file != null)
                System.out.println(file.getPath());
        } else if (e.getSource() == Menu.bSpline) {
            sp.splineType = "BSpline";
            sp.reinitializeSpline(sp.points);
        } else if (e.getSource() == Menu.hermiteSpline) {
            sp.splineType = "HermiteSpline";
            sp.reinitializeSpline(sp.points);
        } else if (e.getSource() == Menu.deleteAll) {
            sp.splineType = "BSpline";
            sp.spline = null;
            sp.points = new LinkedList<>();
            Menu.hermiteSpline.setEnabled(false);
        } else if (e.getSource() == Menu.disableSpline) {
            sp.enableSpline = !sp.enableSpline;
            Menu.disableSpline.setText(sp.enableSpline ? "Disable" : "Enable");
        } else if (e.getSource() == Menu.showWheelsSplines) {
            sp.showWheelsSplines = !sp.showWheelsSplines;
            Menu.showWheelsSplines.setText(sp.showWheelsSplines ? "Hide Wheels' Splines" : "Show Wheels' Splines");
        }

        sp.repaint();
    }
}
