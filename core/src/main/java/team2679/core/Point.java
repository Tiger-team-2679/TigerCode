package team2679.core;

import java.beans.ConstructorProperties;

public class Point {
    public final double x, y;

    @ConstructorProperties({"x", "y"})
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getX(){return x;}

    public double getY(){return y;}
}