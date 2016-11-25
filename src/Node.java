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

//its parent,
// its children,
// the action that led to this state,
// the number of times this node has been visited and the
// average score found so far for this node.
