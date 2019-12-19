package team2679.core;

import java.util.List;

public interface Spline {

    public double interpolate_X(double percent);

    public double interpolate_Y(double percent);

    public double getLength();

    List<Point> getPoints();

}
