package inside; 

import java.awt.Image;
import java.awt.Toolkit;

public class Platform {
    private int x; // 발판의 x 좌표
    private int y; // 발판의 y 좌표
    private int platformWidth; // 발판의 너비
    private int platformHeight; // 발판의 높이
    private Image image; // 발판의 이미지

    public Platform(int x, int y, String imagePath) {
        this.x = x;
        this.y = y;
        this.image = Toolkit.getDefaultToolkit().getImage(imagePath);
        this.platformWidth = image.getWidth(null);
        this.platformHeight = image.getHeight(null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlatformWidth() {
        return platformWidth;
    }

    public int getPlatformHeight() {
        return platformHeight;
    }
    
    public void setPlatformHeight(int height) {
        this.platformHeight = height;
    }

    public Image getImage() {
        return image;
    }

    // 발판을 이동시키는 메서드
    public void move(int speed) {
        this.x -= speed;
        
        
    }
}
