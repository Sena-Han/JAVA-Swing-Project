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


import panels.IntroPanel;
import panels.StoryPanel;
//import main.ListenAdapter;


public class Main extends MouseAdapter {
	
	private JFrame frame;
	
	private IntroPanel introPanel; // 게임 시작 화면 
	private StoryPanel storyPanel; //바바 스토리
	
	private CardLayout cardLayout;

	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
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
	
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1080, 720); // 창 사이즈 (100,100좌표는 아래의 frame.setLocationRelativeTo(null) 때문에 의미가 없어진다)
		frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 엑스버튼을 누르면 종료
		
		cardLayout = new CardLayout(0, 0); // 카드레이아웃 객체 생성
		frame.getContentPane().setLayout(cardLayout); // 프레임을 카드레이아웃으로 변경

		introPanel = new IntroPanel();
		introPanel.addMouseListener(this); // intro패널은 여기서 바로 넣는 방식으로 마우스리스너를 추가함.
		storyPanel = new StoryPanel(frame, cardLayout, this);
		storyPanel.addMouseListener(this);
		
		introPanel.setLayout(null);
		
		// 프레임에 패널들을 추가한다.(카드 레이아웃을 위한 패널들)
		frame.getContentPane().add(introPanel, "intro");
		frame.getContentPane().add(storyPanel, "story");
	}
	
	@Override
	public void mousePressed(MouseEvent e) { // mouseClicked로 변경가능
		if (e.getComponent().toString().contains("IntroPanel")) { // IntroPanel에서 마우스를 눌렀다면
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			cardLayout.show(frame.getContentPane(), "story"); // select패널을 카드레이아웃 최상단으로 변경
			storyPanel.requestFocus(); // 리스너를 select패널에 강제로 줌
			
		}
	}
}