import java.util.ArrayList;

public class Node {

    Node parent;
    ArrayList<Node> children;
    OthelloState state;
    OthelloMove move;
    int depth;
    boolean isWinner = false;

    public Node(OthelloState state) {
        this.depth = 0;
        this.state = state;
        this.children = new ArrayList<>();
    }

    public Node(Node parent) {
        this.parent = parent;
        this.depth = this.parent.depth + 1;
        this.children = new ArrayList<>();
    }

    public void setMove(OthelloMove move) {
        this.move = move;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void isWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

    public void addMove(OthelloMove move) {
        this.move = move;
    }

}