package game;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.sound.sampled.Clip;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import inside.Screen;
import inside.Size;
import inside.Vava;
import inside.VavaImg;
import inside.Platform;
import inside.Rgb;
import inside.Score;
import inside.GameObjectImg;
import inside.Obstacle;
import inside.FeverGage;
import inside.FeverItem;
import inside.BoosterItem;
import inside.Emp;
import inside.GiantItem;
import inside.Item;
import inside.BGM;

import main.Main;

public class GameCore extends JPanel {
	
	//sound
	private BGM playSound;
	private BGM crash;
	private BGM fallSound;
	private BGM getJelly;
	private BGM jumpSound;
	private BGM getHP;
	private BGM badEnd;
	private BGM giantSound;
	private BGM happyEnd;
	
	// 더블 버퍼링 이미지
	private Image bufferImage;
	private Graphics bufferg;
	
	// list
	private List<Obstacle> obstacleList = new ArrayList<>(); 
	private List<Score> scoreList = new ArrayList<>(); 
	private List<Platform> platformList = new ArrayList<>(); 
	private List<Integer> mapLthList = new ArrayList<>(); 
	private List<FeverItem> feverItemList = new ArrayList<>(); 
	private List<Item> ItemList = new ArrayList<>();

	// vava
	private ImageIcon vavaIc;
	private ImageIcon vavaJumpIc;
	private ImageIcon doubleJumpIc;
	private ImageIcon jumpFallIc;
	private ImageIcon attackingIc;
	private ImageIcon hitIc;
	
	Vava vava;
	
	int vavaFront;
	int vavaFoot;
	
	// 발판
	private ImageIcon platform1;
	private ImageIcon platform2;
	
	private int platformH = 2000;
	
	// score
	private ImageIcon scoreA;
	private ImageIcon scoreB;
	private ImageIcon scoreC;
	private ImageIcon scoreEffect;
	
	private int sumScore = 0; 
	
	//피버타임  
	private ImageIcon red; // hp 감소 
	private ImageIcon green; //hp 증가 
	private ImageIcon yellow; // score 증가 
	private ImageIcon blue; // score 감소 
	private ImageIcon black; // J
	private boolean haveJ = false;
	
	// item
	private ImageIcon BoosterItem;
	private ImageIcon GiantItem;
	
	// hp
	private ImageIcon hpCoffee;
	private ImageIcon hpEDrink;
	private ImageIcon hpBar = new ImageIcon("img/UI/hp1.png");
	
	//fever time 
	private ImageIcon feverBar = new ImageIcon("img/GameObject/Fever/feverBar.png");
	
	// obstacle
	private ImageIcon obstacle1;
	private ImageIcon obstacle2;
	private ImageIcon obstacle3;
	private ImageIcon hitRedScreen = new ImageIcon("img/UI/redscreen.png");
	
	// screen
	private ImageIcon backScreenImg;
	private ImageIcon backScreenImg2;
	private ImageIcon backScreenImg3;
	
	Color screenFade;
	
	Screen back1;
	Screen back2;
	
	private int mapL = 0;

	private int page = 0;
	private int stage = 1;
	
	//배경소스
	private ImageIcon backIc;
	private ImageIcon backIc2;
	private ImageIcon backIc3;

	//버튼아이콘
	private ImageIcon jumpUp = new ImageIcon("img/UI/jumpN.png");
	private ImageIcon jumpDown = new ImageIcon("img/UI/jumpY.png");
	private ImageIcon attackUp = new ImageIcon("img/UI/attackN.png");
	private ImageIcon attackDown = new ImageIcon("img/UI/attackY.png");
	private ImageIcon escB = new ImageIcon("img/UI/esc.png");
	private JButton escBut;
	
	GameObjectImg gameObje1;
	GameObjectImg gameObje2;
	GameObjectImg gameObje3;
	
	FeverGage feverGage;
	Timer feverTimer;
	Timer inFeverTimer;

	Image jumpB = jumpUp.getImage();
	Image attackB = attackUp.getImage();
	
	JFrame sFrame;
	CardLayout cardLayout;
	
	private AlphaComposite alpha; //투명도

	private int gameSpeed = 5; // 게임 스피드

	private boolean fadeOn = false;
	private boolean redScreenOn = false;
	private boolean escOn = false;
	private boolean attackOn = false;
	private boolean deathHitOn = false;
	private boolean rightKeyPressed = false;
	private boolean inFeverTime = false;

	private int[] sizeArr;
	private int[][] mapCArr;
	private int[][][] rgbArr;
	
	Main main;

	public GameCore(JFrame sFrame, CardLayout cardLayout, Object object) {

		this.main = (Main) object;
		this.sFrame = sFrame;
		this.cardLayout = cardLayout;
		
		feverGage = new FeverGage();
		
		escBSet();
	}
	
	// 캐릭터 이미지 초기화 메서드
	private void vavaImgSet(VavaImg vava) {
		// VavaImg에서 각 상태에 해당하는 이미지들을 가져와 초기화
		vavaIc = vava.getVavaIc(); 
		vavaJumpIc = vava.getJumpIc();
		doubleJumpIc = vava.getDoubleJumpIc();
		jumpFallIc = vava.getFallIc();
		attackingIc = vava.getAttackIc(); 
		hitIc = vava.getHitIc();
	}
	
