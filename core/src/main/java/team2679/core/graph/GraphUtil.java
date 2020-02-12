package team2679.core.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphUtil {
    public static ArrayList<Double> generateTiming(IntervalGraph<Double> distanceGraph) {
        ArrayList<Double> timing = new ArrayList<>(distanceGraph.list.size());
        double time = 0;
        for (double v : distanceGraph) {
            timing.add(time);
            time += distanceGraph.step / v;
        }
        return timing;
    }

    public static Graph applyTiming(IntervalGraph<Double> target, ArrayList<Double> timing) {
        HashMap<Double, Double> points =  new HashMap<Double, Double>(target.list.size());
        for (int i = 0; i < timing.size(); i++) {
            points.put(timing.get(i), target.list.get(i));
        }
        return new Graph(points);
    }
}
