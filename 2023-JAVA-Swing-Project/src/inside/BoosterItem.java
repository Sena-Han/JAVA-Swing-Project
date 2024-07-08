package inside;

import java.awt.Image;


public class BoosterItem extends Item {
    public BoosterItem(Image image, int x, int y, int width, int height, int alpha) {
        super(image, x, y, width, height, alpha);
    }

    public void applyBoost() {
        // 캐릭터 속도를 증가시키는 등의 효과 적용
    }
}