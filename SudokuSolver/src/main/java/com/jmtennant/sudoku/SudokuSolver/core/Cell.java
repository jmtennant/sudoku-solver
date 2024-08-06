package com.jmtennant.sudoku.SudokuSolver.core;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.dsa.list.List;

/**  
 * Sudoku cell class, a Cell object represents a cell in the sudoku board and holds the element in that cell
 *  with more fields to be added as necessary
 *  
 *  @author Jacob Tennant
 *  @version August 6, 2024
 */
public class Cell {
    /** element held by the sudoku cell, represented by an integer for our purposes 
     *  if element is -1, then cell is empty
     */
	private int element; 
    
	/** Row in board that cell is in */
	private int row;
	/** Column in board that cell is in */
	private int col;
	
	private ArrayBasedList<Integer> options;
	
	/**
	 * Constructor that creates an empty cell
	 */
	public Cell() {
		this(0);
	}
	
	/**
	 * Constructor
	 * @param element
	 */
	public Cell(int element) {
		super();
	    setElement(element);
	    options = new ArrayBasedList<Integer>();
	}

	/**
	 * Getter for element held by cell
	 * @return the element
	 */
	public int getElement() {
		return element;
	}

	/**
	 * Setter for element held by cell
	 * @param element the element to set
	 */
	public void setElement(int element) {
		this.element = element;
	}
    
	/**
	 * Getter for options
	 * @return options list
	 */
	public List<Integer> getOptions(){
		return this.options;
	}
	
	/** 
	 * Adds an option to the cell, delegates to list behavior
	 * @param newOption new option to add
	 */
	public void addOption(int newOption) {
		this.options.addLast(newOption);
	}
	
	public void setOptions( List<Integer> newOptions ) {
		this.options = new ArrayBasedList<Integer>();
		for( int i = 0; i < newOptions.size(); i++) {
			this.options.addLast(newOptions.get(i));
		}
	}
    /**
     * Getter for number of options. If cell not empty, returns 0;
     * @return
     */
	public int numOptions() {
		if( this.element != 0 ) {
			return this.options.size();
		} else {
			return 0;
		}
	}

	/**
	 * Getter for row field
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Setter for row field
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Getter for column field
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Setter for column field
	 * @param col the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	public boolean isEmpty() {
		return this.element == 0;
	}
}
