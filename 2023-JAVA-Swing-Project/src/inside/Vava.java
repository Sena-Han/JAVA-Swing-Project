package inside;

import java.awt.Image;

public class Vava {

	private Image image; // 바바 이미지

	// 바바 크기 및 좌표
	private int x = 160;
	private int y = 0;
	private int width = 80;
	private int height = 120;

	private int hp = 1000; // 바바 체력
	private int alpha = 255; // 바바 투명도
	
	private int countJump = 0; // 점프 횟수
	private int countAttack = 0; //공격 횟수

	private int fastTime = 10; // 부스터 지속 시간
	private int bigTime = 10; // 거대화 지속 시간

	private boolean jump = false; // 점프
	private boolean fall = false; // 낙하
	private boolean attack = false; // 공격
	private boolean giant = false; // 거대화 상태
	
	private boolean invincible = false; // 무적

	public Vava(Image image) {
		this.setImage(image);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getBigTime() {
		return bigTime;
	}

	public void setBigTime(int big) {
		this.bigTime = big;
	}

	public int getFastTime() {
		return fastTime;
	}

	public void setFastTime(int fast) {
		this.fastTime = fast;
	}

	public int getCountJump() {
		return countJump;
	}

	public void setCountJump(int countJump) {
		this.countJump = countJump;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}
	
	public boolean isGiant() {
	    return giant;
	}

	public void setGiant(boolean giant) {
	    this.giant = giant;
	}

	public boolean isFall() {
		return fall;
	}

	public void setFall(boolean fall) {
		this.fall = fall;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getCountAttack() {
		return countAttack;
	}

	public void setCountAttack(int countAttack) {
		this.countAttack = countAttack;
	}

	public boolean isAttack() {
		return attack;
	}

	public void setAttack(boolean attack) {
		this.attack = attack;
	}
	public void applyBoost() {
		setFastTime(8);
    }

    public void resetBoost() {
    	setFastTime(0); 
    }
    
    public void applyGiant() {
		int currentHeight = getHeight();
		int newHeight = currentHeight + 50;
		setHeight(newHeight);
		
		int currentWidth = getWidth();
		int newWidth= currentWidth + 50;
		setWidth(newWidth);
    }

    public void resetGiant() {
    	setWidth(getWidth() -50); // 원상태로
    	setHeight(getHeight() -50); // 원상태로
    	}
}