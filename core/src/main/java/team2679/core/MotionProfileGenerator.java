package team2679.core;

import java.util.HashMap;
import java.util.Map;

public class MotionProfileGenerator {

    private double acceleration;
    private double step;
    private double lastVelocity;

    public MotionProfileGenerator(double acceleration, double step) {
        this.acceleration = acceleration;
        this.step = step;
    }

    public Graph generate(Graph speedCap, double startVelocity, double endVelocity) {
        Map points = new HashMap();
        lastVelocity = startVelocity;
        for (double distance = 0; distance < speedCap.getRange().getMaximum(); distance += step) {
            double currentVelocity = Math.sqrt(lastVelocity * lastVelocity + 2 * acceleration * distance);
            currentVelocity = Math.min(currentVelocity, speedCap.value(distance));
            points.put(distance, currentVelocity);
            lastVelocity = currentVelocity;
        }
        Graph accelerate = new Graph(points);
        points = new HashMap();
        for (double distance = accelerate.getRange().getMaximum(); distance > 0; distance -= step) {
            double currentVelocity = Math.sqrt(lastVelocity * lastVelocity + 2 * acceleration * distance);
            currentVelocity = Math.min(currentVelocity, accelerate.value(distance));
            points.put(distance, currentVelocity);
            lastVelocity = currentVelocity;
        }
        return new Graph(points);
    }

}