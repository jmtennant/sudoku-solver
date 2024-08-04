# sudoku-solver
A project to explore different approaches and algorithms for efficiently solving sudoku problems.

### Solution Verification Algorithms:
* _BasicBoardCheck_ - For each cell, check each other cell in row, column, and block for matching values, and for empty cells. Runs in O(n^3), no extra data cost
* _FrequencyCheck_ - Maintain frequency table for each row, each column, each block for how many times each value appears in the grouping. Iterate through cells and increment associated frequencies, checking if any frequency exceeds 1. Also checks for empty cells. Runs in O(n^2) with data cost of mainting 3n additional arrays of size n. Manages lists of arrays using my ArrayBasedList implementation that I made in CSC316 at NC State.
