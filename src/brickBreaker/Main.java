package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		//creating the JFrame
		JFrame obj = new JFrame();
		//creating the game on the frame
		Gameplay gamePlay = new Gameplay();
		//setting the dimensions on the frame
		obj.setBounds(10,10,700,600);
		obj.setTitle("Breakout Ball");
		//not letting the frame be rezizable to the user
		obj.setResizable(false);
		//showing the frame
		obj.setVisible(true);
		//when a user hits close on the frame
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//adding the game to the frame
		obj.add(gamePlay);
	}

}
