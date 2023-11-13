package inside;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

public class VavaJump implements KeyListener {
	private Vava vava;
	
	private ImageIcon jumpIc; // 점프 모션
	private ImageIcon doubleJumpIc; // 2단 점프 모션
	
    private boolean canDoubleJump = false;// 2단 점프 가능 여부 추적
    private boolean isJumping = false;// 현재 점프 중인지 여부 추적

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
    
    public VavaJump(Vava vava) {
        this.vava = vava;
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
        
    private void jump() {
    	if (!vava.isFall() && !vava.isJump()) {
            if (vava.getCountJump() == 0) {
                // 1단 점프
            	vava.setImage(jumpIc.getImage()); // 점프 이미지로 변경
            } 
            else if (vava.getCountJump() == 1) {
                // 2단 점프
            	vava.setImage(doubleJumpIc.getImage()); // 2단 점프 이미지로 변경
            }

            vava.setJump(true);
            vava.setCountJump(vava.getCountJump() + 1);
            isJumping = true;
        //점프 애니메이션 등을 처리
    	}
    }
}
