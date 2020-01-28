package team2679.core.graph;

/**
 * Notice that, the acronym of this class name is: J.A.L.A.P.E.N.O,
 * which is also the name of the method that is introduced in the algorithm.
 */
public class JerkAccelerationLimitedApexProfileEncoderNumerousObtaining {

    // Members:
    private double acceleration;
    private double jerk;
    private double step;
    MotionProfileGenerator mpg;

    public JerkAccelerationLimitedApexProfileEncoderNumerousObtaining(double acceleration, double jerk, double step) {
        this.acceleration = acceleration;
        this.jerk = jerk;
        this.step = step;
    }

//    public Graph generate(IntervalGraph<Double> graph, DifferentialDriveSC dd) {
//
//        // 1. Apply velocities motion profiling
//        mpg = new MotionProfileGenerator(acceleration, step);
//        IntervalGraph velocityDistanceProfiled = mpg.generate(graph, 0, 0);
//
//        // 2. Create acceleration to distance graph
//        ArrayList<Double> accelerationPoints = new ArrayList<>();
//        for (double i : graph) {
//            accelerationPoints.add(velocityDistanceProfiled.derivative(i));
//        }
//        Graph accelerationDistance = new Graph(accelerationPoints);
//
//        // 3. Apply acceleration motion profiling - jerk
//        mpg = new MotionProfileGenerator(jerk, step);
//        Graph accelerationDistanceProfiled = mpg.generate(accelerationDistance, 0, 0);
//
//        // 4. Create squared velocity to distance graph
//        TreeMap<Double, Double> velocityDistanceTransformed = new TreeMap<>();
//        double sum = 0;
//        for (double i = accelerationDistanceProfiled.getRange().getMinimum(); i < accelerationDistanceProfiled.getRange().getMaximum() - step; i += step) {
//            sum += (accelerationDistanceProfiled.value(i) + accelerationDistanceProfiled.value(i + step)) / 2 * step;
//            velocityDistanceTransformed.put(i, Math.sqrt(sum));
//        }
//        Graph finalVelocityDistance = new Graph(velocityDistanceTransformed);
//
//        // 5. Transform Velocity/Distance graph into Velocity/Time graph
//        TreeMap<Double, Double> velocityTimePoints = new TreeMap<>();
//        for (double i = finalVelocityDistance.getRange().getMinimum(); i < finalVelocityDistance.getRange().getMaximum(); i += step) {
//            double v = finalVelocityDistance.value(i);
//            System.out.println("X: " + i / v + ", Y: " + v);
//            velocityTimePoints.put(i / v, v);
//        }
//        return new Graph(velocityTimePoints);
//    }
}
