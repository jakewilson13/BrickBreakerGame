package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;
	//key listener detects the arrow keys when you press when moving the slider
	//action listener for moving the ball
//jPanel is a lightweight container
public class Gameplay extends JPanel implements KeyListener, ActionListener {
	
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	//controls speed of the ball
	private Timer timer;
	private int delay = 4;
	//player on x axis
	private int playerX = 310;
	//positioning the ball on the x and y axis
	private int ballposX = 250;
	private int ballposY = 350;
	//setting the direction of the ball
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0,0, 3, 592);
		g.fillRect(0,0, 692, 3);
		g.fillRect(691,0, 3, 592);
		
		//scores
		g.setColor(Color.red);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		
		
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
				
		//the ball
		g.setColor(Color.white);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//if you smash all of the bricks in the game 
		if(totalBricks <=0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("You Won!", 300, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		
		//if ball goes passed 570px down the Y-Axis then the game is over
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 25));
			g.drawString("Game Over, Scores: ", 200, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 350);
		}
		//disposes of all the graphics
		g.dispose();
	}
	
	
	
	
		//implementing all of the methods that are required for the keylistener and actionlistener
		@Override
		public void actionPerformed(ActionEvent e) {
			timer.start();
			if(play) {
				if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
					ballYdir = -ballYdir;
				}
				
				for(int i = 0; i<map.map.length; i++) {
					for(int j = 0; j<map.map[0].length; j++) {
						//if the map is = to 0, then it makes all of the bricks
						if(map.map[i][j] > 0) {
							int brickX = j* map.brickWidth + 80;
							int brickY = i * map.brickHeight + 50;
							int brickWidth = map.brickWidth;
							int brickHeight = map.brickHeight;
							
							Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
							Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
							Rectangle brickRect = rect;
							//A: takes you out of the loop
							//if the ball intersects the bricks, take away one brick & add 5 points to your score
							A: if(ballRect.intersects(brickRect)) {
								map.setBrickValue(0,  i,  j);
								totalBricks--;
								score+=5;
								// || means or
								if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
									ballXdir = -ballXdir;
								} else {
									ballYdir = -ballYdir;
								}
								//will take you completely out of the loop shown at the top
								break A;
							}
						}
					}
				}
				
				ballposX += ballXdir;
				ballposY += ballYdir;
				//for the left border
				if(ballposX < 0) {
					ballXdir = -ballXdir;
				}
				//for the top
				if(ballposY < 0) {
					ballYdir = -ballYdir;
				}
				//for the right border
				if(ballposX > 670) {
					ballXdir = -ballXdir;
				}
			}
			//recalls the paint method so we can redraw the paint method that allows us to actually move the panel
			repaint();
		}
				
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyReleased(KeyEvent e) {}	
		//if you have pressed the right arrow key or the left arrow key
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
				if(playerX >=600) {
					playerX = 600;
				} else {
					moveRight();
				}
			}
			
			if(e.getKeyCode()== KeyEvent.VK_LEFT) {
				if(playerX < 10) {
					playerX = 10;
				} else {
					moveLeft();
				}
			}
			//restarting the game when pressing enter
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(!play) {
					play = true;
					ballposX = 200;
					ballposY = 350;
					ballXdir = -1;
					ballYdir = -2;
					playerX = 310;
					score = 0;
					totalBricks = 21;
					map = new MapGenerator(3, 7);
					//repainting is just repaints everything in the game
					repaint();
				}
			}
		}
		public void moveRight() {
			play = true;
			//if pressed it will move 20 pixels to the right side
			playerX+=30;
		}
		public void moveLeft() {
			play = true;
			//if pressed it will move 20 pixels to the left side
			playerX-=30;
		}
	
}
