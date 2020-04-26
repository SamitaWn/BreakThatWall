

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	public static final int width = 700,height = 500;
	public MainFrame() {
		setTitle("BreakThatWall");
		this.setSize(width, height);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		setLocationRelativeTo(null);
		add(new GamePanel());
		setVisible(true);
		

		
	}



	public static void main(String[] args) {
		new MainFrame();

	}

}
