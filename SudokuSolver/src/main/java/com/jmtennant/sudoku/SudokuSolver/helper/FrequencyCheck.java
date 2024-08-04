package com.jmtennant.sudoku.SudokuSolver.helper;

import com.jmtennant.sudoku.SudokuSolver.core.Board;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;

/**
 * This class is a solution verifier that extends BoardCheck to perform a solution verification of a solved sudoku board. This
 * solution is alternate to BasicBoardCheck in that it maintains frequency tables for each row, column, and block (so 3n tables total)
 * It is faster than BasicBoardCheck, running in O(n^2) rather than O(n^3), but has a higher data cost. 
 * 
 * This class uses the ArrayBasedList data structure that I built in my CSC316: Data Structures & Algorithms course at 
 * North Carolina State University. The course uses the following textbook:
 * Data Structures & Algorithms In Java, Sixth Edition, by Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 * The course was taught by Dr. Jason King
 * 
 * @author Jacob Tennant
 */
public class FrequencyCheck extends BoardCheck {

	@Override
	public boolean checkBoard(Board board) {
		//get board size and sqrt
		int n = board.size();
		int sqrt = (int) Math.sqrt( (double) n );
		
		//Initialize staging for string output
		StringBuilder outputStage = new StringBuilder();
		outputStage.append("Output for board of size: " + n + "\n");
		boolean isValidBoard = true;
		
		//initialize frequency tables
		//blocks go 0 through n-1 such that 0,0 -> 0, 0,1 -> 1
		ArrayBasedList<Integer[]> rowFrequencies = new ArrayBasedList<Integer[]>(n); //list of tables, 1 for each row  
		ArrayBasedList<Integer[]> colFrequencies = new ArrayBasedList<Integer[]>(n); //list of tables, 1 for each col
		ArrayBasedList<Integer[]> blockFrequencies = new ArrayBasedList<Integer[]>(n); //list of tables, 1 for each block
		for( int i = 0; i < n; i++) {
			rowFrequencies.add(i, new Integer[n + 1]);
			colFrequencies.add(i, new Integer[n + 1]);
			blockFrequencies.add(i, new Integer[n + 1]);
		}
		//each possible cell value (1 through n) corresponds to index in a frequency table. Value at index represents frequency in group
		
		// iterate through row
		for( int i = 0; i < n; i++ ) {
			//iterate through each col in row
			for( int j = 0; j < n; j++ ) {
				//cell coordinates will be (i, j)
				int value = board.getCellValue(i, j);
				
				//check for empty cells
				if( (value = board.getCellValue(i, j)) == 0 ) {
					outputStage.append("Empty cell at (" + i + ", " + j + ")\n");
					isValidBoard = false;
				}
				
				//validate no matches in row, set outputs if not
				rowFrequencies.get(i)[value]++;
				if( rowFrequencies.get(i)[value] > 1 ) {
					outputStage.append("Row match at (" + i + ", " + j + ") for value " + value + "\n");
					isValidBoard = false;
				}
				
				//validate no matches in row, set outputs if not
				colFrequencies.get(j)[value]++;
				if( colFrequencies.get(j)[value] > 1 ) {
					outputStage.append("Col match at (" + i + ", " + j + ") for value " + value + "\n");
					isValidBoard = false;
				}
				
				//validate no matches within block
				int[] blockCoords = board.getBlockOfCell(i, j);
				int b = blockCoords[0] * sqrt + blockCoords[1];
				blockFrequencies.get(b)[value]++;
				if( blockFrequencies.get(b)[value] > 1 ) {
					outputStage.append("Block match at (" + i + ", " + j + "), (block " + b + ") for value " + value + "\n");
					isValidBoard = false;
				}
				
			}
		}
		
		this.output = outputStage.toString();
		return isValidBoard;
	}

}
