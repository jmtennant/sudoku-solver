package com.jmtennant.sudoku.SudokuSolver.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Board Object represents a sudoku board of length and width n such that the board contains 
 * n x n cells. Methods for access and mutation will be added as required by the different 
 * algorithms I develop to try and tackle this problem. 
 */
public class Board {
	private Cell[][] cells; //rows, columns
	private int size;
	
	/**
	 * Constructor for Sudoku Board
	 * @param size
	 */
	public Board(int size) {
		super();
		//check if size is a perfect square
		double sqrt = Math.sqrt( (double) size);
		if( Math.floor(sqrt) != sqrt ) {
			throw new IllegalArgumentException("Invalid Board size: " + size + " is not a perfect square");
		}
		this.size = size;
		this.cells = new Cell[this.size][this.size]; //instantiate array
		//instantiate each element in array
		for( int i = 0; i < this.cells.length; i++ ) {
			for( int j = 0; j < this.cells[i].length; j++ ) {
				this.cells[i][j] = new Cell();
				this.cells[i][j].setRow(i);
				this.cells[i][j].setCol(j);
			}
		}
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
	
	/**
	 * Validater function for cell value, checks if value within bounds for board
	 * @param value value being checked
	 * @throws IllegalArgumentException if out of bounds for board (not between 0 and n, incl.)
	 */
	public void validateCellValue( int value ) {
		if( value < 0 || value > this.size ) {
			throw new IllegalArgumentException("Value " + value + " out of bounds for range 1 to " + this.size );
		}
	}
	
	public Cell getCell( int row, int col ) {
		validateCoordinates(row, col);
		return this.cells[row][col];
	}
	
	
	/**
	 * Setter for cell 
	 * @param row Row coordinate of target cell
	 * @param col Column coordinate of target cell 
	 * @param value Value to set in cell
	 * @return previous value held by cell
	 */
	public int setCellValue( int row, int col, int value ) {
		validateCoordinates( row, col );
		validateCellValue(value);
		
		Cell cell = this.cells[row][col];
		int oldValue = cell.getElement();
		cell.setElement(value);
		cell.setRow(row);
		cell.setCol(col);
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
    public int[] getBlockOfCell( int row, int col ) {
    	validateCoordinates( row, col );
    	int[] output = new int[2];
    	output[0] = row / (int) Math.sqrt( (double) this.size ); //get x value of block (0,1,2) are in block 0 
    	output[1] = col / (int) Math.sqrt( (double) this.size ); //get y value of block (3,4,5) in block 1, etc
    	return output;
    }
    
    /**
     * Gets the cells from the target block in a list from Left to Right, Top to Bottom
     * @param x row that block is in (0 to sqrt(n)-1, incl.)
     * @param y column that block is in (0 to sqrt(n)-1, incl.)
     * @return an ArrayList of Cells from the block
     */
    public List<Cell> getListCellsFromBlock( int x, int y){
    	List<Cell> output = new ArrayList<Cell>(size);
    	int b = (int) Math.sqrt( (double) this.size );
    	for( int i = 0; i < b; i++) { // for sqrt(n) rows
    		int xCoord = x*b + i; // calculate x coordinate
    		for( int j = 0; j < b; j++ ) { // for sqrt(n) cols
    			int yCoord = y*b + j; // calculate y coordinate
    			output.addLast( cells[xCoord][yCoord] );
    		}
    	}
    	return output;
    }
    
    /**
     * Gets the int values from the cells in target block in array from left to right, top to bottom
     * @param x block row that target block is in (0 to sqrt(n)-1, incl.)
     * @param y block column that target block is in (0 to sqrt(n)-1, incl.)
     * @return array of int values representing values of cells in the block
     */
    public int[] getBlockValues( int x, int y ) {
    	int[] output = new int[this.size];
    	int b = (int) Math.sqrt( (double) this.size );
    	int k = 0; //location in output array
    	for( int i = 0; i < b; i++) { // for sqrt(n) rows
    		int xCoord = x*b + i; // calculate x coordinate
    		for( int j = 0; j < b; j++ ) { // for sqrt(n) cols
    			int yCoord = y*b + j; // calculate y coordinate
    			output[k] = this.cells[xCoord][yCoord].getElement();
    			k++; // move to next spot in output array
    		}
    	}
    	return output;
    }
    
    /**
     * Getter for the whole board
     * @return 2D array of cells
     */
    public Cell[][] getBoard(){
    	return cells;
    }
    
    /**
     * Sets cell to empty, delegates to setCellValue
     * @param row target row
     * @param col target column
     * @return value replaced by 0
     */
    public int setCellToEmpty( int row, int col ) {
    	return setCellValue(row, col, 0);
    }

    /**
     * Getter for size dimension
     * @return number of cells in a row, in a column, and in a block
     */
	public int size() {
		return this.size;
	}
	
	/** 
	 * Getter for all the cells in a row
	 * @param i row coordinate
	 * @return array of cells in column
	 */
	public Cell[] getRow(int i) {
		Cell[] output = new Cell[this.size];
		for( int j = 0; j < this.size; j++ ) {
			output[j] = this.cells[i][j];
		}
		return output;
	}
	
	/** 
	 * Getter for all the cells in a column
	 * @param j column coordinate
	 * @return array of cells in column
	 */
	public Cell[] getColumn(int j) {
		Cell[] output = new Cell[this.size];
		for( int i = 0; i < this.size; i++ ) {
			output[i] = this.cells[i][j];
		}
		return output;
	}
	
	/** 
	 * Getter for all the cells in a block
	 * @param row cell row coordinate (0 through size - 1)
	 * @param col cell col coordinate (0 through size - 1)
	 * @return array of cells in block
	 */
	public Cell[] getBlock( int row, int col ) {
		int x = getBlockOfCell(row, col)[0];
		int y = getBlockOfCell(row, col)[1];		
		
		return getBlockFromBCoords( x, y );
    	
    }
	
	public Cell[] getBlockFromBCoords(int x, int y) {
		Cell[] output = new Cell[this.size];
    	int b = (int) Math.sqrt( (double) this.size );
    	int k = 0; //location in output array
    	for( int i = 0; i < b; i++) { // for sqrt(n) rows
    		int xCoord = x*b + i; // calculate x coordinate
    		for( int j = 0; j < b; j++ ) { // for sqrt(n) cols
    			int yCoord = y*b + j; // calculate y coordinate
    			output[k] = this.cells[xCoord][yCoord];
    			k++; // move to next spot in output array
    		}
    	}
    	return output;
	}
	
	public List<Integer> setCellOptions(int row, int col, List<Integer> options){
		validateCoordinates(row, col);
		Cell cell = this.cells[row][col];
		List<Integer> oldOptions = cell.getOptions();
		cell.setOptions(options);
		return oldOptions;
	}
	
	public void print() {
		StringBuilder builder = new StringBuilder();
		for( Cell[] row : this.cells ) {
			for( Cell cell : row ) {
				builder.append(cell.getElement());
				builder.append(",");
			}
			builder.append("\n");
		}
		System.out.print(builder.toString());
	}

	
}
