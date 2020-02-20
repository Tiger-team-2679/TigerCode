package team2679.core.graph;

import org.apache.commons.math3.util.Pair;
import team2679.core.graph.GraphList;
import team2679.core.graph.IntervalGraph;

/**
 * Houses two graphs for both the left and right wheels of the robot.
 */
public class WheelGraph {

    private IntervalGraph<Double>[] wheels = new IntervalGraph[2];

    /**
     * @param leftWheel The graph to assign for the left wheel.
     * @param rightWheel The graph to assign for the right wheel.
     */
    public WheelGraph(IntervalGraph<Double> leftWheel, IntervalGraph<Double> rightWheel) {
        wheels[0] = leftWheel;
        wheels[1] = rightWheel;
    }

    public IntervalGraph<Double> getLeft() {
        return wheels[0];
    }

    public void setLeft(IntervalGraph<Double> leftWheel) {
        wheels[0] = leftWheel;
    }

    public IntervalGraph<Double> getRight() {
        return wheels[1];
    }

    public void setRight(IntervalGraph<Double> rightWheel) {
        wheels[1] = rightWheel;
    }

}
