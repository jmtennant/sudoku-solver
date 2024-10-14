package com.jmtennant.sudoku.SudokuSolver.algorithm;

import com.jmtennant.sudoku.SudokuSolver.core.Board;
import com.jmtennant.sudoku.SudokuSolver.core.Cell;

import edu.ncsu.csc316.dsa.queue.ArrayBasedQueue;
import edu.ncsu.csc316.dsa.queue.Queue;

/**
 * <p>
 * For reference to how this specific algorithm operates, see <a href="../../../../../../../../../docs/algorithms/PruneBoard">PruneBoard</a> file
 * </p>
 * 
 * @author Jacob Tennant (jmtennant543@gmail.com)
 * @version 10/7/2024
 */
public class PruneSolver extends AbstractSolver {

	
	@Override
	public boolean solveBoard(Board board) {
		boolean hasEffect = false;
		int n = board.size();
		Queue<Cell> cellQueue = new ArrayBasedQueue<Cell>();
		
		populateOptions(board);
		
		//fill queue with initial 1-option cells
		for( int i = 0; i < n; i++ ) {
			for( int j = 0; j < n; j++ ) {
				//System.out.println("Checking cell (" + i + ", " + j + "), numOptions = " + board.getCell(i, j).numOptions() );
				if(board.getCell(i, j).numOptions() == 1) {
					cellQueue.enqueue(board.getCell(i, j));
					//System.out.println("Enqueueing cell: (" + i + ", " + j + ")");
				}
			}
		}
		//Queue Q is full of all cells that initially only have 1 option
		//We fill in the element with that option, and then update all of the cells that could be affected
		//and add any new one-option cells to the queue
		while( !cellQueue.isEmpty() ) {
			Cell cell = cellQueue.dequeue();
			board.setCellValue(cell.getRow(), 
					           cell.getCol(), 
					           cell.getOptions().getFirst());
			hasEffect = true;
			
			//get the cells in the block that C is in
			Cell[] block = board.getBlock( cell.getRow(), cell.getCol());
			
			for( int k = 0; k < n; k++ ) {
				//get next step in iteration from row that C is in
				//then update options and enqueue if only one left
				Cell candidate = board.getCell(k, cell.getCol());
				if( candidate.isEmpty() ) {
					board.setCellOptions(candidate.getRow(), 
							             candidate.getCol(), 
							             getCellOptions(candidate, board));
				}
				if( candidate.numOptions() == 1 ) {
					cellQueue.enqueue(candidate);
				}
				
				//now do it for columns
				candidate = board.getCell(cell.getRow(), k);
				if( candidate.isEmpty() ) {
					board.setCellOptions(candidate.getRow(), 
							             candidate.getCol(), 
							             getCellOptions(candidate, board));
				}
				if( candidate.numOptions() == 1 ) {
					cellQueue.enqueue(candidate);
				}
				
				//now do it for block
				candidate = block[k];
				if( candidate.isEmpty() ) {
					board.setCellOptions(candidate.getRow(), 
							             candidate.getCol(), 
							             getCellOptions(candidate, board));
				}
				if( candidate.numOptions() == 1 ) {
					cellQueue.enqueue(candidate);
				}
			}
		}
		return hasEffect;
	}

}
