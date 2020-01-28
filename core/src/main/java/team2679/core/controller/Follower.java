package team2679.core.controller;

import team2679.core.graph.Graph;

public class Follower {

    private Graph graph;
    private PID pid;
    private double kv;
    private double ka;

    /**
     * @param graph Graph to follow.
     * @param pid Pid object to apply correction.
     * @param kv Velocity constant (not necessarily velocity, more precisely:
     *           the relation factor between the real values and desired ones).
     * @param ka Acceleration constant (A relation factor for to applied on the slope of the graph).
     */
    public Follower(Graph graph, PID pid, double kv, double ka) {
        this.graph = graph;
        this.pid = pid;
        this.kv = kv;
        this.ka = ka;
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
        return kv * target + change + ka * graph.derivative(currentTime);
    }
}
