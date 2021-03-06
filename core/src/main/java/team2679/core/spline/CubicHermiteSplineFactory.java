package team2679.core.spline;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import team2679.core.util.Util;

import java.util.ArrayList;
import java.util.List;

public class CubicHermiteSplineFactory {

    public static class Waypoint{
        public Point point;
        public Vector2D tangentVector;

        public Waypoint(Point point, Vector2D tangentVector) {
            this.point = point;
            this.tangentVector = tangentVector;
        }
    }

    private static final PolynomialFunction h00 = new PolynomialFunction(new double[]{1, 0, -3, 2});
    private static final PolynomialFunction h10 = new PolynomialFunction(new double[]{0, 1, -2, 1});
    private static final PolynomialFunction h01 = new PolynomialFunction(new double[]{0, 0, 3, -2});
    private static final PolynomialFunction h11 = new PolynomialFunction(new double[]{0, 0, -1, 1});

    public static PolynomialSpline cubicHermiteSpline(List<CubicHermiteSplineFactory.Waypoint> waypoints) {
        if (waypoints.size() == 0) {
            throw new IllegalArgumentException("Waypoints list is empty");
        }
        ArrayList<PolynomialSpline.Segment> segments = new ArrayList<>();
        segments.ensureCapacity(waypoints.size());
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Waypoint startPoint = waypoints.get(i);
            Waypoint endPoint = waypoints.get(i + 1);

            PolynomialFunction xFunction = GenerateSegment(startPoint.point.x,
                    startPoint.tangentVector.getX(),
                    endPoint.point.x,
                    endPoint.tangentVector.getX());
            PolynomialFunction yFunction = GenerateSegment(startPoint.point.y,
                    startPoint.tangentVector.getY(),
                    endPoint.point.y,
                    endPoint.tangentVector.getY());

            segments.add(new PolynomialSpline.Segment(xFunction, yFunction));
        }
        return new PolynomialSpline(segments);
    }

    public static PolynomialSpline cubicHermiteSplineOriented(List<OrientedWaypoint> waypoints) {
        ArrayList<Waypoint> finalWaypoints = new ArrayList<>(waypoints.size());

        for (OrientedWaypoint waypoint : waypoints) {
            finalWaypoints.add(new Waypoint(waypoint.point, new Vector2D(Math.sin(waypoint.angle), Math.cos(waypoint.angle))));
        }
        return cubicHermiteSpline(finalWaypoints);
    }

    private static PolynomialFunction GenerateSegment(double p0, double m0, double p1, double m1){
        return Util.multiply(h00, p0).add(
                Util.multiply(h10, m0)
        ).add(
                Util.multiply(h01, p1)
        ).add(
                Util.multiply(h11, m1)
        );
    }
}
