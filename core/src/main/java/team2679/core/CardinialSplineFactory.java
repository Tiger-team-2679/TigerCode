package team2679.core;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class CardinialSplineFactory {
    private double tension;

    public CardinialSplineFactory(double tension) {
        this.tension = tension;
    }

    public PolynomialSpline cardinialSpline(List<Point> points){
        ArrayList<CubicHermiteSplineFactory.Waypoint> waypoints = new ArrayList<>();
        waypoints.ensureCapacity(points.size());
        for (int i = 0; i < points.size() - 2; i++) {
            double xTangent = (1 - tension) * (points.get(i+2).x - points.get(i).x);
            double yTangent = (1 - tension) * (points.get(i+2).y - points.get(i).y);

            Vector2D orientationVector = new Vector2D(xTangent, yTangent);
            waypoints.add(new CubicHermiteSplineFactory.Waypoint(points.get(i+1), orientationVector));
        }

        return CubicHermiteSplineFactory.cubicHermiteSpline(waypoints);
    }
}
