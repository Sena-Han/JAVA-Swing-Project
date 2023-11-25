package inside;

public class GiantItem extends Item {
    public GiantItem(String name) {
        super(name);
    }

    @Override
    public void use(Vava vava) {
        // 거대화 아이템의 동작
        System.out.println(getName() + ": 거대화");
        // vava의 크기 증가 등의 동작 구현
        vava.setBigTime(8); // 거대화 지속 시간을 8로 설정
    }
}