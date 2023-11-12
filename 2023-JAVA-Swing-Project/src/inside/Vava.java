package inside;

import java.awt.Image;

public class Vava {

	private Image image; // 바바 이미지

	// 바바 크기 및 좌표
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;

	// 바바 상태
	private int hp = 1000; // 바바 체력
	private int alpha = 255; // 바바 투명도

	private int big = 0; // 거대화 남은 시간
	private int fast = 0; // 가속화 남은 시간

	private int countJump = 0; // 점프 횟수

	private boolean invincible = false; // 무적 여부
	private boolean fall = false; // 낙하 여부
	private boolean jump = false; // 점프 여부

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

	public int getBig() {
		return big;
	}

	public void setBig(int big) {
		this.big = big;
	}

	public int getFast() {
		return fast;
	}

	public void setFast(int fast) {
		this.fast = fast;
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
}