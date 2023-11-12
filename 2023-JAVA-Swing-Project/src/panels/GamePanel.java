package panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.AlphaComposite;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import inside.MapObject;
import inside.Obstacle;
import inside.Score;
import inside.Screen;
import inside.Vava;
import main.Main;

public class GamePanel extends JPanel {
	
	//screen
	private ImageIcon backScreenImg;
	private ImageIcon backScreenImg2;
	private ImageIcon backScreenImg3;
	private ImageIcon backScreenImg4;

	// 더블 버퍼링 이미지
	private Image bufferImage;
	private Graphics bufferg;

	// obstacle
	private ImageIcon obstacle1 = new ImageIcon(""); // 1칸 장애물
	private ImageIcon obstacle2 = new ImageIcon(""); // 2칸 장애물
	private ImageIcon obstacleFly = new ImageIcon(""); // death 장애물

	// score
	private ImageIcon scoreA = new ImageIcon(""); // A학점 이미지를 통해 A스코어 생성
	private ImageIcon scoreB = new ImageIcon(""); // B학점 이미지를 통해 B스코어 생성
	private ImageIcon scoreC = new ImageIcon(""); // C학점 이미지를 통해 C스코어 생성

	private List<Obstacle> obstacleList = new ArrayList<>(); // 장애물 리스트
	private List<Score> scoreList = new ArrayList<>(); // 스코어 리스트
	
	private AlphaComposite alphaComposite; // 투명도 관련 변수
	
	private int sumScore = 0; // 결과점수 변수 (누적 score)
	
	private boolean fadeOn = false;

	Vava v1; // 바바 객체
	
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
	
	// 화면을 그림
	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) bufferg;
		
		if (bufferg==null) {
			bufferImage = createImage(this.getWidth(), this.getHeight());
			if (bufferImage == null) System.out.println("더블 버퍼링용 오프 스크 생성 실패");
			else bufferg = bufferImage.getGraphics();
		}
		
		super.paintComponent(bufferg);

		// 장애물을 그림
		for (int i = 0; i < obstacleList.size(); i++) {

			Obstacle tempObstacle = obstacleList.get(i);

			if (tempObstacle.getX() > 0 && tempObstacle.getX() < 100) { // 나중에 좌표 수정해야함
				
				bufferg.drawImage(tempObstacle.getImage(), tempObstacle.getX(), tempObstacle.getY(), tempObstacle.getWidth(),
						tempObstacle.getHeight(), null);
			}
		}
		
		// score(학점)를 그림
		for (int i = 0; i < scoreList.size(); i++) {

			Score tempScore = scoreList.get(i);

			if (tempScore.getX() > -90 && tempScore.getX() < 810) {

				alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) tempScore.getAlpha() / 255);
				g2.setComposite(alphaComposite); // 투명화

				bufferg.drawImage(tempScore.getImage(), tempScore.getX(), tempScore.getY(), tempScore.getWidth(),
						tempScore.getHeight(), null);

				// alpha값을 되돌림
				alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2.setComposite(alphaComposite);
			}
		}
		
		bufferg.drawImage(back11.getImage(), back11.getX(), 0, back11.getWidth(), back11.getHeight(), null);
		bufferg.drawImage(back12.getImage(), back12.getX(), 0, back12.getWidth(), back12.getHeight(), null);
		bufferg.drawImage(back21.getImage(), back21.getX(), 0, back21.getWidth(), back21.getHeight(), null);
		bufferg.drawImage(back22.getImage(), back22.getX(), 0, back22.getWidth(), back22.getHeight(), null);
		
		if (fadeOn) {
			bufferg.setColor(screenFade);
			bufferg.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		// 버퍼 이미지를 화면에 출력
		g.drawImage(bufferImage, 0, 0, this);
	}
}