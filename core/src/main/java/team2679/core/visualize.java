package team2679.core;

public class visualize {

    public static void main(String[] args) {

        int pointsW = 200;

        FileManager fm = new FileManager("C:/Users/OWNER/git/repository/TigerCode/core/src/main/java/team2679/core/points.txt");
        Spline spline = fm.load("BSpline"); // Loads saved points.
        VelocitiesAdapter vs = new VelocitiesAdapter(spline, 15, 0.5);

//        vs.saveCSV(vs.getMaxVelocities(100), "Ves", "ves.csv");
        vs.setMaxVelocities(100);

        System.out.println(vs.getDuration());

        int duration = (int)vs.getDuration();
        int length = duration * pointsW;
        double[][] velocities = new double[length][2];
        System.out.println("Duration: " + duration + ", length: " + length);
        int index = 0;

        for (double i = 0; i<duration-1.0/pointsW; i+=1.0/pointsW) {
            velocities[index] = vs.getVelocities(i);
            index++;
        }

        vs.saveCSV(vs.velocities, "velocities.csv");
        vs.saveCSV(vs.getMaxVelocities(100), "v", "max_velocities.csv");
        vs.saveCSV(velocities, "time_velocities.csv");
        vs.saveCSV(vs.points, "points.csv");
    }

}
