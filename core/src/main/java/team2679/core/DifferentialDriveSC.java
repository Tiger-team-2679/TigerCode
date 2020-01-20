package team2679.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates a graph of the average speed between the wheels for the robot against distance considering the robot's max speed.
 */
public class DifferentialDriveSC {

    double width;
    double maxVelocity;
    int dataPoints;

    /**
     * @param width The space between the wheels of the robot.
     * @param maxVelocity The fastest the robot can travel.
     * @param dataPoints The amount of samples the graph will have on the x-axis.
     */
    public DifferentialDriveSC(double width, double maxVelocity, int dataPoints) {
        this.width = width;
        this.maxVelocity = maxVelocity;
        this.dataPoints = dataPoints;
    }

    /**
     * @param spline A spline of the robot's path.
     * @return A graph of the average speed between the wheels for the robot against distance considering the robot's max speed.
     */
    public Graph getSpeedCap(ExtendedSpline spline) {
        Map points = new HashMap();
        double length = 0;
        Point currentPoint = spline.interpolatePoint(0);
        Point lastPoint;
        for (double percent = 0; percent <= 1; percent += 1.0 / dataPoints) {
            lastPoint = currentPoint;
            currentPoint = spline.interpolatePoint(percent);
            length += Util.distance(lastPoint, currentPoint);
            double radius = 1 / spline.getCurvature(percent);
            double averageSpeed = ((maxVelocity * (radius - (width / 2)) / (radius + (width / 2))) + maxVelocity) / 2;
            points.put(length, averageSpeed);
        }
        return new Graph(points);
    }

}