package team2679.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Takes a graph that was speed capped, and limits it based on max acceleration and deceleration.
 */
public class MotionProfileGenerator {

    private double acceleration;
    private double step;

    /**
     * @param acceleration The fastest the robot can accelerate and decelerate.
     * @param step The amount of space traveled between samples to make the graph. (The lower the number, the more accurate it will be)
     */
    public MotionProfileGenerator(double acceleration, double step) {
        this.acceleration = acceleration;
        this.step = step;
    }

    /**
     * @param speedCap A graph of the average speed of the robot against distance limited to the robot's max speed.
     * @param startVelocity The speed of the robot at the start of the path.
     * @param endVelocity The speed of the robot at the end of the path.
     * @return A graph that was changed based on the original to have realistic acceleration and deceleration.
     */
    public Graph generate(Graph speedCap, double startVelocity, double endVelocity) {
        Map points = new HashMap();
        double lastVelocity = startVelocity;
        for (double distance = 0; distance < speedCap.getRange().getMaximum(); distance += step) {
            double currentVelocity = Math.sqrt(lastVelocity * lastVelocity + 2 * acceleration * distance);
            currentVelocity = Math.min(currentVelocity, speedCap.value(distance));
            points.put(distance, currentVelocity);
            lastVelocity = currentVelocity;
        }
        Graph accelerate = new Graph(points);
        points = new HashMap();
        lastVelocity = endVelocity;
        for (double distance = accelerate.getRange().getMaximum(); distance > 0; distance -= step) {
            double currentVelocity = Math.sqrt(lastVelocity * lastVelocity + 2 * acceleration * distance);
            currentVelocity = Math.min(currentVelocity, accelerate.value(distance));
            points.put(distance, currentVelocity);
            lastVelocity = currentVelocity;
        }
        return new Graph(points);
    }

}