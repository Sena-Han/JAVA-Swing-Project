package inside;

import javax.swing.ImageIcon;

public class MapObject {
	
    private ImageIcon backScreenImg;
    
    public MapObject() {
    	//기본생성
    }
    
    public ImageIcon getbackScreenImg() {
        return backScreenImg;
    }

    public void setBackScreenImg(ImageIcon backScreenImg) {
        this.backScreenImg = backScreenImg;
    }

}