package inside;

import java.awt.Image;

public class Score {
	
	private Image image; // 학점(스코어) 형상화 이미지

	// 학점 이미지 크기 및 좌표
	private int x;
	private int y;
	private int width;
	private int height;
	
	// 스코어 점수
	private int sumScore;
	
	// 젤리 먹으면 투명해지게 하도록 하는 투명도 조절 변수
	private float alpha;

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getAlpha() {
		return alpha;
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public int getScore() {
		return sumScore;
	}

}