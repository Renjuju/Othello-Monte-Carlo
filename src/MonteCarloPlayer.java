import java.util.List;
import java.util.Random;

/**
 * Created by renju on 11/19/16.
 */
class MonteCarloPlayer extends OthelloPlayer {

    private int iterations;

    MonteCarloPlayer(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public OthelloMove getMove(OthelloState state) {
        Node node = MonteCarloTreeSearch(state);
        if(node == null) {
            return null;
        }
        return node.move;
    }

    private Node MonteCarloTreeSearch(OthelloState state) {
        Node root = createNode(state);
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

    private Node createNode(OthelloState state) {
        return new Node(state);
    }

    private Node bestChild(Node node) {
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

    private Node treePolicy(Node node) {
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
            System.out.println("Greedy best child");
            tempNode = bestChild(node);
        } else {
            //random child
            tempNode = node.children.get(random.nextInt(node.children.size()));
        }
        System.out.println("Greedy");
        return treePolicy(tempNode);
    }

    private int score(Node node) {
        return node.state.score();
    }

    private Node defaultPolicy(Node node) {
        Node tempNode = new Node();
        tempNode.setState(node.state.clone());
        OthelloRandomPlayer player = new OthelloRandomPlayer();

        while(!tempNode.state.gameOver()) {
            tempNode.state.applyMove(player.getMove(tempNode.state));
        }
        return tempNode;
    }

    private void backup(Node node, int score) {
        node.visits++;
        node.averageScore = score;

        if(node.hasParent()) {
           backup(node.parent, score);
        }
    }
}