package classes;

import java.util.ArrayList;

public class Country {
    private static ArrayList<Boundary> boundariesNodes = new ArrayList<Boundary>();

    public ArrayList<Boundary> getBoundariesNodes(){
        return boundariesNodes;
    }
}
