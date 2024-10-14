package com.jmtennant.sudoku.SudokuSolver.core;

import java.util.ArrayList;
import java.util.List;

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
	
	private ArrayList<Integer> options;
	
	private boolean isHint;
	
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
	    options = new ArrayList<Integer>();
	    setHint(false); //cell is not a hint cell by default
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
		this.options = new ArrayList<Integer>(newOptions);
	}
    /**
     * Getter for number of options. If cell not empty, returns 0;
     * @return
     */
	public int numOptions() {
		if( this.element == 0 ) {
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
	
	/**
	 * Accessor for empty status
	 * @return true if empty, else false
	 */
	public boolean isEmpty() {
		return this.element == 0;
	}
	
	/**
	 * Accessor for hint status
	 * @return true if is a hint (ie a starting value on board)
	 */
	public boolean isHint() {
		return this.isHint;
	}
	
	public void setHint( boolean isHint ) {
		this.isHint = isHint;
	}
	
	public String toString() {
		String output = "";
		output += "(" + this.row + ", " + this.col + "), val = " + this.element;
		return output;
	}
}
