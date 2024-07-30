package com.jmtennant.sudoku.SudokuSolver.core;

public class Board {
	private Cell[][] cells;
	private int size;
	
	  
	
	/**
	 * Constructor for Sudoku Board
	 * @param size
	 */
	public Board(int size) {
		super();
		this.size = size;
		this.cells = new Cell[this.size][this.size];
	}
	
	/**
	 * Validates a set of cell coordinates for the sudoku board
	 * @param row Row value to validate
	 * @param col Column value to validate
	 * @throws IllegalArgumentException if either row or col argument is outside range of board (0 to size-1)
	 */
	public void validateCoordinates( int row, int col ) {
		if( row < 0 || row >= size ) {
			throw new IllegalArgumentException("Row index " + row + " out of bounds for size " + size );
		}
		if( col < 0 || col >= size ) {
			throw new IllegalArgumentException("Column index " + col + " out of bounds for size " + size );
		}
	}
	
	public void validateCellValue( int value ) {
		if( value < 1 || value > this.size ) {
			throw new IllegalArgumentException("Value " + value + " out of bounds for range 1 to " + this.size );
		}
	}
	
	public int setCellValue( int row, int col, int value ) {
		validateCoordinates( row, col );
		validateCellValue(value);
		
		Cell cell = this.cells[row][col];
		int oldValue = cell.getElement();
		cell.setElement(value);
		
		return oldValue;
	}

	/**
	 * Gets the value stored by the cell in the specified location
	 * @param row Row coordinate of target cell
	 * @param col Column coordinate of target cell
	 * @return integer value held by cell
	 */
    public int getCellValue( int row, int col ) {
    	validateCoordinates( row, col );
    	return this.cells[row][col].getElement();
    }
	
    /**
     * Calculates which block the specified cell coordinates reside in
     * Blocks are of q x q where q = sqrt(n), where board is n x n
     * Therefore if starting at 0 for rows & columns, Cell(r,c) is in Block(r/q, c/q)
     * @param row The row in which the specified cell resides
     * @param col The column in which the specified cell resides
     * @return ordered pair (Br, Bc) identifying the block the cell resides in
     */
    public int[] getBlock( int row, int col ) {
    	validateCoordinates( row, col );
    	int[] output = new int[2];
    	output[0] = row / (int) Math.sqrt( (double) this.size ); //get x value of block (0,1,2) are in block 0 
    	output[1] = col / (int) Math.sqrt( (double) this.size ); //get y value of block (3,4,5) in block 1, etc
    	return output;
    }
    
}