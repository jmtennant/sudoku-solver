package com.jmtennant.sudoku.SudokuSolver.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jmtennant.sudoku.SudokuSolver.core.Board;
import com.jmtennant.sudoku.SudokuSolver.core.Cell;

public class FrequencySolver extends AbstractSolver {
	
	protected class QEntry {
		public Cell cell;
		public int option;
		
		public QEntry( Cell cell, int option ) {
			this.cell = cell;
			this.option = option;
		}
	}
	
	@Override
	public boolean solveBoard(Board board) {
		boolean hasEffect = false;
		AbstractSolver.populateOptions(board);
		//get board size for n
		int n = board.size(); 
		int root = (int) Math.sqrt( (double) board.size() );
		////declare frequency tables
		List<Integer[]> rowFreqs = new ArrayList<Integer[]>();
		List<Integer[]> colFreqs = new ArrayList<Integer[]>();
		List<Integer[]> blockFreqs = new ArrayList<Integer[]>();
		
		//initialize frequency tables with arrays for each R, B, C
		for( int i = 0; i < n; i++ ) {
			rowFreqs.add(i, new Integer[ n + 1 ] );
			colFreqs.add(i, new Integer[ n + 1 ] );
			blockFreqs.add(i, new Integer[ n + 1 ]);
			for( int j = 0; j <= n; j++ ) { //initialize values to zero;
				rowFreqs.get(i)[j] = 0;
				colFreqs.get(i)[j] = 0;
				blockFreqs.get(i)[j] = 0;
			}
		}
		
		//populate the frequency tables with initial values
		populateFrequencies(board, rowFreqs, colFreqs, blockFreqs );
		//queue will hold cells that can be solved
		Queue<QEntry> queue = new LinkedList<QEntry>();
		List<Cell> queuedCells = new ArrayList<Cell>();
		
		//find cells with option frequency = 1 in rows
		//for each row
		for( int k = 0; k < n; k++ ) {
			Integer[] optionFreqs = rowFreqs.get(k); //get freqs for options for that row
			for( int i = 1; i <= n; i++ ) { // for each freq
				if( optionFreqs[i] == 1 ) { //if freq = 1
					for( Cell cell : board.getRow(k) ) { // for each cell in row
						if( cell.isEmpty() && cell.getOptions().contains(Integer.valueOf(i))) { //if cell contains F=1 option
							if( !queuedCells.contains(cell) ) {
								queue.add(new QEntry(cell, Integer.valueOf(i))); //add cell to queue along with which option
								queuedCells.add(cell);
							}
							break; //stop looking through row for that specific option
						}
					}
				}
			}
		}
		
		//find cells with option frequency = 1 in cols
		//for each column
		for( int k = 0; k < n; k++ ) {
			Integer[] optionFreqs = colFreqs.get(k); //get freqs for options for that col
			for( int i = 1; i <= n; i++ ) { // for each freq
				if( optionFreqs[i] == 1 ) { //if freq = 1
					for( Cell cell : board.getColumn(k) ) { // for each cell in row
						if( cell.isEmpty() && cell.getOptions().contains(Integer.valueOf(i))) { //if cell contains F=1 option
							if( !queuedCells.contains(cell) ) {
								queue.add(new QEntry(cell, Integer.valueOf(i))); //add cell to queue along with which option
								queuedCells.add(cell);
							}
							break; //stop looking through row for that specific option
						}
					}
				}
			}
		}
		
		
		//find cells with option frequency = 1 in blocks
		//for each block
		for( int k = 0; k < n; k++ ) {
			Integer[] optionFreqs = blockFreqs.get(k); //get freqs for options for that block
			for( int i = 1; i <= n; i++ ) { // for each freq
				if( optionFreqs[i] == 1 ) { //if freq = 1
					int[] blockCoords = { k / root, k % root };
					for( Cell cell : board.getBlockFromBCoords(blockCoords[0], blockCoords[1]) ) { // for each cell in block
						if( cell.isEmpty() && cell.getOptions().contains(Integer.valueOf(i))) { //if cell contains F=1 option
							if( !queuedCells.contains(cell) ) {
								queue.add(new QEntry(cell, Integer.valueOf(i))); //add cell to queue along with which option
								queuedCells.add(cell);
							}
							break; //stop looking through row for that specific option
						}
					}
				}
			}
		}
		
		/*
		 * update cell 
		 * -> update options in B,R,C, 
		 * -> update option frequencies for whole board 
		 * -> add new freq-1 cells to queue
		 * repeat until queue empty
		 */
		while( !queue.isEmpty() ) {
			System.out.println("Queue size: " + queue.size());
			
			QEntry entry = queue.poll();
			if( !entry.cell.isEmpty() ) {
				continue;
			}
			Cell currentCell = entry.cell;
			
			System.out.println("Solving cell: (" + currentCell.getRow() + ", " + currentCell.getCol() + 
								") with value: " + entry.option);
			System.out.print("Cell options are: ");
			for( Integer num : currentCell.getOptions() ) {
				System.out.print(num + ",");
			}
			System.out.println();
			
			
			board.setCellValue(currentCell.getRow(), currentCell.getCol(), entry.option);
			hasEffect = true;
			
			//re-populate options for each affected cell
			for( Cell cellInRow : board.getRow(currentCell.getRow())) {
				cellInRow.setOptions( getCellOptions(cellInRow, board) );
			}
			for( Cell cellInCol : board.getColumn(currentCell.getCol())) {
				cellInCol.setOptions( getCellOptions(cellInCol, board) );
			}
			int[] b = board.getBlockOfCell(currentCell.getRow(), currentCell.getCol());
			for( Cell cellInBlock : board.getBlockFromBCoords(b[0], b[1]) ) {
				cellInBlock.setOptions( getCellOptions( cellInBlock, board) );
			}
			
			populateOptions(board);
			
			//recount the frequencies for the whole board
			populateFrequencies(board, rowFreqs, colFreqs, blockFreqs);
			
			//find new cells with freq-1 options and add them to the queue
			
			//for each row
			for( int k = 0; k < n; k++ ) {
				Integer[] optionFreqs = rowFreqs.get(k); //get freqs for options for that row
				for( int i = 1; i <= n; i++ ) { // for each freq
					if( optionFreqs[i] == 1 ) { //if freq = 1
						for( Cell cell : board.getRow(k) ) { // for each cell in row
							if( cell.isEmpty() && cell.getOptions().contains(Integer.valueOf(i))) { //if cell contains F=1 option
								if( !queuedCells.contains(cell) ) {
									queue.add(new QEntry(cell, Integer.valueOf(i))); //add cell to queue along with which option
									queuedCells.add(cell);
									System.out.println("Adding cell: " + cell.toString() );
								}
								break; //stop looking through row for that specific option
							}
						}
					}
				}
			}
			
			//find cells with option frequency = 1 in cols
			//for each column
			for( int k = 0; k < n; k++ ) {
				Integer[] optionFreqs = colFreqs.get(k); //get freqs for options for that col
				for( int i = 1; i <= n; i++ ) { // for each freq
					if( optionFreqs[i] == 1 ) { //if freq = 1
						for( Cell cell : board.getColumn(k) ) { // for each cell in row
							if( cell.isEmpty() && cell.getOptions().contains(Integer.valueOf(i))) { //if cell contains F=1 option
								if( !queuedCells.contains(cell) ) {
									queue.add(new QEntry(cell, Integer.valueOf(i))); //add cell to queue along with which option
									queuedCells.add(cell);
									System.out.println("Adding cell: " + cell.toString() );
								}
								break; //stop looking through row for that specific option
							}
						}
					}
				}
			}
			
			//find cells with option frequency = 1 in blocks
			//for each block
			for( int k = 0; k < n; k++ ) {
				Integer[] optionFreqs = blockFreqs.get(k); //get freqs for options for that block
				for( int i = 1; i <= n; i++ ) { // for each freq
					if( optionFreqs[i] == 1 ) { //if freq = 1
						int[] blockCoords = { k / root, k % root };
						for( Cell cell : board.getBlockFromBCoords(blockCoords[0], blockCoords[1]) ) { // for each cell in block
							if( cell.isEmpty() && cell.getOptions().contains(Integer.valueOf(i))) { //if cell contains F=1 option
								if( !queuedCells.contains(cell) ) {
									queue.add(new QEntry(cell, Integer.valueOf(i))); //add cell to queue along with which option
									queuedCells.add(cell);
									System.out.println("Adding cell: " + cell.toString() );
								}
								break; //stop looking through row for that specific option
							}
						}
					}
				}
			}
			
			
		}
		return hasEffect;
	}
	
