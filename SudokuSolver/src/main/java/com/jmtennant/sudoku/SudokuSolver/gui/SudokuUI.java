/**
 * 
 */
package com.jmtennant.sudoku.SudokuSolver.gui;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

import com.jmtennant.sudoku.SudokuSolver.algorithm.AbstractSolver;
import com.jmtennant.sudoku.SudokuSolver.algorithm.PruneSolver;
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
	private String boardFile = "C:\\Users\\jacob\\git\\sudoku-solver\\SudokuSolver\\input\\board-G-9-2.txt";
	private Board board;
	private Solver solver;
	private final int boardSize;
	private JPanel[][] cellPanels;
	private static final int FONT_SIZE = 25;
	private static final int OPTION_FONT_SIZE = 10;
	
	
	public SudokuUI() {
		//build the board object from input
		try {
			board = SudokuBoardReader.readBoardGridFormat(boardFile);
			solver = new PruneSolver();
			
			
			//AbstractSolver.populateOptions(board);
			solver.solveBoard(board);
			
		} catch ( IOException e ) {
			System.out.println(e.getMessage());
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
		
		for( int i = 0; i < boardSize; i++) {
			for( int j = 0; j < boardSize; j++ ) {
				JPanel cellPanel = createCellPanel();
				boardPanel.add(cellPanel);
				cellPanels[i][j] = cellPanel;
			}	
		}
		
		add(boardPanel);
		
		setVisible(true);
		updateBoard();
	}
	
	private JPanel createCellPanel() {
		//initialize panels, doing card layout to swap between cards
		JPanel cellPanel = new JPanel();
		JPanel optionPanel = new JPanel();
		CardLayout cardLayout = new CardLayout();
		
		
		//calc sqrt
		int sqrt = (int) Math.sqrt( (double) boardSize);
		//configure cellPanel
		cellPanel.setLayout(cardLayout);
		
		//configure optionPanel
		optionPanel.setLayout( new GridLayout(sqrt, sqrt));
		
		//add smaller labels for the options to optionPanel
		for( int i = 1; i <= boardSize; i++) {
			JLabel optionLabel = new JLabel(String.valueOf(i));
			optionLabel.setFont( new Font("Arial", Font.BOLD, OPTION_FONT_SIZE ));
			optionLabel.setHorizontalAlignment(SwingConstants.CENTER);
			optionLabel.setVerticalAlignment(SwingConstants.CENTER);
			optionPanel.add(optionLabel);
		}
		
		//add big label for the cellPanel
		JLabel valueLabel = new JLabel();
		valueLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add both components to the cellPanel as separate cards
		cellPanel.add(optionPanel, "options");
		cellPanel.add(valueLabel, "value");
		
		//store optionPanel and valueLabel in cellPanel for future access
		cellPanel.putClientProperty("optionPanel", optionPanel);
		cellPanel.putClientProperty("valueLabel", valueLabel);
		cellPanel.putClientProperty("cardLayout", cardLayout);
		
		cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
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
		JPanel optionPanel = (JPanel) cellPanel.getClientProperty("optionPanel"); //get panels and layout
		CardLayout cardLayout = (CardLayout) cellPanel.getClientProperty("cardLayout");
		Component[] labels = optionPanel.getComponents(); //get option labels
		
		//find if options should be shown or not
		for( int i = 0; i < labels.length; i++) {
			JLabel optionLabel = (JLabel) labels[i];
			boolean containsI = false;
			for( int j : options ) {
				if( j == i + 1) {
					containsI = true;
				}
			}
			if( containsI ) {
				optionLabel.setVisible(true);
			} else {
				optionLabel.setVisible(false);
			}
		}
		
		//set visibilities
		cardLayout.show(cellPanel, "options");
		
		cellPanel.revalidate();
		cellPanel.repaint();
	}
	
	public void updateCellValue(int row, int col, int value) {
		JPanel cellPanel = cellPanels[row][col];
		
		
		JPanel optionPanel = (JPanel) cellPanel.getClientProperty("optionPanel");
		JLabel valueLabel = (JLabel) cellPanel.getClientProperty("valueLabel");
		CardLayout cardLayout = (CardLayout) cellPanel.getClientProperty("cardLayout");
		
		if( value != 0 ) {
			valueLabel.setText(String.valueOf(value));
			cardLayout.show(cellPanel, "value");
		} else {
			cardLayout.show(cellPanel, "options");
		}
		
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

