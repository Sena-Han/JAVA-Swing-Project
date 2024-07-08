package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.GameEnd;
import game.GameCore;
import game.GameStory;
import game.GameVavaImg;
import game.GameIntro;
import inside.VavaImg;
import main.listenAdapter;

import java.awt.CardLayout;

public class Main extends listenAdapter {

	private JFrame frame;
	private GameIntro gameIntro;
	private GameStory gameStory;
	private GameVavaImg gameVavaImg;       
	private GameCore gameCore;       
	private GameEnd gameEnd;
	private CardLayout cardLayout;
	private VavaImg vava;

	public GameCore getGameCore() {
		return gameCore;
	}

	public void setGameCore(GameCore game) {
		this.gameCore = game;
	}

	public GameEnd getGameEnd() {
		return gameEnd;
	}


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
		init();
	}

	private void init() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 485);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            
		frame.setTitle("바바야 졸업하자!");
		cardLayout = new CardLayout(0, 0);  
		frame.getContentPane().setLayout(cardLayout);       

		gameIntro = new GameIntro();
		gameIntro.addMouseListener(this);
		
		gameVavaImg = new GameVavaImg(this);
		gameCore = new GameCore(frame, cardLayout, this);
		gameEnd = new GameEnd(this);

		gameStory = new GameStory(frame, cardLayout, this);
		gameStory.addMouseListener(this);
		
		gameIntro.setLayout(null);
		gameStory.setLayout(null);
		gameVavaImg.setLayout(null);
		gameCore.setLayout(null);
		gameEnd.setLayout(null);

		frame.getContentPane().add(gameIntro, "intro");
		frame.getContentPane().add(gameStory, "story");
		frame.getContentPane().add(gameVavaImg, "vavaimg");
		frame.getContentPane().add(gameCore, "game");
		frame.getContentPane().add(gameEnd, "end");

	}

	@Override
	public void mousePressed(MouseEvent e) {
	    if (e.getComponent().toString().contains("GameIntro")) 
	    {
	        try {
	            Thread.sleep(300);
	        } catch (InterruptedException e1) {
	            e1.printStackTrace();
	        }
	        
	        cardLayout.show(frame.getContentPane(), "story");
	        gameStory.requestFocus();
	        
	    } else if (e.getComponent().toString().contains("StoryPanel")) 
	    {
	        if (gameStory.getCurrentImageIndex() == gameStory.getImageNum()-1) {
	            // 마지막 이미지를 눌렀을 때
	            cardLayout.show(frame.getContentPane(), "vavaimg");
	            gameVavaImg.requestFocus();
	        } else {
	            // 마지막 이미지가 아닐 때는 다음 이미지로 이동
	            gameStory.moveToNextImage();
	        }
	    } else if (e.getComponent().getName() != null) 
	    {
	        // 나머지 버튼들의 처리
	        if (e.getComponent().getName().equals("start")) 
	        {
	        	cardLayout.show(frame.getContentPane(), "game");
                gameCore.gameSet(gameVavaImg.getVava());
                gameCore.gameStart();
                gameCore.requestFocus();
	        } else if (e.getComponent().getName().equals("end")) 
	        {
	            // endAccept 처리
	            frame.getContentPane().remove(gameCore);
	            gameCore = new GameCore(frame, cardLayout, this);
	            gameCore.setLayout(null);
	            frame.getContentPane().add(gameCore, "game");

	            frame.getContentPane().remove(gameVavaImg);
	            gameVavaImg = new GameVavaImg(this);
	            gameVavaImg.setLayout(null);
	            frame.getContentPane().add(gameVavaImg, "vavaimg");
	            cardLayout.show(frame.getContentPane(), "vavaimg");
	            gameVavaImg.requestFocus();
	        }
	    }
	}
	
	// Main 클래스에 handleStoryEnd() 메서드 추가
	public void handleStoryEnd() {
		System.out.println("Current Image Index: " + gameStory.getCurrentImageIndex());
	    System.out.println("Image Number: " + gameStory.getImageNum());
	    
	    cardLayout.show(frame.getContentPane(), "vavaimg");
	    gameVavaImg.requestFocus();
	}
}
