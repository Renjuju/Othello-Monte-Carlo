import java.util.List;

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
                backup(node, node2Score);
            }
        }
        return bestChild(root);
    }

    public Node createNode(int[][] board) {
        Node node = new Node();
        int boardSize = board.length;
        OthelloState state = new OthelloState(boardSize);
        node.setState(state);
        return node;
    }

    public Node bestChild(Node node) {
        Node bestChild = null;
        int maxAverage = Integer.MIN_VALUE;
        int minAverage = Integer.MAX_VALUE;
        if(node.state.nextPlayerToMove == node.state.PLAYER1) {
            //max average score
            for(Node child : node.children) {
                if(child.averageScore > maxAverage) {
                    bestChild = child;
                    maxAverage = child.averageScore;
                }
            }
            return bestChild;
        } else {
            //min average score
            for(Node child : node.children) {
                if(child.averageScore < minAverage) {
                    bestChild = child;
                    minAverage = child.averageScore;
                }
            }
            return bestChild;
        }
    }
// treePolicy(node): this function does the following:
// If 'node' still has any children that are not in the tree, then it generates one of those children
// ('newnode'), it adds 'newnode' as a child to 'node', and returns ‘newnode’.
//  If 'node' is a terminal node (no actions can be performed). Then it returns “node”
//
// If 'node' is not a terminal but all its children are in the tree,
// then: 90% of the times "nodetmp = bestChild(node)",
// and 10% of the times "nodetmp = [a child of node at random]" (if you are curious,
// this is called an epsilon-greedy strategy). Then, the function returns "treePolicy(nodetmp)"
    public Node treePolicy(Node node) {
        List<OthelloMove> moveList = node.state.generateMoves();

        for(OthelloMove move : moveList) {
            boolean hasMove = false;
            for(Node child : node.children) {
                if(child.move.equals(move)) {
                    hasMove = true;
                }
            }
            if(!hasMove) {
                Node newNode = new Node(node);
                newNode.setMove(move);
                newNode.setState(node.state.applyMoveCloning(move));
                node.addChild(newNode);
                return newNode;
            }
        }
        return null;
    }

    public int score(Node node) {
        return node.state.score();
    }

    public Node defaultPolicy(Node node) {
        return null;
    }

    public void backup(Node node, int score) {

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