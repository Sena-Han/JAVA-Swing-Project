package inside;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VavaControl implements KeyListener {
	private boolean canDoubleJump = false; // 2단 점프 가능 여부를 추적
    private boolean isJumping = false; // 현재 점프 중인지 여부를 추적
    
    // 추가: 타이머로 그래픽 업데이트를 처리하기 위한 변수들
    private Timer timer;
    private int backgroundX = 0;
    private int platformX = 0;

    public VavaControl() {
    	// 추가: 타이머 초기화
        timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 타이머마다 실행되는 코드
                updateGraphics();
            }
        });
        timer.start();
    }

    public VavaControl(boolean canDoubleJump, boolean isJumping) {
        this.canDoubleJump = canDoubleJump;
        this.isJumping = isJumping;
    }
    
    private void updateGraphics() {
        // 그래픽 업데이트 코드를 여기에 추가
        // 예를 들어, 배경이나 발판을 그리는 코드를 작성
        backgroundX--;
        platformX--;
    }

    public boolean isCanDoubleJump() {
        return canDoubleJump;
    }

    public void setCanDoubleJump(boolean canDoubleJump) {
        this.canDoubleJump = canDoubleJump;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            if (!isJumping) {
                // 1단 점프
                jump();
                System.out.println("1단 점프");
            } else if (canDoubleJump) {
                // 2단 점프
                jump();
                System.out.println("2단 점프");
                canDoubleJump = false;
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
        	// 우측키로 공격
            attack();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	 // 키를 뗄 때
    }

    @Override
    public void keyTyped(KeyEvent e) {
    	// 키를 타이핑할 때
    }

    // 캐릭터의 점프 동작을 처리
    private void jump() {
        // 점프 로직을 여기에 작성
        // isJumping 상태를 변경하고 점프 애니메이션 등을 처리
    }

    // 캐릭터의 공격 동작을 처리
    private void attack() {
        // 공격 로직을 여기에 작성
        // 적 공격, 애니메이션 등을 처리

        // 예시: 특정 조건에서 장애물 부수기
//        if (checkCollisionWithObstacle()) {
//            System.out.println("공격으로 장애물 부수기");
//            // 부수기 로직을 추가
//
//            // 예시: 부수고 난 후의 처리
//            handleObstacleDestroyed();
        }
    }
