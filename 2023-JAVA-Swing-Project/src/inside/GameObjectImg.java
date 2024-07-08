package inside;

import javax.swing.ImageIcon;

public class GameObjectImg {
	
	// 배경
    private ImageIcon backScreenImg; // 배경 이미지
    private ImageIcon feverScoreImg; // 피버타임 배경 
    
    // hp
    private ImageIcon hpCoffee; // hp 회복 아이템 이미지
	private ImageIcon hpEDrink; // hp 회복 아이템 이미지
	
	// 장애물
	private ImageIcon obstacle1; // 1단 장애물 이미지
	private ImageIcon obstacle2; // 2단 장애물 이미지
	private ImageIcon obstacle3; // 임시 생성
	private ImageIcon obstacleDeath; // death 장애물 이미지
	
	// 학점(점수)
	private ImageIcon scoreA; // A학점 이미지
	private ImageIcon scoreB; // B학점 이미지
	private ImageIcon scoreC; // C학점 이미지
	
	// 발판 이미지 아이콘들
	private ImageIcon field1Ic; // 발판
	private ImageIcon field2Ic; // 공중발판
	
	private ImageIcon scoreEffectIc; 
	
	// 배경
    //private ImageIcon backScreenImg; // 배경 이미지
    private ImageIcon feverScreenImg; // 피버타임 배경
    
    //아이템 
	private ImageIcon red; // 1단 장애물 이미지
	private ImageIcon green; // 2단 장애물 이미지
	private ImageIcon yellow; // 임시 생성
	private ImageIcon blue; // death 장애물 이미지
	private ImageIcon black;
	
	private ImageIcon BoosterItem;
    private ImageIcon GiantItem; 
    
    public GameObjectImg() {
    	// 기본 생성
    }
    
//    public GameObjectImg(ImageIcon backScreenImg, ImageIcon feverScoreImg) {
//        // 이미지를 직접 전달받는 생성자
//        this.backScreenImg = backScreenImg;
//        this.feverScoreImg = feverScoreImg;
//    }
//    
//    public GameObjectImg(ImageIcon backScreenImg, ImageIcon feverScoreImg, ImageIcon obstacle1, ImageIcon obstacle2, ImageIcon scoreA, ImageIcon scoreB, ImageIcon scoreC) {
//        // 이미지를 직접 전달받는 생성자
//        this.backScreenImg = backScreenImg;
//        this.feverScoreImg = feverScoreImg;
//        
//        this.obstacle1 = obstacle1;
//        this.obstacle2 = obstacle2;
//        
//        this.scoreA = scoreA;
//        this.scoreB = scoreB;
//        this.scoreC = scoreC;
//    }
    
    /*public GameObjectImg(ImageIcon back, ImageIcon r, ImageIcon g, ImageIcon y, ImageIcon bu, ImageIcon ba, ImageIcon e) {
    	this.feverScreenImg=back;
    	
    	this.red=r;
    	this.green=g;
    	this.yellow=y;
    	this.blue=bu;
    	this.black=ba;
    	
    	this.effectIc=e;
    }*/
    
    public GameObjectImg(ImageIcon backScreenImg, ImageIcon feverScoreImg, 
    		ImageIcon scoreA, ImageIcon scoreB, ImageIcon scoreC, ImageIcon hpCoffee, ImageIcon hpEDrink,
    		ImageIcon scoreEffectIc, ImageIcon field1Ic, ImageIcon field2Ic, 
    		ImageIcon obstacle1, ImageIcon obstacle2, ImageIcon obstacle3, 
    		ImageIcon obstacleDeath, ImageIcon BoosterItem, ImageIcon GiantItem,  
    		ImageIcon red, ImageIcon green, ImageIcon yellow, ImageIcon blue, ImageIcon black) {
        this.backScreenImg = backScreenImg;
        this.feverScoreImg = feverScoreImg;
         
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.scoreC = scoreC;
        this.hpCoffee = hpCoffee;
        this.hpEDrink = hpEDrink;
        
        this.scoreEffectIc = scoreEffectIc;
        
    	this.setField1Ic(field1Ic); // 발판
    	this.setField2Ic(field2Ic); // 공중발판
         
        this.obstacle1 = obstacle1;
        this.obstacle2 = obstacle2;
        this.obstacle3 = obstacle3;
        this.obstacleDeath = obstacleDeath;
         
    	this.red = red;
    	this.green = green;
    	this.yellow = yellow;
    	this.blue = blue;
    	this.black = black;
    	
    	this.BoosterItem = BoosterItem; 
        this.GiantItem = GiantItem;

   }
    
