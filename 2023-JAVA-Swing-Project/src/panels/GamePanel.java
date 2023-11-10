package panels;

import java.awt.Image;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//import main.Main;
import inside.Obstacle;
import inside.Vava;

public class GamePanel extends JPanel {

	private Image bufferImage;
	private Graphics bufferg;

	private ImageIcon obstacle1; // 1칸 장애물 (1단 점프)
	private ImageIcon obstacle2; // 2칸 장애물 (2단 점프)
	private ImageIcon obstacleDeath; // death 장애물

	private List<Obstacle> obstacleList = new ArrayList<>(); // 장애물 리스트
	
	Vava v1; // 바바 객체
}