	// 게임 오브젝트 초기화 메서드
	private void gameObjectStore() {

		gameObje1 = new GameObjectImg(new ImageIcon("img/map1/background.png"),
				new ImageIcon("img/Objectimg/map1img/bg2.png"), new ImageIcon("img/map1/scoreA1.png"),
				new ImageIcon("img/map1/scoreB.png"), new ImageIcon("img/map1/scoreC.png"),
				new ImageIcon("img/Objectimg/map1img/life.png"), new ImageIcon("img/Objectimg/map1img/life2.png"), new ImageIcon("img/map1/effect.png"),
				new ImageIcon("img/map1/platform.png"),
				new ImageIcon("img/map1/platform2.png"), new ImageIcon("img/map1/obstacle1.png"),
				new ImageIcon("img/map1/obstacle2.png"), new ImageIcon("img/map1/obstacle31.png"),
				new ImageIcon("img/map1/obstacle2.png"),new ImageIcon("img/GameObject/etc/BoosterItem.png"),
				new ImageIcon("img/GameObject/etc/giant.png"),
				new ImageIcon("img/GameObject/Fever/red.PNG"),new ImageIcon("img/GameObject/Fever/green.PNG"),
				new ImageIcon("img/GameObject/Fever/yellow.PNG"),new ImageIcon("img/GameObject/Fever/blue.PNG"),
				new ImageIcon("img/GameObject/Fever/black.PNG"));

		gameObje2 = new GameObjectImg(new ImageIcon("img/fevermap/background2.png"),
				new ImageIcon("img/Objectimg/map1img/bg2.png"), new ImageIcon("img/map1/scoreA1.png"),
				new ImageIcon("img/map1/scoreB.png"), new ImageIcon("img/map1/scoreC.png"),
				new ImageIcon("img/Objectimg/map1img/life.png"), new ImageIcon("img/Objectimg/map1img/life2.png"), new ImageIcon("img/map1/effect.png"),
				new ImageIcon("img/fevermap/platform.png"),
				new ImageIcon("img/map1/platform2.png"), new ImageIcon("img/map1/obstacle1.png"),
				new ImageIcon("img/map1/obstacle2.png"), new ImageIcon("img/map1/obstacle31.png"),
				new ImageIcon("img/map1/obstacle2.png"),new ImageIcon("img/GameObject/etc/BoosterItem.png"),
				new ImageIcon("img/GameObject/etc/giant.png"),
				new ImageIcon("img/GameObject/Fever/red.PNG"),new ImageIcon("img/GameObject/Fever/green.PNG"),
				new ImageIcon("img/GameObject/Fever/yellow.PNG"),new ImageIcon("img/GameObject/Fever/blue.PNG"),
				new ImageIcon("img/GameObject/Fever/black.PNG"));

		gameObje3 = new GameObjectImg(new ImageIcon("img/map2/background.png"),
				new ImageIcon("img/Objectimg/map3img/bg2.png"), new ImageIcon("img/map1/scoreA1.png"),
				new ImageIcon("img/map1/scoreB.png"), new ImageIcon("img/map1/scoreC.png"),
				new ImageIcon("img/Objectimg/map1img/life.png"), new ImageIcon("img/Objectimg/map1img/life2.png"), new ImageIcon("img/map1/effect.png"),
				new ImageIcon("img/map2/platform.png"), 
				new ImageIcon("img/map2/platform2.png"),
				new ImageIcon("img/map2/obstacle1.png"), new ImageIcon("img/map2/obstacle2.png"),
				new ImageIcon("img/map2/obstacle3.png"), new ImageIcon("img/map2/obstacle1.png"),
				new ImageIcon("img/GameObject/etc/BoosterItem.png"),
				new ImageIcon("img/GameObject/etc/giant.png"),
				new ImageIcon("img/GameObject/Fever/red.PNG"),new ImageIcon("img/GameObject/Fever/green.PNG"),
				new ImageIcon("img/GameObject/Fever/yellow.PNG"),new ImageIcon("img/GameObject/Fever/blue.PNG"),
				new ImageIcon("img/GameObject/Fever/black.PNG"));
	}
	
	// 게임 오브젝트 이미지 아이콘 초기화
	private void gameObjectImgSet(GameObjectImg gobje) {

		// 점수 아이콘 이미지 초기화
		scoreA = gobje.getScoreA();
		scoreB = gobje.getScoreB();
		scoreC = gobje.getScoreC();
		hpCoffee = gobje.getHpCoffee();
		hpEDrink = gobje.getHpEDrink();
		
		// 점수 효과 아이콘 이미지 초기화
		scoreEffect = gobje.getscoreEffectIc();

		// 발판 이미지 초기화
		platform1 = gobje.getField1Ic(); // 일반 플랫폼
		platform2 = gobje.getField2Ic(); // 공중 플랫폼

		// 장애물 이미지 초기화
		obstacle1 = gobje.getObstacle1(); // 1번 장애물
		obstacle2 = gobje.getObstacle2(); // 2번 장애물
		obstacle3 = gobje.getObstacle3(); // 3번 장애물
		
		// 아이템 이미지 초기화
		BoosterItem = gobje.getBoosterItem();
		GiantItem = gobje.getGiantItem();
		
		// 피버타임 색상 이미지 초기화
		red=gobje.getRed();
		green=gobje.getGreen();
		yellow=gobje.getYellow();
		blue=gobje.getBlue();
		black=gobje.getBlack();
		
	}

	//게임 설정 초기화
	public void gameSet(VavaImg va) {

		setFocusable(true);// 포커스 설정 - 게임 화면에 키 입력을 받기 위해 필요
		vavaImgSet(va); // 캐릭터 이미지 초기화 메서드 호출
		gameObjectSet(); // 게임 오브젝트 초기화 메서드 호출
		initListener(); // 키 이벤트 리스너 초기화 메서드 호출
		mapRepaint(); // 화면 갱신을 위한 스레드 시작
	}

	// 게임을 시작하는 메서드
	public void gameStart() {

		gamePlayMapSet(); // 맵 이동 메서드 호출 - 현재 위치에 따라 맵을 이동시킴
		fillFeverGage(); // 피버 게이지 초기화 메서드 호출
		vavaFall(); // 낙하 처리 메서드 호출 - 캐릭터의 낙하 동작을 수행

	}
	
	private void gameObjectSet() {
		// 배경 음악 초기화
		playSound = new BGM("sound/playSound.wav", true);

		// 게임 오브젝트 초기화 함수 호출
		gameObjectStore();

		gameObjectImgSet(gameObje1);
		gameMapSet(1, mapL);
		mapLthList.add(mapL);

		gameObjectImgSet(gameObje2);
		gameMapSet(2, mapL);
		mapLthList.add(mapL);

		gameObjectImgSet(gameObje3);
		gameMapSet(3, mapL);
		mapLthList.add(mapL);
		
		// 배경 이미지 및 화면 객체 초기화
		backScreenImg = gameObje1.getBackScreenImg();
		backIc = gameObje1.getFeverScoreImg();

		backScreenImg2 = gameObje2.getBackScreenImg();
		backIc2 = gameObje2.getFeverScoreImg();

		backScreenImg3 = gameObje3.getBackScreenImg();
		backIc3 = gameObje3.getFeverScoreImg();

		// vava 객체 초기화
		vava = new Vava(vavaIc.getImage());

		// vava의 전면 위치 및 발 위치 초기화
		vavaFront = vava.getX() + vava.getWidth();
		vavaFoot = vava.getY() + vava.getHeight();

		// 화면 객체 초기화
		back1 = new Screen(backScreenImg.getImage(), 0, 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));

