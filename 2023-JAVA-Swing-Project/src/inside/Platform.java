package inside; 

import java.awt.Image;
import java.awt.Toolkit;

public class Platform {
    private int x; // 발판의 x 좌표
    private int y; // 발판의 y 좌표
    private int width; // 발판의 너비
    private int height; // 발판의 높이
    private Image image; // 발판의 이미지

    public Platform(int x, int y, String imagePath) {
        this.x = x;
        this.y = y;
        this.image = Toolkit.getDefaultToolkit().getImage(imagePath);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    // 발판을 이동시키는 메서드
    public void move(int speed) {
        this.x -= speed;
        
        
    }
}
