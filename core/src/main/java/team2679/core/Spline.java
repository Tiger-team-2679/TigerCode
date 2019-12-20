package team2679.core;

import java.util.List;

public interface Spline {

    public Point interpolatePoint(double percent);

    public double getLength();

    List<Point> getPoints();

}
