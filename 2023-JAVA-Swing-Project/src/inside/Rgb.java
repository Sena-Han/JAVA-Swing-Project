package inside;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Rgb {
	 public static int[][][] getRGBValues(String imagePath) throws Exception {
	        try {
	            Path filePath = Paths.get(imagePath);

	            // 1. 파일 경로 확인
	            System.out.println("File path: " + filePath.toAbsolutePath());

	            // 2. 파일 존재 여부 확인
	            if (!Files.exists(filePath)) {
	                System.err.println("File does not exist.");
	                return null;  // 또는 적절한 예외 처리
	            }

	            // 3. 파일 읽기 권한 확인
	            if (!Files.isReadable(filePath)) {
	                System.err.println("No read access to the file.");
	                return null;  // 또는 적절한 예외 처리
	            }
	            BufferedImage image = ImageIO.read(filePath.toFile());

	            int width = image.getWidth();
	            int height = image.getHeight();

	            int[][][] rgbValues = new int[width][height][3];

	            for (int i = 0; i < width; i++) {
	                for (int j = 0; j < height; j++) {
	                    int pixel = image.getRGB(i, j);

	                    rgbValues[i][j][0] = (pixel >> 16) & 0xFF; // Red
	                    rgbValues[i][j][1] = (pixel >> 8) & 0xFF;  // Green
	                    rgbValues[i][j][2] = pixel & 0xFF;         // Blue
	                }
	            }

	            return rgbValues;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	        }
}
