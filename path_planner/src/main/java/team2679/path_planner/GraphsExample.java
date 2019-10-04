package team2679.path_planner;

import team2679.core.*;

public class GraphsExample {

    public static void main(String[] args) {

        FileManager fm = new FileManager("default");
        Spline spline = fm.load("BSpline"); // Loads saved points.

        Path path = new Path(spline);
        Velocities vs = new Velocities(path, 15, 0.5);

        Graph[] timeGraphs = vs.getTimeGraphs();
        Graph[] disGraphs = vs.getDisGraphs();

        for (int i = 0; i<100; i++) {
            System.out.println(timeGraphs[0].value(i/100.0));
        }

    }

}
