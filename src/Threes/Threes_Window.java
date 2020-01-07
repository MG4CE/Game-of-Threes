/**
* Date: June 15, 2018
*
* Purpose: The basic frame of the program defining the start size of the program.
*
* Functions: 
* 
* 	Threes_Window() - Constructor to define the  main frame of the program
* 				   	  and define the panel which houses everything.   	 	
*/

//package Threes;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Container;

public class Threes_Window extends JFrame{

	// Set up constants for width and height of frame 
	static final int WIDTH = 550; 
	static final int HEIGHT = 750;
	
	public Threes_Window(String title) { 
	   	// Set the title of the frame, must be before variable declarations 
	   	super(title);
			   
	   	Threes_Panel basicPanel; 
	   	Container container;
	   	
	   	// Instantiate and add the SimplePanel to the frame 
	   	basicPanel = new Threes_Panel(); 
	   	basicPanel.setBackground(Color.getHSBColor(0, 0, 0.98f)); 
	   	container = getContentPane();
	    setLocationByPlatform(true);
	   	container.add(basicPanel);
	   	container.validate();
	   	
	} 
	    
	public static void main(String args[]) { 
		// Instantiate a FirstApplication object so you can display it 
		Threes_Window frame =  new Threes_Window("Threes!"); 
	    frame.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		
	    // Set the size of the application window (frame) 
	    frame.setLocation(0,0);
		frame.setSize(WIDTH, HEIGHT);
			  
	    frame.setVisible(true); // Show the application (frame) 
	} 
}

