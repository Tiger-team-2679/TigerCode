package team2679.path_planner;

import team2679.core.*;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphsExample {

    public static void main(String[] args) {

        FileManager fm = new FileManager("default");
        Spline spline = fm.load("BSpline"); // Loads saved points.

        Path path = new Path(spline);
        Velocities vs = new Velocities(path, 15, 0.5);
        vs.setMaxVelocities(100);

        Graph[] timeGraphs = vs.getTimeGraphs();
        Graph[] disGraphs = vs.getDisGraphs();

        int pointsW = 100;
        int duration = (int)(vs.getDuration());
        int length = duration * pointsW;
        double[][] velocities = new double[length][2];
        int index = 0;

        for (double i = 0; i<duration-1.0/pointsW; i+=1.0/pointsW) {
            velocities[index] = new double[] {disGraphs[0].value(i), disGraphs[1].value(i)};
            index++;
        }

        fm.saveCSV(velocities, "time_velocities.csv");

    }

}
