package classes;

public class Node {
    private int id;
    private boolean visited;
    private boolean direct;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public Node(int id, boolean visited, boolean direct) {
        this.id = id;
        this.visited = visited;
        this.direct = direct;
    }
}
