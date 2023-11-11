package panels;

import java.awt.Image;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import inside.Vava;
import inside.Screen;
import inside.Obstacle;
import inside.MapObject;
import main.Main;

public class GamePanel extends JPanel {
	private ImageIcon backScreenImg;
	private ImageIcon backScreenImg2;
	private ImageIcon backScreenImg3;
	private ImageIcon backScreenImg4;
	
	private Image bufferImage;
	private Graphics bufferg;

	private ImageIcon obstacle1; // 1칸 장애물 (1단 점프)
	private ImageIcon obstacle2; // 2칸 장애물 (2단 점프)
	private ImageIcon obstacleFly; // death 장애물
	
	private boolean fadeOn = false;
	
	private List<Obstacle> obstacleList = new ArrayList<>(); // 장애물 리스트
	
	Vava v1;
	
	Screen back11;
	Screen back12;
	Screen back21;
	Screen back22;
	
	Color screenFade;
	
	JFrame superFrame;
	CardLayout cardLayout;
	Main main;
	
	public GamePanel(JFrame superFrame, CardLayout cardLayout, Object o) {
		this.superFrame = superFrame;
		this.cardLayout = cardLayout;
		this.main = (Main)o;
	}
	
	protected void paintComponent(Graphics g) {
		if (bufferg==null) {
			bufferImage = createImage(this.getWidth(), this.getHeight());
			if (bufferImage == null) System.out.println("더블 버퍼링용 오프 스크 생성 실패");
			else bufferg = bufferImage.getGraphics();
		}
		
		super.paintComponent(bufferg);
		
		bufferg.drawImage(back11.getImage(), back11.getX(), 0, back11.getWidth(), back11.getHeight(), null);
		bufferg.drawImage(back12.getImage(), back12.getX(), 0, back12.getWidth(), back12.getHeight(), null);
		bufferg.drawImage(back21.getImage(), back21.getX(), 0, back21.getWidth(), back21.getHeight(), null);
		bufferg.drawImage(back22.getImage(), back22.getX(), 0, back22.getWidth(), back22.getHeight(), null);
		
		if (fadeOn) {
			bufferg.setColor(screenFade);
			bufferg.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}
}
