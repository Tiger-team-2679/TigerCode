package team2679.core;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private Spline spline;
    private List<Point> points;

    public Path(Spline spline) {
        this.spline = spline;
    }

    /**
     * Returns a specific amount of points from the spline.
     * @param numberOfPoints
     * @return
     */
    public List<Point> getPoints(int numberOfPoints) {
        points = new ArrayList<>();
        for (double i = 0; i < numberOfPoints; i++) {
            double percent = i / numberOfPoints;
            points.add(spline.interpolatePoint(percent));
        }
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        if (points.isEmpty()){
            return getPoints(1000);
        }
        return points;
    }

    public Spline getSpline() {
        return spline;
    }

}
