/**
 * Created by renju on 11/19/16.
 */
public class MonteCarloPlayer extends OthelloPlayer {
    @Override
    public OthelloMove getMove(OthelloState state) {
        MonteCarloTreeSearch(state, 100);
        return null;
    }

    public Node MonteCarloTreeSearch(OthelloState state, int iterations) {
        Node root = createNode(state.board);
        for(int i = 0; i < iterations; i++) {
            Node node = treePolicy(root);
            if(node != null) {
                Node node2 = defaultPolicy(node);
                int node2Score = score(node2);
                //backup(node, node2Score);
            }
        }
        return null;
    }

    public Node createNode(int[][] board) {
        Node node = new Node();
        int boardSize = board.length;
        OthelloState state = new OthelloState(boardSize);
        node.setState(state);
        return node;
    }

    public Node treePolicy(Node node) {
        return null;
    }

    public int score(Node node) {
        return node.state.score();
    }

    public Node defaultPolicy(Node node) {
        return null;
    }

    public void backUp(Node node, int score) {

    }
}

//    MonteCarloTreeSearch(board,iterations):
//        root = createNode(board);
//        for i = 0...iterations:
//        node = treePolicy(root);
//        if (node!=null)
//        node2 = defaultPolicy(node);
//        Node2Score = score(node2);
//        backup(node,Node2Score);
//        return action(bestChild(root))