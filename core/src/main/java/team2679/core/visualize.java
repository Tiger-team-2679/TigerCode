package team2679.core;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.Arrays;

public class visualize {

    public static void main(String[] args) {
        FileManager fm = new FileManager("default");
        Spline spline = fm.load("BSpline"); // Loads saved points.
        VelocitiesAdapter vs = new VelocitiesAdapter(spline, 15, 0.5);

//        vs.saveCSV(vs.getMaxVelocities(100), "Ves", "ves.csv");
        vs.setMaxVelocities(100);

        System.out.println(vs.getDuration());

        int duration = (int)vs.getDuration();
        int length = duration * 100;
        double[][] velocities = new double[length][2];
        System.out.println("Duration: " + duration + ", length: " + length);
        int index = 0;

        for (double i = 0; i<duration-0.01; i+=0.01) {
            velocities[index] = vs.velocityForTime(i);
            index++;
        }

        vs.saveCSV(vs.velocities, "velocities.csv");
        vs.saveCSV(vs.getMaxVelocities(100), "v", "mves.csv");
        vs.saveCSV(velocities, "ves.csv");

    }

}
