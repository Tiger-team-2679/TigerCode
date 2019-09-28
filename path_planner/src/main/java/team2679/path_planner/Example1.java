package team2679.path_planner;

public class Example1 {

    public static void main(String[] args) {

        /**
         * Graphical mode example to create the spline and save the data locally.
         */
 
        FRCNavigator nav = new FRCNavigator();
//        nav.load("default");
        nav.saveAtEnd(true, "C:/Users/OWNER/git/repository/TigerCode/core/src/main/java/team2679/core/points.txt");

    }

}
