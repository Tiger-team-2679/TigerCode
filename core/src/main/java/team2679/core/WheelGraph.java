package team2679.core;

/**
 * Houses two graphs for both the left and right wheels of the robot.
 */
public class WheelGraph {

    private GraphList wheels;

    /**
     * @param leftWheel The graph to assign for the left wheel.
     * @param rightWheel The graph to assign for the right wheel.
     */
    public WheelGraph(IntervalGraph<Double> leftWheel, IntervalGraph<Double> rightWheel) {
        wheels = new GraphList();
        wheels.set(leftWheel, 0);
        wheels.set(rightWheel, 1);
    }

    public IntervalGraph<Double> getLeft() {
        return wheels.get(0);
    }

    public void setLeft(IntervalGraph<Double> leftWheel) {
        wheels.set(leftWheel, 0);
    }

    public IntervalGraph<Double> getRight() {
        return wheels.get(1);
    }

    public void setRight(IntervalGraph<Double> rightWheel) {
        wheels.set(rightWheel, 1);
    }

}
