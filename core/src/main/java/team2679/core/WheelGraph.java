package team2679.core;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;

/**
 * Houses two graphs for both the left and right wheels of the robot.
 */
public class WheelGraph {

    private GraphList wheels;

    /**
     * @param leftWheel The graph to assign for the left wheel.
     * @param rightWheel The graph to assign for the right wheel.
     */
    public WheelGraph(IntervalGraph leftWheel, IntervalGraph rightWheel) {
        wheels = new GraphList();
        wheels.set(leftWheel, 0);
        wheels.set(rightWheel, 1);
    }

    public IntervalGraph getLeft() {
        return wheels.get(0);
    }

    public void setLeft(IntervalGraph leftWheel) {
        wheels.set(leftWheel, 0);
    }

    public IntervalGraph getRight() {
        return wheels.get(1);
    }

    public void setRight(IntervalGraph rightWheel) {
        wheels.set(rightWheel, 1);
    }

}
