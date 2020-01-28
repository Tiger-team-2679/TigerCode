package team2679.core.graph;

import team2679.core.spline.Point;

public class OrientedWaypoint {
    public Point point;
    public double angle;

    public OrientedWaypoint(Point point, double angle) {
        this.point = point;
        this.angle = angle;
    }
}
