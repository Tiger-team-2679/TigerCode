package team2679.core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;

public class VelocitiesAdapter {

    public ArrayList<Point> rPoints = new ArrayList<>();
    public ArrayList<Point> lPoints = new ArrayList<>();
    protected double[][] points;
    protected double[][] velocities;
    protected Spline spline;
    private double lengthOp;
    private double wheelDistance;


    /**
     * Constructor.
     * @param spline
     * @param wheelDistance
     * @param lengthOp The number to applied over the length.
     */
    public VelocitiesAdapter(Spline spline, double wheelDistance, double lengthOp){
        this.spline = spline;
        this.wheelDistance = wheelDistance;
        this.lengthOp = lengthOp;
        wheelsSplines(wheelDistance, (int)(spline.getLength() * lengthOp));
    }

    /**
     * Constructor.
     * @param wheelDistance
     * @param lengthOp
     */
    public VelocitiesAdapter(double wheelDistance, double lengthOp){
        this.wheelDistance = wheelDistance;
        this.lengthOp = lengthOp;
    }

    /**
     * Update the spline.
     * @param spline
     */
    public void update(Spline spline){
        this.spline = spline;
        wheelsSplines(wheelDistance, (int)(spline.getLength() * lengthOp));
    }

    /**
     * Returns a specific amount of points from the spline.
     * @param numberOfPoints
     * @return
     */
    private double[][] getSplinePoints(int numberOfPoints) {
        double[][] splinePoints = new double[numberOfPoints][2];
        for (double i = 0; i < numberOfPoints; i++) {
            splinePoints[(int) i][0] = spline.interpolate_X(i / numberOfPoints);
            splinePoints[(int) i][1] = spline.interpolate_Y(i / numberOfPoints);
        }
        return splinePoints;
    }

    /**
     * Calculates the splines for each motor, for a given distance between the wheel and the center of the robot.
     * @param distance
     * @param numberOfPoints
     */
    private  void wheelsSplines(double distance, int numberOfPoints){
        double[][] splinePoints = getSplinePoints(numberOfPoints);
        points = splinePoints;
        velocities = new double[points.length - 1][2];
        rPoints = new ArrayList<>();
        lPoints = new ArrayList<>();
        for (int i = 0; i<splinePoints.length-1; i++){
            double m = (splinePoints[i+1][1] - splinePoints[i][1]) / (splinePoints[i+1][0] - splinePoints[i][0]); // (y2 - y1) / (x2 - x1)
            m = -1.0 / m;
            double delta = distance * (Math.sqrt(1.0 / (1 + Math.pow(m, 2))));
            double rx = splinePoints[i][0] + delta;
            double ry = splinePoints[i][1] + m * delta;
            double lx = splinePoints[i][0] - delta;
            double ly = splinePoints[i][1] - m * delta;
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
     * Get each motor's desired velocity for every point on the spline.
     * @param wheelDistance Distance from the wheel to the center of the robot.
     * @param timeForStep Time for each step.
     * @return
     */
    private double[][] getGeneralVelocities(double wheelDistance, double timeForStep){
        double[][] velocities = new double[rPoints.size()-1][2]; // 0 = right, 1 = left
        for (int i = 0; i<velocities.length; i++){
            velocities[i][0] = Util.distance(rPoints.get(i), rPoints.get(i+1)) / timeForStep;
            velocities[i][1] = Util.distance(lPoints.get(i), lPoints.get(i+1)) / timeForStep;
        }
        return velocities;
    }

    /**
     * Get desired velocities for left and right motors in a given index.
     * @param index
     * @param basicSpeed The desired robot speed for the specific point.
     * @return
     */
    public double[] velocity(int index, double basicSpeed){
        double dis = Util.distance(new Point(points[index][0], points[index][1]),
                new Point(points[index+1][0], points[index+1][1]));
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
     * Get velocities for a given point in time.
     * @param t
     * @return
     */
    public double[] velocityForTime(double t) {
        double time = 0;
        double lastTime = 0;
        for (int i = 0; i<rPoints.size()-1; i++) {
            lastTime = time;
            time += Util.distance(rPoints.get(i), rPoints.get(i+1)) / velocities[i][0];
            if (t >= lastTime && t < time) {
                double rm = (rPoints.get(i+1).y - rPoints.get(i).y) / (rPoints.get(i+1).x - rPoints.get(i).x);
                double rb = rPoints.get(i).y - rm * rPoints.get(i).x;
                double lm = (lPoints.get(i+1).y - lPoints.get(i).y) / (lPoints.get(i+1).x - lPoints.get(i).x);
                double lb = lPoints.get(i).y - lm * lPoints.get(i).x;
                double rx = (t - lastTime) * velocities[i][0] + rPoints.get(i).x;
                double lx = (t - lastTime) * velocities[i][1] + lPoints.get(i).x;
                double rv = rm * rx + rb;
                double lv = lm * lx + lb;
                if (rv == 0.0) {
                    System.out.println("rm: " + rm + ", rb: " + rb + ", time: " + time);
                }
                return new double[] {rv, lv};
            }
        }
        System.out.println("A problem has occurred");
        return null;
    }

    public double[] getVelocities(double t) {
        double time = 0;
        double lastTime = 0;
        for (int i = 0; i<rPoints.size()-1; i++) {
            lastTime = time;
            time += Util.distance(rPoints.get(i), rPoints.get(i+1)) / velocities[i][0];
            if (t >= lastTime && t < time)
                return new double[] {velocities[i][0], velocities[i][1]};
        }
        System.out.println("A problem has occurred");
        return null;
    }

    /**
     * @return Number of points in the spline (depends on the lambda parameter in the constructor.
     */
    public int getNumPoints() {
        return rPoints.size();
    }

    /**
     * Save velocities in a local .csv file.
     * @param ves
     * @param path
     */
    public void saveCSV(double[][] ves, String path){
        try {
            Formatter formatter = new Formatter(path + (path.endsWith(".csv")?"":".csv"));
            formatter.format("%s", "Right, Left\r\n");
            for (int i = 0; i<ves.length; i++) {
                formatter.format("%s", ves[i][0] + ", " + ves[i][1] + "\r\n");
            }
            formatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveCSV(double[] ves, String label, String path){
        try {
            Formatter formatter = new Formatter(path + (path.endsWith(".csv")?"":".csv"));
            formatter.format("%s", label + "\r\n");
            for (int i = 0; i<ves.length; i++) {
                if(i % 4 == 0)
                    formatter.format("%s", ves[i] + "\r\n");
            }
            formatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the maximum robot velocity at each point depending on the motor maximum possible velocity.
     * @param motorMaxVelocity
     * @return
     */
    public double[] getMaxVelocities(double motorMaxVelocity){
        double[] maxVelocities = new double[getNumPoints()-1];
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
        int[] iterator = new int[getNumPoints()-1];
        for (int i = 0; i < iterator.length; i++) {
            iterator[i] = i;
        }
        return iterator;
    }
}
