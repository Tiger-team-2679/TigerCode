package team2679.path_planner;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import team2679.core.Graph;

import javax.swing.*;

public class Util {


    public static void displayGraph(Graph graph, String title, String xTitle, String yTitle, String seriesName, double step) {
        double[] range = graph.getRange();
        double[] xChart = new double[(int)((range[1] - range[0]) / step)];
        double[] yChart = new double[xChart.length];
        double x = range[0];
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

}
