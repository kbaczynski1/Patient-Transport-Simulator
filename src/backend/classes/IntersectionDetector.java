package classes;

import javax.print.attribute.IntegerSyntax;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;


public class IntersectionDetector {

    public void detect() {
        ArrayList<Road> roads = DataBase.getRoadsList();
        boolean[][] checkedRoads = new boolean[roads.size()][roads.size()];
        // set diagonal as true, since we don't want to check road with itself
        for (int i = 0; i < checkedRoads.length; i++) {
            checkedRoads[i][i] = true;
        }
        // check
//        for (int i = 0; i < checkedRoads.length; i++) {
//            for (int j = 0; j< checkedRoads[i].length; j++){
//                System.out.print(checkedRoads[i][j]);
//            }
//            System.out.println();
//        }

        ArrayList<Intersection> intersections = new ArrayList<>();

        for(int i = 0; i < roads.size(); i++) {
            for (int j = 0; j < roads.size(); j++) {
                // check intersection
                if(checkedRoads[i][j]) {
                    continue;
                }

                Intersection intersection = roads.get(i).doesIntersect(roads.get(j));

                if ( intersection != null) {
                    DataBase.addIntesection(intersection);
                }
                checkedRoads[i][j] = true;
                checkedRoads[j][i] = true;
            }
        }

//        System.out.println("Znalezione przecięcia");
//        for (Intersection intersection: DataBase.getIntersectionsList()) {
//            System.out.println(intersection);
//        }

        // TODO Implement sweep Line algorithm instead
        Collections.sort(roads);


    }
}
