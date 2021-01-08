package classes;

import java.awt.geom.Point2D;

// TODO there is something wrong with inheritances
// TODO does it really need to have id and name in it? -> maybe rewrite boundary and implement
//      id and name in Monument and Hospital
// TODO Intersection needs to be with Hospitals in case of creating new roads and creating graph
public class Intersection extends Boundary{
    Intersection(int id, String name, Point2D.Double cords) {
        super(id, name, cords);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
