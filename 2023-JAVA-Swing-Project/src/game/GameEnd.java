package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.sound.sampled.Clip;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import inside.BGM;

public class GameEnd extends JPanel {
	
	private BGM happyEnd;
	private BGM badEnd;
	
	ImageIcon button = new ImageIcon("img/end/button.png");
	JButton retry;
	JLabel text;
	JLabel screen;
	
	private int result;
	
	public void setResultScore(int result) {
		text.setText(result+"");
	}
	
	public void setEnding(boolean haveJ, int score) {
		String imagePath;
		
		if (haveJ || score > 1000000) {
			happyEnd = new BGM("sound/happyEnd.wav", false);
			imagePath="img/end/ending1.png";
		} else {
            badEnd = new BGM("sound/badEnd.wav", false);//여기추가
			imagePath="img/end/ending2.png";
		}
		 // 배경 이미지 설정
        screen.setIcon(new ImageIcon(imagePath));
	}

	public GameEnd(Object o) {
		retry = new JButton(button);
		retry.setName("end");
		retry.addMouseListener((MouseListener) o);
		retry.setBounds(400, 310, 291, 81);
		retry.setBorderPainted(false);
		retry.setFocusPainted(false);
		retry.setContentAreaFilled(false);
		add(retry);

		text = new JLabel("0");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 49));
		text.setBounds(315, 150, 460, 85);
		add(text);
		
		screen = new JLabel("");
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setBackground(SystemColor.activeCaptionText);
		screen.setBounds(0, 0, 785, 462);
		add(screen);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 이미지를 다시 그려서 크기를 조절
        if (screen.getIcon() != null) {
            g.drawImage(((ImageIcon) screen.getIcon()).getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

}
