package inside;

import javax.swing.ImageIcon;

public class VavaImg {
	
	private ImageIcon vavaIc; // 기본 이미지
	private ImageIcon hitIc; // 충돌 이미지
	private ImageIcon attackingIc; // 공격 이미지
	private ImageIcon vavaJumpIc; // 점프 이미지
	private ImageIcon doubleJumpIc; // 2단 점프 이미지
	private ImageIcon jumpFallIc; // 2단 점프 후 낙하 이미지
	
	public VavaImg(ImageIcon vavaIc, ImageIcon hitIc, ImageIcon attackingIc, ImageIcon jumpFallIc, ImageIcon vavaJumpIc, ImageIcon doubleJumpIc) {
		this.vavaIc = vavaIc;
		this.hitIc = hitIc;
		this.attackingIc = attackingIc;
		this.jumpFallIc = jumpFallIc;
		this.vavaJumpIc = vavaJumpIc;
		this.doubleJumpIc = doubleJumpIc;
	}

	public ImageIcon getVavaIc() {
		return vavaIc;
	}
	public void setVavaIc(ImageIcon vavaIc) {
		this.vavaIc = vavaIc;
	}
	public ImageIcon getAttackIc() {
		return attackingIc;
	}
	public void setAttackIc(ImageIcon attackIc) {
		this.attackingIc = attackIc;
	}
	public ImageIcon getHitIc() {
		return hitIc;
	}
	public void setHitIc(ImageIcon hitIc) {
		this.hitIc = hitIc;
	}
	public ImageIcon getFallIc() {
		return jumpFallIc;
	}
	public void setFallIc(ImageIcon fallIc) {
		this.jumpFallIc = fallIc;
	}

	public ImageIcon getJumpIc() {
		return vavaJumpIc;
	}

	public void setJumpIc(ImageIcon jumpIc) {
		this.vavaJumpIc = jumpIc;
	}

	public ImageIcon getDoubleJumpIc() {
		return doubleJumpIc;
	}

	public void setDoubleJumpIc(ImageIcon doubleJumpIc) {
		this.doubleJumpIc = doubleJumpIc;
	}
}
