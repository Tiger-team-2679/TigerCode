package team2679.core;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

    ArrayList<Point> points = new ArrayList<>();

    public Graph(ArrayList<Point> points) {
        this.points = points;
    }

    public double value(double x) {
        for (int i = 0; i<points.size()-1; i++) {
            if (x >= points.get(i).x && x < points.get(i+1).x) {
                return points.get(i).y;
            }
        }
        System.out.println("Something went wrong!");
        return 0;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }
}
