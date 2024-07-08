package inside;

import java.awt.Image;

public class Item {
	private Image image; // 아이템 형상화 이미지

	// 아이템 이미지 크기 및 좌표
	private int x;
	private int y;
	private int width;
	private int height;
	private float alpha;

	public Item(Image image, int x, int y, int width, int height, int alpha) {
	        this.image = image;
	        this.x = x;
	        this.y = y;
	        this.width = width;
	        this.height = height;
	        this.alpha=alpha;
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

}