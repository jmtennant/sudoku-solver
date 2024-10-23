package com.jmtennant.sudoku.SudokuSolver.helper;

import com.jmtennant.sudoku.SudokuSolver.core.Board;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;

/**
 * VerfiySolution interface provides behaviors for implementing different algorithms for verifying solved boards
 */
public class BoardChecker {

	private String output;
	
	/**
	 * Counts number of non-empty cells in a board
	 * @param board sudoku board object to look through
	 * @return number on cells in board that aren't empty
	 */
	public int countSolved( Board board ) {
		int numSolved = 0;
		for( int i = 0; i < board.size(); i++ ) {
			for( int j = 0; j < board.size(); j++ ) {
				if( !board.getCell(i, j).isEmpty() ) {
					numSolved++;
				}
			}
		}
		return numSolved;
	}
	
	
	
	/**
	 * Checks the solved sudoku board to verify solution is accurate
	 * Generates additional output that can be accessed via getOutput indicating where board fails if not valid
	 * @param board board object to check
	 * @return true if board is a valid solved board, else false
	 */
	public boolean checkBoard(Board board) {
		int n = board.size();
		StringBuilder outputStage = new StringBuilder();
		outputStage.append("Output for board of size: " + n + "\n");
		boolean isValidBoard = true;
		
		// iterate through each cell
		for( int i = 0; i < n; i++ ) { //iterate through each row
			for( int j = 0; j < n; j++ ) { //iterate through each column
				int value;
				//check for empty cells
				if( (value = board.getCellValue(i, j)) == 0 ) {
					outputStage.append("Empty cell at (" + i + ", " + j + ")\n");
					isValidBoard = false;
				}
				//check the row, col, block for matching values
				int[] blockCoords = board.getBlockOfCell(i, j);
				int[] block = board.getBlockValues(blockCoords[0], blockCoords[1]);
				//iterate through 0 to n - 1
				for( int k = 0; k < n; k++ ) {
					
					//check associated item in row
					if( board.getCellValue(i, k) == value && k != j ) { //if value matches and isn't current cell itself
						outputStage.append("Row match between (" + i + ", " + j + ") and (" + i + ", " + k + ")\n");
					}
					
					//check associated item in column
					if( board.getCellValue(k, j) == value && k != i ) { //if value matches and isn't current cell itself
						outputStage.append("Column match between (" + i + ", " + j + ") and (" + k + ", " + j + ")\n");
					}
					
					//check associated item in block
					int b = (int) Math.sqrt( (double) board.size() );
					int valueIndexInBlock = b*i + j;
					if( block[k] == value && k != valueIndexInBlock) { //if value matches and isnt current cell
						int x = blockCoords[0] * b + k / b;
					    int y = blockCoords[1] * b + k % b;
						outputStage.append("Block match between (" + i + ", " + j + ") and (" + x + ", " + y + ")\n");
					}
				}
			}
		}
		this.output = outputStage.toString();
		return isValidBoard;
	}
	
	/**
	 * This method is a solution verifier that performs a solution verification of a solved sudoku board. This
     * solution is alternate to boardCheck() in that it maintains frequency tables for each row, column, and block (so 3n tables total)
     * It is faster than the regular boardCheck, running in O(n^2) rather than O(n^3), but has a higher data cost. 
	 * @param board
	 * @return
	 */
	public boolean checkBoardFaster( Board board ) {
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
