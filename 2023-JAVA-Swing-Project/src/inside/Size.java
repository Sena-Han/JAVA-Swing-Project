package inside;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Size {
	public static int[] getSize(String src) throws Exception {
        Path filePath = Paths.get(src);

        // 1. 파일 경로 확인
        System.out.println("File path: " + filePath.toAbsolutePath());

        // 2. 파일 존재 여부 확인
        if (!filePath.toFile().exists()) {
            System.err.println("File does not exist.");
            return null;  // 또는 적절한 예외 처리
        }

        // 3. 파일 읽기 권한 확인
        if (!filePath.toFile().canRead()) {
            System.err.println("No read access to the file.");
            return null;  // 또는 적절한 예외 처리
        }

        BufferedImage img = ImageIO.read(filePath.toFile());
        int width = img.getWidth();
        int height = img.getHeight();
        int[] tempSize = { width, height };
        return tempSize;
    }

}
