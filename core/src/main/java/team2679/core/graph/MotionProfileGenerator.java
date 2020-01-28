package team2679.core.graph;

import team2679.core.graph.Graph;
import team2679.core.graph.IntervalGraph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Takes a graph that was speed capped, and limits it based on max acceleration and deceleration.
 */
public class MotionProfileGenerator {

    private double acceleration;

    /**
     * @param acceleration The fastest the robot can accelerate and decelerate.
     */
    public MotionProfileGenerator(double acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * @param speedCap A graph of the average speed of the robot against distance limited to the robot's max speed.
     * @param startVelocity The speed of the robot at the start of the path.
     * @param endVelocity The speed of the robot at the end of the path.
     * @return A graph that was changed based on the original to have realistic acceleration and deceleration.
     */
    public IntervalGraph generate(IntervalGraph<Double> speedCap, double startVelocity, double endVelocity) {
        ArrayList<Double> points = new ArrayList<>();
        double lastVelocity = startVelocity;
        for (double a : speedCap) {
            double currentVelocity = Math.sqrt(lastVelocity * lastVelocity + 2 * acceleration * speedCap.step);
            currentVelocity = Math.min(currentVelocity, a);
            points.add(currentVelocity);
            lastVelocity = currentVelocity;
        }

        IntervalGraph<Double> accelerate = new IntervalGraph(points, speedCap.step);
        points = new ArrayList<>();
        lastVelocity = endVelocity;
        for (int i = speedCap.list.size() - 1; i >= 0; i--) {
            double currentVelocity = Math.sqrt(lastVelocity * lastVelocity + 2 * acceleration * speedCap.step);
            currentVelocity = Math.min(currentVelocity, accelerate.list.get(i));
            points.add(currentVelocity);
            lastVelocity = currentVelocity;
        }
        return new IntervalGraph(points, speedCap.step);
    }

    public static Graph convertToTimeGraph(IntervalGraph<Double> distanceGraph) {
        HashMap<Double, Double> points = new HashMap<>();
        double time = 0;
        for (double v : distanceGraph) {
            points.put(time, v);
            time += distanceGraph.step / v;
        }
        return new Graph(points);
    }

}