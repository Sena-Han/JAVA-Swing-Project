package panels;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

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
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.awt.image.PixelGrabber;

import java.util.ArrayList;
import java.util.List;

import inside.Item;
import inside.GiantItem;
import inside.BoosterItem;
import inside.GameObjectImg;
import inside.Platform;
import inside.Obstacle;
import inside.Score;
import inside.Screen;
import inside.Vava;
import inside.VavaImg;
import inside.VavaAttack;
import inside.VavaFall;
import inside.VavaJump;
import inside.FeverGage;
import inside.FeverScore;
import emp.Emp;

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
	
	// vava attack
	private Image attackballImage; // 어택볼 이미지 추가
	private VavaAttack vavaAttack; // vavaAttack 객체 선언
	
	// hp
	private ImageIcon hpBar; // hp 게이지
	private ImageIcon hpCoffee; // hp 회복 아이템. 회복 낮음.
	private ImageIcon hpEDrink; // hp 회복 아이템. 회복 높음.
	
	//fever time 
	private ImageIcon feverBar;
	
	// screen
	private ImageIcon backScreenImg;
	private ImageIcon backScreenImg2;
	private ImageIcon backScreenImg3;
	private ImageIcon backScreenImg4;
	
	//피버타임 스크린  
	private ImageIcon feverScreen;
	
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
	private int sumScore = 0; // 결과점수 변수 (누적 score)

	//fever time score
	private ImageIcon scoreAplus; //피버타임 전용 a+이미지 
	
	private ImageIcon feverScoreEffectIc;
	private int feverSumScore = 0;

	// Item
	private JButton useGiantItemButton;
	private JButton useBoosterItemButton;
	
	// list
	private List<Obstacle> obstacleList; // 장애물 리스트
	private List<Score> scoreList; // 스코어 리스트
	private List<Platform> platforms; // 발판 리스트
	private List<Integer> mapLthList; // 다음 맵의 시작지점을 확인하기위한 배열
	private List<FeverScore> feverScoreList;
	
	//발판
	private Platform platform;
	    
	//낙하
	private VavaFall vavaFall;

	
	private boolean fadeOn = false;
	private boolean redScreenOn = false; // 충돌 시 레드스크린
	private boolean escKeyOn = false; // esc키를 누르면 일시정지
	
	private AlphaComposite alpha; // 투명도
	
	private int[] sizeArr;  // 맵 사이즈를 저장할 배열
	private int[][] mapCArr; // 맵 픽셀값을 저장할 배열
    private int mapL;    // 맵의 길이
    
	private int gameSpeed = 5;
	private int runPage = 0;
	private int feverPage = 0;
	
	JFrame sFrame;
	CardLayout cardLayout;

	Vava vava; // 바바 객체
	
	int front;
	int foot;

	
	Screen back1;
	//Screen back12;
	//Screen back21;
	Screen back2;
	Screen feverBack;
	Screen originalBack;
	
	FeverGage feverGage;
	Timer feverTimer;
	Timer inFeverTimer;
	boolean inFeverTime;
	
	Color screenFade;
	
	GameObjectImg gameObje1;
	GameObjectImg gameObje2;
	GameObjectImg gameObje3;
	GameObjectImg gameObje4;
	
	GameObjectImg fever;
	
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
        // platform = new Platform(initialX, initialY, "platformImage.png"); // Platform 객체 초기화, 다시 수정해야함
        vavaFall = new VavaFall(platform); //VavaFall 객체 초기화
        
        // 아이템 버튼들 초기화
        useGiantItemButton = new JButton("거대화");
        
        useGiantItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (!vava.isGiant()) {
            	Item giantItem = new GiantItem("거대화 아이템");
                giantItem.use(vava); // vava에게 아이템 사용
                updateItemDurations(vava);
            	}
            }
        });
        
        useGiantItemButton.setBounds(10, 10, 150, 30); // 거대화 버튼 위치 설정 - 추후 조정 (x,y,width,height)
        add(useGiantItemButton);

        useBoosterItemButton = new JButton("부스터");
        
        useBoosterItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (!vava.isBooster()) {
            	Item boosterItem = new BoosterItem("부스터 아이템");
                boosterItem.use(vava); // vava에게 아이템 사용
                updateItemDurations(vava);
            	}
            }
        });

        useBoosterItemButton.setBounds(170, 10, 150, 30); // 부스터 버튼 위치 설정 - 추후 조정 (x,y,width,height)
        add(useBoosterItemButton);
        
        // Timer를 생성하고 주기적으로 updateItemDurations 메서드를 호출
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItemDurations(vava);
                // 추가 로직
            }
        });
        timer.start();
	}
	 private void updateItemDurations(Vava vava) {
		 if (vava != null) {
		        vava.updateItemDurations();
		    }
	    }

	
	
	// initListener()와 동일
	private void keyListenerSet() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (!escKeyOn) {
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					repaint();
						
					if (escKeyOn) {
						while (escKeyOn) {
							try {
								Thread.sleep(10);
								} 
							catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
						
					try {
						Thread.sleep(10);
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	// vava 이미지 설정
	private void vavaImgSet(VavaImg vobje) {
		vavaIc = vobje.getVavaIc();  // 기본
		hitIc = vobje.getHitIc(); // 충돌
		attackIc = vobje.getAttackIc(); // 공격
		//jumpIc = vobje.getJumpIc(); // 점프
		//doubleJumpIc = vobje.getDoubleJumpIc(); // 2단 점프
		fallIc = vobje.getFallIc(); // 2단 점프 후 낙하
	}	
	
	// 게임 속 오브젝트들의 이미지를 맵마다 다르게 저장해 둠.
	private void gameObjectStore() {
		try {
			gameObje1=new GameObjectImg(new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/Screen/bg1.png"), new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/Score/scoreA.png"));
			gameObje2=new GameObjectImg(new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/Screen/bback1.png"), new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/Score/scoreA.png"));
			gameObje3=new GameObjectImg(new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/Screen/back1.png"), new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/Score/scoreA.png"));
			gameObje4=new GameObjectImg(new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/Screen/screen_02.png"), new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/Score/scoreA.png"));
				
			fever=new GameObjectImg(new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/fever/FeverScreen.jpeg"), new ImageIcon("2023-JAVA-Swing-Project/img/GameObject/FeverScore.png"));
				
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error loading game object images.");
		}
	}

	// 장애물, 학점 등 이미지 설정
	private void gameObjectImgSet(GameObjectImg gobje) {
		hpCoffee = gobje.getHpCoffee();
		hpEDrink = gobje.getHpEDrink();
		
		obstacle1 = gobje.getObstacle1();
		obstacle2 = gobje.getObstacle2();
		obstacleDeath = gobje.getObstacleDeath();
		
		scoreA = gobje.getScoreA();
		scoreB = gobje.getScoreB();
		scoreC = gobje.getScoreC();
		
		scoreEffectIC = gobje.getscoreEffectIc();
		
		scoreAplus=gobje.getFeverScoreImg();
	}
	
	public void gameSet(VavaImg va) {
		setFocusable(true);
		vavaImgSet(va);
		gameObjeSet();
		keyListenerSet();
		gameRepaint();
	}
	
	public void gameStart() {
		gamePlayMapSet();
		fillFeverGage();
		// fall();
	}
	
	// 스윙 컴포넌트가 자신의 모양을 그리는 메서드
	@Override
	protected void paintComponent(Graphics g) {
		// Vava 이미지
		bufferg.drawImage(vava.getImage(), vava.getX(), vava.getY(), vava.getWidth(), vava.getHeight(), null);

		// 어택볼
		if (vavaAttack.isAttacking()) {
			bufferg.drawImage(vavaAttack.getAttackballImage(), vavaAttack.getAttackballX(), vavaAttack.getAttackballY(),
				            vavaAttack.getAttackballWidth(), vavaAttack.getAttackballHeight(), null);
		}  
		
		Graphics2D g2D = (Graphics2D) bufferg;
		
		// 더블 버퍼
		if (bufferg == null) {
			bufferImage = createImage(this.getWidth(), this.getHeight());
			
			if (bufferImage != null) 
				bufferg = bufferImage.getGraphics();
			else 
				System.out.println("실패");
		}
		
		super.paintComponent(bufferg);
		super.paintComponent(g);

		// 발판
		for (Platform platform : platforms) {
		    g.drawImage(platform.getImage(), platform.getX(), platform.getY(), this);
		}

		super.paintComponent(bufferg);

		// 장애물
		for (int i = 0; i < obstacleList.size(); i++) {
			Obstacle tmpObstacle = obstacleList.get(i);

			if (tmpObstacle.getX() > -90 && tmpObstacle.getX() < 180) {// 나중에 좌표 수정해야함 
				bufferg.drawImage(tmpObstacle.getImage(), tmpObstacle.getX(), tmpObstacle.getY(), tmpObstacle.getWidth(),
								tmpObstacle.getHeight(), null);
			}
		}
		// 충돌 시 레드스크린
		if (redScreenOn) {
			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 125 / 255);
			g2D.setComposite(alpha);

			bufferg.drawImage(redScreenIc.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2D.setComposite(alpha);
		}		
				
		// esc 키를 눌렀을 시 화면이 잠시 어두워짐
		if (escKeyOn) {
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
		for (int i = 0; i < scoreList.size(); i++) {
			Score tmpScore = scoreList.get(i);

			if (tmpScore.getX() > -90 && tmpScore.getX() < 810) {

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) tmpScore.getAlpha() / 255);
				g2D.setComposite(alpha);

				bufferg.drawImage(tmpScore.getImage(), tmpScore.getX(), tmpScore.getY(), tmpScore.getWidth(), tmpScore.getHeight(), null);
						
				// alpha 값 다시 되돌리기 (255로)
				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2D.setComposite(alpha);
					}
				}
				
		// fever score
		for (int i = 0; i < feverScoreList.size(); i++) {
			Score tmpFeverScore = scoreList.get(i);

			if (tmpFeverScore.getX() > -90 && tmpFeverScore.getX() < 810) {

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) tmpFeverScore.getAlpha() / 255);
				g2D.setComposite(alpha); // 투명화

				bufferg.drawImage(tmpFeverScore.getImage(), tmpFeverScore.getX(), tmpFeverScore.getY(), tmpFeverScore.getWidth(),
						tmpFeverScore.getHeight(), null);

				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2D.setComposite(alpha);
			}
		}
		
		//배경이미지 
		if (inFeverTime) {
			bufferg.drawImage(feverBack.getImage(), feverBack.getX(), 0, feverBack.getWidth(), feverBack.getHeight(), null);
		} else {
			bufferg.drawImage(back1.getImage(), back1.getX(), 0, back1.getWidth(), back1.getHeight(), null);
			bufferg.drawImage(back2.getImage(), back2.getX(), 0, back2.getWidth(), back2.getHeight(), null);
		}
		//bufferg.drawImage(back1.getImage(), back1.getX(), 0, back1.getWidth(), back1.getHeight(), null);
		//bufferg.drawImage(back2.getImage(), back2.getX(), 0, back2.getWidth(), back2.getHeight(), null);
		
		//스테이지 넘어갈 때 페이드 효과 
		if (fadeOn) {
			bufferg.setColor(screenFade);
			bufferg.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		
		// 바바 hp 게이지
		bufferg.drawImage(hpBar.getImage(), 20, 30, null);
		bufferg.setColor(Color.BLACK);
		bufferg.fillRect(84 + (int) (470 * ((double) vava.getHp() / 1000)), 65, 1 + 470 - (int) (470 * ((double) vava.getHp() / 1000)), 21);
		
		
		//피버타임 게이지 
		bufferg.drawImage(feverBar.getImage(), 20, 50, null);
		bufferg.setColor(Color.YELLOW);
		bufferg.fillRect(84 + (int) (470 * ((double) feverGage.getFg() / 100)), 65, 1 + 470 - (int) (470 * ((double) feverGage.getFg() / 100)), 21);
		
		
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
		
				// 점수를 그린다
		Emp.drawFancyString(g2D, Integer.toString(sumScore+feverSumScore), 600, 58, 30, Color.WHITE);
		
		// 화면에 그림
		g.drawImage(bufferImage, 0, 0, this); 
		// 화면에 그림
		// 화면 흐름 확인을 위한 로그 추가
	    //System.out.println("back1 X: " + back1.getX());
	    //System.out.println("back2 X: " + back2.getX());
	}
	// 장애물 충돌 (death 장애물 x)
	private void hitObstacle() {
		new Thread(new Runnable() {
			@Override
			public void run() {
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

	//충돌시 레드스크린 
	private void hitRedScreen() {
		try {
			Thread.sleep(500); // 0.5초 대기시킴. 수치는 나중에 수정.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		redScreenOn = false; // 레드스크린 off
	}	
	
	private void hitChangeIc() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (vava.getImage() == hitIc.getImage()) // 바바 모션을 기본으로 변경
			vava.setImage(vavaIc.getImage());
	}
	
	private void hitBlinkEffect() {
		for (int j = 0; j < 10; j++) {// 충돌하여 무적 상태임을 보여주기 위해 vava가 10번 깜빡거림.
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

	// 발판을 이동시킴
    private void movePlatforms() 
    {
        int platformSpeed = 5; // 발판 이동 속도, 나중에

        for (Platform platform : platforms) {
            platform.move(platformSpeed);
        }
    }

	public void update() {
		// 발판 이동 로직
		movePlatforms();
		// 그 외 게임 업데이트 로직
	}
// 이미지의 크기를 가져오는 메서드
	private int[] getSize(String src) throws Exception {
	    // 파일 경로로부터 이미지 파일을 읽어옴
		
	    /*File imgf = new File(src);
	    BufferedImage img = ImageIO.read(imgf);
	    
	    // 이미지의 너비와 높이를 가져와 배열에 저장
	    int width = img.getWidth();
	    int height = img.getHeight();
	    int[] tempSize = { width, height };
	    
	    // 이미지의 너비와 높이를 포함한 배열 반환
	    return tempSize;
	    */
		
		try {
	        // 파일 경로로부터 이미지 파일을 읽어옴
	        File imgf = new File(src);
	        BufferedImage img = ImageIO.read(imgf);

	        // 이미지의 너비와 높이를 가져와 배열에 저장
	        int width = img.getWidth();
	        int height = img.getHeight();
	        int[] tempSize = {width, height};

	        // 이미지의 너비와 높이를 포함한 배열 반환
	        return tempSize;
	    } catch (IOException e) {
	        // 파일을 읽을 수 없는 경우 예외 처리
	        e.printStackTrace();
	        //throw new Exception("Error reading image file: " + e.getMessage());
	        System.out.println("Error reading image file: " + e.getMessage());
	        System.exit(0); // 이미지 불러오기 실패 시 프로그램 종료
	        return null; // 아래 코드에서 사용하지 않기 위해 null 반환

	    }
	    

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

	//맵 초기화(그림판 이미지를 받아서 세팅)
	private void gameMapSet(int num, int l) {
		String tmpM = null;
		int tmpML = 0;


        if (num == 1) {
            tmpM = "img/map/map1.png";
        } else if (num == 2) {
            tmpM = "img/map/map2.JPEG";
        } else if (num == 3) {
            tmpM = "img/map/map3.JPEG";
        } else if (num == 4) {
            tmpM = "img/map/map4.JPEG";
        } else if (num == 5) {
        	tmpM = "img/map/map5.JPEG";
        }
        
        try {
            sizeArr = getSize(tmpM); // 맵 사이즈를 배열에 저장
            mapCArr  = getPic(tmpM); // 맵 픽셀값을 배열에 저장
            tmpML= sizeArr[0];
            this.mapL += tmpML; // mapLength 갱신
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
      // tmpMapLength = sizeArr[0];

        int maxX = sizeArr[0]; // 맵의 넓이
        int maxY = sizeArr[1]; // 맵의 높이

       // 학점
        for (int i = 0; i < maxX; i += 1) { 
        	for (int j = 0; j < maxY; j += 1) {
        		if (mapCArr[i][j] == 5) { // 색값이 5면 scoreA 생성
        			// 40 = 좌표에 곱하는 수, 30 = 높이 (수정할 때 참고) 
        			scoreList.add(new Score(scoreA.getImage(), i * 40 + l * 40, j * 40, 30, 30, 255, 1234));

        		} else if (mapCArr[i][j] == 10) { // 색값이 10이면 scoreB 생성
        			// 40 = 좌표에 곱하는 수, 30 = 높이 (수정할 때 참고) 
        			scoreList.add(new Score(scoreB.getImage(), i * 40 + l * 40, j * 40, 30, 30, 255, 2345));

        		} else if (mapCArr[i][j] == 15) { // 색값이 15면 scoreC 생성
        			// 40 = 좌표에 곱하는 수, 30 = 높이 (수정할 때 참고) 
        			scoreList.add(new Score(scoreC.getImage(), i * 40 + l * 40, j * 40, 30, 30, 255, 3456));
        		} 
        			// image, x좌표, y좌표, 너비, 높이, 투명도, 점수
        	}
        }
        
        //피버타임 스코어 
        for (int i = 0; i < maxX; i += 1) { // 젤리는 1칸을 차지하기 때문에 1,1사이즈로 반복문을 돌린다.
			for (int j = 0; j < maxY; j += 1) {
				if (mapCArr[i][j] == 46729) { // 색값이 16776960일 경우 기본젤리 생성
					// 좌표에 80을 곱하고, 넓이와 높이는 60으로 한다.
					feverScoreList.add(new FeverScore(scoreAplus.getImage(), i * 80 + l  * 80, j * 80, 60, 60, 255, 5678));

				} 
			}
		}
        // 일단 death 장애물은 제외
        for (int i = 0; i < maxX; i += 2) {// i 값 증가치 나중에 수정
			for (int j = 0; j < maxY; j += 2) {
				if (mapCArr[i][j] == 522) // 값은 나중에 수정
					obstacleList.add(new Obstacle(obstacle1.getImage(), i * 40 + l * 40, j * 40, 80, 80));
				else if (mapCArr[i][j] == 522) // 값은 나중에 수정
					obstacleList.add(new Obstacle(obstacle2.getImage(), i * 40 + l * 40, j * 40, 80, 160));
			}
		}
        //this.mapLength += tmpMapLength; // mapLength 갱신
	}
		
	//게임 오브젝트 초기화
	private void gameObjeSet() {
		vava = new Vava(vavaIc.getImage()); // vava 생성
		
		mapLthList = new ArrayList<>(); // 다음 맵의 시작지점을 확인하기위한 배열
		
		// 맵 인스턴스들을 생성
		gameObjectStore();
		
		gameMapSet(1, mapL);
		mapLthList.add(mapL);

		gameMapSet(2, mapL);
		mapLthList.add(mapL);

		gameMapSet(3, mapL);
		mapLthList.add(mapL);

		gameMapSet(4, mapL);
		mapLthList.add(mapL);
		
		gameObjectImgSet(fever);
		gameMapSet(5, mapL);
		mapLthList.add(mapL);
		
		screenFade = new Color(0,0,0,0);

		backScreenImg=gameObje1.getBackScreenImg();
		backScreenImg2=gameObje2.getBackScreenImg();
		backScreenImg3=gameObje3.getBackScreenImg();
		backScreenImg4=gameObje4.getBackScreenImg();
		
		feverScreen=fever.getBackScreenImg();
	    
		
		back1 = new Screen(backScreenImg.getImage(), 0, 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));
		back2 = new Screen(backScreenImg.getImage(), backScreenImg.getImage().getWidth(null), 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));
	/*
		//배경 인스턴스 생성 
		if (backScreenImg != null) {
		    back1 = new Screen(backScreenImg.getImage(), 0, 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));
		    back2 = new Screen(backScreenImg.getImage(), backScreenImg.getImage().getWidth(null), 0, backScreenImg.getImage().getWidth(null), backScreenImg.getImage().getHeight(null));
		    //feverBack = new Screen(feverScreen.getImage(), 0, 0, feverScreen.getImage().getWidth(null), feverScreen.getImage().getHeight(null));
		} else {
		    System.err.println("backScreenImg is null. Image loading failed.");
		}
	*/	
				
		obstacleList = new ArrayList<>(); // 장애물 리스트
  		scoreList = new ArrayList<>(); // 스코어 리스트
  		platforms = new ArrayList<>(); // 발판 리스트
  		feverScoreList = new ArrayList<>(); // 피버타임 스코어 리스트 
  		
  		platforms.add(platform); // 추가된 부분: List에 발판 추가
  		
  		hpBar = new ImageIcon("img/hpbar.png"); // 경로들은 나중에 수정
  		redScreenIc = new ImageIcon("img/redscreen.png");
  		feverBar = new ImageIcon("img/GameObject/Fever/feverBar.png"); // 피버타임 게이지바 추후 경로 수정 


	}
	
	//화면 움직임 
	private void gamePlayMapSet() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					runPage += gameSpeed; // 화면이 이동하면 runPage에 이동한 만큼 저장된다.
					
					//배경 이미지 변경 
					if (fadeOn == false) {//페이드아웃이 아닐때 
						if (mapL>mapLthList.get(2)*40+1080 && back1.getImage() != backScreenImg4.getImage()) {
							System.out.println("Condition 1: mapLength=" + mapL + ", mapLengthList=" + mapLthList.get(2));
							fadeOn=true;
							backScreenTransition(backScreenImg4);
						} else if (mapL>mapLthList.get(1)*40+1080 && mapL < mapLthList.get(2)*40+1080 && !back1.getImage().equals(backScreenImg3.getImage())) {
							System.out.println("Condition 2: mapLength=" + mapL + ", mapLengthList=" + mapLthList.get(1) + ", " + mapLthList.get(2));
							fadeOn=true;
							backScreenTransition(backScreenImg3);
						} else if (mapL>mapLthList.get(0)*40+1080 && mapL<mapLthList.get(1)*40+1080 && back1.getImage().equals(backScreenImg2.getImage())) {
							 System.out.println("Condition 3: mapLength=" + mapL + ", mapLengthList=" + mapLthList.get(0) + ", " + mapLthList.get(1));
							fadeOn=true;
							backScreenTransition(backScreenImg2);
						}
					}
				
					mapL+=gameSpeed;
					
					if (back1.getX() < -(back1.getWidth()-1)) {
						back1.setX(back1.getWidth());
					}
					if (back2.getX() < -(back2.getWidth()-1)) {
						back2.setX(back2.getWidth());
					}
					
					back1.setX(back1.getX()-gameSpeed/3);
					back2.setX(back2.getX()-gameSpeed/3);

					// 학점
					for (int i = 0; i < scoreList.size(); i++) {
						Score tmpScore = scoreList.get(i); // 리스트 안에 있는 개별 학점 불러옴

						if (tmpScore.getX() < -90) { // 학점의 x 좌표에 따른 학점 제거 (x좌표 조정 필요)
								// 발판리스트명.remove(tmpScore)
							} 
						else {

							// 스피드 조절 여기에 
							// ex) tmpScore.setX(tmpScore.getX() - gameSpeed); 
							
							if (tmpScore.getImage() == scoreEffectIC.getImage() && tmpScore.getAlpha() > 4) {
								tmpScore.setAlpha(tmpScore.getAlpha() - 5);
							}

							foot = vava.getY() + vava.getHeight(); // vava 발 위치 재스캔

							// 캐릭터의 범위 안에 학점이 있으면 아이템을 먹는다.
							if (tmpScore.getX() + tmpScore.getWidth() * 20 / 100 >= vava.getX() 
									&& tmpScore.getX() + tmpScore.getWidth() * 80 / 100 <= front
									&& tmpScore.getY() + tmpScore.getWidth() * 20 / 100 >= vava.getY()
									&& tmpScore.getY() + tmpScore.getWidth() * 80 / 100 <= foot 
									&& tmpScore.getImage() != scoreEffectIC.getImage()) {
								
								if (tmpScore.getImage() == hpCoffee.getImage() || tmpScore.getImage() == hpEDrink.getImage()) {
									if ((vava.getHp() + 100) > 1000) {
										vava.setHp(1000);
									} 
									else {
										vava.setHp(vava.getHp() + 100);
										}
									}
								tmpScore.setImage(scoreEffectIC.getImage()); // 젤리의 이미지를 이펙트로 바꾼다
								sumScore = sumScore + tmpScore.getScore(); // 총점수에 젤리 점수를 더한다

								}
							//vava 범위 안에 학점이 있으면 아이템을 먹음
							else if (tmpScore.getX() + tmpScore.getWidth() * 20 / 100 >= vava.getX() 
									&& tmpScore.getX() + tmpScore.getWidth() * 80 / 100 <= front
									&& tmpScore.getY() + tmpScore.getWidth() * 20 / 100 >= vava.getY() + vava.getHeight() * 1 / 3
									&& tmpScore.getY() + tmpScore.getWidth() * 80 / 100 <= foot
									&& tmpScore.getImage() != scoreEffectIC.getImage()) {
								
								if (tmpScore.getImage() == hpCoffee.getImage() || tmpScore.getImage() == hpEDrink.getImage()) {
									if ((vava.getHp() + 100) > 1000) {
										vava.setHp(1000);
									} 
									else {
										vava.setHp(vava.getHp() + 100);
									}
								}
								tmpScore.setImage(scoreEffectIC.getImage()); // 젤리의 이미지를 이펙트로 바꾼다
								sumScore = sumScore + tmpScore.getScore(); // 총점수에 젤리 점수를 더한다
							}
						}
					}
					// 장애물
					for (int i = 0; i < obstacleList.size(); i++) {
						Obstacle tmpObstacle = obstacleList.get(i);
							
						if (tmpObstacle.getX() < -90) { // 수치 나중에 수정
							// 나중에 추가
						}
						else {
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
					// 추가된 로그
	                //System.out.println("Game loop is running. runPage: " + runPage + ", mapLength: " + mapL);

					try {
	                    // 스레드가 너무 빨리 돌지 않도록 일시적으로 정지
	                    Thread.sleep(10);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

				}
			}

		}).start();
	
	}
	
	private void backScreenTransition(ImageIcon backScreenIc) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				screenFadeOut();
				
				back1=new Screen(backScreenIc.getImage(), 0, 0, backScreenIc.getImage().getWidth(null),backScreenIc.getImage().getHeight(null));
				back2=new Screen(backScreenIc.getImage(), backScreenIc.getImage().getWidth(null),0, backScreenIc.getImage().getWidth(null), backScreenIc.getImage().getHeight(null));
				
				screenFadeIn();
				fadeOn=false;
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
	
	// feverGage를 초기화하는 메서드
    public void initializeFeverGage(FeverGage feverGage) {
        this.feverGage = feverGage;
    }
	
	// 45초 동안 피버타임 게이지를 채우는 메서드
	private void fillFeverGage() {
			Timer fillGaugeTimer = new Timer(45 * 1000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            feverGage.setFg(100); // 게이지를 100으로 채움
	            startFeverTime(); // 피버타임 시작
	        }
	    });
	    fillGaugeTimer.setRepeats(false); // 한 번만 실행
	    fillGaugeTimer.start(); // 타이머 시작
	    
	  	}
	
	
	//피버타임 시작 
	private void startFeverTime() {
	    inFeverTime = true;
	    feverGage.setFg(0); // 게이지 초기화

	    // 현재 실행 중인 배경을 originalBack에 저장
	    originalBack = new Screen(back1.getImage(), back1.getX(), 0, back1.getWidth(), back1.getHeight());

	    // 화면 전환 코드를 추가
	    feverScreenTransition(feverBack);
	    
	    // 10초 후에 원래 상태로 돌아오는 타이머 설정
	    Timer returnToNormalTimer = new Timer(10 * 1000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//feverMapSet 메서드 호출
	    	    feverTImeSet();
	        	endFeverTime();
	        }
	    });
	    returnToNormalTimer.setRepeats(false); // 한 번만 실행
	    returnToNormalTimer.start();
	}
	
	//피버타임 종료 
	private void endFeverTime() {
	    inFeverTime = false;
	    feverGage.setFg(0); // 게이지 초기화

	    // 화면을 다시 원래 상태로 전환
	    feverScreenTransition(originalBack);
	}
	
	//피버타임 화면 전환 
	private void feverScreenTransition(Screen newBack) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				screenFadeOut();
				
				//originalBack = new Screen(back1.getImage(), back1.getX(), 0, back1.getWidth(), back1.getHeight());
				
				back1=newBack;
				
				screenFadeIn();
				fadeOn=false;
			}
		}).start();
	}
	
	private void feverTImeSet() {
		new Thread(new Runnable() {
			public void run() {
				feverPage +=gameSpeed; //화면이 이동하면 이동한 만큼 저장 
				
				feverBack.setX(feverBack.getX()-gameSpeed/3);
				
				// 젤리위치를 -4 씩 해준다.
				for (int i = 0; i < feverScoreList.size(); i++) {

					FeverScore tmpFeverScore = feverScoreList.get(i); // 임시 변수에 리스트 안에 있는 개별 젤리를 불러오자

					if (tmpFeverScore.getX() < -90) { // 젤리의 x 좌표가 -90 미만이면 해당 젤리를 제거한다.(최적화)

						//fieldList.remove(tempJelly);

					} else {

						if (tmpFeverScore.getImage() == feverScoreEffectIc.getImage() && tmpFeverScore.getAlpha() > 4) {
							tmpFeverScore.setAlpha(tmpFeverScore.getAlpha() - 5);
						}

						foot = vava.getY() + vava.getHeight(); // vava 발 위치 재스캔

						// 캐릭터의 범위 안에 학점이 있으면 아이템을 먹는다.
						if (tmpFeverScore.getX() + tmpFeverScore.getWidth() * 20 / 100 >= vava.getX() 
								&& tmpFeverScore.getX() + tmpFeverScore.getWidth() * 80 / 100 <= front
								&& tmpFeverScore.getY() + tmpFeverScore.getWidth() * 20 / 100 >= vava.getY()
								&& tmpFeverScore.getY() + tmpFeverScore.getWidth() * 80 / 100 <= foot 
								&& tmpFeverScore.getImage() != feverScoreEffectIc.getImage()) {
							tmpFeverScore.setImage(feverScoreEffectIc.getImage()); // 젤리의 이미지를 이펙트로 바꾼다
							feverSumScore = feverSumScore + tmpFeverScore.getScore(); // 총점수에 젤리 점수를 더한다 
							}
					}
					
				}
				
			}
		}).start();
	}
	
}
