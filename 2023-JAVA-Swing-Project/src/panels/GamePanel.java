package panels;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import inside.Obstacle;
import inside.Score;
import inside.Vava;

public class GamePanel extends JPanel {

	private Image bufferImage;
	private Graphics bufferg;

	private ImageIcon obstacle1; // 1칸 장애물
	private ImageIcon obstacle2; // 2칸 장애물
	private ImageIcon obstacleFly; // death 장애물

	// score
	private ImageIcon scoreA = new ImageIcon(""); // A학점 이미지를 통해 A스코어 생성
	private ImageIcon scoreB = new ImageIcon(""); // B학점 이미지를 통해 B스코어 생성
	private ImageIcon scoreC = new ImageIcon(""); // C학점 이미지를 통해 C스코어 생성

	private List<Obstacle> obstacleList = new ArrayList<>(); // 장애물 리스트
	private List<Score> scoreList = new ArrayList<>(); // 스코어 리스트

	private int sumScore = 0; // 결과점수 변수 (누적 score)

	Vava v1; // 바바 객체
}