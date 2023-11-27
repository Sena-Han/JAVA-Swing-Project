package inside;

public class BoosterItem extends Item {
    public BoosterItem(String name) {
        super(name);
    }

    @Override
    public void use(Vava vava) {
        // 부스터 아이템 동작
        System.out.println(getName() + ": 부스터");
        
        vava.setBooster(true); // 부스터 아이템 사용 플래그 설정
        vava.setFastTime(8); // 부스터 지속 시간을 8로 설정
        
        int newFastTime = vava.getFastTime() + 10; // 수치 조정 필요. 빨라지는 속도 만큼 더해주면 될듯
        vava.setFastTime(newFastTime);

        // 부스터의 추가로직이 필요하면 여기
    }
}
