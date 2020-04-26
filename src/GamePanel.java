
//draw Graphic
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener,ActionListener {
	private boolean hint = true;
	private boolean running = false;
	private int score = 0;

	
	private Timer time;
	private int delay =10;
	
	private int width = MainFrame.width,height = MainFrame.height;
	private int px = width/2;
	
	private int bx = width/2 ,by = height-150;
	private int bdx = 5 ,bdy = 5;
	
	private CreateMap map;
	private int row = 2,col =2 ,level = 1;
	private int totalBrick = row*col;
	private int win=0;

	public GamePanel() {
		map = new CreateMap(row,col);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time = new Timer(delay,this);
		time.start();

		AudioInputStream audioInputStream;
    	Clip clip;
    	try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("../res/Music/NekoBgm.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
    	clip.start();
		} catch (LineUnavailableException e) {
			System.out.println("Don't found music file.");
		} catch (IOException e) {
			System.out.println("Input song fail.");
		}catch (UnsupportedAudioFileException e) {
			System.out.println("Invalid file type.");
		}
    	  
	}
	
	@Override
	public void paint(Graphics g) {
		//bg Color
		g.setColor(Color.darkGray);
		g.fillRect(1, 1, width-2, height-2);
		
		// draw brick
		map.draw(g);
		//draw board
		g.setColor(Color.black);
		g.fillRect(0, 0, 3, height);
		g.fillRect(691, 0, 3, height);
		g.fillRect(0, 0, width, 3);
		//text Level and Score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,20));
		g.drawString("LEVEL :"+level, 300, 25);
		g.drawString("SCORE :"+score, 550, 25);
		//image cat used as a player
		BufferedImage cat=null;
		try {
			cat= ImageIO.read(new File("../res/Graphic/Cat.png"));
		} catch (IOException e) {
			System.out.println("Can't open file");
		}
		g.drawImage(cat, px-50, height-70, 100, 50, null);
		//image ball
		BufferedImage ball=null;
		try {
			ball = ImageIO.read(new File("../res/Graphic/Ball.png"));
		} catch (IOException e) {
			System.out.println("Can't open file");
		}
		g.drawImage(ball, bx, by, 20, 20, null);
		//hint appear when start game
		if(hint){
			
			BufferedImage help=null;
			try {
				help = ImageIO.read(new File("../res/Graphic/hint.png"));
			} catch (IOException e) {
				System.out.println("Can't open file");
			}
			g.drawImage(help, 150, 20, 400, 450, null);

		}
		//if win game
		if(totalBrick<=0 && level==4){
			running = false;
			bdx=0;
			bdy=0;
			win=1;
			g.setColor(Color.black);
			g.fillRect(197, 47, 306, 206);
			g.setColor(Color.yellow);
			g.fillRect(198, 48, 304, 204);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(200, 50, 300, 200);

			g.setColor(Color.orange);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You Won\n", 290, 100);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("SCORE : "+score, 300, 150);
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter To Restart", 240,200);
			
		}
		//run when hit all brick
		else if(totalBrick<=0 && level<4) {
			running = false;
			bdx=0;
			bdy=0;
			win=1;
			g.setColor(Color.black);
			g.fillRect(197, 47, 306, 206);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(200, 50, 300, 200);

			g.setColor(Color.blue);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You Won\n", 290, 100);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("SCORE : "+score, 300, 150);
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter To Next Level", 240,200);
			
		}
		//runwhen you lost Ball
		if(by>width) {
			running = false;
			bdx=0;
			bdy=0;
			win=0;
			
			g.setColor(Color.black);
			g.fillRect(197, 47, 306, 206);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(200, 50, 300, 200);
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over\n", 270, 100);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("SCORE RESET TO ZERO", 230, 150);
			g.setColor(Color.black);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter To Restart", 250,200);
			score = 0;
		}
		//delete old obj
		g.dispose();
	
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {

		time.start();
		if(running) {
			if(new Rectangle(bx,by,20,20).intersects(new Rectangle(px-50, height-60, 100, 10))) {
				bdy = -bdy;
			}
			A:for(int i =0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					
					if(map.map[i][j]>0) {
						
						int brickX = j*map.brickWidth+50;
						int brickY = i*map.brickHeight+50;
						int briW = map.brickWidth;
						int briH = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,briW,briH);
						Rectangle ballRect = new Rectangle(bx,by,20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBrick--;
							score+=5;
							
							if(bx+19 <=brickRect.x || bx-50+1>=brickRect.x + brickRect.width) {
								bdx =-bdx;
							}
							else {
								bdy =-bdy;
							}
							break A;
							
						}
					}
				}
			}
			
			
			bx+=bdx;
			by+=bdy;
			if(bx<0) {
				bdx = -bdx;
			}
			if(by<0) {
				bdy = -bdy;
			}
			if(bx>675) {
				bdx = -bdx;
			}
		}
		
		repaint();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(px>650) {
				px=650;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(px<50) {
				px=50;
			}
			else {
				moveLeft();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!running) {
				
				if (win>0 && level==4){
					row=2;
					col=2;
					level=1;
					win=0;
					score = 0;
				}
				else if(win>0 && level<4) {
					row++;
					col++;
					level++;
					win=0;
				}
				
				running=true;
				totalBrick = row*col;
				px = width/2;
				bx = width/2; 
				by = height-100;
				bdx = 5+level; 
				bdy = 5+level;
				map = new CreateMap(row,col);
				repaint();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
	}

	private void moveLeft() {
		running = true;
		hint = false;
		px-=15;	
		
	}

	private void moveRight() {
		running = true;
		hint = false;
		px+=15;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
