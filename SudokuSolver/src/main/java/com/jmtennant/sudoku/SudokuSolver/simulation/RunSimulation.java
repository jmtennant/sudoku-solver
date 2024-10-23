package com.jmtennant.sudoku.SudokuSolver.simulation;

import com.jmtennant.sudoku.SudokuSolver.algorithm.FrequencySolver;
import com.jmtennant.sudoku.SudokuSolver.algorithm.PruneSolver;
import com.jmtennant.sudoku.SudokuSolver.algorithm.Solver;
import com.jmtennant.sudoku.SudokuSolver.core.Board;
import com.jmtennant.sudoku.SudokuSolver.helper.BoardChecker;
import com.jmtennant.sudoku.SudokuSolver.util.SudokuBoardReader;

/**
 * Non-gui method of running algorithms/solvers on a board and producing output about solving capacity / performance
 */
public class RunSimulation {
	
	private Board board;
	private Solver pruneSolver;
	private Solver freqSolver;
	private BoardChecker checker;
	
	public RunSimulation( boolean passFail, String inputFormat, String input) throws Exception{
		if( inputFormat.equalsIgnoreCase("line") ) {
			board = SudokuBoardReader.readBoardFromLineString( input );
		} else if( inputFormat.equalsIgnoreCase("Grid") ) {
			board = SudokuBoardReader.readBoardGridFormat(input);
		} else {
			throw new IllegalArgumentException("Invalid format argument" );
		}
		
		checker = new BoardChecker();
		pruneSolver = new PruneSolver();
		freqSolver = new FrequencySolver();
	}
	
	public void run() {
		int numHints = checker.countSolved(board);
		int totalToSolve = board.size() * board.size() - numHints;
		
		boolean hadEffect = false;
		
		long startTime = System.nanoTime();
		
		do {
			boolean pruneWorked = pruneSolver.solveBoard(board);
			boolean freqWorked = freqSolver.solveBoard(board);
			hadEffect = pruneWorked || freqWorked;
		} while( hadEffect == true );
		
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		
		int numSolved = checker.countSolved(board) - numHints;
		
		System.out.println("Board size: " + board.size() + "x" + board.size() );
		System.out.println("Time Elapsed: " + timeElapsed / 1000000 + "ms");
		System.out.println("Hints: " + numHints);
		System.out.println("Solved: " + numSolved + "/" + totalToSolve);
	}
}
