package team2679.core;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PolynomialSpline implements ExtendedSpline {

    public static class Segment {
        PolynomialFunction xFunction;
        PolynomialFunction yFunction;

        public Segment(PolynomialFunction xFunction, PolynomialFunction yFunction) {
            this.xFunction = xFunction;
            this.yFunction = yFunction;
        }

        public Point interpolate(double t) {
            return new Point(xFunction.value(t), yFunction.value(t));
        }
    }
    private TreeMap<Double, Segment> segmentsMap = new TreeMap<>();

    public PolynomialSpline(List<Segment> segments) {
        for (int i = 0; i < segments.size(); i++) {
            segmentsMap.put((double)(i)/(segments.size() - 1.0), segments.get(i));
        }
    }

    @Override
    public double getCurvature(double percent) {
        ImmutablePair<Segment, Double> segmentParameterPair = segmentAndMappedParameter(percent);

        Segment segment = segmentParameterPair.left;
        double t = segmentParameterPair.right;

        double xDerivative = segment.xFunction.polynomialDerivative().value(t);
        double yDerivative = segment.yFunction.polynomialDerivative().value(t);
        double xSecondDerivative = segment.xFunction.polynomialDerivative().polynomialDerivative().value(t);
        double ySecondDerivative = segment.yFunction.polynomialDerivative().polynomialDerivative().value(t);

        return Util.calculateCurvature(xDerivative, yDerivative, xSecondDerivative, ySecondDerivative);
    }

    private final static Range<Double> segmentFunctionRange = Range.between(0.0, 1.0);
    @Override
    public Point interpolatePoint(double percent) {
        ImmutablePair<Segment, Double> segmentParameterPair = segmentAndMappedParameter(percent);

        Segment segment = segmentParameterPair.left;
        double t = segmentParameterPair.right;

        return segment.interpolate(t);
    }

    private ImmutablePair<Segment, Double> segmentAndMappedParameter(double percent) {
        Map.Entry<Double, Segment> segmentEntry = segmentsMap.floorEntry(percent);
        Segment curveSegment = segmentEntry.getValue();

        double segmentEnd;
        if (percent >= segmentsMap.lastKey()){
            segmentEnd = 1.0;
        } else {
            segmentEnd = segmentsMap.higherKey(percent);
        }
        Range<Double> originalRange = Range.between(segmentEntry.getKey(), segmentEnd);

        double t = Util.rangeMapping(originalRange, segmentFunctionRange, percent);

        return new ImmutablePair<Segment, Double>(curveSegment, t);
    }

}
