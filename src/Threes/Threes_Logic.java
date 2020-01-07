/**
* Date: June 15, 2018
*
* Purpose: Handles the logic behind the game.
*
* Functions: 
* 
* 	Threes_Logic() - Constructor that initializes both logic arrays
* 
*  	newGame() - Resets all the fields/arrays, randomizes both starting tiles and the next tile
*  
*  	play() - Takes in the direction and moves and merges the tiles based on the direction and puts the next tile based on the direction	 	
*  
*  	nextNumber() - Randomizes number based on the numbers before, gets the location availability and most optimal location for the next number spawn
*  
*  	getNext() - Returns the next number
*  
*  	isGameOver() - Checks if the game is over
*  
*  	isSame() - Compares both built in logic arrays
*  
*  	getArray() - Returns the value in the logicArray using the index	
*  
*  	getScore() - Returns score
*  
*  	setCheckArray() - Sets the checkArray to the logicArray
*  
*/
//package Threes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Threes_Logic {

	private int logicArray[][];
	private int checkArray[][];
	private static int size = 4;
	private int score;
	private int nextNumber;
	private int lastMergeX;
	private int lastMergeY;
	private int lastMerge;
	private int nextInRow;
	private int nextBefore;

	public Threes_Logic() {
		logicArray = new int[size][size];
		checkArray = new int[size][size];
	}

	//Resets everything and spawns new default tiles
	public void newGame() {
		
		//Reset all fields and array
		score = 0;
		lastMergeX = -1;
		lastMergeY = -1;
		lastMerge = 0;
		nextInRow = 0;
		nextBefore = 0;
		logicArray = new int[size][size];

		//Instantiate randomizer
		Random rand = new Random();

		int rowS;
		int colS;
		int temp1;
		int temp2;

		//Randomize location for 1 Tile
		rowS = rand.nextInt(4);
		colS = rand.nextInt(4);

		logicArray[rowS][colS] = 1;

		temp1 = rowS;
		temp2 = colS;

		//Makes sure both locations are not the same
		while (temp1 == rowS && temp2 == colS) {
			rowS = rand.nextInt(4);
			colS = rand.nextInt(4);
		}

		//Sets location for tile 2
		logicArray[rowS][colS] = 2;

		//Randomizes next tile
		nextNumber("NEW");
	}

	//Handles the moving and merge of the tiles
	public void play(String move) {

		//Set checkArray for comparison
		setCheckArray();

		if (move == "UP") { //Does work for going up
			//Looks at the tiles from top to bottom and from left to right to ensure tiles only move once
			for (int row = 0; row < 4; row++) {
				for (int col = 0; col < 4; col++) {
					//Sets a limit for how high the tiles can go so not less than 0 for the rows
					if (logicArray[row][col] > 0 && row <= 3 && row > 0) {
						//If there is a space the tile is moved up
						if (logicArray[row - 1][col] == 0) {
							logicArray[row - 1][col] = logicArray[row][col];
							logicArray[row][col] = 0;
							//Handles all merging, except for 1 & 2 tiles 
						} else if (logicArray[row][col] == logicArray[row - 1][col] && logicArray[row][col] != 1
								&& logicArray[row][col] != 2) {
							logicArray[row - 1][col] = logicArray[row][col] + logicArray[row - 1][col];
							logicArray[row][col] = 0;
							score += logicArray[row - 1][col];
							//Takes the highest value merged tile for next tile spawn
							if (lastMerge < logicArray[row - 1][col]) {
								lastMergeX = col;
								lastMergeY = row;
								lastMerge = logicArray[row][col];
							}
						//Handles Merging of 1 and 2 tiles 
						} else if ((logicArray[row][col] == 1 && logicArray[row - 1][col] == 2)
								|| (logicArray[row][col] == 2 && logicArray[row - 1][col] == 1)) {
							logicArray[row - 1][col] = 3;
							logicArray[row][col] = 0;
							score += 3;
							//Changes last merge if there no higher value update the last merge location
							if (lastMerge < 3) {
								lastMergeX = col;
								lastMergeY = row;
								lastMerge = logicArray[row][col];
							}
						}
					}
				}
			}
			
			
			//If the board has changed spawn in a new tile for going up
			if (isSame() == false)
				nextNumber("UP");
			
			//Reset last merge values
			lastMerge = 0;
			lastMergeX = -1;
			lastMergeY = -1;
			

		} else if (move == "DOWN") {  //Does work for going down
			//Looks at the tiles from bottom to top and from left to right to ensure tiles only move once
			for (int row = 3; row >= 0; row--) {
				for (int col = 0; col < 4; col++) {
					//Sets a limit for how high the tiles can go so not greater than 3 for the rows
					if (logicArray[row][col] > 0 && row < 3 && row >= 0) {
						//If there is a space the tile is moved down
						if (logicArray[row + 1][col] == 0) {
							logicArray[row + 1][col] = logicArray[row][col];
							logicArray[row][col] = 0;
							//Handles all merging, except for 1 & 2 tiles 
						} else if (logicArray[row][col] == logicArray[row + 1][col] && logicArray[row][col] != 1
								&& logicArray[row][col] != 2) {
							logicArray[row + 1][col] = logicArray[row][col] + logicArray[row + 1][col];
							logicArray[row][col] = 0;
							score += logicArray[row + 1][col];
							//Takes the highest value merged tile for next tile spawn
							if (lastMerge < logicArray[row + 1][col]) {
								lastMergeX = col;
								lastMergeY = row;
								lastMerge = logicArray[row][col];
							}
							//Handles Merging of 1 and 2 tiles 
						} else if ((logicArray[row][col] == 1 && logicArray[row + 1][col] == 2)
								|| (logicArray[row][col] == 2 && logicArray[row + 1][col] == 1)) {
							logicArray[row + 1][col] = 3;
							logicArray[row][col] = 0;
							score += 3;
							//Changes last merge if there no higher value update the last merge location
							if (lastMerge < 3) {
								lastMergeX = col;
								lastMergeY = row;
								lastMerge = logicArray[row][col];
							}
						}
					}
				}
			}

			//If the board changed spawn in new tile for going down
			if (isSame() == false)
				nextNumber("DOWN");
			
			//Reset last merge values
			lastMerge = 0;
			lastMergeX = -1;
			lastMergeY = -1;
			
		} else if (move == "LEFT") { //Does work for going left
			//Looks at the tiles from top to bottom and from left to right to ensure tiles only move once
			for (int row = 0; row < 4; row++) {
				for (int col = 0; col < 4; col++) {
					//Sets a limit for how high the tiles can go so not less than 0 for the columns
					if (logicArray[row][col] > 0 && col <= 3 && col > 0) {
						//If there is a space the tile is moved to the left
						if (logicArray[row][col - 1] == 0) {
							logicArray[row][col - 1] = logicArray[row][col];
							logicArray[row][col] = 0;
							//Handles all merging, except for 1 & 2 tiles 
						} else if (logicArray[row][col] == logicArray[row][col - 1] && logicArray[row][col] != 1
								&& logicArray[row][col] != 2) {
							logicArray[row][col - 1] = logicArray[row][col] + logicArray[row][col - 1];
							logicArray[row][col] = 0;
							score += logicArray[row][col - 1];
							//Takes the highest value merged tile for next tile spawn
							if (lastMerge < logicArray[row][col - 1]) {
								lastMergeX = col;
								lastMergeY = row;
								lastMerge = logicArray[row][col];
							}
							//Handles Merging of 1 and 2 tiles 
						} else if ((logicArray[row][col] == 1 && logicArray[row][col - 1] == 2)
								|| (logicArray[row][col] == 2 && logicArray[row][col - 1] == 1)) {
							logicArray[row][col - 1] = 3;
							logicArray[row][col] = 0;
							score += 3;
							//Changes last merge if there no higher value update the last merge location
							if (lastMerge < 3) {
								lastMergeX = col;
								lastMergeY = row;
								lastMerge = logicArray[row][col];
							}
						}
					}
				}
			}

			//If the board has changed spawn in new tile for going left
			if (isSame() == false)
				nextNumber("LEFT");
			
			//Reset last merge values
			lastMerge = 0;
			lastMergeX = -1;
			lastMergeY = -1;
			
		} else if (move == "RIGHT") { //Does work for going right
			//Looks at the tiles from top to bottom and from right to left to ensure tiles only move once
			for (int row = 0; row < 4; row++) {
				for (int col = 3; col >= 0; col--) {
					//Sets a limit for how high the tiles can go so not less than 0 for the columns
					if (logicArray[row][col] > 0 && col < 3 && row >= 0) {
						//If there is a space the tile is moved to the right
						if (logicArray[row][col + 1] == 0) {
							logicArray[row][col + 1] = logicArray[row][col];
							logicArray[row][col] = 0;
							//Handles all merging, except for 1 & 2 tiles 
						} else if (logicArray[row][col] == logicArray[row][col + 1] && logicArray[row][col] != 1
								&& logicArray[row][col] != 2) {
							logicArray[row][col + 1] = logicArray[row][col] + logicArray[row][col + 1];
							logicArray[row][col] = 0;
							score += logicArray[row][col + 1];
							//Takes the highest value merged tile for next tile spawn
							if (lastMerge < logicArray[row][col + 1]) {
								lastMergeX = col;
								lastMergeY = row;
								lastMerge = logicArray[row][col];
							}
							//Handles Merging of 1 and 2 tiles 
						} else if ((logicArray[row][col] == 1 && logicArray[row][col + 1] == 2)
								|| (logicArray[row][col] == 2 && logicArray[row][col + 1] == 1)) {
							logicArray[row][col + 1] = 3;
							logicArray[row][col] = 0;
							score += 3;
							//Changes last merge if there no higher value update the last merge location
							if (lastMerge < 3) {
								lastMergeX = col;
								lastMergeY = row;
								lastMerge = logicArray[row][col];
							}
						}
					}
				}
			}
			
			//If the board has changed spawn in new tile for going right
			if (isSame() == false)
				nextNumber("RIGHT");
			
			//Reset last merge values
			lastMerge = 0;
			lastMergeX = -1;
			lastMergeY = -1;
		}
	}

	public void nextNumber(String move) {
		
		Random rand = new Random(); //Initialize random class

		ArrayList<Integer> openBox = new ArrayList<Integer>(); //Initialize array list to use for open space storage

		if (move == "UP") { //If the board went up
			//If the space in the last row is open from the last merge x use it for new tile
			if (lastMergeX != -1 && logicArray[3][lastMergeX] == 0) {
				logicArray[3][lastMergeX] = nextNumber;
				//Anything else randomize location
			}else {
				//Define main row
				int row = 3;
				//Find all open spaces on the row and store them
				for (int col = 0; col < 4; col++) {
					if (logicArray[row][col] == 0) {
						openBox.add(col);
					}
				}
				//If there is one space use that space
					if (openBox.size() == 1) { 
						logicArray[row][openBox.get(0)] = nextNumber;
						//Anything else randomize between those open spaces
					} else {
						int location = rand.nextInt(openBox.size() - 1);
						logicArray[row][openBox.get(location)] = nextNumber;
					}
				}
		}
		
		if (move == "DOWN") { //If the board went down
			//If the space in the first row is open from the last merge x use it for new tile
			if (lastMergeX != -1 && logicArray[0][lastMergeX] == 0) {
				logicArray[0][lastMergeX] = nextNumber;
				//Anything else randomize location
			} else {
				//Define main row
				int row = 0;
				//Find all open spaces on the row and store them
				for (int col = 0; col < 4; col++) {
					if (logicArray[row][col] == 0) {
						openBox.add(col);
					}
				}
				//If there is one space use that space
					if (openBox.size() == 1) {
						logicArray[row][openBox.get(0)] = nextNumber;
						//Anything else randomize between those open spaces
					} else {
						int location = rand.nextInt(openBox.size() - 1);
						logicArray[row][openBox.get(location)] = nextNumber;
					}
				}
		}
		
		if (move == "LEFT") { //If the board went left
			//If the space in the last col is open from the last merge y use it for new tile
			if (lastMergeY != -1 && logicArray[lastMergeY][0] == 0) {
				logicArray[lastMergeY][0] = nextNumber;
				//Anything else randomize location
			} else {
				//Define main col
				int col = 3;
				//Find all open spaces on the col and store them
				for (int row = 0; row < 4; row++) {
					if (logicArray[row][col] == 0) {
						openBox.add(row);
					}
				}
				//If there is one space use that space
					if (openBox.size() == 1) {
						logicArray[openBox.get(0)][col] = nextNumber;
						//Anything else randomize between those open spaces
					} else {
						int location = rand.nextInt(openBox.size() - 1);
						logicArray[openBox.get(location)][col] = nextNumber;
					}
				}
		}
		
		if (move == "RIGHT") {//If the board went right
			//If the space in the first col is open from the last merge y use it for new tile
			if (lastMergeY != -1 && logicArray[lastMergeY][3] == 0) {
				logicArray[lastMergeY][3] = nextNumber;
			} else {
				//Define main col
				int col = 0;
				//Find all open spaces on the col and store them
				for (int row = 0; row < 4; row++) {
					if (logicArray[row][col] == 0) {
						openBox.add(row);
					}
				}
				//If there is one space use that space
					if (openBox.size() == 1) {
						logicArray[openBox.get(0)][col] = nextNumber;
						//Anything else randomize between those open spaces
					} else {
						int location = rand.nextInt(openBox.size() - 1);
						logicArray[openBox.get(location)][col] = nextNumber;
					}
				}
		}

		//Randomize new number
		nextNumber = rand.nextInt(3) + 1;
		
		//
		//A more predictable equal pattern might have been a better idea for the 1 and 2 tiles
		//
		
		//If 1 number shows up 2 times in a row randomize until its different
		if (nextInRow == 1) {
			while (nextNumber == nextBefore) {
				nextNumber = rand.nextInt(3) + 1;
			}
			nextInRow = 0;
			
			//if the number before is equal to the new number add 1 to the times in a row
		} else if (nextBefore == nextNumber) {
			nextInRow += 1;
			
			//if the number is different then before change nextBefore and reset times in a row 
		} else {
			nextBefore = nextNumber;
			nextInRow = 0;
			
		}
		
		//Set up next tile spawn for the next move on a new game
		if (move == "NEW") {
			//Randomize a tile value from 1-3
			nextNumber = rand.nextInt(3) + 1;
			nextBefore = nextNumber;
			nextInRow = 0;
		}
	}

	//Returns nextNumber
	public int getNext() {
		return nextNumber;
	}

	//Checks if there are no more moves available to see if the game is over
	public boolean isGameOver() {
		//Returns false if there are any open tiles which means you can move
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (logicArray[row][col] == 0)
					return false;
				}
			}
		
		//Checks to see if any tiles can merge (very inefficient method but it works)
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (row - 1 >= 0) { //Checks if it can check above
					//Checks if it can merge with the above tile if they are 1 or 2
					if (logicArray[row][col] == 1 || logicArray[row][col] == 2) {
						if ((logicArray[row - 1][col] == 1 && logicArray[row][col] == 2) || (logicArray[row - 1][col] == 2 && logicArray[row][col] == 1)){
							return false;
						}
					}
					//Checks if they are the same means they can merge
					else if ( logicArray[row - 1][col] == logicArray[row][col]) {
						return false;
					}
				}
				if (row + 1 <= 3) {//Checks if it can check below
					//Checks if it can merge with the tile below if they are 1 or 2
					if (logicArray[row][col] == 1 || logicArray[row][col] == 2) {
						if ((logicArray[row + 1][col] == 1 && logicArray[row][col] == 2) || (logicArray[row + 1][col] == 2 && logicArray[row][col] == 1)){
							return false;
						}
					}
					//Checks if they are the same means they can merge
					else if ( logicArray[row + 1][col] == logicArray[row][col]) {
						return false;
					}
				}
				if (col - 1 >= 0) { //Check if it can check to the left
					//Checks if it can merge with the left tile if they are 1 or 2
					if (logicArray[row][col] == 1 || logicArray[row][col] == 2) {
						if ((logicArray[row][col - 1] == 1 && logicArray[row][col] == 2) || (logicArray[row][col - 1] == 2 && logicArray[row][col] == 1)){
							return false;
						}
					}
					//Checks if they are the same means they can merge
					else if ( logicArray[row][col - 1] == logicArray[row][col]) {
						return false;
					}
				}
				if (col + 1 <= 3) {  //Check if it can check to the right
					//Checks if it can merge with the right tile if they are 1 or 2
					if (logicArray[row][col] == 1 || logicArray[row][col] == 2) {
						if ((logicArray[row][col + 1] == 1 && logicArray[row][col] == 2) || (logicArray[row][col + 1] == 2 && logicArray[row][col] == 1)){
							return false;
						}
					}
					//Checks if they are the same means they can merge
					else if ( logicArray[row][col + 1] == logicArray[row][col]) {
						return false;
					}
				}
			}
		}
		return true; //If all checks are failed the game is over
	}

	//Compares both logic arrays
	public boolean isSame() {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				if (logicArray[row][col] != checkArray[row][col]) {
					return false;
				}
			}
		}
		return true;
	}

	//Return value of array in said location
	public int getArray(int row, int col) {
		return logicArray[row][col];
	}

	//Returns score
	public int getScore() {
		return score;
	}

	//Sets check array equal to logic array
	public void setCheckArray() {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				checkArray[row][col] = logicArray[row][col];
			}
		}
	}

}
