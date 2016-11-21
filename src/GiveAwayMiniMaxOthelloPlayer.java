import java.util.ArrayList;
import java.util.List;

public class GiveAwayMiniMaxOthelloPlayer extends OthelloPlayer {
    //based on the give away strategy - counter intuitive but it works
    private int depth;

    GiveAwayMiniMaxOthelloPlayer(int depth) {
        this.depth = depth;
    }

    public OthelloMove getMove(OthelloState state) {
        return miniMax(state);
    }

    private OthelloMove miniMax(OthelloState state) {
        List<OthelloMove> moves = state.generateMoves();

        int max = Integer.MIN_VALUE;
        OthelloMove bestMove = null;
        for(OthelloMove move : moves) {
            state = min(state.applyMoveCloning(move), depth);
            if(state.score() > max) {
                max = state.score();
                bestMove = move;
            }
        }
        return bestMove;
    }

    //see min
    private OthelloState max(OthelloState state, int depth) {
        depth--;
        OthelloState bestState = null;
        List<OthelloMove> moves = state.generateMoves();

        if(moves.size() == 0 || depth == 0) {
            return state;
        }

        int val = Integer.MIN_VALUE;

        OthelloState tempState;
        for(OthelloMove move : moves) {
            tempState = min(state.applyMoveCloning(move), depth);
            if(tempState.score() > val) {
                val = tempState.score();
                bestState = tempState;
            }
        }
        return bestState;
    }

    //see max
    private OthelloState min(OthelloState state, int depth) {
        depth--;

        OthelloState bestState = null;
        List<OthelloMove> moves = state.generateMoves();

        if(moves.size() == 0 || depth == 0) {
            return state;
        }

        int val = Integer.MAX_VALUE;
        OthelloState tempState;
        for(OthelloMove move : moves) {
            tempState = max(state.applyMoveCloning(move), depth);
            bestState = tempState;
            if(countMatches(tempState.toString(), ".") < 40) {
                //optimize to enemy until certain number of pieces on the board
                //give away evaluation function.
                //maximizes enemy points until the end game, which then gives advantage to the AI
                if(tempState.score() > val) {
                    val = tempState.score();
                    bestState = state;
                }
            } else {
                if(tempState.score() < val) {
                    val = tempState.score();
                    bestState = state;
                }
            }
        }
        return bestState;
    }

    private int countMatches(String s, String c) {
        return s.length() - s.replace(c, "").length();
    }
}