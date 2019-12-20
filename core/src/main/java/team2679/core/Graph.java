package team2679.core;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

    ArrayList<Point> points = new ArrayList<>();

    public Graph(ArrayList<Point> points) {
        this.points = points;
    }

    /**
     * Get a graph value (Y value) by a given X value.
     * @param x X value of the graph. Should be in the range of the Graph.
     * @return Graph value for the given X value.
     */
    public double value(double x) {
        for (int i = 0; i<points.size()-1; i++) {
            if (x >= points.get(i).x && x < points.get(i+1).x) {
                // Interpolation:

                // Slope = ( y2 - y1 ) / ( x2 - x1 )
                double m = (points.get(i + 1).y - points.get(i).y) / (points.get(i + 1).x - points.get(i).x);

                // Intercept = y1 - mx1
                double b = points.get(i).y - m * points.get(i).x;

                // Result = mx + b
                return m * x + b;
            }
        }
        System.out.println("Something went wrong!");
        return 0;
    }

    /**
     * Get the x values range. Including the first point as well as the last point.
     * @return Double array in the form: [firstPointX, lastPointX]
     */
    public double[] getRange() {
        return new double[]{points.get(0).x, points.get(points.size() - 1).x};
    }

    /**
     * Get an iterable object (double array) with all graph values (Y values)
     * in the graph range, and by a specific step.
     * @param step A step to iterate through the X values of the graph. The smaller the step, the longer the iterator.
     * @return
     */
    public double[] iterate(double step) {
        double[] range = getRange();
        double len = range[1] - range[0];
        double[] iterator = new double[(int)(Math.abs(len) / step)];

        double value = range[0];

        for (int i = 0; i < iterator.length; i++) {
            iterator[i] = value(value);
            value += step;
        }

        return iterator;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }
}
