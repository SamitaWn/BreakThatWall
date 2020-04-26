
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CreateMap {
	public int map[][];
	public int brickWidth,brickHeight;
	public int wid = MainFrame.width,hei = MainFrame.height;
	public CreateMap(int row,int cal) {
		map = new int[row][cal];
		for(int i =0 ;i< map.length;i++) {
			for(int j =0;j<map[0].length;j++) {
				map[i][j]=1;
			}
		}
		
		brickWidth = (wid-100)/cal;
		brickHeight = ((hei/2)-50)/row;
	}
	
	public void draw(Graphics g) {
		for(int i =0 ;i< map.length;i++) {
			for(int j =0;j<map[0].length;j++) {
				if(map[i][j]>0) {
					BufferedImage wall=null;
					try {
						wall= ImageIO.read(new File("../res/Graphic/Wall.png"));
					} catch (IOException e) {
						System.out.println("Can't open file");
					}
					g.drawImage(wall, j*brickWidth+50, i*brickHeight+50, brickWidth, brickHeight, null);

					
					((Graphics2D) g).setStroke(new BasicStroke(5));
					g.setColor(Color.black);
					g.drawRect(j*brickWidth+50, i*brickHeight+50, brickWidth, brickHeight);
				}
			}
		}
		
	}
	
	public void setBrickValue(int value,int row,int col) {
		map[row][col]=value;
	}
}
