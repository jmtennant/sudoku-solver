package com.jmtennant.sudoku.SudokuSolver.helper;

import com.jmtennant.sudoku.SudokuSolver.core.Board;

/**
 * VerfiySolution interface provides behaviors for implementing different algorithms for verifying solved boards
 */
public interface VerifySolution {

	/**
	 * Checks the solved sudoku board to verify solution is accurate
	 * Generates additional output that can be accessed via getOutput indicating where board fails if not valid
	 * @param board board object to check
	 * @return true if board is a valid solved board, else false
	 */
	boolean checkBoard( Board board );
	
	/**
	 * Returns string of output indicating where and how solved board fails verification
	 * @return output string
	 */
	String getOutput();
}
