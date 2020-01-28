package team2679.path_planner;

import team2679.core.Velocities;
import team2679.core.spline.BSpline;
import team2679.core.spline.ExtendedSpline;
import team2679.core.spline.Path;
import team2679.core.spline.Point;

import java.util.ArrayList;

public class Example3 {

    public static void main(String[] args) {

        /**
         * Example - how to use the spline operation on a randomly (manually) chosen control points.
         */

        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i<10; i++){
            points.add(new Point(Math.random()*i, i));
        }

        ExtendedSpline spline = new BSpline(points);

        Velocities vs = new Velocities(new Path(spline), 15, 0.5);

        // Print velocities:
        for (int i : vs.iterate()){
            double[] ves = vs.velocity(i, 50);
            System.out.println("Right speed: " + ves[0]);
            System.out.println("Left speed: " + ves[1] + "\n");
        }

    }

}
