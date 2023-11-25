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
import java.awt.event.KeyAdapter;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.AlphaComposite;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.PixelGrabber;

import java.util.ArrayList;
import java.util.List;

import inside.Item;
import inside.GiantItem;
import inside.BoosterItem;
import inside.GameObjectImg;
import inside.Obstacle;
import inside.Platform;
import inside.Score;
import inside.Screen;
import inside.Vava;
import inside.VavaAttack;
import inside.VavaImg;
import inside.VavaJump;

import main.Main;
import main.ListenAdapter;

public class GamePanel extends JPanel {
	
	// 더블 버퍼링 이미지
	private Image bufferImage;
	private Graphics bufferg;
		
	// vava
	private ImageIcon vavaIc; // 기본
	private ImageIcon hitIc; // 충돌
	private ImageIcon attackIc; // 공격
	//private ImageIcon jumpIc; // 점프
	//private ImageIcon doubleJumpIc; // 2단 점프
	private ImageIcon fallIc; // 2단 점프 후 낙하
	
	// vava attack
	private Image attackballImage; // 어택볼 이미지 추가
	private VavaAttack vavaAttack; // vavaAttack 객체 선언
	
	// hp
	private ImageIcon hpBar; // hp 게이지
	private ImageIcon hpCoffee; // hp 회복 아이템. 회복 낮음.
	private ImageIcon hpEDrink; // hp 회복 아이템. 회복 높음.
	
	// screen
	private ImageIcon backScreenImg;
	private ImageIcon backScreenImg2;
	private ImageIcon backScreenImg3;
	private ImageIcon backScreenImg4;
	
	// obstacle
	private ImageIcon obstacle1; // 1단 장애물
	private ImageIcon obstacle2; // 2단 장애물
	private ImageIcon obstacleDeath; // death 장애물
	
	// hit
	private ImageIcon redScreenIc; // 충돌 시 레드스크린

	// score
	private ImageIcon scoreA; // A학점 이미지
	private ImageIcon scoreB; // B학점 이미지
	private ImageIcon scoreC; // C학점 이미지
	
	private ImageIcon scoreEffectIC; 
	private int sumScore = 0; // score 누적 변수 선언

	// Item
	private JButton useGiantItemButton;
	private JButton useBoosterItemButton;
	
	// list
	private List<Obstacle> obstacleList; // 장애물 리스트
	private List<Score> scoreList; // 스코어 리스트
	private List<Platform> platforms; // 발판 리스트
	private List<Integer> mapLthList; // 맵의 시작하는 부분 체크
	
	
	// map
	private int[] mapSArr;  // 맵 사이즈를 저장할 배열
    private int[][] mapCArr; // 맵 픽셀값을 저장할 배열
    private int mapL;    // 맵의 길이
    
	private boolean fadeOn = false;
	private boolean redScreenOn = false; // 충돌 시 레드스크린
	private boolean escKeyOn = false; // esc키를 누르면 일시정지
	
	private AlphaComposite alpha; // 투명도
	
	Vava vava; // 바바 객체
	
	int front;
	int foot;
	
	JFrame sFrame;
	CardLayout cardLayout;
	
	GameObjectImg gameObje1; // 맵 수만큼 더 늘어남.
	GameObjectImg gameObje2;
	GameObjectImg gameObje3;
	GameObjectImg gameObje4;
	
	Screen back11;
	Screen back12;
	Screen back21;
	Screen back22;
	
	Color screenFade;
	
	Main main;
	
