package team2679.core;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private ExtendedSpline spline;
    private List<Point> points;
    private List<Double> curvatures;

    public Path(ExtendedSpline spline) {
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

    public List<Double> getCurvatures(int numberOfPoints) {
        curvatures = new ArrayList<>();
        for (double i = 0; i < numberOfPoints; i++) {
            double percent = i / numberOfPoints;
            curvatures.add(spline.getCurvature(percent));
        }
        return curvatures;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        if (points == null || points.isEmpty()){
            return getPoints(1000);
        }
        return points;
    }

    public List<Double> getCurvatures() {
        if (curvatures == null || curvatures.isEmpty()) {
            return getCurvatures(1000);
        }
        return curvatures;
    }

    public Spline getSpline() {
        return spline;
    }

}
