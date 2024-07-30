package com.jmtennant.sudoku.SudokuSolver.core;

/**  
 * Sudoku cell class, a Cell object represents a cell in the sudoku board and holds the element in that cell
 *  with more fields to be added as necessary
 *  
 *  @author Jacob Tennant
 *  
 */
public class Cell {
    /** element held by the sudoku cell, represented by an integer for our purposes 
     *  if element is -1, then cell is empty
     */
	private int element; 

	/**
	 * Constructor that creates an empty cell
	 */
	public Cell() {
		this(-1);
	}
	
	/**
	 * Constructor
	 * @param element
	 */
	public Cell(int element) {
		super();
	    setElement(element);
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
    
	
	
}
