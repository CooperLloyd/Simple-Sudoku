import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.StringBuilder;

public class SimpleSudoku {



	/**
	 * Returns a list of length 9 containing the digits from the specified row in
	 * the specified puzzle.
	 *
	 * @param n      the row index
	 * @param puzzle a Sudoku puzzle
	 * @return a list of length 9 containing the digits from the specified row in
	 *         the specified puzzle
	 */
	public static List<Integer> row(int n, Puzzle puzzle) {
		List<Integer> row = new ArrayList<>();
		for (int i = 0; i < Puzzle.SIZE; i++) {
			row.add(puzzle.get(n, i));
		}
		return row;
	}
	/**
	 * Returns a list of length 9 containing the digits from the specified column in
	 * the specified puzzle.
	 *
	 * @param column_number: he column index
	 * @param puzzle a Sudoku puzzle
	 * @return a list of length 9 containing the digits from the specified column in
	 *         the specified puzzle
	 */
	public static List<Integer> col(int column_number, Puzzle puzzle) {
		List<Integer> col = new ArrayList<>();
		for (int i = 0; i < Puzzle.SIZE; i++) {
			col.add(puzzle.get(i, column_number));
		}
		return col;
	}

	public static List<Integer> block(int block_number, Puzzle puzzle) {

		List<Integer> block = new ArrayList<>();

		int b = block_number;
		int row_number;
		int column_number;

		if (b == 0 || b == 1 || b == 2){
			row_number = 0;
			column_number = b * 3;
		}
		else if (b == 3 || b == 4 || b == 5){
			row_number = 3;
			column_number = (b % 3) * 3;
		}
		else {
			row_number = 6;
			column_number = (b % 3) * 3;
		}
		for (int i = row_number; i < row_number + 3; i++) {

			for (int x = column_number; x < column_number + 3; x++){
		
				block.add(puzzle.get(i, x));
			}		
		}
		return block;
	}

	/**
	 * Reads a puzzle from the file with specified file name.
	 *
	 * @param fileName the puzzle file name
	 * @return a Puzzle object reference containing the digits of the puzzle
	 */
	public static Puzzle read_puzzle(String fileName) {
		Puzzle p = new Puzzle();
		InputStream stream = SimpleSudoku.class.getResourceAsStream(fileName);
		Scanner scanner = new Scanner(stream);
		for (int i = 0; i < Puzzle.SIZE*Puzzle.SIZE; i++) {
			int s = scanner.nextInt();
			p.addValue(s);
		}
		scanner.close();
		return p;
	}
	/**
	 * Takes a completed puzzle and prints it. Creates a string containing
	 *  all elements in each row then adds the string to an arrayList.
	 * @param puzzle
	 */
	public static void print_puzzle(Puzzle puzzle){
		List<String> finalArr = new ArrayList<>();
		for(int i=0; i<9; ++i ) {
			StringBuilder sb = new StringBuilder();
			for(int j=0; j<9; ++j ) {
				String val = String.valueOf(puzzle.get(i, j));
				sb.append(val + " ");
			}
			String result = sb.toString();
			finalArr.add(result);
		}
		for(String row: finalArr ) {
			System.out.println(row);
		}
	}
	
	public static boolean valid(List<Integer> arr_to_check) {
		List<Integer> a = arr_to_check;
		
		int count = 0;
		boolean result = false;
		
		for(int i=1; i<Puzzle.SIZE+1; ++i) {
			if(a.contains(i)) {
				count++;
			}
			else {
				result = false;
			}
		}
		if(count==9) {
			result = true;
		}
		return result;	
	}
	/**checks if rows of puzzle are valid. 
	 * 
	 * @param row
	 * @param puzzle
	 * @return array of indexes values of rows that aren't valid
	 */
	public static List<Integer> test_rows(Puzzle puzzle) {
		
		List<Integer> not_valid_rows = new ArrayList<>();
		List<Integer> row = new ArrayList<>();
		
	
		for(int i=0; i<Puzzle.SIZE; i++) {
			row = SimpleSudoku.row(i, puzzle);
			if(!SimpleSudoku.valid(row)) {
				not_valid_rows.add(i);
			}
		}
		return not_valid_rows;
	}
	
	public static List<Integer> test_columns(Puzzle puzzle) {
		
		List<Integer> not_valid_cols = new ArrayList<>();
		List<Integer> col = new ArrayList<>();
		
	
		for(int i=0; i<Puzzle.SIZE; i++) {
			col = SimpleSudoku.col(i, puzzle);
			if(!SimpleSudoku.valid(col)) {
				not_valid_cols.add(i);
			}
		}
		return not_valid_cols;
	}
	public static List<Integer> test_blocks(Puzzle puzzle) {
		
		List<Integer> not_valid_blocks = new ArrayList<>();
		List<Integer> block = new ArrayList<>();
		
	
		for(int i=0; i<Puzzle.SIZE; i++) {
			block = SimpleSudoku.block(i, puzzle);
			if(!SimpleSudoku.valid(block)) {
				not_valid_blocks.add(i);
			}
		}
		return not_valid_blocks;
	}
		
	public static Puzzle puzzle_solver(Puzzle puzzle) {
		
		int val = 0;
		List<Integer> c = col(SimpleSudoku.test_columns(puzzle).get(0), puzzle);
		
		for(int i=1; i<Puzzle.SIZE+1; ++i) {
			if(!c.contains(i)) {
				val = (i);
			}
		}
	puzzle.setMissingTo(val);
		return puzzle;
	}
		

	public static void main(String[] args) {
		Puzzle puzzle;
		if (args.length > 0) {
			puzzle = read_puzzle(args[0]);
		}
		else {
			puzzle = read_puzzle("puzzle01" + ".txt"); //change puzzle here
			if(puzzle.hasMissingValue()) {
				System.out.println("Puzzle solved" + "\n");
				print_puzzle(puzzle_solver(puzzle));
				System.exit(0);
			}
			else {
				if(test_rows(puzzle).size()== 0 && test_columns(puzzle).size()== 0
						&& test_blocks(puzzle).size()== 0) {
					System.out.println("The puzzle is a valid solution" + "\n");
					print_puzzle(puzzle);
					System.exit(0);
				}
				else {
					System.out.println("Index of Row(s) containing error" + test_rows(puzzle) + "\n");
					System.out.println("Index of Column(s) containing error" + test_columns(puzzle) + "\n");
					System.out.println("Index of Block(s) containing error" + test_blocks(puzzle) + "\n");
					System.exit(0);
				}
			}
		}
	}
}
