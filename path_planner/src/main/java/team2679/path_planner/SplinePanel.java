package team2679.path_planner;

import team2679.core.*;
import team2679.core.spline.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import team2679.core.spline.Point;
import team2679.core.util.MappingProvider;
import team2679.core.graph.JerkAccelerationLimitedApexProfileEncoderNumerousObtaining;
import team2679.core.util.Util;


public class SplinePanel extends JPanel implements MouseListener, MouseMotionListener {

    // Constants
    final String IMAGE_PATH = "fieldImage.png";
    final Color SPLINE_COLOR = Color.green;
    final double WHEEL_DISTANCE = 15;
    final int TOP_PAD = 30;

    // Members:
    Image map = getImage(IMAGE_PATH);
    ExtendedSpline spline;
    String splineType = "BSpline";
    List<Point> points = new ArrayList<>();
    int[] eraser = null;
    Velocities vs = new Velocities(WHEEL_DISTANCE, 0.5);
    boolean enableSpline = true;
    boolean showWheelsSplines = true;
    JerkAccelerationLimitedApexProfileEncoderNumerousObtaining jerk;


     /**
     * Constructor.
     */
    public SplinePanel() {

        map.getWidth(this);
        map.getHeight(this);
        wait(1000);

        jerk = new JerkAccelerationLimitedApexProfileEncoderNumerousObtaining(0.5, 0.5, 0.001);

        // Add listeners:
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    private Rectangle getRect(int[] rect) {
        int x1 = rect[0] < rect[2]?rect[0]:rect[2];
        int y1 = rect[1] < rect[3]?rect[1]:rect[3];
        int x2 = rect[0] > rect[2]?rect[0]:rect[2];
        int y2 = rect[1] > rect[3]?rect[1]:rect[3];
        return new Rectangle(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Initializes the chosen spline with a new set of points.
     *
     * @param points
     */
    public void reinitializeSpline(List<Point> points) {
        if (points.isEmpty()) return;
        switch (splineType) {
            case "BSpline":
                spline = new BSpline(points);
                if (points.size() > 2)
                    vs.update(new Path(spline));
                else
                    vs.deleteAllPoints();
                break;
            case "HermiteSpline":
                try {
                    spline = new HermiteSpline(points);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (points.size() > 5)
                    vs.update(new Path(spline));
                else
                    vs.deleteAllPoints();
                break;
             default:
                 System.out.println("Unresolved splineType! Available: BSpline, HermiteSpline");
        }

    }

    /**
     * Wait for milliSeconds.
     *
     * @param milliSeconds
     */
    private void wait(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an Image object.
     *
     * @param path
     * @return
     */
    public Image getImage(String path) {
        Image tempImage = null;
        try {
            tempImage = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }


    /**
     * Painter.
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(map, 0, 0, this);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke(4));
        for (int i = 1; i < points.size(); i++) {
            g2d.setColor(Color.red);
            g2d.drawLine((int) points.get(i - 1).getX(), (int) points.get(i - 1).getY(), (int) points.get(i).getX(), (int) points.get(i).getY());
        }

        for (int i = 0; i < points.size(); i++) {
            g2d.setColor(Color.black);
            g2d.fillOval((int) points.get(i).x - 10, (int) points.get(i).y - 10, 20, 20);
        }

        if (spline != null && enableSpline) {
            Polygon p = new Polygon();
            g2d.setColor(SPLINE_COLOR);
            for (double i = 0; i <= 1; i += 0.0001) {
                Point point = spline.interpolatePoint(i);
                p.addPoint((int) point.x, (int) point.y);
            }
            g2d.setColor(SPLINE_COLOR);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawPolyline(p.xpoints, p.ypoints, p.npoints);
        }

        if (enableSpline && showWheelsSplines && !("HermiteSpline".equals(splineType)) && points.size() > 1) {
            Polygon right = new Polygon();
            Polygon left = new Polygon();
            for (Point p : vs.rPoints) {
                right.addPoint((int) p.x, (int) p.y);
            }
            for (Point p : vs.lPoints) {
                left.addPoint((int) p.x, (int) p.y);
            }
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g2d.setColor(new Color(100, 100, 100));
            g2d.drawPolyline(right.xpoints, right.ypoints, right.npoints);
            g2d.setColor(new Color(150, 150, 150));
            g2d.drawPolyline(left.xpoints, left.ypoints, left.npoints);
        }

        if (eraser != null) {
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.blue);
            g2d.draw(getRect(eraser));
            g2d.setColor(new Color(0, 0.3f, 1f, 0.5f));
            g2d.fill(getRect(eraser));
        }

        // For a better design:
        if (!points.isEmpty()) {
            g2d.setColor(Color.BLACK);
            g2d.fillOval((int) points.get(0).x - 10, (int) points.get(0).y - 10, 20, 20);
        }
    }

    private void erase() {
        if (points.size() <= 2 || (splineType.equals("HermiteSpline") && points.size() <= 4)) {
            TigerTrack.hermiteSpline.setEnabled(false);
            return;
        }
        for (int i = points.size()-1; i>=0; i--) {
            if (getRect(eraser).intersects(new Rectangle((int)points.get(i).x, (int)points.get(i).y, 1, 1))) {
                points.remove(i);
                break;
            }
        }
    }

    /**
     * @return The chosen spline.
     */
    public Spline getSpline() {
        return spline;
    }

    /**
     * Checks if the mouse is currently over one of the points.
     *
     * @param e
     * @return Index of the currently covered point, -1 if none are covered.
     */
    private int inRange(MouseEvent e) {
        for (Point p : points) {
            if (Util.distance(p, new Point(e.getX(), e.getY())) <= 10) {
                return points.indexOf(p);
            }
        }
        return -1;
    }

    /**
     * Returns a specific amount of equally spaced points from the spline.
     *
     * @param numberOfPoints
     * @return
     */
    private double[][] getSplinePoints(int numberOfPoints) {
        double[][] splinePoints = new double[numberOfPoints][2];
        for (double i = 0; i < numberOfPoints; i++) {
            Point point = spline.interpolatePoint(i / numberOfPoints);
            splinePoints[(int) i][0] = point.x;
            splinePoints[(int) i][1] = point.y;
        }
        return splinePoints;
    }

    /**
     * Get the spline's length. As higher the numberOfPoints is, the length will become more accurate.
     *
     * @param numberOfPoints
     * @return
     */
    public double getSplineLength(int numberOfPoints) {
        double[][] ps = getSplinePoints(numberOfPoints);
        double length = 0;
        for (int i = 0; i < ps.length - 1; i++) {
            length += Util.distance(new Point(ps[i][0], ps[i][1]), new Point(ps[i + 1][0], ps[i + 1][1]));
        }
        return length;
    }

    /**
     * Loads the points from a local txt file.
     *
     * @param path Can be passed with "default" to load the points from a default path (current directory).
     */
    public void load(String path) {
        try {
            points = MappingProvider.pointReader.<Point>readValues(new File(path)).readAll();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading points from " + path,
                    "File error",
                    JOptionPane.ERROR_MESSAGE);

        }
        vs.update(new Path(spline));
        repaint();
    }

    /**
     * Main.
     *
     * @param args
     */
    public static void main(String[] args) {
        new SplinePanel();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    int draggingPoint = -1;

    /**
     * Handles mouse pressed event.
     *
     * @param mouseEvent
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        draggingPoint = inRange(mouseEvent);

        if (SwingUtilities.isRightMouseButton(mouseEvent) && !(splineType.equals("HermiteSpline") && points.size() <= 4)
                && !(points.size() <= 2)) {
            if (draggingPoint != -1) {
                points.remove(draggingPoint);
                reinitializeSpline(points);
            }
            eraser = new int[] {mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getX() + 1, mouseEvent.getY() + 1};
            erase();
            repaint();
        } else if (points.isEmpty()) {
            for (int i : new int[2]) points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            reinitializeSpline(points);
        } else if (draggingPoint == -1) {
            points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            if (points.size() == 3 && points.get(0).x == points.get(1).x && points.get(0).y == points.get(1).y) {
                points.remove(0);
            }
            reinitializeSpline(points);
        }

        if (points.size() >= 4)
            TigerTrack.hermiteSpline.setEnabled(true);
        else
            TigerTrack.hermiteSpline.setEnabled(false);

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        draggingPoint = -1;

        if (eraser != null) {
            eraser = null;
        }

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * Handles mouse dragged event.
     *
     * @param mouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (draggingPoint != -1) {
            points.set(draggingPoint, new Point(mouseEvent.getX(), mouseEvent.getY()));
        }

        if (eraser != null) {
            eraser[2] = mouseEvent.getX();
            eraser[3] = mouseEvent.getY();
            erase();
        }

        reinitializeSpline(points);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
