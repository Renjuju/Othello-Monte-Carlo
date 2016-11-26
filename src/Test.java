/**
 *
 * @author santi
 */
public class Test {
    
    
    public static void main(String args[]) {
        // Create the game state with the initial position for an 8x8 board:



        int counter = 0;
        int wins = 0;
//        double start = System.currentTimeMillis();
        while(counter < 10) {
            OthelloState state = new OthelloState(8);
            OthelloPlayer players[] = {new MonteCarloPlayer(10000), new OthelloRandomPlayer()};
            do{
                // Display the current state in the console:
//                System.out.println("\nCurrent state, " + OthelloState.PLAYER_NAMES[state.nextPlayerToMove] + " to move:");
//                System.out.print(state);

                // Get the move from the player:
                OthelloMove move = players[state.nextPlayerToMove].getMove(state);
//                System.out.println(move);
                state = state.applyMoveCloning(move);
            }while(!state.gameOver());
            if(state.score() > 0) {
                wins++;
            }
            counter++;
        }

        System.out.println("Number of wins " + wins);
//        double end = System.currentTimeMillis();
//        System.out.println("Took : " + ((end - start) / 1000 + " seconds"));

        // Show the result of the game:
//        System.out.println("\nFinal state with score: " + state.score());
//        System.out.println(state);
    }    
    
}