    public ImageIcon getBackScreenImg() {
        return backScreenImg;
    }

    public void setBackScreenImg(ImageIcon backScreenImg) {
        this.backScreenImg = backScreenImg;
    }

	public ImageIcon getHpCoffee() {
		return hpCoffee;
	}

	public void setHpCoffee(ImageIcon hpCoffee) {
		this.hpCoffee = hpCoffee;
	}

	public ImageIcon getHpEDrink() {
		return hpEDrink;
	}

	public void setHpEDrink(ImageIcon hpEDrink) {
		this.hpEDrink = hpEDrink;
	}

	public ImageIcon getObstacle1() {
		return obstacle1;
	}

	public void setObstacle1(ImageIcon obstacle1) {
		this.obstacle1 = obstacle1;
	}

	public ImageIcon getObstacle2() {
		return obstacle2;
	}

	public void setObstacle2(ImageIcon obstacle2) {
		this.obstacle2 = obstacle2;
	}

	public ImageIcon getObstacleDeath() {
		return obstacleDeath;
	}

	public void setObstacleDeath(ImageIcon obstacleDeath) {
		this.obstacleDeath = obstacleDeath;
	}

	public ImageIcon getScoreA() {
		return scoreA;
	}

	public void setScoreA(ImageIcon scoreA) {
		this.scoreA = scoreA;
	}

	public ImageIcon getScoreB() {
		return scoreB;
	}

	public void setScoreB(ImageIcon scoreB) {
		this.scoreB = scoreB;
	}

	public ImageIcon getScoreC() {
		return scoreC;
	}

	public void setScoreC(ImageIcon scoreC) {
		this.scoreC = scoreC;
	}
	
	public ImageIcon getscoreEffectIc() {
		return scoreEffectIc;
	}

	public void setscoreEffectIc(ImageIcon scoreEffectIc) {
		this.scoreEffectIc = scoreEffectIc;
	}
	
    public ImageIcon getFeverScoreImg() {
        return feverScoreImg;
    }

    public void setFeverScoreImg(ImageIcon feverScoreImg) {
        this.feverScoreImg = feverScoreImg;
    }

	public ImageIcon getField1Ic() {
		return field1Ic;
	}

	public void setField1Ic(ImageIcon field1Ic) {
		this.field1Ic = field1Ic;
	}

	public ImageIcon getField2Ic() {
		return field2Ic;
	}

	public void setField2Ic(ImageIcon field2Ic) {
		this.field2Ic = field2Ic;
	}

	public ImageIcon getObstacle3() {
		return obstacle3;
	}

	public void setObstacle3(ImageIcon obstacle3) {
		this.obstacle3 = obstacle3;
	}
	public ImageIcon getFeverScreenImg() {
		return feverScreenImg;
	}

	public void setFeverScreenImg(ImageIcon back) {
	    this.feverScreenImg = back;
	}

	public ImageIcon getRed() {
		return red;
	}

	public void setRed(ImageIcon red) {
		this.red = red;
	}

	public ImageIcon getGreen() {
		return green;
	}

	public void setGreen(ImageIcon green) {
		this.green = green;
	}

	public ImageIcon getYellow() {
		return yellow;
	}

	public void setYellow(ImageIcon yellow) {
		this.yellow = yellow;
	}

	public ImageIcon getBlue() {
		return blue;
	}

	public void setBlue(ImageIcon blue) {
		this.blue = blue;
	}

	public ImageIcon getBlack() {
		return black;
	}

	public void setBlack(ImageIcon black) {
		this.black = black;
	}
		
	public ImageIcon getBoosterItem() {
		return BoosterItem;
	}

	public void setBoosterItem(ImageIcon BoosterItem) {
		this.BoosterItem = BoosterItem;
	}
	      
	public ImageIcon getGiantItem() {
		return GiantItem;
	}

	public void setGiantItem(ImageIcon GiantItem) {
		this.GiantItem = GiantItem;
	}
}