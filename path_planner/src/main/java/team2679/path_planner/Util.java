package team2679.path_planner;

import org.apache.commons.lang3.Range;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import team2679.core.graph.Graph;
import team2679.core.graph.IntervalGraph;

import javax.swing.*;

public class Util {


    public static void displayGraph(Graph graph, String title, String xTitle, String yTitle, String seriesName, double step) {
        Range<Double> range = graph.getRange();
        double[] xChart = new double[(int)((range.getMaximum() - range.getMinimum()) / step)];
        double[] yChart = new double[xChart.length];
        double x = range.getMinimum();
        for (int i = 0; i < xChart.length; i++) {
            xChart[i] = x;
            yChart[i] = graph.value(x);
            x += step;
        }
        XYChart chart = QuickChart.getChart(title, xTitle, yTitle, seriesName, xChart, yChart);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        }).start();
    }

    public static void displayGraph(IntervalGraph<Double> graph, String title, String xTitle, String yTitle, String seriesName) {
        double[] xChart = new double[graph.list.size()];
        double[] yChart = new double[xChart.length];
        double distance = 0;
        for (int i = 0; i < xChart.length; i++) {
            xChart[i] = distance;
            yChart[i] = graph.list.get(i);
            distance += graph.step;
        }
        XYChart chart = QuickChart.getChart(title, xTitle, yTitle, seriesName, xChart, yChart);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new SwingWrapper(chart).displayChart().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        }).start();
    }

}
