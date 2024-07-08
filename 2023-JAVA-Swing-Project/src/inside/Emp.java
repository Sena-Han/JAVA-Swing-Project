package inside;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

public class Emp {
	
	public static long getTime() {
		return Timestamp.valueOf(LocalDateTime.now()).getTime();
	}

	public static int[] getSize(String src) throws Exception {
		File imgf = new File(src);
	    
        // 1. 파일 경로 확인
        System.out.println("File path: " + imgf.getAbsolutePath());

        // 2. 파일 존재 여부 확인
        if (!imgf.exists()) {
            System.err.println("File does not exist.");
            return null;  // 또는 적절한 예외 처리
        }
     // 3. 파일 읽기 권한 확인
        if (!imgf.canRead()) {
            System.err.println("No read access to the file.");
            return null;  // 또는 적절한 예외 처리
        }
        
		BufferedImage img = ImageIO.read(imgf);
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		int[] tempSize = {width, height};
		
		return tempSize;
	}
	
	public static int[][] getPic(String src) throws Exception{
		File imgf = new File(src);
	    
        // 1. 파일 경로 확인
        System.out.println("File path: " + imgf.getAbsolutePath());

        // 2. 파일 존재 여부 확인
        if (!imgf.exists()) {
            System.err.println("File does not exist.");
            return null;  // 또는 적절한 예외 처리
        }
     // 3. 파일 읽기 권한 확인
        if (!imgf.canRead()) {
            System.err.println("No read access to the file.");
            return null;  // 또는 적절한 예외 처리
        }
		
		BufferedImage img = ImageIO.read(imgf);
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		int[] pixels=new int[width*height];
		
		PixelGrabber grab = new PixelGrabber(img, 0, 0, width, height, pixels, 0,width);
		grab.grabPixels();
		
		int[][] picture=new int[width][height];
		
		for(int i=0;i<pixels.length;i++)
		      picture[i%width][i/width]=pixels[i] + 16777216;
		
		return picture;
	}
	
	public static void drawFancyString(Graphics2D g2D, String str, int x, int y, float size, Color c) {
		  if(str.length() == 0)
			  return;
		  
		  AffineTransform org = g2D.getTransform();
		  Font font = new Font("Arial", Font.BOLD, 30);
		  
		  TextLayout textLayout = new TextLayout(str, font, g2D.getFontRenderContext());
		  AffineTransform transform = g2D.getTransform();
		  
		  FontMetrics fontMetrics = g2D.getFontMetrics(font);
		  
		  Shape out = textLayout.getOutline(null);
		  Rectangle bound = out.getBounds();
		  transform.translate(x, y+fontMetrics.getAscent());
		  
		  g2D.setTransform(transform);
		  g2D.setColor(c);
		  g2D.fill(out);
		  g2D.setStroke(new BasicStroke(size/25));
		  g2D.setColor(Color.BLACK);
		  g2D.draw(out);
		  
		  g2D.setTransform(org);
		}
	
}
