package team2679.core;

final public class Util {

    public static int x = 3;

    /**
     * Calculates the distance in pxs between two given points.
     *
     * @param point1
     * @param point2
     * @return
     */
    public static double distance(Point point1, Point point2) {
        double deltaX = point2.x - point1.x;
        double deltaY = point2.y - point1.y;
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

}
