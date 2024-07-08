package game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import inside.VavaImg;

public class GameVavaImg extends JPanel {
	
    // 캐릭터 이미지
    private ImageIcon ch01 = new ImageIcon("img/vavaUI/vava.png");
    // 시작 버튼 이미지
    private ImageIcon start = new ImageIcon("img/vavaUI/button.png");
    
    // 배경 이미지
    private JLabel backScreen;
    // 시작 버튼
    private JButton StartB;
    // 캐릭터 이미지를 저장할 객체
    private VavaImg vava;

    // 캐릭터 이미지를 반환하는 메서드
    public VavaImg getVava() {
        return vava;
    }

    public GameVavaImg(Object object) {

        // 캐릭터 버튼 (눌러도 아무 동작 없음)
        JButton ch1 = new JButton(ch01);
        ch1.setName("ch1");
        ch1.setBounds(50, 102, 292, 282);
        add(ch1);
        ch1.setBorderPainted(false);
        ch1.setContentAreaFilled(false);
        ch1.setFocusPainted(false);
        
        // 시작 버튼
        StartB = new JButton(start);
        StartB.setName("start");
        StartB.addMouseListener((MouseListener) object);
        StartB.setBounds(410, 310, 291, 81);
        add(StartB);
        StartB.setBorderPainted(false);
        StartB.setContentAreaFilled(false);
        StartB.setFocusPainted(false);

        // 배경
        backScreen = new JLabel("");
        backScreen.setIcon(new ImageIcon("img/vavaUI/backScreen.png"));
        backScreen.setBounds(0, 0, 784, 461);
        add(backScreen);

        // 초기 캐릭터 이미지 설정
        vava = new VavaImg(new ImageIcon("img/vava/vava_walk.gif"),
                new ImageIcon("img/vava/vava_hit.png"),
                new ImageIcon("img/vava/vava_attack.gif"),
                new ImageIcon("img/vava/vava_fall.png"),
                new ImageIcon("img/vava/vava_jump.png"),
                new ImageIcon("img/vava/vava_jump.png"));
    }
    
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
     // 배경 이미지를 다시 그려서 크기를 조절
        if (backScreen.getIcon() != null) {
            g.drawImage(((ImageIcon) backScreen .getIcon()).getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
