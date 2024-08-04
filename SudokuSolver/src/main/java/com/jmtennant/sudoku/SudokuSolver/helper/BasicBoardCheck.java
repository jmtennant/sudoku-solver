/**
 * 
 */
package com.jmtennant.sudoku.SudokuSolver.helper;

import com.jmtennant.sudoku.SudokuSolver.core.Board;

/**
 * Exhaustive verification method with no frequency storage. Checks each cell against every other cell in its 
 * row, column, and block leading to a time complexity of O(n^2 * 3n) = O(n^3)
 * 
 * @author Jacob Tennant (jmtennant543@gmail.com)
 */
public class BasicBoardCheck extends BoardCheck {

	
	@Override
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
				int[] block = board.getValuesInBlock(blockCoords[0], blockCoords[1]);
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

}
