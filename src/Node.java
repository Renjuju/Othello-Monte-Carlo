import java.util.ArrayList;

public class Node {

    Node parent;
    ArrayList<Node> children;
    OthelloState state;
    OthelloMove move;
    int depth;
    int visits = 0;
    int averageScore = 0;

    public Node() {
        this.depth = 0;
        this.state = null;
        this.children = new ArrayList<>();
        this.parent = null;
        this.move = null;
        this.visits = 0;
        this.averageScore = 0;
    }

    public Node(Node parent) {
        this.parent = parent;
        this.depth = this.parent.depth + 1;
        this.children = new ArrayList<>();
        this.averageScore = 0;
        this.visits = 0;
    }

    public Node(OthelloState state) {
        this.parent = null;
        this.depth = 0;
        this.visits = 0;
        this.averageScore = state.score();
        this.children = new ArrayList<>();
        this.state = state;
    }

    public void setMove(OthelloMove move) {
        this.move = move;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void addMove(OthelloMove move) {
        this.move = move;
    }

    public void setState(OthelloState state) {
        this.state = state;
    }

    public boolean hasParent() {
        return this.parent != null;
    }
}