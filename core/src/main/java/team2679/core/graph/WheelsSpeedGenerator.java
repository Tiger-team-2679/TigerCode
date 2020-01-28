package team2679.core.graph;

import team2679.core.graph.IntervalGraph;
import team2679.core.graph.WheelGraph;

import java.util.ArrayList;

public class WheelsSpeedGenerator {

    public WheelGraph generate(IntervalGraph<Double> average, WheelGraph relative) {
        ArrayList<Double> left = new ArrayList<>();
        for (int i = 0; i < average.list.size(); i++) {
            double y = average.list.get(i) * relative.getLeft().list.get(i);
            left.add(y);
        }
        ArrayList<Double> right = new ArrayList<>();
        for (int i = 0; i < average.list.size(); i++) {
            double y = average.list.get(i) * relative.getRight().list.get(i);
            right.add(y);
        }
        return new WheelGraph(new IntervalGraph<>(left, relative.getLeft().step), new IntervalGraph<>(right, relative.getRight().step));
    }

}
