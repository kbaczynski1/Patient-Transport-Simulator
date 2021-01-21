package classes;

import javax.print.attribute.IntegerSyntax;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.*;


public class IntersectionDetector {

    private Queue<IntersectionEvent> queue;
    private NavigableSet<Road> tree;
    private ArrayList<Intersection> intersections;

    public IntersectionDetector(ArrayList<Road> roads) {
        this.queue = new PriorityQueue<>(new IntersectionEventComparator());
        this.tree = new TreeSet<>(new RoadComparator());
        this.intersections = new ArrayList<>();
        for (Road road: roads) {
            this.queue.add(new IntersectionEvent(road.getStartPoint(), road, 0));
            this.queue.add(new IntersectionEvent(road.getEndPoint(), road, 1));
        }
    }

    public void scanIntersections() {
        while(!this.queue.isEmpty()) {
            IntersectionEvent event = this.queue.poll();
            double length = event.getValue();
            if(event.getType() == 0) {
                for (Road road: event.getRoads()) {
                    this.recalculate(length);
                    this.tree.add(road);
                    if(this.tree.lower(road) != null) {
                        Road r = this.tree.lower(road);
                        this.checkIntersection(r, road, length);
                    }
                    if (this.tree.higher(road) != null) {
                        Road r = this.tree.higher(road);
                        this.checkIntersection(r, road, length);
                    }
                    if (this.tree.lower(road) != null && this.tree.higher(road) != null) {
                        Road r = this.tree.lower(road);
                        Road t = this.tree.higher(road);
                        this.removeFuture(r, t);
                    }
                }
            } else if (event.getType() == 1) {
                for (Road road : event.getRoads()) {
                    if (this.tree.lower(road) != null && this.tree.higher(road) != null) {
                        Road r = this.tree.lower(road);
                        Road t = this.tree.higher(road);
                        this.checkIntersection(r, t, length);
                    }
                }
            } else if (event.getType() == 2) {
                Road road1 = event.getRoads().get(0);
                Road road2 = event.getRoads().get(1);
                this.swap(road1, road2);
                if(road1.getValue() < road2.getValue()) {
                    if (this.tree.higher(road1) != null) {
                        Road t = this.tree.higher((road1));
                        this.checkIntersection(t, road1, length);
                        this.removeFuture(t, road2);
                    }
                    if (this.tree.lower(road2) != null) {
                        Road r = this.tree.lower(road2);
                        this.checkIntersection(r, road2, length);
                        this.removeFuture(r, road1);
                    }
                } else {
                    if (this.tree.higher(road2) != null) {
                        Road t = this.tree.higher(road2);
                        this.checkIntersection(t, road2, length);
                        this.removeFuture(t,road1);
                    }
                    if (this.tree.lower(road1) != null) {
                        Road r = this.tree.lower(road1);
                        this.checkIntersection(r, road1, length);
                        this.removeFuture(r, road2);
                    }
                }
                this.intersections.add(new Intersection(DataBase.getIntersectionsList().size() + DataBase.getHospitalsList().size() + 1, road1.getId(), road2.getId(), event.getPoint()));
            }
        }
    }

    private boolean removeFuture(Road road1, Road road2) {
        for(IntersectionEvent event : this.queue) {
            if(event.getType() == 2) {
                if((event.getRoads().get(0) == road1 && event.getRoads().get(1) == road2) ||
                   (event.getRoads().get(0) == road2 && event.getRoads().get(1) == road1)) {
                    this.queue.remove(event);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIntersection(Road road1, Road road2, double L) {
        double x1 = road1.getStartPoint().getX();
        double y1 = road1.getStartPoint().getY();
        double x2 = road1.getEndPoint().getX();
        double y2 = road1.getEndPoint().getY();
        double x3 = road2.getStartPoint().getX();
        double y3 = road2.getStartPoint().getY();
        double x4 = road2.getEndPoint().getX();
        double y4 = road2.getEndPoint().getY();
        double r = (x2 - x1) * (y4 - y3) - (y2 - y1) * (x4 - x3);

        if ( r != 0) {
            double t = ((x3 - x1) * (y4 - y3) - (y3 - y1) * (x4 - x3)) / r;
            double u = ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1)) / r;
            if(t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                double x_c = x1 + t * (x2 - x1);
                double y_c = y1 + t * (y2 - y1);
                if ((x_c != x1 || y_c != y1) && (x_c != x2 || y_c != y2) && (x_c != x3 || y_c != y3) && (x_c != x4 || y_c != y4)) {
                    if (x_c > L) {
                        this.queue.add(new IntersectionEvent(new Point2D.Double(x_c, y_c), new ArrayList<>(Arrays.asList(road1, road2)), 2));
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private void swap(Road road1, Road road2) {
        this.tree.remove(road1);
        this.tree.remove(road2);
        double value = road1.getValue();
        road1.setValue(road2.getValue());
        road2.setValue(value);
        this.tree.add(road1);
        this.tree.add(road2);
    }

    private void recalculate(double L) {
        Iterator<Road> iter = this.tree.iterator();
        while(iter.hasNext()) {
            iter.next().calculateValue(L);
        }
    }

    public void printIntersections() {
        System.out.println("Found intersections");
        for(Intersection inter: this.intersections) {
            System.out.println("x= " + inter.getCords().getX() + ", y= " + inter.getCords().getY());
        }
    }
    public ArrayList<Intersection> getIntersections() {
        return this.intersections;
    }

    private class IntersectionEventComparator implements Comparator<IntersectionEvent> {
        @Override
        public int compare(IntersectionEvent e1, IntersectionEvent e2) {
            if(e1.getValue() > e2.getValue()) {
                return 1;
            } else if (e1.getValue() < e2.getValue()) {
                return -1;
            }
            return 0;
        }
    }

    private class RoadComparator implements Comparator<Road> {
        @Override
        public int compare(Road road1, Road road2) {
        if(road1.getValue() < road2.getValue()) {
            return 1;
        } else if (road1.getValue() > road2.getValue()) {
            return -1;
        }
        return 0;
        }
    }
}
