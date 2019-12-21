package team2679.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Velocities {

    private Path path;
    public ArrayList<Point> rPoints = new ArrayList<>();
    public ArrayList<Point> lPoints = new ArrayList<>();
    private double[][] velocities;
    private double lengthOp;
    private double wheelDistance;

    public Velocities(Path path, double wheelDistance, double lengthOp) {
        this.path = path;
        this.wheelDistance = wheelDistance;
        this.lengthOp = lengthOp;
        wheelsSplines(wheelDistance, (int)(path.getSpline().getLength() * lengthOp));
    }

    public Velocities(double wheelDistance, double lengthOp) {
        this.wheelDistance = wheelDistance;
        this.lengthOp = lengthOp;
    }

    public Velocities() {}

    public void update(Path path){
        this.path = path;
        wheelsSplines(wheelDistance, (int)(path.getSpline().getLength() * lengthOp));
    }

    /**
     * Calculates the splines for each motor, for a given distance between the wheel and the center of the robot.
     * @param distance
     * @param numberOfPoints
     */
    private  void wheelsSplines(double distance, int numberOfPoints){
        List<Point> splinePoints = path.getPoints(numberOfPoints);
        path.setPoints(splinePoints);
        velocities = new double[splinePoints.size() - 1][2];
        rPoints = new ArrayList<>();
        lPoints = new ArrayList<>();
        for (int i = 0; i<splinePoints.size()-1; i++){
            double m = (splinePoints.get(i+1).y - splinePoints.get(i).y) / (splinePoints.get(i+1).x - splinePoints.get(i).x); // (y2 - y1) / (x2 - x1)
            m = -1.0 / m;
            double delta = distance * (Math.sqrt(1.0 / (1 + Math.pow(m, 2))));
            double rx = splinePoints.get(i).x + delta;
            double ry = splinePoints.get(i).y + m * delta;
            double lx = splinePoints.get(i).x - delta;
            double ly = splinePoints.get(i).y - m * delta;
            if (i != 0 && !rightSide(rx, ry, distance)){
                lPoints.add(new Point(rx, ry));
                rPoints.add(new Point(lx, ly));
            }
            else{
                rPoints.add(new Point(rx, ry));
                lPoints.add(new Point(lx, ly));
            }
        }
    }

    /**
     * Checks if the calculated point is on the right side of the spline.
     * @param rx
     * @param ry
     * @param distance
     * @return
     */
    private boolean rightSide(double rx, double ry, double distance){
        return Util.distance(new Point(rx, ry), rPoints.get(rPoints.size()-1)) < distance;
    }

    /**
     * Get desired velocities for left and right motors in a given index.
     * @param index
     * @param basicSpeed The desired robot speed for the specific point.
     * @return
     */
    public double[] velocity(int index, double basicSpeed){
        double dis = Util.distance(new Point(path.getPoints().get(index).x, path.getPoints().get(index).y),
                new Point(path.getPoints().get(index+1).x, path.getPoints().get(index+1).y));
        double time = dis / basicSpeed;
        double rightV = Util.distance(rPoints.get(index), rPoints.get(index+1)) / time;
        double leftV = Util.distance(lPoints.get(index), lPoints.get(index+1)) / time;
        velocities[index][0] = rightV;
        velocities[index][1] = leftV;
        return new double[]{rightV, leftV}; // 0 = right, 1 = left
    }

    /**
     * Set motors velocities by passing an array of basic speeds for the robot.
     * This method calls velocity() iteratively.
     * @param basicSpeeds
     */
    public void setVelocities(double[] basicSpeeds) {
        for (int i = 0; i<basicSpeeds.length; i++) {
            velocity(i, basicSpeeds[i]);
        }
    }

    /**
     * Set motors velocities by passing the max possible velocity for the motor.
     * This method calculates the maximum velocity of the robot at each point on the path given the max motor velocity,
     * and then passes result to setVelocities() to find velocities for each of the motors.
     * Final velocities will be saved in 'velocities[][]'.
     * @param motorMaxVelocity
     */
    public void setMaxVelocities(double motorMaxVelocity) {
        setVelocities(getMaxVelocities(motorMaxVelocity));
    }

    /**
     * Returns the time duration for driving along the path, using velocities that should have already been set.
     * @return
     */
    public double getDuration() {
        double sum = 0;
        for (int i = 0; i < velocities.length-1; i++) {
            sum += Util.distance(rPoints.get(i), rPoints.get(i + 1)) / velocities[i][0];
        }
        return sum;
    }

    /**
     * Get the maximum robot velocity at each point depending on the motor maximum possible velocity.
     * @param motorMaxVelocity
     * @return
     */
    public double[] getMaxVelocities(double motorMaxVelocity){
        double[] maxVelocities = new double[iterate().length];
        for (int i = 0; i<maxVelocities.length; i++){
            double[] ds = {Util.distance(rPoints.get(i), rPoints.get(i+1)),
                    Util.distance(lPoints.get(i), lPoints.get(i+1))};
            int longI = ds[0] > ds[1]?0:1;
            double t = ds[longI] / motorMaxVelocity;
            double v2 = ds[1 - longI] / t;
            maxVelocities[i] = (motorMaxVelocity + v2) / 2;
        }
        return maxVelocities;
    }

    /**
     * Returns an iterable object for iterating through the velocities.
     * @return
     */
    public int[] iterate(){
        int[] iterator = new int[rPoints.size()-1];
        for (int i = 0; i < iterator.length; i++) {
            iterator[i] = i;
        }
        return iterator;
    }

    /**
     * Get two time Graphs of time vs. velocity.
     * @return index 0 = right, index 1 = left.
     */
    public Graph[] getTimeGraphs() {
        double time = 0;
        Map<Double, Double> rtPoints = new HashMap<>(velocities.length);
        Map<Double, Double> ltPoints = new HashMap<>(velocities.length);
        for (int i = 0; i<velocities.length; i++) {
            rtPoints.put(time, velocities[i][0]);
            ltPoints.put(time, velocities[i][1]);
            time += Util.distance(rPoints.get(i), rPoints.get(i+1)) / velocities[i][0];
        }
        return new Graph[] {new Graph(rtPoints), new Graph(ltPoints)};
    }

    /**
     * Get two distance Graphs of distance vs. velocity.
     * @return index 0 = right, index 1 = left.
     */
    public Graph[] getDisGraphs() {
        double dis = 0;
        Map<Double, Double> rtPoints = new HashMap<>(velocities.length);
        Map<Double, Double> ltPoints = new HashMap<>(velocities.length);

        for (int i = 0; i<velocities.length; i++) {
            rtPoints.put(dis, velocities[i][0]);
            ltPoints.put(dis, velocities[i][1]);
            dis += Util.distance(path.getPoints().get(i), path.getPoints().get(i+1));
        }
        return new Graph[] {new Graph(rtPoints), new Graph(ltPoints)};
    }

    public Graph getTimeGraph() {
        double time = 0;
        Map<Double, Double> points = new HashMap<>(velocities.length);
        for (int i = 0; i<velocities.length; i++) {
            points.put(time, (velocities[i][0] + velocities[i][1])/2);
            time += Util.distance(rPoints.get(i), rPoints.get(i+1)) / velocities[i][0];
        }
        return new Graph(points);
    }

    public Graph getDisGraph() {
        double dis = 0;
        Map<Double, Double> points = new HashMap<>(velocities.length);
        for (int i = 0; i<velocities.length; i++) {
            points.put(dis, (velocities[i][0] + velocities[i][1])/2);
            dis += Util.distance(path.getPoints().get(i), path.getPoints().get(i+1));
        }
        return new Graph(points);
    }
}
