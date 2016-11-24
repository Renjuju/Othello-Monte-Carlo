import java.util.List;
import java.util.Random;

/**
 * Created by renju on 11/19/16.
 */
public class MonteCarloPlayer extends OthelloPlayer {

    private int iterations;

    MonteCarloPlayer(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public OthelloMove getMove(OthelloState state) {
        return MonteCarloTreeSearch(state).move;
    }

    public Node MonteCarloTreeSearch(OthelloState state) {
        Node root = createNode(state.board);
        for(int i = 0; i < this.iterations; i++) {
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
        if(node.state.nextPlayerToMove == 0) {
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

        //terminal node
        if(moveList.size() == 0) {
            return node;
        }
        //checks if children not in tree
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


        //epsilon greedy strategy
        Node tempNode;
        Random random = new Random();
        if(random.nextInt(100) < 90) {
            tempNode = bestChild(node);
        } else {
            //random child
            tempNode = node.children.get(random.nextInt(node.children.size()));
        }
        return treePolicy(tempNode);
    }

    public int score(Node node) {
        return node.state.score();
    }

    //defaultPolicy(node):
    // this function just uses the random agent to select actions at random for each player,
    // until the game is over,
    // and returns the final state of the game.
    public Node defaultPolicy(Node node) {
        Node tempNode = new Node();
        tempNode.setState(node.state);

        OthelloPlayer players[] = {new OthelloRandomPlayer(),
                new OthelloRandomPlayer()};

        while(!tempNode.state.gameOver()) {
            OthelloMove move = players[tempNode.state.nextPlayerToMove].getMove(tempNode.state);
            tempNode.state = tempNode.state.applyMoveCloning(move);
        }
        return tempNode;
    }

    //backup(node,score): increments in 1 the number of times "node" has been visited,
    // and updates the average score in "node" with the value "score".
    // If "node" has a parent, then it calls "backup(node.parent,score)".
    public void backup(Node node, int score) {
        node.visits++;
        node.averageScore = score;

        if(node.hasParent()) {
           backup(node.parent, score);
        }
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