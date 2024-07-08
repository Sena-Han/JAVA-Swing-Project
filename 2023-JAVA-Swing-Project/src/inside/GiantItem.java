package inside;

import java.awt.Image;

public class GiantItem extends Item {
    public GiantItem(Image image, int x, int y, int width, int height, int alpha) {
        super(image, x, y, width, height, alpha);
    }

    public void applyGrowth() {
        // 캐릭터 크기를 증가시키는 등의 효과 적용
    }
}