	private void populateFrequencies(Board board, List<Integer[]> rowFreqs, List<Integer[]> colFreqs, List<Integer[]> blockFreqs ) {
		//clear previous values in freq tables
		for( int i = 0; i < rowFreqs.size(); i++ ) {
			Integer[] rowTable = rowFreqs.get(i);
			Integer[] colTable = colFreqs.get(i);
			Integer[] blockTable = blockFreqs.get(i);
			for( int j = 0; j < rowTable.length; j++ ) {
				rowTable[j] = 0;
				colTable[j] = 0;
				blockTable[j] = 0;
			}
		}
		
		//count up how many times each option appears for each row, col, block
		for( int r = 0; r < board.size(); r++ ) {
			for( int c = 0; c < board.size(); c++ ) {
				Cell cell = board.getCell(r, c);
				if( cell.isEmpty() ) {
					//for each option in the cell
					for( int option : cell.getOptions() ) {
						//add to frequency for row
						rowFreqs.get(r)[option]++;
						//add to frequency for col
						colFreqs.get(c)[option]++;
						//add to frequency for block
						int root = (int) Math.sqrt( (double) board.size() );
						int k = root * board.getBlockOfCell(r, c)[0] + board.getBlockOfCell(r, c)[1];
						blockFreqs.get(k)[option]++;
					}
				}
			}
		}
	}

}
