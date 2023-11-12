package panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import inside.Obstacle;
import inside.Score;
import inside.Vava;

public class GamePanel extends JPanel {

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

	Vava v1; // 바바 객체
	
	// 화면을 그림
	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) bufferg; 

		// 장애물을 그림
		for (int i = 0; i < obstacleList.size(); i++) {

			Obstacle tempObstacle = obstacleList.get(i);

			if (tempObstacle.getX() > 0 && tempObstacle.getX() < 100) { // 나중에 좌표 수정해야함
				
				bufferg.drawImage(tempObstacle.getImage(), tempObstacle.getX(), tempObstacle.getY(), tempObstacle.getWidth(),
						tempObstacle.getHeight(), null);
			}
		}

		for (int i = 0; i < scoreList.size(); i++) {

			Score tempScore = scoreList.get(i);

			if (tempScore.getX() > -90 && tempScore.getX() < 810) {

				alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) tempScore.getAlpha() / 255);
				g2.setComposite(alphaComposite); // 투명하게 하는방법 2

				bufferg.drawImage(tempScore.getImage(), tempScore.getX(), tempScore.getY(), tempScore.getWidth(),
						tempScore.getHeight(), null);

				// alpha값을 되돌린다
				alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2.setComposite(alphaComposite);
			}
		}

		// 버퍼 이미지를 화면에 출력
		g.drawImage(bufferImage, 0, 0, this);
	}
}