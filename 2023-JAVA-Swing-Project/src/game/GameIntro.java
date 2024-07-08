package game;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameIntro extends JPanel {
	
	private ImageIcon introImg = new ImageIcon("img/intro/intro.png");

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(introImg.getImage(), 0, 0, getWidth(), getHeight(), null);
	}
}