package team2679.core;

import javafx.scene.PointLight;
import org.apache.commons.lang3.Range;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import static java.lang.Math.pow;

final public class Util {
    /**
     * Calculates the distance in pxs between two given points.
     *
     * @param point1
     * @param point2
     * @return
     */
    public static double distance(Point point1, Point point2) {
        double deltaX = point2.x - point1.x;
        double deltaY = point2.y - point1.y;
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    /**
     * Helper function for multiplying a PolynomialFunction by double.
     * @param function the function to multiply.
     * @param d a double to multiply by.
     * @return a new polynomial of function times d
     */
    public static PolynomialFunction multiply(PolynomialFunction function, double d){
        return function.multiply(new PolynomialFunction(new double[]{d}));
    }

    public static double rangeMapping(Range<Double> original, Range<Double> target, double value) {
        return (value - original.getMinimum())
                *(target.getMaximum() - target.getMinimum())
                /(original.getMaximum() - original.getMinimum())
                + target.getMinimum();
    }

    public static double calculateCurvature(double xDerivative, double yDerivative, double xSecondDerivative, double ySecondDerivative) {
        return (xDerivative * ySecondDerivative - yDerivative * xSecondDerivative)
                / pow(xDerivative * xDerivative + yDerivative * yDerivative, 1.5);
    }
}
