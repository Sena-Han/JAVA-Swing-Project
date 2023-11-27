package game;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
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
	
	
	private ImageIcon ch01 = new ImageIcon("2023-JAVA-Swing-Project/img/selectCh1.png");
	private ImageIcon start = new ImageIcon("2023-JAVA-Swing-Project/img/GameStartBtn.png");
	
	private JButton ch1;
	private JButton StartBtn;
	
	private VavaImg vavaImg1;
	
	public VavaImg getVavaImg1() {
		return vavaImg1;
	}
	
	public GameVavaImg(Object o)
	{
		// 시작 버튼
		StartBtn = new JButton(start);
		StartBtn.setName("StartBtn");
		StartBtn.addMouseListener((MouseListener) o);
		StartBtn.setBounds(254, 334, 291, 81);
		add(StartBtn);
		StartBtn.setBorderPainted(false);
		StartBtn.setContentAreaFilled(false);
		StartBtn.setFocusPainted(false);
		
		ch1 = new JButton(ch01);
		ch1.setName("ch1");
		
		ch1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
        		vavaImg1 = new VavaImg(new ImageIcon("2023-JAVA-Swing-Project/img/vava/player_attack.png"), 
        				new ImageIcon("2023-JAVA-Swing-Project/img/vava/player_attack.png"), 
        				new ImageIcon("2023-JAVA-Swing-Project/img/vava/player_attack.png"),
        				new ImageIcon("2023-JAVA-Swing-Project/img/vava/player_attack.png")); // 대충 아무거나 넣어둠
			}
		});
		
		ch1.setBounds(90, 102, 150, 200);
		add(ch1);
		ch1.setBorderPainted(false);
		ch1.setContentAreaFilled(false);
		ch1.setFocusPainted(false);
		
		// 배경
		JLabel selectBg = new JLabel("");
		selectBg.setForeground(Color.ORANGE);
		selectBg.setHorizontalAlignment(SwingConstants.CENTER);
		selectBg.setIcon(new ImageIcon("2023-JAVA-Swing-Project/img/backTest.png"));
		selectBg.setBounds(0, 0, 784, 461);
		add(selectBg);


	}
}
