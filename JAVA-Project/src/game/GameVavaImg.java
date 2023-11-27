package game;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import inside.VavaImg;
import inside.Vava;

public class GameVavaImg extends JPanel {
	
	
	private ImageIcon ch01 = new ImageIcon("img/selectCh1.png");
	private ImageIcon start = new ImageIcon("img/GameStartBtn.png");
	
	private JButton ch1;
	private JButton StartBtn;
	private Image background; // 배경 이미지 추가
	private VavaImg vavaImg1;
	
	public VavaImg getVavaImg1() {
		return vavaImg1;
	}
	
	public GameVavaImg(Object o) {
		// 시작 버튼
		int screenWidth = 1080; // 창의 폭
		int buttonWidth = 291; // 버튼의 폭

		// 창의 가운데에 버튼을 위치시키기 위한 X 좌표 계산
		int xCoordinate = (screenWidth - buttonWidth) / 2;
		
		StartBtn = new JButton(start);
		StartBtn.setName("StartBtn");
		StartBtn.addMouseListener((MouseListener) o);
		//StartBtn.setBounds(254, 500, 291, 81);
		StartBtn.setBounds(xCoordinate, 500, 291, 81);
		add(StartBtn);
		StartBtn.setBorderPainted(false);
		StartBtn.setContentAreaFilled(false);
		StartBtn.setFocusPainted(false);
		
		ch1 = new JButton(ch01);
		ch1.setName("ch1");
		
		ch1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
        		vavaImg1 = new VavaImg(new ImageIcon("img/vava/player_attack.png"), 
        				new ImageIcon("img/vava/player_attack.png"), 
        				new ImageIcon("img/vava/player_attack.png"),
        				new ImageIcon("img/vava/player_attack.png")); // 대충 아무거나 넣어둠
			}
		});
		
		ch1.setBounds(90, 102, 150, 200);
		add(ch1);
		ch1.setBorderPainted(false);
		ch1.setContentAreaFilled(false);
		ch1.setFocusPainted(false);
		/*
		// 배경
		JLabel selectBg = new JLabel("");
		selectBg.setForeground(Color.ORANGE);
		selectBg.setHorizontalAlignment(SwingConstants.CENTER);
		selectBg.setIcon(new ImageIcon("img/backTest.png"));
		selectBg.setBounds(0, 0, 784, 461);
		add(selectBg);
*/
		// 배경 이미지
        background = new ImageIcon("img/backTest.png").getImage();
	}
	
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
	        // 배경 이미지 그리기
		 g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	 }
}