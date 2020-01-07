/**
* Date: June 15, 2018
*
* Purpose: 	Creates the required elements of the game and initializes the game logic class.
*
* Functions: 
* 
* 	Threes_Panel() - Constructor for the panel which creates and initializes all of the buttons
* 					 labels for the game
* 
* 	newGame() - Resets everything to the default start for a new game
* 
* 	isGameOver() - checks if the game over using the logic class and executes actions when the game is over
* 
* 	update() - Updates the JLabel board based on the the logic array board 
* 
* 	colorCheck() - Verifies and changes the colors of the number tiles
* 
* 	keyPressed() - Detects arrow key press and sends directions to the logic class also update the board after each move
* 
*   enableListeners() - Enables key inputs
*	
*	disableListeners() - Disables key inputs
*
*	actionPerformed() - Detects when new game or exit button is pressed and excutes following
*						actions for either button
* 		 	
*/
//package Threes;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Threes_Panel extends JPanel implements KeyListener, ActionListener {

	private JButton exitB, resetButton, overNewGame, overExit;
	private JLabel nextLabel, arrayBack, newGame, exit, next, nextLayout, overBackground, gameOver, overScoreNum;
	private JLabel[][] label;

	private static int bWidth = 90;
	private static int bHeight = 120;
	private static int spaceCount = 1;

	Threes_Logic game;

	//Constructor
	public Threes_Panel() {

		game = new Threes_Logic();

		setFocusable(true);
		setLayout(null); // set the layout for the panel to not have a layout manager

		//Game Over label when the game is over
		gameOver = new JLabel("Game Over");
		gameOver.setBounds(132, 120, 400, 200);
		gameOver.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
		add(gameOver);

		//Label to display the final score of the player
		overScoreNum = new JLabel("", SwingConstants.CENTER);
		overScoreNum.setBounds(200, 195, 138, 200);
		overScoreNum.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		add(overScoreNum);

		//Exit button for the end game screen
		overExit = new JButton("Exit");
		overExit.setBackground(new Color(217, 217, 217));
		overExit.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		overExit.setLocation(197, 430);
		overExit.setSize(140, 50);
		add(overExit);

		//New game button for the end game screen
		overNewGame = new JButton("New Game");
		overNewGame.setBackground(new Color(217, 217, 217));
		overNewGame.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		overNewGame.setLocation(197, 360);
		overNewGame.setSize(140, 50);
		overNewGame.setFocusable(false);
		add(overNewGame);

		//Transparent background for the game over screen
		overBackground = new JLabel("");
		overBackground.setBounds(0, 0, 1080, 1920);
		overBackground.setOpaque(true);
		overBackground.setBackground(new Color(255, 255, 255, 200));
		add(overBackground);

		//Label for the next tile 
		nextLabel = new JLabel("Next");
		nextLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		nextLabel.setBounds(242, 100, 240, 45);
		add(nextLabel);

		//Background for the next tile array uses different color
		nextLayout = new JLabel("");
		nextLayout.setBounds(243, 32, 40, 55);
		nextLayout.setOpaque(true);
		nextLayout.setBackground(new Color(155, 255, 55));
		add(nextLayout);

		//Label to show the next incoming tile
		next = new JLabel("");
		next.setBounds(228, -15, 70, 120);
		next.setOpaque(true);
		next.setBackground(new Color(187, 217, 221));
		add(next);

		// Create a JLabel array for the game board
		label = new JLabel[4][4];
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				label[row][col] = new JLabel("");
				label[row][col].setBounds((91 + (col * bWidth)), (170 + (row * bHeight)), bWidth - 10, bHeight - 10);
				label[row][col].setOpaque(true);
				label[row][col].setBackground(new Color(187, 217, 221));
				label[row][col].setFont(new Font("Comic Sans MS", Font.BOLD, 55));
				add(label[row][col]);
			}
		}

		// Create array background
		arrayBack = new JLabel("");
		arrayBack.setBounds(80, 160, 370, 490);
		arrayBack.setOpaque(true);
		arrayBack.setBackground(new Color(203, 226, 220));
		add(arrayBack);

		// Add exit button
		exitB = new JButton(" ");
		exitB.setBackground(new Color(129, 135, 153));
		exitB.setLocation(395, 40);
		exitB.setSize(70, 50);
		add(exitB);

		//Label for exit button
		exit = new JLabel("Exit");
		exit.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		exit.setBounds(415, 82, 240, 45);
		add(exit);

		// Add reset button to allow for a new game
		resetButton = new JButton("");
		resetButton.setBackground(new Color(129, 135, 153));
		resetButton.setLocation(60, 40);
		resetButton.setSize(70, 50);
		resetButton.setFocusable(false);
		add(resetButton);

		//Label for new game button
		newGame = new JLabel("New Game");
		newGame.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		newGame.setBounds(54, 82, 240, 45);
		add(newGame);

		// Add event listeners for the buttons
		exitB.addActionListener(this);
		resetButton.addActionListener(this);
		overExit.addActionListener(this);
		overNewGame.addActionListener(this);
		
		validate();
		newGame();
	}

	public void newGame() { //new game reset method
		
		//Disables the game over screen
		overBackground.setVisible(false);
		overExit.setVisible(false);
		gameOver.setVisible(false);
		overScoreNum.setVisible(false);
		overNewGame.setVisible(false);

		//Disables listeners until update is done, then calls the logic reset and updates the game
		disableListerners();
		game.newGame();
		update();
		colorCheck();
		repaint();
		enableListeners();
	}

	public void isGameOver() {
		//If the game is over show the game over screen
		if (game.isGameOver()) {
			//Enables the game over screen and updates the score
			overBackground.setVisible(true);
			overExit.setVisible(true);
			gameOver.setVisible(true);
			overScoreNum.setVisible(true);
			overNewGame.setVisible(true);
			overScoreNum.setText("" + game.getScore());
		}
	}

	public void colorCheck() { //Method to check all the color of each tile
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				//If the tile is empty the color is reset to the default color
				if (label[row][col].getText().trim().equals("")) {
					label[row][col].setBackground(new Color(187, 217, 221));
				//If the tile is equal to 1 the tile color is changed to blue and the number to white
				} else if (label[row][col].getText().trim().equals("1")) {
					label[row][col].setBackground(new Color(102,204,255));
					label[row][col].setForeground(Color.white);
				//If the tile is equal to 2 the tile color is changed to red and the number to white
				} else if (label[row][col].getText().trim().equals("2")) {
					label[row][col].setBackground(new Color(249, 106, 119));
					label[row][col].setForeground(Color.white);
				//Any other number the color is set to white with a number color of black
				} else {
					label[row][col].setBackground(new Color(254, 255, 255));
					label[row][col].setForeground(Color.black);
				}
			}
		}
	}

	public void update() { //Updates the label array based on logic array
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				//If the logic array is 0 the tiles text is set to empty
				if (game.getArray(row, col) == 0) {
					label[row][col].setText("");
				//For numbers larger than 1 or less then 10 the tile is set to the number and a font of 55
				} else if (game.getArray(row, col) / 10 < 1) {
					label[row][col].setText(" " + game.getArray(row, col));
					label[row][col].setFont(new Font("Comic Sans MS", Font.BOLD, 55));
				//For numbers larger than  10 or less then 100 the tile is set to the number and a font of 38
				} else if (game.getArray(row, col) / 10 >= 1 && game.getArray(row, col) / 10 < 10) {
					label[row][col].setText(" " + game.getArray(row, col));
					label[row][col].setFont(new Font("Comic Sans MS", Font.BOLD, 38));
				//For number equal or bigger than a 100 the tile is set to the number and to a font of 30
				} else if (game.getArray(row, col) / 100 >= 1) {
					label[row][col].setText(" " + game.getArray(row, col));
					label[row][col].setFont(new Font("Comic Sans MS", Font.BOLD, 30));
				}
			}
		}
		//Changes the next tile if it changes 
		if (game.getNext() == 1) { //If 1 the color is blue
			nextLayout.setBackground(new Color(102, 203, 255));
		} else if (game.getNext() == 2) { //If 2 the color is red
			nextLayout.setBackground(new Color(249, 106, 119));
		} else if (game.getNext() == 3) { //If 3 the color is white
			
			nextLayout.setBackground(new Color(254, 255, 255));
		}

		colorCheck(); //Calls for a color check
		repaint(); //Repaints the board
	}


	// *********************************************
	// ***************Input Methods*****************
	// *********************************************

	//Turns on key inputs
	public void enableListeners() { 
		addKeyListener(this);
		this.setRequestFocusEnabled(true);
	}

	//Turns off key inputs
	public void disableListerners() {
		removeKeyListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		// Ask the event which button it represents
		if (e.getActionCommand().equals(" ")) {
			//Closes the game
			System.exit(0);
		}

		if (e.getActionCommand().equals("")) {
			// Call the method to reset the game
			newGame();
		}
		if (e.getActionCommand().equals("Exit")) {
			//Closes the game
			System.exit(0);
		}

		if (e.getActionCommand().equals("New Game")) {
			// Call the method to reset the game
			newGame();
		}
	}

	@Override
	//Fetches key strokes and checks if they arrow keys to go either up, down, left or right
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//If up tell logic class to go up and update the board
		if (code == KeyEvent.VK_UP) {
			System.out.println("UP");
			game.play("UP");
			update();
		}
		//If down tell logic class to go down and update the board
		if (code == KeyEvent.VK_DOWN) {
			System.out.println("DOWN");
			game.play("DOWN");
			update();
		}
		//If left tell logic class to go left and update the board
		if (code == KeyEvent.VK_LEFT) {
			System.out.println("LEFT");
			game.play("LEFT");
			update();
		}
		//If right tell logic class to go right and update the board
		if (code == KeyEvent.VK_RIGHT) {
			System.out.println("RIGHT");
			game.play("RIGHT");
			update();
		}
		repaint(); //Repaint the panel
		isGameOver(); //Check if the game is over
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	// *******************************************
	// *******************************************
	// *******************************************
}