		back2 = new Screen(backScreenImg.getImage(), backScreenImg.getImage().getWidth(null), 0,
				backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));

		screenFade = new Color(0, 0, 0, 0); // 화면 페이드 효과용 색상 초기화

	}

	// 게임 화면 그림
	@Override
	protected void paintComponent(Graphics g) {
		
		// 더블 버퍼
		if (bufferg == null) 
		{
			bufferImage = createImage(this.getWidth(), this.getHeight());
			
			if (bufferImage == null)
				System.out.println("bufferImage가 null입니다.");
			else 
				bufferg = bufferImage.getGraphics();
		}
		
		// 투명도
		Graphics2D g2 = (Graphics2D) bufferg;
		super.paintComponent(bufferg);

		// 배경이미지
		bufferg.drawImage(back1.getImage(), back1.getX(), 0, back1.getWidth(), back1.getHeight() * 5 / 4, null);
		bufferg.drawImage(back2.getImage(), back2.getX(), 0, back2.getWidth(), back2.getHeight() * 5 / 4, null);

		if (fadeOn) {
			bufferg.setColor(screenFade); // 투명하게 하는 방법
			bufferg.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		// 발판
		for (Platform tempFoot : platformList) {
		    // 화면에 보이는 영역 내의 플랫폼만 그린다
		    if (tempFoot.getX() > -90 && tempFoot.getX() < 810) {
		        bufferg.drawImage(tempFoot.getImage(), tempFoot.getX(), tempFoot.getY(),
		                tempFoot.getWidth(), tempFoot.getHeight(), null);
		    }
		}

		 // 학점
		for (int i = 0; i < scoreList.size(); i++) {

			Score tempJelly = scoreList.get(i);

			if (tempJelly.getX() > -90 && tempJelly.getX() < 810) {

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) tempJelly.getAlpha() / 255);
				g2.setComposite(alpha);

				bufferg.drawImage(tempJelly.getImage(), tempJelly.getX(), tempJelly.getY(), tempJelly.getWidth(),
						tempJelly.getHeight(), null);

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2.setComposite(alpha);
			}
		}	

		for (int i = 0; i < feverItemList.size(); i++) {
			FeverItem tmpItem = feverItemList.get(i);

			if (tmpItem.getX() > -90 && tmpItem.getX() < 810) {

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) tmpItem.getAlpha() / 255);
				g2.setComposite(alpha);

				bufferg.drawImage(tmpItem.getImage(), tmpItem.getX(), tmpItem.getY(), tmpItem.getWidth(),
						tmpItem.getHeight(), null);

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2.setComposite(alpha);
			}
		}
		
		// 아이템
		for (int i = 0; i < ItemList.size(); i++) {

			Item tmpItems = ItemList.get(i);

			if (tmpItems.getX() > -90 && tmpItems.getX() < 810) {

				bufferg.drawImage(tmpItems.getImage(), tmpItems.getX(), tmpItems.getY(), tmpItems.getWidth(),
						tmpItems.getHeight(), null);
				if (tmpItems instanceof BoosterItem) {
		            ((BoosterItem) tmpItems).applyBoost();
		        } else if (tmpItems instanceof GiantItem) {
		            ((GiantItem) tmpItems).applyGrowth();
		        }
			}
		}

		// 장애물
		for (int i = 0; i < obstacleList.size(); i++) {

			Obstacle tempTacle = obstacleList.get(i);

			if (tempTacle.getX() > -90 && tempTacle.getX() < 810) {
				
				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) tempTacle.getAlpha() / 255);
				g2.setComposite(alpha);

				bufferg.drawImage(tempTacle.getImage(), tempTacle.getX(), tempTacle.getY(), tempTacle.getWidth(),
						tempTacle.getHeight(), null);
				
				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2.setComposite(alpha);
			}
		}

		// 캐릭터
		if (vava.isInvincible()) {
			
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) vava.getAlpha() / 255);
			g2.setComposite(alpha);

			bufferg.drawImage(vava.getImage(), vava.getX() - 110, vava.getY() - 170,
					vavaIc.getImage().getWidth(null) * 8 / 10, vavaIc.getImage().getHeight(null) * 8 / 10, null);

			
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2.setComposite(alpha);

		} else if (vava.isGiant()==true) {
	         bufferg.drawImage(vava.getImage(), vava.getX() - 110, vava.getY() -195,
	                 vavaIc.getImage().getWidth(null), vavaIc.getImage().getHeight(null), null); 
		} else { 

			bufferg.drawImage(vava.getImage(), vava.getX() - 110, vava.getY() - 170,
					vavaIc.getImage().getWidth(null) * 8 / 10, vavaIc.getImage().getHeight(null) * 8 / 10, null);
		}

		if (redScreenOn) {

			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 125 / 255);
			g2.setComposite(alpha);

			bufferg.drawImage(hitRedScreen.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2.setComposite(alpha);
		}

		Emp.drawFancyString(g2, Integer.toString(sumScore), 650, 70, 30, Color.WHITE);

		// 플레이어의 HP 바 이미지 그리기
		bufferg.drawImage(hpBar.getImage(), 20, 30, null);
		
		// HP 바의 현재 상태를 표현하는 검은색 사각형 그리기
		bufferg.setColor(Color.BLACK);
		bufferg.fillRect(93 + (int) (470 * ((double) vava.getHp() / 1000)), 65,
				1 + 470 - (int) (470 * ((double) vava.getHp() / 1000)), 20);

		// 특별 스테이지 게이지 
		if (feverGage.getFg() < 100) {
			bufferg.drawImage(feverBar.getImage(), 20, 70, null);
			bufferg.setColor(Color.BLACK);
			bufferg.fillRect(88 + (int) (470 * ((double) feverGage.getFg() / 100)), 101, 1 + 470 - (int) (470 * ((double) feverGage.getFg() / 100))+5, 21);
		}

		// 점프 및 공격 버튼 이미지 그리기
		bufferg.drawImage(jumpB, 0, 360, 132, 100, null);
		bufferg.drawImage(attackB, 650, 360, 132, 100, null);

		if (escOn) {
			// 투명도 조절
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 100 / 255);
			g2.setComposite(alpha);

			bufferg.setColor(Color.BLACK);
			bufferg.fillRect(0, 0, 850, 550);

			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2.setComposite(alpha);
		}

		 // 그린 그래픽을 화면에 표시
		g.drawImage(bufferImage, 0, 0, this);
	}
	
	// 45초 동안 피버타임 게이지를 채우는 메서드
	private void fillFeverGage() {
		int duration = 43; // 채우는 데 걸리는 시간 (초)
		int interval = 1000; // 주기 (밀리초)

		Timer fillGaugeTimer = new Timer(interval, new ActionListener() {
			private int currentTime = 0;

		    @Override
		    public void actionPerformed(ActionEvent e) {
		         double progress = (double) currentTime / duration;
		         int gageValue = (int) (progress * 100);
		         feverGage.setFg(gageValue);

		         currentTime++;

		         if (currentTime > duration) {
		        	 inFeverTime=true;
		             ((Timer) e.getSource()).stop(); // 타이머 중지
		         }
		     }
		 });

		 fillGaugeTimer.setInitialDelay(0);
		 fillGaugeTimer.start();
	}
	
	private boolean isSpecificRGB(int[] rgb, int targetRed, int targetGreen, int targetBlue) {
	    return rgb[0] == targetRed && rgb[1] == targetGreen && rgb[2] == targetBlue;
	}

	private void gameMapSet(int n, int l) {

		String tmpM = null;
		int tmpML = 0;

		if (n == 1) {
			tmpM = "img/map/map13.png";
		} else if (n == 2) {
			tmpM = "img/map/map2.png";
		} else if (n == 3) {
			tmpM = "img/map/map3.png";
		}

		try {
			sizeArr = Size.getSize(tmpM);
			mapCArr = Emp.getPic(tmpM);
			rgbArr = Rgb.getRGBValues(tmpM);  // 맵 RGB 값을 배열에 저장
			this.mapL += tmpML; // mapLength 갱신
								
		} catch (Exception e1) {
			e1.printStackTrace();
		}
					
		tmpML = sizeArr[0];
		int maxX = sizeArr[0]; 
		int maxY = sizeArr[1]; 
		
		for (int i = 0; i < rgbArr.length; i++) {
			for (int j = 0; j < rgbArr[0].length; j++) {
		            int[] rgb = {rgbArr[i][j][0], rgbArr[i][j][1], rgbArr[i][j][2]};
		            if (isSpecificRGB(rgb, 0, 0, 0)) {
							platformList.add(new Platform(platform1.getImage(), i * 40 + l * 40, j * 40, 80, 80));
					} else if (isSpecificRGB(rgb, 100, 100, 100)) {
						platformList.add(new Platform(platform2.getImage(), i * 40 + l * 40, j * 40, 80, 80));
					} 
		     }
		} 
		
		for (int i = 0; i < rgbArr.length; i++) {
			for (int j = 0; j < rgbArr[0].length; j++) {
		            int[] rgb = {rgbArr[i][j][0], rgbArr[i][j][1], rgbArr[i][j][2]};
		           if (isSpecificRGB(rgb, 255, 0, 0)) { 
						obstacleList.add(new Obstacle(obstacle1.getImage(), i * 40 + l * 40, j * 40, 80, 80, 0, false, 255));
					} else if (isSpecificRGB(rgb, 255, 0, 150)) {
						obstacleList.add(new Obstacle(obstacle2.getImage(), i * 40 + l * 40, j * 40, 80, 160, 0, true, 255));
					} else if (isSpecificRGB(rgb, 255, 0, 255)) { 
						obstacleList.add(new Obstacle(obstacle3.getImage(), i * 40 + l * 40, j * 40, 120, 240, 0, false, 255));
					} 
				}
	        }  
		
		for (int i = 0; i < rgbArr.length; i++) {
			for (int j = 0; j < rgbArr[0].length; j++) {
		            int[] rgb = {rgbArr[i][j][0], rgbArr[i][j][1], rgbArr[i][j][2]};
		                // 특정 RGB 값에 따라 로직 수행
		            if (isSpecificRGB(rgb, 255, 255, 0)) {
						scoreList.add(new Score(scoreA.getImage(), i * 40 + l * 40, j * 40, 30, 30, 255, 3500));
					} else if (isSpecificRGB(rgb, 200, 200, 0)) {
						scoreList.add(new Score(scoreB.getImage(), i * 40 + l * 40, j * 40, 30, 30, 255, 2500));
					} else if (isSpecificRGB(rgb, 150, 150, 0)) {
						scoreList.add(new Score(scoreC.getImage(), i * 40 + l * 40, j * 40, 30, 30, 255, 1500));
					} else if (isSpecificRGB(rgb, 255, 100, 0)) {
						scoreList.add(new Score(hpCoffee.getImage(), i * 40 + l * 40, j * 40, 30, 30, 255, 2500));
					} else if (isSpecificRGB(rgb, 112, 146, 190)) {
						scoreList.add(new Score(hpEDrink.getImage(), i * 40 + l * 40, j * 40, 30, 30, 255, 2500));
					} 
				}
		    }

		for (int i = 0; i < rgbArr.length; i++) {
			for (int j = 0; j < rgbArr[0].length; j++) {
		            int[] rgb = {rgbArr[i][j][0], rgbArr[i][j][1], rgbArr[i][j][2]};
		           if (isSpecificRGB(rgb, 237, 28, 36)) {
						feverItemList.add(new FeverItem(red.getImage(), i * 40 + l * 40, j * 40, 70, 70, 225));
					} else if (isSpecificRGB(rgb, 255, 174, 201)) {
						feverItemList.add(new FeverItem(green.getImage(), i * 40 + l * 40, j * 40, 70, 70, 225));
					} else if (isSpecificRGB(rgb, 121, 95, 0)) {
						feverItemList.add(new FeverItem(yellow.getImage(), i * 40 + l * 40, j * 40, 70, 70, 225));
					} else if (isSpecificRGB(rgb, 185, 122, 87)) {
						feverItemList.add(new FeverItem(blue.getImage(), i * 40 + l * 40, j * 40, 70, 70, 225));
					} else if (isSpecificRGB(rgb, 63, 72, 204)) {
						feverItemList.add(new FeverItem(black.getImage(), i * 40 + l * 40, j * 40, 70, 70, 225));
					} 
				}
		    } 
		
		for (int i = 0; i < maxX; i += 1) {
			for (int j = 0; j < maxY; j += 1) {
				if (mapCArr[i][j] == 11920925) {
					ItemList.add(new Item(BoosterItem.getImage(), i * 30 + l * 30, j * 30, 70, 70, 225));
					System.out.println("BoosterItem: " + BoosterItem.getImage());
				} else if (mapCArr[i][j] == 2273612) {
					ItemList.add(new Item(GiantItem.getImage(), i * 30 + l * 30, j * 30, 70, 70, 225));
				}
			}
		}
			
		this.mapL = this.mapL + tmpML;
	}
	
	// 바바 충돌 관련 상태 조정 메서드
	private void vavaHit(boolean on) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				if (on == true) // death 장애물
				{
					System.out.println("death 장애물 충돌");
					
					redScreenOn = true; // 충돌 레드 스크린 깔아줌
					
					vava.setImage(hitIc.getImage()); // 충돌 이미지 변경
					vava.setAlpha(50); // 충돌 표시를 위해 투명도 50으로 조절
					vava.setHp(0); // hp 0
					
					deathHitOn = true; // 게임 오버
					crash = new BGM("sound/crash.wav", false); // 게임 오버(배드엔딩) BGM
				}

				else // 일반 장애물
				{
					System.out.println("일반 장애물 충돌");
					
					redScreenOn = true;
					
					vava.setImage(hitIc.getImage());
					vava.setAlpha(50);
					vava.setHp(vava.getHp() - 150); // hp 150 감소
					
					vava.setInvincible(true); // 충돌했으니 잠시 무적 상태
					crash = new BGM("sound/crash.wav", false); // 충돌 효과음

					hitRedScreenEnd(); // 레드 스크린 종료시킴
					hitVavaIc(); // 모션 조정
					hitAlphaEnd(); // 무적 표시를 위해 깜빡거리는 효과를 줌

					vava.setInvincible(false); // 무적은 마지막에 종료됨
				}
			}
		}).start();
	}
	
	private void hitRedScreenEnd() {
		
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException 발생");
		}

		redScreenOn = false;
	}
	
	private void hitVavaIc() {
		
		try {
			Thread.sleep(500); // 약 0.5초로 넉넉하게 설정
		} catch (InterruptedException e) {
			System.out.println("InterruptedException 발생");
		}

		if (vava.getImage() == hitIc.getImage()) // 계속 충돌 모션이 유지되는 경우가 생겨 if문 추가
			vava.setImage(vavaIc.getImage());
	}
	
	private void hitAlphaEnd() {
		
		for (int i = 0; i < 10; i++) {

			if (vava.getAlpha() == 120)
				vava.setAlpha(50);
			else
				vava.setAlpha(120);
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println("InterruptedException 발생");
			}
		}
		
		vava.setAlpha(255); // 투명도 원상 복귀
	}

	private void gamePlayMapSet() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					if (page > 800) {
						vava.setHp(vava.getHp() - 10);
						page = 0;
					}

					page += gameSpeed;
					vavaFoot = vava.getY() + vava.getHeight();
					
					if (vavaFoot > 1999 || vava.getHp() < 1 || deathHitOn || haveJ) {
						if (deathHitOn) {
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						if (haveJ) {
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						playSound.stop();
						main.getGameEnd().setEnding(haveJ, sumScore);
						main.getGameEnd().setResultScore(sumScore);
		                
						cardLayout.show(sFrame.getContentPane(), "end");
						main.setGameCore(new GameCore(sFrame, cardLayout, main));
						
					
						sFrame.requestFocus();
						escOn = true;
					}
     
					if (fadeOn == false) 
					{ 
						if (mapL > mapLthList.get(1) * 40 + 800 && back1.getImage() != backScreenImg3.getImage()) 
						{
							System.out.println("Condition 1: mapLength=" + mapL + ", mapLengthList=" + mapLthList.get(2));
							fadeOn=true;
							backScreenTransition(backScreenImg3);
						} else if (mapL > mapLthList.get(0) * 40 + 800
								&& mapL < mapLthList.get(1) * 40 + 800
								&& back1.getImage() != backScreenImg2.getImage() && inFeverTime) 
						{
							System.out.println("Condition 1: mapLength=" + mapL + ", mapLengthList=" + mapLthList.get(2));
							fadeOn=true;
							backScreenTransition(backScreenImg2);
						} 
					}
					

					mapL += gameSpeed;

					if (back1.getX() < -(back1.getWidth() - 1))
						back1.setX(back1.getWidth());
			
					if (back2.getX() < -(back2.getWidth() - 1))
						back2.setX(back2.getWidth());

					back1.setX(back1.getX() - gameSpeed / 3);
					back2.setX(back2.getX() - gameSpeed / 3);

					for (int i = 0; i < scoreList.size(); i++) 
					{
						Score tmpScore = scoreList.get(i);

						if (tmpScore.getX() < -90) {

							platformList.remove(tmpScore);

						} else {

							tmpScore.setX(tmpScore.getX() - gameSpeed);
							if (tmpScore.getImage() == scoreEffect.getImage() && tmpScore.getAlpha() > 4) {
								tmpScore.setAlpha(tmpScore.getAlpha() - 5);
							}

							vavaFoot = vava.getY() + vava.getHeight();

							if (
							vava.getImage() != attackingIc.getImage()
									&& tmpScore.getX() + tmpScore.getWidth() * 20 / 100 >= vava.getX()
									&& tmpScore.getX() + tmpScore.getWidth() * 80 / 100 <= vavaFront
									&& tmpScore.getY() + tmpScore.getWidth() * 20 / 100 >= vava.getY()
									&& tmpScore.getY() + tmpScore.getWidth() * 80 / 100 <= vavaFoot
									&& tmpScore.getImage() != scoreEffect.getImage()) {
								
								if (tmpScore.getImage() == hpCoffee.getImage()) {
//									System.out.println("tmpFeverItme: " + tempJelly.getImage());
//									System.out.println("balck: " + hpCoffee.getImage() +"\n");
									getHP = new BGM("sound/getHP.wav", false);
									
									System.out.println("coffee");
									if ((vava.getHp() + 100) > 1000) {
										vava.setHp(1000);
									} else {
										vava.setHp(vava.getHp() + 100);
									}
								}
								
								if (tmpScore.getImage() == hpEDrink.getImage()) {
									if ((vava.getHp() + 150) > 1000) {
										vava.setHp(1000);
									} else {
										vava.setHp(vava.getHp() + 150);
									}
									getHP = new BGM("sound/getHP.wav", false);
								}
								tmpScore.setImage(scoreEffect.getImage());
								sumScore = sumScore + tmpScore.getScore();
								getJelly = new BGM("sound/getJelly.wav", false);

							} else if (
							vava.getImage() == attackingIc.getImage()
									&& tmpScore.getX() + tmpScore.getWidth() * 20 / 100 >= vava.getX()
									&& tmpScore.getX() + tmpScore.getWidth() * 80 / 100 <= vavaFront
									&& tmpScore.getY() + tmpScore.getWidth() * 20 / 100 >= vava.getY()
											+ vava.getHeight() * 1 / 3
									&& tmpScore.getY() + tmpScore.getWidth() * 80 / 100 <= vavaFoot
									&& tmpScore.getImage() != scoreEffect.getImage()) {

								if (tmpScore.getImage() == hpCoffee.getImage()) {
									if ((vava.getHp() + 100) > 1000) {
										vava.setHp(1000);
									} else {
										vava.setHp(vava.getHp() + 100);
									}
								}
								
								if (tmpScore.getImage() == hpEDrink.getImage()) {
									if ((vava.getHp() + 150) > 1000) {
										vava.setHp(1000);
									} else {
										vava.setHp(vava.getHp() + 150);
									}
									
								}
								tmpScore.setImage(scoreEffect.getImage());
								sumScore = sumScore + tmpScore.getScore();

							}
						}
					}
					
					for (int i = 0; i < ItemList.size(); i++) 
					{
						Item tmpItem = ItemList.get(i);
						
						if (tmpItem.getX()<-90) {
							ItemList.remove(tmpItem);
						} else {
							tmpItem.setX(tmpItem.getX()-gameSpeed);
							
							if (tmpItem.getImage() == scoreEffect.getImage() && tmpItem.getAlpha() > 4) {
								tmpItem.setAlpha(tmpItem.getAlpha() - 5);
							}
							
							vavaFoot = vava.getY() + vava.getHeight();
							
							if (tmpItem.getX() + tmpItem.getWidth() * 20 / 100 >= vava.getX()
									&& tmpItem.getX() + tmpItem.getWidth() * 80 / 100 <= vavaFront
									&& tmpItem.getY() + tmpItem.getWidth() * 20 / 100 >= vava.getY()
									&& tmpItem.getY() + tmpItem.getWidth() * 80 / 100 <= vavaFoot
									&& tmpItem.getImage() != scoreEffect.getImage()) {
								
								if  (tmpItem.getImage()==GiantItem.getImage()) {
//									System.out.println("tmpFeverItme: " + tmpItem.getImage());
//									System.out.println("balck: " + GiantItem.getImage() +"\n");
//									System.out.println("giant");
									  // BoosterItem 효과 적용
									vava.setGiant(true);
									vava.applyGiant();
									giantSound = new BGM("sound/giantSound.wav", false);
												                                                     
									new Thread(() -> {
										try {
											Thread.sleep(5000); // 5초간 일시 정지
											vava.setGiant(false);

											vava.resetGiant();
										}  catch (InterruptedException e) {
											 e.printStackTrace();
											}
										}).start(); 

					            ItemList.remove(tmpItem); // 아이템 제거
								}
								
								else if  (tmpItem.getImage() == BoosterItem.getImage()) {
									System.out.println("tmpFeverItme: " + tmpItem.getImage());
//									System.out.println("balck: " + BoosterItem.getImage() +"\n");
//									System.out.println("booster");
									// GiantItem 효과 적용
						       		vava.applyBoost();
						       		gameSpeed += 5;
						       								    
						       		new Thread(() -> {
						       			try {
						       				Thread.sleep(5000); // 5초간 일시 정지
						       				gameSpeed -= 5;
						       		} catch (InterruptedException e) {
						       			e.printStackTrace();
						       		}
						       	}).start();
						        ItemList.remove(tmpItem); // 아이템 제거 
								}
								tmpItem.setImage(scoreEffect.getImage()); 
							}
						}
					}
					

					for (int i = 0; i < feverItemList.size(); i++) {

						FeverItem tmpFever = feverItemList.get(i);
						//System.out.println("tmpFeverItem in: " + tempJelly);

						if (tmpFever.getX() < -90) {

							platformList.remove(tmpFever);

						} else {

							tmpFever.setX(tmpFever.getX() - gameSpeed);
							if (tmpFever.getImage() == scoreEffect.getImage() && tmpFever.getAlpha() > 4) {
								tmpFever.setAlpha(tmpFever.getAlpha() - 5);
							}

							vavaFoot = vava.getY() + vava.getHeight();

							if (
									tmpFever.getX() + tmpFever.getWidth() * 80 / 100 <= vavaFront
									&& tmpFever.getY() + tmpFever.getWidth() * 20 / 100 >= vava.getY()
									&& tmpFever.getY() + tmpFever.getWidth() * 80 / 100 <= vavaFoot
									&& tmpFever.getImage() != scoreEffect.getImage()) {
								
								System.out.println("tmpFeverItme: " + tmpFever.getImage());
								System.out.println(tmpFever.getImage());
								System.out.println(green.getImage());
								
								if (isSameImage(tmpFever, red.getImage())) {
									System.out.println("red");
									if ((vava.getHp() - 250) < 0) {
										vava.setHp(0);
									} else {
										vava.setHp(vava.getHp() - 250);
									}
								} else if (isSameImage(tmpFever, green.getImage())) {
									System.out.println("green");
									if ((vava.getHp() + 250) > 1000) {
										vava.setHp(1000);
									} else {
										vava.setHp(vava.getHp() + 250);
									}
								} else if (isSameImage(tmpFever, yellow.getImage())) {
									System.out.println("yellow"); 
									sumScore+=200000;
								} else if(isSameImage(tmpFever, blue.getImage())) {
									System.out.println("blue");
									if (sumScore-100000<0) {
										sumScore=0;
									} else {
										sumScore -= 200000;
									}
								} else if (isSameImage(tmpFever, black.getImage())) {
									System.out.println("black"); 
									haveJ=true;
								}

								
								getHP = new BGM("sound/getHP.wav", false);
								System.out.println("eat item");
								tmpFever.setImage(scoreEffect.getImage());
								//sumScore = sumScore + tempJelly.getScore();

							} 
						}
					}
					
					for (int i = 0; i < platformList.size(); i++) 
					{
						Platform tmpPlatform = platformList.get(i);

						if (tmpPlatform.getX() < -90) {
							platformList.remove(tmpPlatform);

						} else {
							tmpPlatform.setX(tmpPlatform.getX() - gameSpeed);
						}
					}
					
					for (int i = 0; i < obstacleList.size(); i++) {

						Obstacle tmpObstacle = obstacleList.get(i);

						if (tmpObstacle.getX() < -90) {
							platformList.remove(tmpObstacle);

						} else {
							tmpObstacle.setX(tmpObstacle.getX() - gameSpeed);
							
							if (tmpObstacle.getHeight() == 240 && vava.getImage() == attackingIc.getImage() && tmpObstacle.getAlpha() > 4) {
								tmpObstacle.setAlpha(tmpObstacle.getAlpha() - 5);
							}

							vavaFront = vava.getX() + vava.getWidth();
							vavaFoot = vava.getY() + vava.getHeight();
							
							if (tmpObstacle.isDeath()) // death 장애물일 때 게임 종료
							{
								if (!vava.isInvincible() && vava.getImage() != attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 >= vava.getY()
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 <= vavaFoot) {

											vavaHit(true);

								} else if (!vava.isInvincible() && vava.getImage() != attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() <= vava.getY()
										&& tmpObstacle.getY() + tmpObstacle.getHeight() * 95 / 100 > vava.getY()) {

											vavaHit(true);

								} else if (!vava.isInvincible() && vava.getImage() == attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 >= vava.getY() + vava.getHeight() * 2 / 3
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 <= vavaFoot) {

											vavaHit(true);

								} else if (!vava.isInvincible() && vava.getImage() == attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() < vava.getY() && tmpObstacle.getY() + tmpObstacle.getHeight() * 95 / 100 > vava.getY() + vava.getHeight() * 2 / 3) {
											
											vavaHit(true); 
								}
							}
							else if (tmpObstacle.getHeight() == 240)
							{
								if (!vava.isInvincible() && vava.getImage() != attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 >= vava.getY()
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 <= vavaFoot) {
											vavaHit(false);

								} else if (!vava.isInvincible() && vava.getImage() != attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() <= vava.getY()
										&& tmpObstacle.getY() + tmpObstacle.getHeight() * 95 / 100 > vava.getY()) {
											vavaHit(false);

								} else if (!vava.isInvincible() && vava.getImage() == attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 >= vava.getY() + vava.getHeight() * 2 / 3
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 <= vavaFoot) {
									
//											vava.setInvincible(true);
											//tempTacle = null;
											
//											try {
//												Thread.sleep(250);
//											} catch (InterruptedException e) {
//												e.printStackTrace();
//											}
											
//											vava.setInvincible(false);

								} else if (!vava.isInvincible() && vava.getImage() == attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() < vava.getY() && tmpObstacle.getY() + tmpObstacle.getHeight() * 95 / 100 > vava.getY() + vava.getHeight() * 2 / 3) {
											
//											vava.setInvincible(true);
											//tempTacle = null;
											
//											try {
//												Thread.sleep(250);
//											} catch (InterruptedException e) {
//												e.printStackTrace();
//											}
											
//											vava.setInvincible(false);
								}
							}
							else // 일반적인 경우
							{
								if (!vava.isInvincible() && vava.getImage() != attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 >= vava.getY()
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 <= vavaFoot) {

											vavaHit(false);

								} else if (!vava.isInvincible() && vava.getImage() != attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() <= vava.getY()
										&& tmpObstacle.getY() + tmpObstacle.getHeight() * 95 / 100 > vava.getY()) {

											vavaHit(false);

								} else if (!vava.isInvincible() && vava.getImage() == attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 >= vava.getY() + vava.getHeight() * 2 / 3
										&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 <= vavaFoot) {

											vavaHit(false);

								} else if (!vava.isInvincible() && vava.getImage() == attackingIc.getImage()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
										&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= vavaFront
										&& tmpObstacle.getY() < vava.getY() && tmpObstacle.getY() + tmpObstacle.getHeight() * 95 / 100 > vava.getY() + vava.getHeight() * 2 / 3) {
											
											vavaHit(false); 
								}
							}
						}
					}

					int tmpPlatform;
					int tmpNPlatform;

					if (vava.isInvincible()) {
						tmpNPlatform = 400;
					} else {
						tmpNPlatform = 2000;
					}

					
					// 수정 코드
					for (Platform platform : platformList) {
					    int tempX = platform.getX();

					    if (tempX > vava.getX() - 60 && tempX <= vavaFront) {
					        tmpPlatform = platform.getY();
					        int vavaFoot = vava.getY() + vava.getHeight();

					        if (tmpPlatform < tmpNPlatform && tmpPlatform >= vavaFoot) {
					            tmpNPlatform = tmpPlatform;
					        }
					    }
					}

					platformH = tmpNPlatform;

					if (escOn) {
						while (escOn) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}
	
	private void mapRepaint() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					repaint();

					if (escOn) 
					{ 
						while (escOn) 
						{
							try {
								Thread.sleep(10);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	   // 낙하 동작 메서드
	   private void vavaFall() {
	       new Thread(() -> {
	           while (true) 
	           {
	               vavaFoot = vava.getY() + vava.getHeight();

	               if (!escOn && vavaFoot < platformH && !vava.isJump() && !vava.isFall()) 
	               {
	                   vava.setFall(true);// 낙하 중인 상태로 설정
	                   System.out.println("낙하");

	                   if (vava.getCountJump() == 2) 
	                   {
	                       vava.setImage(jumpFallIc.getImage());// 2단 점프 후 이미지 설정
	                       fallSound = new BGM("sound/fallSound.wav", false);// 낙하 사운드 재생
	                   }

	                   long t1 = Emp.getTime();
	                   long t2;
	                   int set = 1;

	                   while (vavaFoot < platformH) 
	                   {
	                       t2 = Emp.getTime() - t1;
	                       int fallY = set + (int) (t2 / 40);
	                       vavaFoot = vava.getY() + vava.getHeight();

	                       if (vavaFoot + fallY >= platformH)
	                           fallY = platformH - vavaFoot;

	                       vava.setY(vava.getY() + fallY);

	                       if (vava.isJump())
	                           break;// 점프 중일 경우 루프 탈출

	                       if (escOn) 
	                       {
	                           long tempT1 = Emp.getTime();
	                           long tempT2 = 0;
	                           while (escOn) 
	                           {
	                               try {
	                                   Thread.sleep(10);
	                               } catch (InterruptedException e) {
	                                   e.printStackTrace();
	                               }
	                           }
	                           tempT2 = Emp.getTime() - tempT1;
	                           t1 = t1 + tempT2;
	                       }

	                       try {
	                           Thread.sleep(10);// 일시 정지
	                       } catch (InterruptedException e) {
	                           e.printStackTrace();
	                       }
	                   }
	                   
	                   vava.setFall(false);// 낙하 완료 후 상태 해제

	                // 낙하 후 이미지 설정
	                   if ((attackOn && !vava.isJump() && !vava.isFall() && vava.getImage() != attackingIc.getImage())
	                           || (!attackOn && !vava.isJump() && !vava.isFall() && vava.getImage() != vavaIc.getImage())) {
	                       vava.setImage(attackOn ? attackingIc.getImage() : vavaIc.getImage());
	                   }

	                   if (!vava.isJump())
	                       vava.setCountJump(0);// 점프 중이 아니면 점프 횟수 초기화
	               }
	               try {
	                   Thread.sleep(10);// 일시 정지
	               } catch (InterruptedException e) {
	                   e.printStackTrace();
	               }
	           }
	       }).start();
	   }
	   
	   // 점프 동작 메서드
	   private void VavaJump() {
		   
	       new Thread(() -> {
	    	   
	           long time1 = Emp.getTime(); // 현재 시간 기록
	           long time2;
	           
	           int h = 8; // 점프 높이 조절
	           int y = 1;
	           
	           vava.setCountJump(vava.getCountJump() + 1);
	           
	           int jumping = vava.getCountJump();
	           vava.setJump(true);

	           if (vava.getCountJump() == 1) 
	           {
	               vava.setImage(vavaJumpIc.getImage()); // 첫 번째 점프 시 이미지 설정
	               jumpSound = new BGM("sound/jumpSound.wav", false);// 점프 사운드 재생
	           } 
	           else if (vava.getCountJump() == 2) 
	           {
	               vava.setImage(doubleJumpIc.getImage()); // 두 번째 점프 시 이미지 설정
	               jumpSound = new BGM("sound/jumpSound.wav", false); // 점프 사운드 재생
	           }

	           while (y >= 0) 
	           {
	               time2 = Emp.getTime() - time1;
	               y = h - (int) (time2 / 40); // 점프 중 위치 변경
	               vava.setY(vava.getY() - y); // Y좌표 이동

	               if (jumping != vava.getCountJump()) {
	                   break; // 현재 점프 횟수가 변경되면 루프 탈출
	               }

	               if (escOn) {
	                  try {
	                      time1 += handleEscOn();// ESC 키가 눌렸을 때 처리
	                  } catch (Exception e) {
	                      e.printStackTrace();
	                  }
	               }

	               if (rightKeyPressed) {
	                   vava.setImage(attackingIc.getImage()); // 오른쪽 키가 눌렸을 때 이미지 변경
	               }

	               try {
	                   Thread.sleep(10);
	               } catch (InterruptedException e) {
	                   e.printStackTrace();
	               }
	           }

	           if (jumping == vava.getCountJump()) {
	               vava.setJump(false); // 점프가 완료되면 점프 상태 해제
	           }
	       }).start();
	   }

	// escOn을 처리
	private long handleEscOn() {
		long tempT1 = Emp.getTime();
		while (escOn) {
			sleep(10);
		}
		return Emp.getTime() - tempT1;
	}

	// sleep
	private void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void backScreenTransition(ImageIcon backScreenIc) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				screenFadeOut();
				
				back1 = new Screen(backScreenIc.getImage(), 0, 0, backScreenIc.getImage().getWidth(null),backScreenIc.getImage().getHeight(null));
				back2 = new Screen(backScreenIc.getImage(), backScreenIc.getImage().getWidth(null),0, backScreenIc.getImage().getWidth(null), backScreenIc.getImage().getHeight(null));
				
				screenFadeIn();
				fadeOn = false;
			}
		}).start();
	}


	private void screenFadeOut() {
		for (int i = 0; i < 256; i += 2) {
			screenFade = new Color(0, 0, 0, i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void screenFadeIn() {
		for (int i = 255; i >= 0; i -= 2) {
			screenFade = new Color(0, 0, 0, i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

		public void escBSet() {
			
			escBut = new JButton(escB);
			escBut.setBounds(350, 200, 120, 50);
			escBut.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) // 버튼 클릭하면 esc버튼 삭제하고 돌아감
				{
					remove(escBut);
					escOn = false;
				}
			});
		}
		
		// 리스너 초기화
		private void initListener() {
			addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						if (!escOn) 
						{
							escOn = true;
							add(escBut); // 돌아가기 버튼 추가
							repaint(); // 화면을 갱신하여 변경사항을 반영
						} 
						else 
						{
							remove(escBut); // 돌아가기 버튼 삭제
							escOn = false;
						}
					}

					if (!escOn) 
					{
						if (e.getKeyCode() == KeyEvent.VK_SPACE) 
						{
							jumpB = jumpDown.getImage();
							
							if (vava.getCountJump() < 2)
								VavaJump();
						}
						
						if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
						{
							attackB = attackDown.getImage();
							attackOn = true;
							rightKeyPressed = true;
							
							if (vava.getImage() != attackingIc.getImage() 
									&& !vava.isJump()
									&& !vava.isFall()) {
								vava.setImage(attackingIc.getImage());
							}
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
					{
						attackB = attackUp.getImage();
						attackOn = false;
						rightKeyPressed = false;
						
						if (vava.getImage() != vavaIc.getImage()
								&& !vava.isJump()
								&& !vava.isFall()) {
							vava.setImage(vavaIc.getImage());
						}
					}

					if (e.getKeyCode() == KeyEvent.VK_SPACE) 
						jumpB = jumpUp.getImage();
				}
			});
		}
		
		private boolean isSameImage(FeverItem item, Image img) {
		    boolean result = item.getImage().equals(img);
		    System.out.println("Image comparison result: " + result);
		    return result;
		}
}
