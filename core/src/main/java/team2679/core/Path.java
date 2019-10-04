package team2679.core;

import java.util.ArrayList;

public class Path {

    private Spline spline;
    private ArrayList<Point> controlPoints;
    private ArrayList<Point> points;

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
    public ArrayList<Point> getPoints(int numberOfPoints) {
        ArrayList<Point> splinePoints = new ArrayList<>();
        for (double i = 0; i < numberOfPoints; i++) {
            double percent = i / numberOfPoints;
            splinePoints.add(new Point(spline.interpolate_X(percent), spline.interpolate_Y(percent)));
        }
        return splinePoints;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Point> getControlPoints() {
        return controlPoints;
    }

    public Spline getSpline() {
        return spline;
    }



}
