package game;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.sound.sampled.Clip;

import inside.BGM;

import main.Main;

public class GameStory extends JPanel{
	JFrame sFrame;
	CardLayout cardLayout;
	Main main;
	
	private BGM introMusic;
	
	ImageIcon[] storyImages;
	int currentImageIndex;
	int imageNum;
	
	public GameStory(JFrame sFrame, CardLayout cardLayout, Object o) {
		this.sFrame = sFrame;
		this.cardLayout = cardLayout;
		
		if (o instanceof Main) {
            this.main = (Main) o;
            introMusic = new BGM("sound/introMusic.wav", true);
        } else {
            // 예외 처리 또는 기본값 설정
            System.out.println("올바른 타입이 아닙니다.");
            this.main = null; // 또는 다른 기본값 설정
        }
		
		requestFocus();
		
		storyImages = new ImageIcon[] {
				new ImageIcon("img/story/story0.png"),
				new ImageIcon("img/story/story1.png"),
				new ImageIcon("img/story/story2.png"),
				new ImageIcon("img/story/story3.png"),
				new ImageIcon("img/story/story4.png"),
				new ImageIcon("img/story/story5.png"),
		};

		currentImageIndex=0;
		imageNum=storyImages.length; // 초기값을 이미지 배열의 크기로 설정
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // mousePressed 이벤트가 발생할  다음 스토리 이미지로 전환
                currentImageIndex++;
                if (currentImageIndex < storyImages.length) {
                    repaint(); // 화면 다시 그리기
                } else {
                    // 스토리가 끝났을 때 다음 화면으로 전환하는 코드 
                    main.handleStoryEnd(); // Main 클래스의 메서드 호출
                    introMusic.stop();
                }
            }   
		});
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 현재 스토리 이미지 그리기
		if (currentImageIndex < storyImages.length) {
			ImageIcon currentImage = storyImages[currentImageIndex];
			g.drawImage(currentImage.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
 	}
	
	public int getCurrentImageIndex() {
        return currentImageIndex;
    }
	
	public int getImageNum() {
		return imageNum;
	}
	
    public void moveToNextImage() {
        currentImageIndex++;
        if (currentImageIndex < storyImages.length) {
            repaint();
        }
    }
}