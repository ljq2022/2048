package twothousandfourtyeight;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel{
	Grid grid = new Grid(this);
	int gridx = 72;
	int gridy = 72;
	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				grid.keyPressed(e);
			}
		});
		setFocusable(true);
		requestFocus();
	}
	private void move()
	{
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillRect(145, 0, 1, 563);
		g.fillRect(290, 0, 1, 563);
		g.fillRect(435, 0, 1, 563);
		g.fillRect(0, 145, 586, 1);
		g.fillRect(0, 290, 586, 1);
		g.fillRect(0, 435, 586, 1);
		Font newFont = new Font("Arial", Font.BOLD, 30);
		g.setFont(newFont);
		for(int y = 0; y < grid.grid.length; y++)
		{
			gridx = 72;
			for(int x = 0; x < grid.grid[0].length; x++)
			{
				if(grid.grid[x][y] != null)
				{
					g.drawString(grid.grid[x][y].toString(), gridx, gridy);
					//System.out.println("x: " + i + "\ty: " + j + "gridx: " + gridx + "\tgridy: " + gridy);
				}
				gridx += 145;
			}
			gridy += 145;
		}
		gridx = 72;
		gridy = 72;
 
	}
	public void gameOver() {
		removeNotify();
		JOptionPane.showMessageDialog(this, "Game Over", "You lose.", JOptionPane.YES_NO_OPTION);
		System.exit(0);
	}
	public void victory() {
		removeNotify();
		JOptionPane.showMessageDialog(this, "Victory!", "You win :D", JOptionPane.YES_NO_OPTION);
	}
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("2048");
		Game game = new Game();
		frame.add(game);
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		while (true) {
			game.move();
			game.repaint();
			Thread.sleep(10);
		}
	}

}