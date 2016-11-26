import java.util.ArrayList;

public class Node {

    Node parent;
    ArrayList<Node> children;
    OthelloState state;
    OthelloMove move;
    int visits = 0;
    int averageScore = 0;

    Node() {
        this.state = null;
        this.children = new ArrayList<>();
        this.parent = null;
        this.move = null;
        this.visits = 0;
        this.averageScore = 0;
    }

    Node(Node parent) {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.averageScore = 0;
        this.visits = 0;
    }

    Node(OthelloState state) {
        this.parent = null;
        this.visits = 0;
        this.averageScore = state.score();
        this.children = new ArrayList<>();
        this.state = state;
    }

    void setMove(OthelloMove move) {
        this.move = move;
    }

    void addChild(Node child) {
        this.children.add(child);
    }

    void setState(OthelloState state) {
        this.state = state;
    }

    boolean hasParent() {
        return this.parent != null;
    }
}