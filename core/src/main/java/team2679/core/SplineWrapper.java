package team2679.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class SplineGraphAdapter implements Iterable<double[]> {

    private Spline spline;
    private ArrayList<double[]> points;

    public SplineGraphAdapter(Spline spline, double step) {
        this.spline = spline;
        this.points = new ArrayList<>();

        Path path = new Path(spline);

        double distance = 0;
        HashMap<Double, Double> xs = new HashMap<>();
        HashMap<Double, Double> ys = new HashMap<>();
        HashMap<Double, Double> curvatures = new HashMap<>();
        for (int i = 0; i < path.getPoints().size() - 1; i++) {
            xs.put(distance, path.getPoints().get(i).x);
            ys.put(distance, path.getPoints().get(i).y);
            curvatures.put(distance, path.getCurvatures().get(i));
            distance += Util.distance(path.getPoints().get(i), path.getPoints().get(i + 1));
        }

        Graph xd = new Graph(xs);
        Graph yd = new Graph(ys);
        Graph cd = new Graph(curvatures);


        for (double i = xd.getRange().getMinimum(); i < xd.getRange().getMaximum(); i += step) {
            points.add(new double[] {xd.value(i), yd.value(i), cd.value(i)});
        }
    }

    @Override
    public Iterator<double[]> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super double[]> action) {
        
    }

    @Override
    public Spliterator<double[]> spliterator() {
        return null;
    }
}
