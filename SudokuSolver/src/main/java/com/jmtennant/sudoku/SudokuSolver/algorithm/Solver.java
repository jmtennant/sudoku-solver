package com.jmtennant.sudoku.SudokuSolver.algorithm;

import com.jmtennant.sudoku.SudokuSolver.core.Board;

/**
 * Interface to standardize the method by which different algorithms will solve sudoku boards
 * 
 * @author Jacob Tennant
 * @version August 6, 2024 
 */
public interface Solver {

	/**
	 * General Solver method that BoardSolvers will implement to solve sudoku boards
	 * @param board sudoku board object to have the solver solve
	 * @return boolean of whether heuristic had any effect on the board
	 */
	public boolean solveBoard( Board board );
}
