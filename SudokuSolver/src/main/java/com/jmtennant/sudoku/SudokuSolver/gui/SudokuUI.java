/**
 * 
 */
package com.jmtennant.sudoku.SudokuSolver.gui;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import com.jmtennant.sudoku.SudokuSolver.algorithm.Solver;
import com.jmtennant.sudoku.SudokuSolver.core.Board;
import com.jmtennant.sudoku.SudokuSolver.util.SudokuBoardReader;

import edu.ncsu.csc316.dsa.list.List;

/**
 * UI Class to visualize sudoku solver
 */
@SuppressWarnings("serial")
public class SudokuUI extends JFrame {
	private JPanel boardPanel;
	private String boardFile = ""; //TODO: Put file here
	private Board board;
	private final int boardSize;
	private JPanel[][] cellPanels;
	
	
	public SudokuUI() {
		//build the board object from input
		try {
			board = SudokuBoardReader.readBoardCoordinateFormat(boardFile);
		} catch ( IOException e ) {
			System.out.println("Invalid input in file: " + boardFile);
			System.exit(1);
		}
		boardSize = board.size();
		//set UI variables
		setTitle("Sudoku Board: " + boardSize + "x" + boardSize);
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create a JPanel with GridLayout for the Sudoku Board
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(boardSize, boardSize));
		cellPanels = new JPanel[boardSize][boardSize];
		
		//add panels for each cell to show options and such
		for( int i = 0; i < boardSize * boardSize; i++) {
			JPanel cellPanel = createCellPanel();
			boardPanel.add(cellPanel);
		}
		
		add(boardPanel);
		setVisible(true);
	}
	
	private JPanel createCellPanel() {
		JPanel cellPanel = new JPanel();
		int sqrt = (int) Math.sqrt( (double) boardSize);
		cellPanel.setLayout(new GridLayout(sqrt, sqrt));
		cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//add smaller labels for the options
		for( int i = 1; i <= boardSize; i++) {
			JLabel optionLabel = new JLabel(String.valueOf(i));
			optionLabel.setFont( new Font("Arial", Font.PLAIN, 10));
			optionLabel.setHorizontalAlignment(SwingConstants.CENTER);
			optionLabel.setVisible(false);
			cellPanel.add(optionLabel);
		}
		
		return cellPanel;
	}
	
	/**
	 * Update UI to show options for a given cell
	 * @param row which row the cell is in
	 * @param col which column the cell is in
	 */
	public void updateCellOptions( int row, int col ) {
		JPanel cellPanel = cellPanels[row][col]; //get panel for this cell
		List<Integer> options = board.getCell(row, col).getOptions(); //get options
		Component[] labels = cellPanel.getComponents(); //get option labels
		
		for( int i = 0; i < labels.length; i++) {
			JLabel optionLabel = (JLabel) labels[i];
			boolean containsI = false;
			for( int j : options ) {
				if( j == i + 1 ) {
					containsI = true;
				}
			}
			if( containsI ) {
				optionLabel.setVisible(true);
			} else {
				optionLabel.setVisible(false);
			}
		}
	}
	
	public void updateCellValue(int row, int col, int value) {
		JPanel cellPanel = cellPanels[row][col];
		cellPanel.removeAll(); //clear the options grid
		
		JLabel valueLabel = new JLabel(String.valueOf(value));
		valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
		valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cellPanel.add(valueLabel);
		cellPanel.revalidate();
		cellPanel.repaint();
	}
	
	public void updateBoard() {
		for( int row = 0; row < boardSize; row++ ) {
			for( int col = 0; col < boardSize; col++ ) {
				if( board.getCell(row, col).isEmpty() ) {
					updateCellOptions(row, col);
				} else {
					updateCellValue(row, col, board.getCellValue(row, col));
				}
			}
		}
	}
	
	public void runSolver(Solver solver) {
		new Thread(() -> {
			solver.solveBoard(board);
		}).start();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(SudokuUI::new);
	}
	
}

