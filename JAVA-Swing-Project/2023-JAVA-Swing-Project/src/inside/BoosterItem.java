package inside;

public class BoosterItem extends Item {
    public BoosterItem(String name) {
        super(name);
    }

    @Override
    public void use(Vava vava) {
        // 부스터 아이템 동작
        System.out.println(getName() + ": 부스터");
        // vava의 이동 속도 증가 
        vava.setFastTime(8); // 부스터 지속 시간을 8로 설정
    }
}