	public GamePanel(JFrame sFrame, CardLayout cardLayout, Object o) {
		this.sFrame = sFrame;
		this.cardLayout = cardLayout;
		
		if (o instanceof Main) {
            this.main = (Main) o;
        } else {
            // 예외 처리 또는 기본값 설정
            System.out.println("올바른 타입이 아닙니다.");
            this.main = null; // 또는 다른 기본값 설정
        }	
		
		// vavaAttack 객체 초기화
        vavaAttack = new VavaAttack();
        
        // 아이템 버튼 초기화
        useGiantItemButton = new JButton("거대화 아이템 사용");
        
        useGiantItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item giantItem = new GiantItem("거대화 아이템");
                giantItem.use(vava); // vava에게 아이템 사용
            }
        });
        
        useGiantItemButton.setBounds(10, 10, 150, 30); // 버튼 위치 설정
        add(useGiantItemButton);

        useBoosterItemButton = new JButton("부스터 아이템 사용");
        
        useBoosterItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Item boosterItem = new BoosterItem("부스터 아이템");
                boosterItem.use(vava); // vava에게 아이템 사용
            }
        });
        
        useBoosterItemButton.setBounds(170, 10, 150, 30); // 버튼 위치 설정
        add(useBoosterItemButton);
	}
	
	// initListener()와 동일
	private void keyListenerSet()
	{
		addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					if (!escKeyOn) 
					{
						escKeyOn = true;
						repaint();
					} 
					else
						escKeyOn = false;
				}
			}
		});
	}
	

	// runRepaint()와 동일
	private void gameRepaint() {
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				while (true) 
				{
					repaint();
					
					if (escKeyOn) 
					{
						while (escKeyOn) 
						{
							try 
							{
								Thread.sleep(10);
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}
						}
					}
					
					try 
					{
						Thread.sleep(10);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	// vava 이미지 설정
	private void vavaImgSet(VavaImg vobje)
	{
		vavaIc = vobje.getVavaIc();  // 기본
		hitIc = vobje.getHitIc(); // 충돌
		attackIc = vobje.getAttackIc(); // 공격
		//jumpIc = vobje.getJumpIc(); // 점프
		//doubleJumpIc = vobje.getDoubleJumpIc(); // 2단 점프
		fallIc = vobje.getFallIc(); // 2단 점프 후 낙하
	}
	
	// 게임 속 오브젝트들의 이미지를 맵마다 다르게 저장해 둠.
	private void gameObjectStore()
	{
		gameObje1 = new GameObjectImg(new ImageIcon("img/GameObject/Screen/screen01.png"));
		gameObje2 = new GameObjectImg(new ImageIcon("img/GameObject/Screen/screen02.png"));
		gameObje3 = new GameObjectImg(new ImageIcon("img/GameObject/Screen/screen03.png"));
		gameObje4 = new GameObjectImg(new ImageIcon("img/GameObject/Screen/screen04.png"));
	}
	
	// 장애물, 학점 등 이미지 설정
	private void gameObjectImgSet(GameObjectImg gobje)
	{
		hpCoffee = gobje.getHpCoffee();
		hpEDrink = gobje.getHpEDrink();
		
		obstacle1 = gobje.getObstacle1();
		obstacle2 = gobje.getObstacle2();
		obstacleDeath = gobje.getObstacleDeath();
		
		scoreA = gobje.getScoreA();
		scoreB = gobje.getScoreB();
		scoreC = gobje.getScoreC();
		
		scoreEffectIC = gobje.getscoreEffectIc();

	}
	
	// 게임을 세팅한다
		public void gameSet(VavaImg va) {

			setFocusable(true);
			vavaImgSet(va);
			gameObjeSet();
			keyListenerSet();
			gameRepaint();
		}

		// 게임을 시작한다
		public void gameStart() {

			gamePlayMapSet();
			// fall();
		}
	
	
	// 스윙 컴포넌트가 자신의 모양을 그리는 메서드
	@Override
	protected void paintComponent(Graphics g) {
		
		// Vava 이미지
		bufferg.drawImage(vava.getImage(), vava.getX(), vava.getY(), vava.getWidth(), vava.getHeight(), null);

		// 어택볼
		if (vavaAttack.isAttacking()) 
		{
		    bufferg.drawImage(vavaAttack.getAttackballImage(), vavaAttack.getAttackballX(), vavaAttack.getAttackballY(),
		            vavaAttack.getAttackballWidth(), vavaAttack.getAttackballHeight(), null);
		}
		    
		Graphics2D g2D = (Graphics2D) bufferg;
		
		// 더블 버퍼
		if (bufferg == null) 
		{
			bufferImage = createImage(this.getWidth(), this.getHeight());
			
			if (bufferImage != null) 
				bufferg = bufferImage.getGraphics();
			else 
				System.out.println("실패");
		}
		
		super.paintComponent(g);

		// 발판
		for (Platform platform : platforms) {
		    g.drawImage(platform.getImage(), platform.getX(), platform.getY(), this);
		}

		super.paintComponent(bufferg);

		// 장애물
		for (int i = 0; i < obstacleList.size(); i++) 
		{
			Obstacle tmpObstacle = obstacleList.get(i);

			if (tmpObstacle.getX() > -90 && tmpObstacle.getX() < 180) // 나중에 좌표 수정해야함
			{
				bufferg.drawImage(tmpObstacle.getImage(), tmpObstacle.getX(), tmpObstacle.getY(), tmpObstacle.getWidth(),
						tmpObstacle.getHeight(), null);
			}
		}
		
		// 충돌 시 레드스크린
		if (redScreenOn) 
		{
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 125 / 255);
			g2D.setComposite(alpha);

			bufferg.drawImage(redScreenIc.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2D.setComposite(alpha);
		}
		
		// esc 키를 눌렀을 시 화면이 잠시 어두워짐
		if (escKeyOn)
		{
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 100 / 255);
			g2D.setComposite(alpha);
			
			bufferg.setColor(Color.BLACK); // fillRect를 호출하기 전에 색상을 지정해줌.
			bufferg.fillRect(0, 0, 850, 550); // 사각형 채우기 (크기는 나중에 수정)
			
			try {
				Thread.sleep(500); // 0.5초 대기시킴. 수치는 나중에 수정.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2D.setComposite(alpha);
		}
		
		// score(학점)
		for (int i = 0; i < scoreList.size(); i++) 
		{
			Score tmpScore = scoreList.get(i);

			if (tmpScore.getX() > -90 && tmpScore.getX() < 810) 
			{

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) tmpScore.getAlpha() / 255);
				g2D.setComposite(alpha);

				bufferg.drawImage(tmpScore.getImage(), tmpScore.getX(), tmpScore.getY(), tmpScore.getWidth(), tmpScore.getHeight(), null);

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2D.setComposite(alpha);
			}
		}
		
		bufferg.drawImage(back11.getImage(), back11.getX(), 0, back11.getWidth(), back11.getHeight(), null);
		bufferg.drawImage(back12.getImage(), back12.getX(), 0, back12.getWidth(), back12.getHeight(), null);
		bufferg.drawImage(back21.getImage(), back21.getX(), 0, back21.getWidth(), back21.getHeight(), null);
		bufferg.drawImage(back22.getImage(), back22.getX(), 0, back22.getWidth(), back22.getHeight(), null);
		
		if (fadeOn) 
		{
			bufferg.setColor(screenFade);
			bufferg.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		// 바바 hp 게이지
		bufferg.drawImage(hpBar.getImage(), 20, 30, null);
		bufferg.setColor(Color.BLACK);
		bufferg.fillRect(84 + (int) (470 * ((double) vava.getHp() / 1000)), 65, 1 + 470 - (int) (470 * ((double) vava.getHp() / 1000)), 21);

		// 무적 (나중에 수정)
		if (vava.isInvincible()) 
		{
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) vava.getAlpha() / 255);
			g2D.setComposite(alpha);

			bufferg.drawImage(vava.getImage(), vava.getX() - 110, vava.getY() - 170,
					vavaIc.getImage().getWidth(null) * 8 / 10, vavaIc.getImage().getHeight(null) * 8 / 10, null);

			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2D.setComposite(alpha);
		} 
		else 
			bufferg.drawImage(vava.getImage(), vava.getX() - 110, vava.getY() - 170, vavaIc.getImage().getWidth(null) * 8 / 10, vavaIc.getImage().getHeight(null) * 8 / 10, null);
		
		g.drawImage(bufferImage, 0, 0, this); // 화면에 그림
	}
	
	// 장애물 충돌 (death 장애물 x)
	private void hitObstacle() 
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				vava.setHp(vava.getHp() - 50); // 충돌로 인해 vava 체력 감소. 수치는 나중에 수정.

				redScreenOn = true; // 충돌되어 레드스크린 on
				vava.setInvincible(true); // 충돌했으므로 vava를 일시적으로 무적 상태로 변환함.

				vava.setImage(hitIc.getImage()); // vava 충돌 모션으로 변경.
				vava.setAlpha(60); // vava 투명도 변경. 수치는 나중에 수정.

				hitRedScreen();
				hitChangeIc();
				hitBlinkEffect();
				
				vava.setInvincible(false); // vava 무적 상태 off
			}
		}).start();
	}
	
	private void hitRedScreen() 
	{
		
		try {
			Thread.sleep(500); // 0.5초 대기시킴. 수치는 나중에 수정.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		redScreenOn = false; // 레드스크린 off
	}
	
	private void hitChangeIc() 
	{
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (vava.getImage() == hitIc.getImage()) // 바바 모션을 기본으로 변경
			vava.setImage(vavaIc.getImage());
	}
	
	private void hitBlinkEffect() 
	{
		
		for (int j = 0; j < 10; j++) // 충돌하여 무적 상태임을 보여주기 위해 vava가 10번 깜빡거림.
		{

			if (vava.getAlpha() == 60) // 수치는 나중에 수정
				vava.setAlpha(160);
			else
				vava.setAlpha(60);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		vava.setAlpha(255); // vava 투명도 원상 복귀.
	}
	
	public void update() 
	{
		// 발판 이동 로직
		movePlatforms();
		// 그 외 게임 업데이트 로직
	}
	
	// 발판을 이동시킴
    private void movePlatforms() 
    {
        int platformSpeed = 5; // 발판 이동 속도

        for (Platform platform : platforms) {
            platform.move(platformSpeed);
        }
    }
    
	// 이미지의 크기를 가져오는 메서드
	private int[] getSize(String src) throws Exception {
		// 파일 경로로부터 이미지 파일을 읽어옴
		File imgf = new File(src);
		BufferedImage img = ImageIO.read(imgf);
		    
		// 이미지의 너비와 높이를 가져와 배열에 저장
		int width = img.getWidth();
		int height = img.getHeight();
		int[] tempSize = { width, height };
		    
		// 이미지의 너비와 높이를 포함한 배열 반환
		return tempSize;
	}
		
	// 이미지의 픽셀값을 가져오는 메서드
	private int[][] getPic(String src) throws Exception {
		// 파일 경로로부터 이미지 파일을 읽어옴
		File imgf = new File(src);
		BufferedImage img = ImageIO.read(imgf);
		    
		// 이미지의 너비와 높이를 가져옴
		int width = img.getWidth();
		int height = img.getHeight();
		    
		// 이미지의 픽셀값을 저장할 배열 초기화
		int[] pixels = new int[width * height];
		    
		// PixelGrabber를 사용하여 이미지의 픽셀값을 가져옴
		PixelGrabber grab = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
		grab.grabPixels();
		    
		// 가져온 픽셀값을 2차원 배열로 변환하여 저장
		int[][] picture = new int[width][height];
		
		for (int i = 0; i < pixels.length; i++) {
			// RGB값을 포함한 픽셀값을 배열에 저장
		    picture[i % width][i / width] = pixels[i] + 16777216;
		}
		    
		// 이미지의 픽셀값을 포함한 2차원 배열 반환
		return picture;
	}
	
    // 맵 초기화 initMap()와 동일.
  	private void gameMapSet(int n, int l) 
  	{
  		String tmpM = null;

  		if (n == 1) {
  			tmpM = "img/map/map1.png";
  		} else if (n == 2) {
  			tmpM = "img/map/map2.png";
        } else if (n == 3) {
            tmpM = "img/map/map3.png";
        } else if (n == 4) {
            tmpM = "img/map/map4.png";
        }
          
        try {
        	mapSArr = getSize(tmpM);
            mapCArr = getPic(tmpM);
            
            int tmpML = mapSArr[0];
            this.mapL += tmpML; // mapL 갱신
            
        } catch (Exception e1) {
        	e1.printStackTrace();
        }
          
        int maxX, maxY;
        maxX = mapSArr[0]; // 넓이
        maxY = mapSArr[1]; // 높이
        
        // 일단 death 장애물은 제외
        for (int i = 0; i < maxX; i += 2) // i 값 증가치 나중에 수정
        {
			for (int j = 0; j < maxY; j += 2) 
			{
				if (mapCArr[i][j] == 522) // 값은 나중에 수정
					obstacleList.add(new Obstacle(obstacle1.getImage(), i * 40 + l * 40, j * 40, 80, 80));
				else if (mapCArr[i][j] == 522) // 값은 나중에 수정
					obstacleList.add(new Obstacle(obstacle2.getImage(), i * 40 + l * 40, j * 40, 80, 160));
			}
		}
  	}
  	
  	//게임 오브젝트 초기화 initObject()와 동일.
  	private void gameObjeSet() 
  	{
  		vava = new Vava(vavaIc.getImage()); // vava 생성
  		
  		mapLthList = new ArrayList<>(); // 맵 시작하는 부분 체크

  		gameObjectStore();
  		
  		gameMapSet(1, mapL);
  		mapLthList.add(mapL);

  		gameMapSet(2, mapL);
  		mapLthList.add(mapL);

  		gameMapSet(3, mapL);
  		mapLthList.add(mapL);

  		gameMapSet(4, mapL);
  		mapLthList.add(mapL);
  		
  		screenFade = new Color(0, 0, 0, 0);

  		backScreenImg = gameObje1.getbackScreenImg();
  		backScreenImg = gameObje2.getbackScreenImg();
  		backScreenImg = gameObje3.getbackScreenImg();
  		backScreenImg = gameObje4.getbackScreenImg();
  		
  		back11 = new Screen(backScreenImg.getImage(), 0, 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));
  		back12 = new Screen(backScreenImg.getImage(), backScreenImg.getImage().getWidth(null), 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));
  		back21 = new Screen(backScreenImg.getImage(), 0, 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));
  		back22 = new Screen(backScreenImg.getImage(), backScreenImg.getImage().getWidth(null), 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));
  		
  		obstacleList = new ArrayList<>(); // 장애물 리스트
  		scoreList = new ArrayList<>(); // 스코어 리스트
  		platforms = new ArrayList<>(); // 발판 리스트
  		
  		hpBar = new ImageIcon("img/hpbar.png"); // 경로들은 나중에 수정
  		redScreenIc = new ImageIcon("img/redscreen.png");
  	}
	
	// 맵 설정을 변경함. 배경 변경, 체력 조정, 장애물 충돌 등등
	private void gamePlayMapSet() // mapMove()와 동일.
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				
				// 학점 
				 (int i = 0; i < scoreList.size(); i++) {

						Score tmpScore = scoreList.get(i); // 리스트 안에 있는 개별 학점 불러옴

						if (tmpScore.getX() < -90) { // 학점의 x 좌표에 따른 학점 제거 (x좌표 조정 필요)

							// 

						} else {

							// 스피드 조절 여기에 
							// ex) tmpScore.setX(tmpScore.getX() - gameSpeed); 
							if (tmpScore.getImage() == scoreEffectIC.getImage() && tmpScore.getAlpha() > 4) {
								tmpScore.setAlpha(tmpScore.getAlpha() - 5);
							}

							foot = vava.getY() + vava.getHeight(); // vava 발 위치 재스캔

							if ( // 캐릭터의 범위 안에 학점이 있으면 아이템을 먹는다.
									tmpScore.getX() + tmpScore.getWidth() * 20 / 100 >= vava.getX()
									&& tmpScore.getX() + tmpScore.getWidth() * 80 / 100 <= front
									&& tmpScore.getY() + tmpScore.getWidth() * 20 / 100 >= vava.getY()
									&& tmpScore.getY() + tmpScore.getWidth() * 80 / 100 <= foot
									&& tmpScore.getImage() != scoreEffectIC.getImage()) {

								if (tmpScore.getImage() == hpCoffee.getImage() || tmpScore.getImage() == hpEDrink.getImage()) {
									if ((vava.getHp() + 100) > 1000) {
										vava.setHp(1000);
									} else {
										vava.setHp(vava.getHp() + 100);
									}
								}
								tmpScore.setImage(scoreEffectIC.getImage()); // 젤리의 이미지를 이펙트로 바꾼다
								sumScore = sumScore + tmpScore.getScore(); // 총점수에 젤리 점수를 더한다

							} else if ( // vava 범위 안에 학점이 있으면 아이템을 먹음
							tmpScore.getX() + tmpScore.getWidth() * 20 / 100 >= vava.getX()
									&& tmpScore.getX() + tmpScore.getWidth() * 80 / 100 <= front
									&& tmpScore.getY() + tmpScore.getWidth() * 20 / 100 >= vava.getY()
											+ vava.getHeight() * 1 / 3
									&& tmpScore.getY() + tmpScore.getWidth() * 80 / 100 <= foot
									&& tmpScore.getImage() != scoreEffectIC.getImage()) {

								if (tmpScore.getImage() == hpCoffee.getImage() || tmpScore.getImage() == hpEDrink.getImage()) {
									if ((vava.getHp() + 100) > 1000) {
										vava.setHp(1000);
									} else {
										vava.setHp(vava.getHp() + 100);
									}
								}
								tmpScore.setImage(scoreEffectIC.getImage()); // 젤리의 이미지를 이펙트로 바꾼다
								sumScore = sumScore + tmpScore.getScore(); // 총점수에 젤리 점수를 더한다
						}
						}
				 }
				
				// 장애물
				for (int i = 0; i < obstacleList.size(); i++)
				{
					Obstacle tmpObstacle = obstacleList.get(i);
					
					if (tmpObstacle.getX() < -90) // 수치 나중에 수정
					{
						// 나중에 추가
					}
					else
					{
						// 게임스피드 관련
						
						front = vava.getX() + vava.getWidth();
						foot = vava.getY() + vava.getHeight();
						
						if (!vava.isInvincible() && tmpObstacle.getX() + tmpObstacle.getWidth() / 2 >= vava.getX()
								&& tmpObstacle.getX() + tmpObstacle.getWidth() / 2 <= front
								&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 >= vava.getY()
								&& tmpObstacle.getY() + tmpObstacle.getHeight() / 2 <= foot) 
						{
							hitObstacle();
						}
					}
				}
			}
		}).start();
	}
}