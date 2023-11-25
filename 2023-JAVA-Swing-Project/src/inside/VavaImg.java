package inside;

import javax.swing.ImageIcon;

public class VavaImg {
	
	// 바바 이미지들
	
	private ImageIcon vavaIc; // 기본 이미지
	private ImageIcon hitIc; // 충돌 이미지
	private ImageIcon attackIc; // 공격 이미지
	//private ImageIcon jumpIc; // 점프 이미지
	//private ImageIcon doubleJumpIc; // 2단 점프 이미지
	private ImageIcon fallIc; // 2단 점프 후 낙하 이미지
	
	

	public ImageIcon getVavaIc() {
		return vavaIc;
	}
	public void setVavaIc(ImageIcon vavaIc) {
		this.vavaIc = vavaIc;
	}
	public ImageIcon getAttackIc() {
		return attackIc;
	}
	public void setAttackIc(ImageIcon attackIc) {
		this.attackIc = attackIc;
	}
	public ImageIcon getHitIc() {
		return hitIc;
	}
	public void setHitIc(ImageIcon hitIc) {
		this.hitIc = hitIc;
	}
	public ImageIcon getFallIc() {
		return fallIc;
	}
	public void setFallIc(ImageIcon fallIc) {
		this.fallIc = fallIc;
	}
	
}
