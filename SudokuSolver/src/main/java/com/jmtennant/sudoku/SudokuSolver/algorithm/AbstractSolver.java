/**
 * 
 */
package com.jmtennant.sudoku.SudokuSolver.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jmtennant.sudoku.SudokuSolver.core.Board;
import com.jmtennant.sudoku.SudokuSolver.core.Cell;



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
	protected static List<Integer> getCellOptions(Cell c, Board board){
		
		
		//initialize
		List<Integer> output = new ArrayList<Integer>(board.size() + 1);
		
		//if cell isn't empty, already has a number so no options
		if( !c.isEmpty() ) {
			return Collections.emptyList();
		}
		
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

	
	public static void populateOptions(Board board) {
		for( int i = 0; i < board.size(); i++ ) {
			for( int j = 0; j < board.size(); j++ ) {
				Cell cell = board.getCell(i, j);
				if( cell.isEmpty() ) {
					cell.setOptions( getCellOptions(cell, board) );
				}
			}
		}
		
		
	}
}
