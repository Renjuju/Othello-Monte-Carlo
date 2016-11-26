# Othello-Monte-Carlo

This program uses the monte-carlo algorithm to win Othello

The assignment can be run using the provided make file

Overall, Monte Carlo looks like it runs ok vs random search and minimax
After 100 iterations at 1000, Monte Carlo beats out random and minimax, though
the Give-away-minimax seems to fair a bit better than Monte Carlo. 100 tests
might not be enough, but it takes a long time to actually run those tests.
Monte Carlo might be significantly better at higher iterations, though those
iterations take a long time to finish. 

## Give-away-minimax

Using the give away heurisitic, the chances of winning are considerably
higher for the AI. The giveaway heuristic lets the opponent take
the first 40 moves, and then the minimax kicks in. Though uninuitive, this
allows the AI to take the bulk of all pieces leading to a win most times
