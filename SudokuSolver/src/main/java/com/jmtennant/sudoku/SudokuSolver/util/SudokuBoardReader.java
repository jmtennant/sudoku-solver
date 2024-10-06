package com.jmtennant.sudoku.SudokuSolver.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.jmtennant.sudoku.SudokuSolver.core.Board;

/**
 * Input Reader class reads files and generates {@link com.jmtennant.sudoku.SudokuSolver.core.Board} objects accordingly.
 * 
 * @author Jacob Tennant
 * @version August 6, 2024
 */
public class SudokuBoardReader {
	
	/**
	 * Reads a file in the Coordinate-Value format, where first line indicates side length of sudoku board,
	 * and then subsequent lines indicate row, column, and value for starting values of the Board, comma separated.
	 * 
	 * @param file Input file to generate board off of
	 * @return Board generated from input file
	 * @throws FileNotFoundException if argued file doesn't exist
	 * @throws IOException if argued file is empty
	 */
	public static Board readBoardCoordinateFormat( String file ) throws FileNotFoundException, IOException {
		
		try( FileReader freader = new FileReader(file);
			 BufferedReader reader = new BufferedReader( freader );){
			
			String line = reader.readLine(); // read first line	
			if(line == null) {
				throw new IOException("File is empty");
			}
			int size = Integer.parseInt(line.trim()); // extract value to get board size
			Board board = new Board(size);
			
			while( (line = reader.readLine())  != null ) { // for each subsequent line
				String[] values = line.split(","); // split by commas
				if( values.length != 3 ) {
					throw new IOException("Invalid file format found at: " + line);
				}
				//set cell value with extracted row, col, and value information from line
				board.setCellValue(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			}
			//return
			return board;
			
		} catch( Exception e ) {	
			throw e;
		}
	}
	
	public static Board readBoardGridFormat( String file ) throws FileNotFoundException, IOException {
		try( FileReader freader = new FileReader(file);
			BufferedReader reader = new BufferedReader( freader );){
				
			String line = reader.readLine(); // read first line	to get size
			if(line == null) {
				throw new IOException("File is empty");
			}
			int size = Integer.parseInt(line.trim()); // extract value to get board size
			Board board = new Board(size);
			int rowTracker = 0;
			while( (line = reader.readLine())  != null && rowTracker < size) { // for each subsequent line
				String[] values = line.split(","); // split by commas
				for( int i = 0; i < values.length; i++ ) {
					board.setCellValue(rowTracker, i, Integer.parseInt(values[i]) ); //add each value to corr. cell
				}
				rowTracker++; //move to next row
			}
			//return
			return board;	
		} catch( Exception e ) {	
			throw e;
		}
	}
}
