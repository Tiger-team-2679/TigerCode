package team2679.core;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private Spline spline;
    private List<Point> controlPoints;
    private List<Point> points;

    public Path(Spline spline) {
        this.spline = spline;
        controlPoints = spline.getPoints();
    }

    /**
     * Update the spline.
     * @param spline
     */
    public void setPath(Spline spline) {
        this.spline = spline;
        controlPoints = spline.getPoints();
    }

    /**
     * Returns a specific amount of points from the spline.
     * @param numberOfPoints
     * @return
     */
    public List<Point> getPoints(int numberOfPoints) {
        ArrayList<Point> splinePoints = new ArrayList<>();
        for (double i = 0; i < numberOfPoints; i++) {
            double percent = i / numberOfPoints;
            splinePoints.add(spline.interpolatePoint(percent));
        }
        return splinePoints;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Point> getControlPoints() {
        return controlPoints;
    }

    public Spline getSpline() {
        return spline;
    }

}
