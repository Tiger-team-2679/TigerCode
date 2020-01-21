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
    public WheelGraph(Graph leftWheel, Graph rightWheel) {
        wheels = new GraphList();
        wheels.set(leftWheel, 0);
        wheels.set(rightWheel, 1);
    }

    public Graph getLeft() {
        return wheels.get(0);
    }

    public void setLeft(Graph leftWheel) {
        wheels.set(leftWheel, 0);
    }

    public Graph getRight() {
        return wheels.get(1);
    }

    public void setRight(Graph rightWheel) {
        wheels.set(rightWheel, 1);
    }

}
