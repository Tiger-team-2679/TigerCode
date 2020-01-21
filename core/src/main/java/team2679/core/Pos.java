package team2679.core;

/**
 * Represents position.
 */
public class Pos {

    private double x;
    private double y;
    private double angle;

    /**
     * @param x The X value of the position.
     * @param y The Y value of the position.
     * @param angle The angle of the object.
     */
    public Pos(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}