package team2679.core.graph;

import team2679.core.spline.WayPoint;
import team2679.core.spline.SplineWrapper;

import java.util.ArrayList;

/**
 * Creates two graphs of the two wheels relative to the robot's average speed against distance.
 */
public class RelativeSpeedGenerator {
    double width;

    /**
     * @param width The space between the wheels of the robot.
     */
    public RelativeSpeedGenerator(double width) {
        this.width = width;
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
