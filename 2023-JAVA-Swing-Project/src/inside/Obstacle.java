package inside;

import java.awt.Image;

public class Obstacle {

	private Image image; // 장애물 이미지

	// 장애물 크기 및 좌표
	private int x;
	private int y;
	private int width;
	private int height;

	// 장애물 상태
	private int speed = 0; // death 장애물 속도
	private int countAttack = 0; // 2단 장애물이 공격받은 횟수 카운트

	private boolean death = false; // death 장애물 구분

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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCountAttack() {
		return countAttack;
	}

	public void setCountAttack(int countAttack) {
		this.countAttack = countAttack;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
