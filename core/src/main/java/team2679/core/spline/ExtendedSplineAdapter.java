package team2679.core.spline;

import org.apache.commons.math3.analysis.UnivariateVectorFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.FiniteDifferencesDifferentiator;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableVectorFunction;
import org.apache.commons.math3.analysis.differentiation.UnivariateVectorFunctionDifferentiator;

import static java.lang.Math.pow;

public class ExtendedSplineAdapter implements ExtendedSpline {

    Spline spline;
    private static final double stepSize = 0.01;
    private static final int nbPoints = 3;
    private static final UnivariateVectorFunctionDifferentiator differentiator
            = new FiniteDifferencesDifferentiator(nbPoints, stepSize, 0, 1);
    public ExtendedSplineAdapter(Spline spline) {
        this.spline = spline;
    }

    public Point interpolatePoint(double percent) {
        return spline.interpolatePoint(percent);
    }

    public double getCurvature(double percent) {
        UnivariateVectorFunction vectorFunctionAdapter = new UnivariateVectorFunction() {
            @Override
            public double[] value(double x) {
                Point point = spline.interpolatePoint(x);
                return new double[]{point.x, point.y};
            }
        };

        UnivariateDifferentiableVectorFunction differentiableFunction
                =  differentiator.differentiate(vectorFunctionAdapter);

        DerivativeStructure value = new DerivativeStructure(1, 2, 0, percent);
        DerivativeStructure[] result =  differentiableFunction.value(value);

        double xDerivative = result[0].getPartialDerivative(1);
        double yDerivative = result[1].getPartialDerivative(1);

        double xSecondDerivative = result[0].getPartialDerivative(2);
        double ySecondDerivative = result[1].getPartialDerivative(2);


        double curvature = (xDerivative * ySecondDerivative - yDerivative * xSecondDerivative) / pow(xDerivative * xDerivative + yDerivative * yDerivative, 1.5);
        return curvature;
    }
}
