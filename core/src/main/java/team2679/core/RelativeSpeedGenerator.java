package team2679.core;

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
    public WheelGraph getRelativeSpeed(ExtendedSpline spline) {
        Map left = new HashMap();
        Map right = new HashMap();
        for (double percent = 0; percent <= 1; percent += 1.0 / dataPoints) {
            right.put(percent, (1 / spline.getCurvature(percent) + width / 2) / (1 / spline.getCurvature(percent)));
        }
        for (double percent = 0; percent <= 1; percent += 1.0 / dataPoints) {
            left.put(percent, (1 / spline.getCurvature(percent) - width / 2) / (1 / spline.getCurvature(percent)));
        }
            return new WheelGraph(new Graph(left), new Graph(right));
    }

}
