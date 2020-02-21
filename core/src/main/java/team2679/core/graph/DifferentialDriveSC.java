package team2679.core.graph;

import team2679.core.spline.SplineWrapper;
import team2679.core.spline.WayPoint;

import java.util.ArrayList;

/**
 * Creates a graph of the average speed between the wheels for the robot against distance considering the robot's max speed.
 */
public class DifferentialDriveSC {

    double width;
    double maxVelocity;

    /**
     * @param width The space between the wheels of the robot.
     * @param maxVelocity The fastest the robot can travel.
     */
    public DifferentialDriveSC(double width, double maxVelocity) {
        this.width = width;
        this.maxVelocity = maxVelocity;
    }

    /**
     * @param spline A spline of the robot's path.
     * @return A graph of the average speed between the wheels for the robot against distance considering the robot's max speed.
     */
    public IntervalGraph getSpeedCap(SplineWrapper spline) {
        IntervalGraph<WayPoint> points = spline.getIntervalGraph();
        ArrayList<Double> velocityPoints = new ArrayList<>();
        for (int i = 0; i < points.list.size(); i++) {
            double curvature = points.list.get(i).curvature;
            double averageSpeed;

            if (curvature == 0) {
                averageSpeed = maxVelocity;
            } else {
                double radius = Math.abs(1 / curvature);
                averageSpeed = ((maxVelocity * ((radius - (width / 2)) / (radius + (width / 2)))) + maxVelocity) / 2;
            }
            
            velocityPoints.add(averageSpeed);
        }
        return new IntervalGraph(velocityPoints, points.step);
    }

}