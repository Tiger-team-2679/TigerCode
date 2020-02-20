package team2679.path_planner;

import team2679.core.Velocities;
import team2679.core.spline.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Example3 {

    public static void main(String[] args) {


        OrientedWaypoint p1 = new OrientedWaypoint(new Point(0, 0), 0);
        OrientedWaypoint p2 = new OrientedWaypoint(new Point(0, 1), 0);
        OrientedWaypoint p3 = new OrientedWaypoint(new Point(0, 2), 0);

        ExtendedSpline spline = CubicHermiteSplineFactory.cubicHermiteSplineOriented(Arrays.asList(p1, p2, p3));
        spline.getCurvature(0.5);
    }

}
