package team2679.path_planner;

import team2679.core.spline.Point;
import team2679.core.graph.DifferentialDriveSC;
import team2679.core.graph.IntervalGraph;
import team2679.core.graph.MotionProfileGenerator;
import team2679.core.spline.ExtendedSplineAdapter;
import team2679.core.spline.SplineWrapper;
import team2679.core.util.MappingProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class TigerTrack extends JFrame implements ActionListener {


    // Members:
    JMenu file, edit, statistics, spline, changeSpline;
    FilesNavigation fileNav;
    SplinePanel sp;

    // File Items:
    public static JMenuItem loadMap, save, load, bSpline, hermiteSpline, deleteAll, disableSpline, showWheelsSplines
            , timeVelocityGraph, distanceVelocityGraph, finalVelocityTimeGraph;


    public TigerTrack() {
        super("TigerTrack");
        sp = new SplinePanel();
        fileNav = new FilesNavigation(sp);

        JMenuBar menu = new JMenuBar();

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
        showWheelsSplines = new JMenuItem("Hide Wheels' Splines");
        edit.add(deleteAll);
        edit.add(showWheelsSplines);

        // Statistics menu:
        statistics = new JMenu("Statistics");
        timeVelocityGraph = new JMenuItem("Robot Velocity Time Graph");
        distanceVelocityGraph = new JMenuItem("Robot Velocity Distance Graph");
        finalVelocityTimeGraph = new JMenuItem("Robot Velocity Time Graph (After jerk and acceleration configuration)");
        statistics.add(timeVelocityGraph);
        statistics.add(distanceVelocityGraph);
        statistics.add(finalVelocityTimeGraph);

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
        showWheelsSplines.addActionListener(this);
        timeVelocityGraph.addActionListener(this);
        distanceVelocityGraph.addActionListener(this);
        finalVelocityTimeGraph.addActionListener(this);

        // Disabling some items:
        hermiteSpline.setEnabled(false);

        // Add Menus to the MenuBar:
        menu.add(file);
        menu.add(edit);
        menu.add(statistics);
        menu.add(spline);

        setJMenuBar(menu);

        add(sp);
        setSize(sp.map.getWidth(sp), sp.map.getHeight(sp) + menu.getHeight() + sp.TOP_PAD*2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);

        sp.repaint();
    }

    public static void main(String[] args) {
        new TigerTrack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadMap) {
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
        } else if (e.getSource() == save) {
            File file = fileNav.openFileChooserDialog();

            try {
                MappingProvider.pointWriter.writeValues(file).writeAll(sp.points);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error saving points to " + file.getPath(),
                        "File error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == load) {
            File file = fileNav.openFileChooserDialog();;

            try {
                sp.points = MappingProvider.pointReader.<Point>readValues(file).readAll();
                sp.reinitializeSpline(sp.points);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error loading points from " + file.getPath(),
                        "File error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == bSpline) {
            sp.splineType = "BSpline";
            sp.reinitializeSpline(sp.points);
        } else if (e.getSource() == hermiteSpline) {
            sp.splineType = "HermiteSpline";
            sp.reinitializeSpline(sp.points);
        } else if (e.getSource() == deleteAll) {
            sp.splineType = "BSpline";
            sp.spline = null;
            sp.points = new LinkedList<>();
            hermiteSpline.setEnabled(false);
        } else if (e.getSource() == disableSpline) {
            sp.enableSpline = !sp.enableSpline;
            disableSpline.setText(sp.enableSpline ? "Disable" : "Enable");
        } else if (e.getSource() == showWheelsSplines) {
            sp.showWheelsSplines = !sp.showWheelsSplines;
            showWheelsSplines.setText(sp.showWheelsSplines ? "Hide Wheels' Splines" : "Show Wheels' Splines");
        }
        else if (e.getSource() == timeVelocityGraph) {
            sp.vs.setMaxVelocities(100);
            Util.displayGraph(sp.vs.getTimeGraph(), "Velocity Time Graph (Max V: 100)", "Time", "Velocity", "V(t)", 0.001);
        }
        else if (e.getSource() == distanceVelocityGraph) {
            DifferentialDriveSC dd = new DifferentialDriveSC(15, 100);
            IntervalGraph<Double> g = dd.getSpeedCap(new SplineWrapper(new ExtendedSplineAdapter(sp.spline), 0.001));
            Util.displayGraph(g, "Velocity Distance Graph (Max V: 100)", "Distance", "Velocity", "V(x)");
        } else if (e.getSource() == finalVelocityTimeGraph) {
            sp.vs.setMaxVelocities(100);
            DifferentialDriveSC dd = new DifferentialDriveSC(15, 100);
            IntervalGraph g = dd.getSpeedCap(new SplineWrapper(new ExtendedSplineAdapter(sp.spline), 0.001));
            MotionProfileGenerator mpg = new MotionProfileGenerator(100);
            Util.displayGraph(mpg.generate(g, 0, 0),
                    "Velocity Distance Graph (After motion profiling)", "Distance", "Velocity", "V(x)");
        }
        sp.repaint();
    }
}
