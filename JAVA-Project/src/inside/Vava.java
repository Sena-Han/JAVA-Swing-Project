package inside;

import java.awt.Image;

public class Vava {

	private Image image; // 바바 이미지

	// 바바 크기 및 좌표
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;

	private int hp = 1000; // 바바 체력
	private int alpha = 255; // 바바 투명도
	
	private int countJump = 0; // 점프 횟수
	private int countAttack = 0; //공격 횟수

	private int fastTime = 10; // 부스터 지속 시간
	private int bigTime = 10; // 거대화 지속 시간

	private boolean jump = false; // 점프
	private boolean fall = false; // 낙하
	
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
	private boolean isGiant; // 거대화 아이템 사용 여부 플래그
    private boolean isBooster; // 부스터 아이템 사용 여부 플래그

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public boolean isBooster() {
        return isBooster;
    }

    public void setBooster(boolean booster) {
        isBooster = booster;
    }
	public void updateItemDurations() {
        if (fastTime > 0) {
            fastTime--;
            if (fastTime == 0) {
                // 부스터 효과 만료
                
            }
        }

        if (bigTime > 0) {
            bigTime--;
            if (bigTime == 0) {
                // 거대화 효과 만료
                
            }
        }
    }
}