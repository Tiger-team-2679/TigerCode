package team2679.core.controller;

import team2679.core.controller.Pos;

/**
 * Tracks position relative to a set starting position using angle and displacement.
 */
public class PositionTracker {

    private Pos pos;

    /**
     * @param pos Starting position.
     */
    public PositionTracker(Pos pos) {
        this.pos = pos;
    }

    /**
     * @param angle Current angle of the robot.
     * @param displacement Distance from when last updated.
     */
    public void update(double angle, double displacement) {
        pos.setX(Math.sin(angle) * displacement + pos.getX());
        pos.setY(Math.cos(angle) * displacement + pos.getY());
        pos.setAngle(angle);
    }

    public Pos getPos() {
        return pos;
    }
}
