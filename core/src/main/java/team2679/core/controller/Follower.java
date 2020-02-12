package team2679.core.controller;

import team2679.core.graph.Graph;

public class Follower {

    private Graph graph;
    private PID pid;
    private double kv;
    private double ka;
    private double ks;

    /**
     * @param graph Graph to follow.
     * @param pid Pid object to apply correction.
     * @param kv Velocity constant (not necessarily velocity, more precisely:
     *           the relation factor between the real values and desired ones).
     * @param ka Acceleration constant (A relation factor for to applied on the slope of the graph).
     * @param ks Static constant for minimal possible value for the system to start move.
     */
    public Follower(Graph graph, PID pid, double kv, double ka, double ks) {
        this.graph = graph;
        this.pid = pid;
        this.kv = kv;
        this.ka = ka;
        this.ks = ks;
    }

    /**
     * Update the current value and get the PID correction.
     * @param currentTime The current time.
     * @param currentValue The current value.
     * @return The value for the motor.
     */
    public double update(double currentTime, double currentValue) {
        double target = graph.value(currentTime);
        double change = pid.update(currentTime, currentValue, target);
        double value = kv * target + change + ka * graph.derivative(currentTime);
        if (value != 0) {
            return value + ks;
        }else {
            return 0;
        }
    }
}
