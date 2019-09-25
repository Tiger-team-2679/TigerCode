package team2679.core;

public class visualize {

    public static void main(String[] args) {
        FileManager fm = new FileManager("default");
        Spline spline = fm.load("BSpline"); // Loads saved points.
        VelocitiesAdapter vs = new VelocitiesAdapter(spline, 15, 0.5);

//        vs.saveCSV(vs.getMaxVelocities(100), "Ves", "ves.csv");
        vs.setMaxVelocities(100);

        System.out.println(vs.getDuration());

        double[][] velocities = new double[((int)vs.getDuration()) * 100][2];

        for (double i = 0; i<((int)vs.getDuration()); i+=0.01) {
            velocities[(int)(i*100)] = vs.getVelocities(i);
        }

        vs.saveCSV(velocities, "ves.csv");

    }

}
