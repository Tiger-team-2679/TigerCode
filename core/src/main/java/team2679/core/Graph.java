package team2679.core;

import org.apache.commons.lang3.Range;

import java.util.Map;
import java.util.TreeMap;

public class Graph {

    TreeMap<Double, Double> dataPoints;
    public Graph(Map<Double, Double> dataPoints) {
        this.dataPoints = new TreeMap<>(dataPoints);
    }

    /**
     * Get a graph value (Y value) by a given X value.
     * @param x X value of the graph. Should be in the range of the Graph.
     * @return Graph value for the given X value.
     */
    public double value(double x) {
        Map.Entry<Double, Double> floorPoint= dataPoints.floorEntry(x);
        Map.Entry<Double, Double> ceilingPoint = dataPoints.higherEntry(x);
        // Interpolation:

        // Slope = ( y2 - y1 ) / ( x2 - x1 )
        double m = (floorPoint.getValue() - ceilingPoint.getValue())
                /  (floorPoint.getKey() - ceilingPoint.getKey());

        // Intercept = y1 - mx1
        double b = floorPoint.getValue() - m * floorPoint.getKey();

        // Result = mx + bFraction
        return m * x + b;
    }

    /**
     * Get the derivative of the graph in a given point.
     * @param x - The value of the graph.
     * @return The slope of the graph at the given point.
     */
    public double derivative(double x) {
        Map.Entry<Double, Double> floorPoint= dataPoints.floorEntry(x);
        Map.Entry<Double, Double> higherPoint = dataPoints.higherEntry(x);

        // Slope = ( y2 - y1 ) / ( x2 - x1 )
        return (floorPoint.getValue() - higherPoint.getValue())
                /  (floorPoint.getKey() - higherPoint.getKey());
    }

    /**
     * Get the x values range. Including the first point as well as the last point.
     * @return Double array in the form: [firstPointX, lastPointX]
     */
    public Range<Double> getRange() {
        return Range.between(dataPoints.firstKey(), dataPoints.lastKey());
    }

    /**
     * Get an iterable object (double array) with all graph values (Y values)
     * in the graph range, and by a specific step.
     * @param step A step to iterate through the X values of the graph. The smaller the step, the longer the iterator.
     * @return
     */
    public double[] iterate(double step) {
        Range<Double> range = getRange();
        double len = range.getMaximum() - range.getMinimum();
        double[] iterator = new double[(int)(Math.abs(len) / step)];

        double value = range.getMinimum();

        for (int i = 0; i < iterator.length; i++) {
            iterator[i] = value(value);
            value += step;
        }

        return iterator;
    }
}
