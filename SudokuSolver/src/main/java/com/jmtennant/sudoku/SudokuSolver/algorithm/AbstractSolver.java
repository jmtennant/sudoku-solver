/**
 * 
 */
package com.jmtennant.sudoku.SudokuSolver.algorithm;

import com.jmtennant.sudoku.SudokuSolver.core.Board;
import com.jmtennant.sudoku.SudokuSolver.core.Cell;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.dsa.list.List;

/**
 * Abstract Class to hold helper methods that would be useful regardless of the exact solving algorithm
 * 
 * @author Jacob Tennant (jmtennant543@gmail.com)
 * @version August 6, 2024
 */
public abstract class AbstractSolver implements Solver {

	/**
	 * Generates a list of possible numbers that could go in the argued cell on the argued board
	 * @param c Cell to check the options for
	 * @param board Sudoku Board that cell is on
	 * @return List of integers of the options of possible numbers that could go in cell
	 */
	protected List<Integer> populateOptions(Cell c, Board board){
		//if cell isn't empty, already has a number so no options
		if( !c.isEmpty() ) {
			return null;
		}
		
		//initialize
		List<Integer> output = new ArrayBasedList<Integer>(board.size() + 1);
		boolean[] found = new boolean[board.size()+1];
		int row = c.getRow();
		int col = c.getCol();
		
		//find which values are in same row
		for( Cell k : board.getRow(row) ) {
			if( !k.isEmpty() ) {
				found[k.getElement()] = true;
			}
		}
		
		//find which values are in same col
		for( Cell k : board.getColumn(col) ) {
			if( !k.isEmpty() ) {
				found[k.getElement()] = true;
			}
		}
		
		//find which values are in same block
		for( Cell k : board.getBlock(row, col) ) {
			if( !k.isEmpty() ) {
				found[k.getElement()] = true;
			}
		}
		
		//add all not found values to list of options
		for( int k = 1; k <= board.size(); k++) {
			if( found[k] == false ) {
				output.addLast(k);
			}
		}
		
		return output;		
	}

}
