package inside;

import java.awt.Image;
import java.awt.Toolkit;

public class VavaAttack {
    private boolean isAttacking = false;
    private int attackballX; 
    private int attackballY; 
    private Image attackballImage; 
    private int attackballWidth; 
    private int attackballHeight; 

    public VavaAttack() {
        attackballX = -1; 
        attackballY = -1; 
        attackballImage = Toolkit.getDefaultToolkit().getImage("attackball.png");
        attackballWidth = attackballImage.getWidth(null); 
        attackballHeight = attackballImage.getHeight(null); 
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void startAttack(Vava vava) {
        isAttacking = true;
        attackballX = vava.getX(); 
        attackballY = vava.getY(); 
    }

    public void endAttack() {
    	
        isAttacking = false;
        attackballX = -1; 
        attackballY = -1; 
    }

public void updateGraphics() {
    // 공격에 관련된 그래픽 업데이트 코드
    if (isAttacking) {
        attackballX += 5; // 5 픽셀씩 이동
    }
}

public int getAttackballX() { 
    return attackballX; 
}

public int getAttackballY() { 
    return attackballY; 
}

public Image getAttackballImage() { 
    return attackballImage; 
}

public int getAttackballWidth() { 
    return attackballWidth; 
}

public int getAttackballHeight() { 
    return attackballHeight; 
}
}