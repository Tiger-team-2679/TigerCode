package team2679.core;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates two graphs of the two wheels relative to the robot's average speed against distance.
 */
public class RelativeSpeedGenerator {
    double width;
    int dataPoints;

    /**
     * @param width The space between the wheels of the robot.
     * @param dataPoints The amount of samples the graph will have on the x-axis.
     */
    public RelativeSpeedGenerator(double width, int dataPoints) {
        this.width = width;
        this.dataPoints = dataPoints;
    }

    /**
     * @param spline A spline of the robot's path.
     * @return A WheelGraph object with two graphs:
     * I Left wheel speed relative to the robot's average speed against distance.
     * II Right wheel speed relative to the robot's average speed against distance.
     */
    public WheelGraph getRelativeSpeed(SplineWrapper spline) {
        ArrayList<Double> left = new ArrayList();
        ArrayList<Double> right = new ArrayList();
        IntervalGraph<WayPoint> points = spline.getIntervalGraph();
        for (int i = 0; i < points.list.size(); i++) {
            right.add((1 / points.list.get(i).curvature + width / 2) / (1 / points.list.get(i).curvature));
        }
        for (int i = 0; i < points.list.size(); i++) {
            left.add((1 / points.list.get(i).curvature - width / 2) / (1 / points.list.get(i).curvature));
        }
        return new WheelGraph(new IntervalGraph(left, points.step), new IntervalGraph(right, points.step));
    }

}
