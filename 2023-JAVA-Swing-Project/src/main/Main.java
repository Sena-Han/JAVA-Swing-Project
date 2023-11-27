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
import inside.Vava;
import inside.VavaImg;
import outside.GameIntro;


public class Main extends MouseAdapter {
	
	private JFrame frame;
	
	private GameIntro introPanel; // 게임 시작 화면 
	private GameStory storyPanel; //바바 스토리
	private GameCore gamePanel;
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

		introPanel = new GameIntro();
		introPanel.addMouseListener(this); // intro패널은 여기서 바로 넣는 방식으로 마우스리스너를 추가함.
		
		storyPanel = new GameStory(frame, cardLayout, this);
		storyPanel.addMouseListener(this);
		
		gamePanel = new GameCore(frame, cardLayout, this);
		
		introPanel.setLayout(null);
		storyPanel.setLayout(null);
		gamePanel.setLayout(null);
		
		// 프레임에 패널들을 추가한다.(카드 레이아웃을 위한 패널들)
		frame.getContentPane().add(introPanel, "intro");
		frame.getContentPane().add(storyPanel, "story");
		frame.getContentPane().add(gamePanel, "game");
		}
	
	@Override
	public void mousePressed(MouseEvent e) { // mouseClicked로 변경가능
		if (e.getComponent().toString().contains("IntroPanel")) { // IntroPanel에서 마우스를 눌렀다면
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			cardLayout.show(frame.getContentPane(), "story"); // storyt패널을 카드레이아웃 최상단으로 변경
			storyPanel.requestFocus(); // 리스너를 story패널에 강제로 줌
		} else if (e.getComponent().toString().contains("StoryPanel")) {
			   // 스토리 패널의 마지막 이미지를 눌렀는지 확인
			if (storyPanel.getCurrentImageIndex() == storyPanel.getImageNum() - 1) {
		        // 마지막 이미지를 눌렀다면, 게임 패널로 전환
		        cardLayout.show(frame.getContentPane(), "game");
		        gamePanel.gameSet(va);
		        gamePanel.gameStart();
		        gamePanel.requestFocus();
		        // 스토리 패널에서 게임 패널로의 전환에 필요한 추가 로직을 추가
		        // 예를 들어, 게임을 초기화하거나 필요한 데이터를 설정하는 등의 작업을 수행할 수 있습니다.
		    } else {
		        // 다음 스토리 이미지로 이동
		        storyPanel.moveToNextImage();
		    }
			
			// 여기에 스토리 패널의 마지막 이미지를 눌렀을 때의 동작 추가
	        // 예를 들어, gamePanel로 전환하는 코드를 추가합니다. 

	        // 여기서는 gamePanel로 전환하는 코드를 추가합니다.
	        //cardLayout.show(frame.getContentPane(), "game");  // "game"은 gamePanel을 나타내는 카드의 이름입니다.
	        //gamePanel.gameSet();
	        //gamePanel.gameStart();
	        //gamePanel.requestFocus();  // gamePanel에 포커스를 설정하여 마우스 이벤트를 감지할 수 있도록 합니다.
	        
	        // 그 외에 스토리 패널에서 gamePanel로의 전환에 필요한 로직을 추가합니다.
	        // 예를 들어, 게임을 초기화하거나 필요한 데이터를 설정하는 등의 작업을 수행할 수 있습니다.
	    }
	}
}