package team2679.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class FileManager {

    private String path;

    public FileManager(){}

    /**
     *
     * @param path Can be passed with "default" to load the points from a default path (current directory).
     */
    public FileManager(String path){
        this.path = validatePath(path);
    }

    /**
     *
     * @param path Can be passed with "default" to load the points from a default path (current directory).
     */
    public void setPath(String path){
        this.path = validatePath(path);
    }

    /**
     * Saves the points in a local txt file.
     * @param points
     */
    public void save(ArrayList<Point> points){
        try {
            Formatter formatter = new Formatter(path);
            for (Point p : points) {
                formatter.format("%s", p.x + "\r\n");
                formatter.format("%s", p.y + "\r\n");
            }
            formatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the points from a local txt file.
     * @param splineType The spline type to load the points into.
     */
    public Spline load(String splineType){
        File file = new File(path);
        ArrayList<Point> points = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                points.add(new Point(Double.parseDouble(sc.next()), Double.parseDouble(sc.next())));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        switch (splineType) {
            case "BSpline":
                return new BSpline(points);
            case "HermiteSpline":
                try {
                    return new HermiteSpline(points);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Unresolved splineType! Available: BSpline, HermiteSpline");
        }
        return null;
    }

    /**
     * Saves an array in a .csv format.
     * @param ves
     * @param path
     */
    public void saveCSV(double[][] ves, String path){
        try {
            Formatter formatter = new Formatter(path + (path.endsWith(".csv")?"":".csv"));
            formatter.format("%s", "Right, Left\r\n");
            for (int i = 0; i<ves.length; i++) {
                formatter.format("%s", ves[i][0] + ", " + ves[i][1] + "\r\n");
            }
            formatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves an array in a .csv format.
     * @param ves
     * @param label
     * @param path
     */
    public void saveCSV(double[] ves, String label, String path){
        try {
            Formatter formatter = new Formatter(path + (path.endsWith(".csv")?"":".csv"));
            formatter.format("%s", label + "\r\n");
            for (int i = 0; i<ves.length; i++) {
                if(i % 4 == 0)
                    formatter.format("%s", ves[i] + "\r\n");
            }
            formatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path
     * @return
     */
    private String validatePath(String path){
        URL url = getClass().getResource("points.txt");
        return path.equals("default")?url.getPath():path + (path.endsWith(".txt")?"":".txt");
    }

}
