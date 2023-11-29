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
	private ImageIcon obstacleDeath; // death 장애물 이미지
	
	// 학점(점수)
	private ImageIcon scoreA; // A학점 이미지
	private ImageIcon scoreB; // B학점 이미지
	private ImageIcon scoreC; // C학점 이미지
	
	private ImageIcon scoreEffectIc; 
	
	// 맵 이미지 관련된 발판 같은 것도 들어가야 할 듯
    
    public GameObjectImg() {
    	// 기본 생성
    }
    
    public GameObjectImg(ImageIcon backScreenImg, ImageIcon feverScoreImg) {
        // 이미지를 직접 전달받는 생성자
        this.backScreenImg = backScreenImg;
        this.feverScoreImg = feverScoreImg;
    }
    
    public GameObjectImg(ImageIcon backScreenImg, ImageIcon feverScoreImg, ImageIcon obstacle1, ImageIcon obstacle2, ImageIcon scoreA, ImageIcon scoreB, ImageIcon scoreC) {
        // 이미지를 직접 전달받는 생성자
        this.backScreenImg = backScreenImg;
        this.feverScoreImg = feverScoreImg;
        
        this.obstacle1 = obstacle1;
        this.obstacle2 = obstacle2;
        
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.scoreC = scoreC;
    }
    
    public GameObjectImg(ImageIcon backScreenImg, ImageIcon feverScoreImg, ImageIcon hpCoffee, ImageIcon hpEDrink, ImageIcon obstacle1, ImageIcon obstacle2, ImageIcon obstacleDeath,
    		ImageIcon scoreA, ImageIcon scoreB, ImageIcon scoreC, ImageIcon scoreEffectIc) {
        this.backScreenImg = backScreenImg;
        this.feverScoreImg = feverScoreImg;
         
        this.hpCoffee = hpCoffee;
        this.hpEDrink = hpEDrink;
         
        this.obstacle1 = obstacle1;
        this.obstacle2 = obstacle2;
        this.obstacleDeath = obstacleDeath;
         
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.scoreC = scoreC;
         
        this.scoreEffectIc = scoreEffectIc;
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

}