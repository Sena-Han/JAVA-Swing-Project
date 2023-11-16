package panels;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.AlphaComposite;

import java.util.ArrayList;
import java.util.List;

import inside.Item;
import inside.GiantItem;
import inside.BoosterItem;
import inside.MapObject;
import inside.Obstacle;
import inside.Score;
import inside.Screen;
import inside.Vava;
import inside.VavaAttack;
import inside.VavaJump;

import main.Main;

public class GamePanel extends JPanel {
	
	// 더블 버퍼링 이미지
	private Image bufferImage;
	private Graphics bufferg;
		
	// vava
	private ImageIcon vavaIc; // 기본 모션
	private ImageIcon hitIc; // 충돌 모션
	private ImageIcon attackIc; // 공격 모션
	//private ImageIcon jumpIc; // 점프 모션
	//private ImageIcon doubleJumpIc; // 2단 점프 모션
	private ImageIcon fallIc; // 2단 점프 후 낙하 모션
	
	private ImageIcon lifeBar; // hp 게이지
	
	// VavaAttack
	private Image attackballImage; // 어택볼 이미지 추가
	private VavaAttack vavaAttack; // vavaAttack 객체 선언
	
	// screen
	private ImageIcon backScreenImg;
	private ImageIcon backScreenImg2;
	private ImageIcon backScreenImg3;
	private ImageIcon backScreenImg4;
	
	// obstacle
	private ImageIcon obstacle1; // 1단 장애물
	private ImageIcon obstacle2; // 2단 장애물
	private ImageIcon obstacleFly; // death 장애물
	
	// hit
	private ImageIcon redScreenIc; // 충돌 시 레드스크린

	// score
	private ImageIcon scoreA = new ImageIcon(""); // A학점 이미지를 통해 A스코어 생성
	private ImageIcon scoreB = new ImageIcon(""); // B학점 이미지를 통해 B스코어 생성
	private ImageIcon scoreC = new ImageIcon(""); // C학점 이미지를 통해 C스코어 생성
	
	// score
	private int sumScore = 0; // 결과점수 변수 (누적 score)

	// Item
	private JButton useGiantItemButton;
	private JButton useBoosterItemButton;
	
	// list
	private List<Obstacle> obstacleList; // 장애물 리스트
	private List<Score> scoreList = new ArrayList<>(); // 스코어 리스트
	
	private boolean fadeOn = false;
	private boolean redScreen = false; // 충돌 시 레드스크린
	
	private AlphaComposite alphaComposite; // 투명도
	
	JFrame sFrame;
	CardLayout cardLayout;

	Vava v1; // 바바 객체
	
	Screen back11;
	Screen back12;
	Screen back21;
	Screen back22;
	
	Color screenFade;
	
	Main main;
	
	public GamePanel(JFrame sFrame, CardLayout cardLayout, Object o) {
		this.sFrame = sFrame;
		this.cardLayout = cardLayout;
		this.main = (Main)o;
		
		// vavaAttack 객체 초기화
        vavaAttack = new VavaAttack();
        
        // 아이템 버튼 초기화
        useGiantItemButton = new JButton("거대화 아이템 사용");
        useGiantItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item giantItem = new GiantItem("거대화 아이템");
                giantItem.use(v1); // vava에게 아이템 사용
            }
        });
        useGiantItemButton.setBounds(10, 10, 150, 30); // 버튼 위치 설정
        add(useGiantItemButton);

        useBoosterItemButton = new JButton("부스터 아이템 사용");
        useBoosterItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item boosterItem = new BoosterItem("부스터 아이템");
                boosterItem.use(v1); // vava에게 아이템 사용
            }
        });
        useBoosterItemButton.setBounds(170, 10, 150, 30); // 버튼 위치 설정
        add(useBoosterItemButton);
	}
	
	// 화면을 그림
	@Override
	protected void paintComponent(Graphics g) {
		
		// Vava 이미지 그리기
		bufferg.drawImage(v1.getImage(), v1.getX(), v1.getY(), v1.getWidth(), v1.getHeight(), null);

		// 어택볼 그리기
		if (vavaAttack.isAttacking()) {
		    bufferg.drawImage(vavaAttack.getAttackballImage(), vavaAttack.getAttackballX(), vavaAttack.getAttackballY(),
		            vavaAttack.getAttackballWidth(), vavaAttack.getAttackballHeight(), null);
		}
		    
		Graphics2D g2D = (Graphics2D) bufferg;
		
		// 더블 버퍼
		if (bufferg == null) {
			bufferImage = createImage(this.getWidth(), this.getHeight());
			
			if (bufferImage == null) 
				System.out.println("스크린 생성 실패");
			else 
				bufferg = bufferImage.getGraphics();
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
		
		// 충돌 시 레드스크린
		if (redScreen) {

			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 125 / 255);
			g2D.setComposite(alphaComposite);

			bufferg.drawImage(redScreenIc.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2D.setComposite(alphaComposite);
		}
		
		// score(학점)를 그림
		for (int i = 0; i < scoreList.size(); i++) {

			Score tempScore = scoreList.get(i);

			if (tempScore.getX() > -90 && tempScore.getX() < 810) {

				alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) tempScore.getAlpha() / 255);
				g2D.setComposite(alphaComposite); // 투명화

				bufferg.drawImage(tempScore.getImage(), tempScore.getX(), tempScore.getY(), tempScore.getWidth(),
						tempScore.getHeight(), null);

				// alpha값을 되돌림
				alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2D.setComposite(alphaComposite);
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
	
	// 장애물 충돌
	private void hit() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				v1.setInvincible(true); // 충돌했으므로 vava를 무적 상태로 변환함.

				System.out.println("vava 무적 상태");

				redScreen = true; // 충돌되어 레드스크린 on

				v1.setHp(v1.getHp() - 50); // 충돌하여 vava 체력 감소. 수치는 나중에 수정.
				v1.setImage(hitIc.getImage()); // vava 충돌 모션으로 변경.
				v1.setAlpha(60); // vava 투명도 변경. 수치는 나중에 수정.

				try {
					Thread.sleep(250); // 0.5초 대기시킴. 수치는 나중에 수정.
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				redScreen = false; // 레드스크린 off

				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (v1.getImage() == hitIc.getImage()) // 0.5초 동안 이미지가 바뀌지 않았다면 기본이미지로 변경
					v1.setImage(vavaIc.getImage());

				for (int j = 0; j < 11; j++) { // 충돌하여 무적 상태임을 보여주기 위해 2.5초간 vava가 깜빡거림.

					if (v1.getAlpha() == 60) // 수치는 나중에 수정
						v1.setAlpha(160);
					else
						v1.setAlpha(60);
					
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				v1.setAlpha(255); // vava 투명도 원상 복귀.

				v1.setInvincible(false); // vava 무적 상태 off
				System.out.println("vava 무적 상태 종료");
			}
		}).start();
	}

}