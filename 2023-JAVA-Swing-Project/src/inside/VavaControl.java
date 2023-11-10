package inside;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VavaControl implements KeyListener {
    private boolean canDoubleJump = false; // 2단 점프 가능 여부를 추적
    private boolean isJumping = false; // 현재 점프 중인지 여부를 추적

    public VavaControl() {
        // Default constructor
    }

    public VavaControl(boolean canDoubleJump, boolean isJumping) {
        this.canDoubleJump = canDoubleJump;
        this.isJumping = isJumping;
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
    }
}