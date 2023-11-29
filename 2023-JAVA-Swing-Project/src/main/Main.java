package main;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.GameCore;
import game.GameStory;
import game.GameVavaImg;
import inside.Vava;
import inside.VavaImg;
import outside.GameIntro;


public class Main extends MouseAdapter {
	
	private JFrame frame;
	
	private GameIntro gameIntro; // 게임 시작 화면 
	private GameStory gameStory; //바바 스토리
	private GameCore gameCore;
	private GameVavaImg gameVavaImg;
	private VavaImg va;

	private CardLayout cardLayout;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1080, 720); // 창 사이즈 (100,100좌표는 아래의 frame.setLocationRelativeTo(null) 때문에 의미가 없어진다)
		frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 엑스버튼을 누르면 종료
		frame.setTitle("바바야 졸업하자!");
		cardLayout = new CardLayout(0, 0); // 카드레이아웃 객체 생성
		frame.getContentPane().setLayout(cardLayout); // 프레임을 카드레이아웃으로 변경

		gameVavaImg = new GameVavaImg(this);
		
		gameIntro = new GameIntro();
		gameIntro.addMouseListener(this); // intro패널은 여기서 바로 넣는 방식으로 마우스리스너를 추가함.
		
		gameStory = new GameStory(frame, cardLayout, this);
		gameStory.addMouseListener(this);
		
		gameCore = new GameCore(frame, cardLayout, this);
		
		gameIntro.setLayout(null);
		gameStory.setLayout(null);
		gameCore.setLayout(null);
		gameVavaImg.setLayout(null);
		
		// 프레임에 패널들을 추가한다.(카드 레이아웃을 위한 패널들)
		frame.getContentPane().add(gameIntro, "intro");
		frame.getContentPane().add(gameStory, "story");
		frame.getContentPane().add(gameCore, "game");
		frame.getContentPane().add(gameVavaImg, "img");
		}
	
	@Override
	public void mousePressed(MouseEvent e) { // mouseClicked로 변경가능
		if (e.getComponent().toString().contains("GameIntro")) { // IntroPanel에서 마우스를 눌렀다면
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			cardLayout.show(frame.getContentPane(), "story"); // story패널을 카드레이아웃 최상단으로 변경
			gameStory.requestFocus(); // 리스너를 story패널에 강제로 줌
		} else if (e.getComponent().toString().contains("StoryPanel")) {
			if (gameStory.getCurrentImageIndex() == gameStory.getImageNum() - 1) {
	            cardLayout.show(frame.getContentPane(), "img"); // 마지막 이미지를 눌렀다면 GameVavaImg 패널로 전환
	            gameVavaImg.requestFocus(); // 리스너를 gameVavaImg 패널에 강제로 줌
	        } else {
	            gameStory.moveToNextImage();
	        }
	    } else if (e.getComponent().toString().contains("StartBtn")) {
	    	if (gameVavaImg.getVavaImg1() == null)
	    		System.out.println("vavaImg is null");
	    	else
	    	{
	    		cardLayout.show(frame.getContentPane(), "game");  // "game"은 gamePanel을 나타내는 카드의 이름입니다.
	    		gameCore.gameSet(gameVavaImg.getVavaImg1());
	    		gameCore.gameStart();
	    		gameCore.requestFocus();  // gamePanel에 포커스를 설정하여 마우스 이벤트를 감지할 수 있도록 합니다.
	    	}
	    }
	}
	public void handleStoryEnd() {
	    System.out.println("Current Image Index: " + gameStory.getCurrentImageIndex());
	    System.out.println("Image Number: " + gameStory.getImageNum());


        if (gameStory.getCurrentImageIndex() == gameStory.getImageNum()) {
            cardLayout.show(frame.getContentPane(), "img");
            gameVavaImg.requestFocus();
        }
    }
}