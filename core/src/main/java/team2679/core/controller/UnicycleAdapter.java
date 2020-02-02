package team2679.core.controller;

public class UnicycleAdapter {
    private double width;

    public UnicycleAdapter(double width) {
        this.width = width;
    }

    public static class WheelsVelocities {
        public WheelsVelocities(double left, double right) {
            this.left = left;
            this.right = right;
        }

        public double left;
        public double right;
    }
    public WheelsVelocities calculateWheelsVelocities(double velocity, double angularVelocity) {
        double right = velocity + angularVelocity * width / 2;
        double left = velocity - angularVelocity * width / 2;

        return new WheelsVelocities(left, right);
    }
}
