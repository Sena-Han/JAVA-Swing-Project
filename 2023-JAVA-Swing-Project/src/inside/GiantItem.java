package inside;

public class GiantItem extends Item {
    public GiantItem(String name) {
        super(name);
    }

    @Override
    public void use(Vava vava) {
        // 거대화 아이템의 동작
        System.out.println(getName() + ": 거대화");
        
        vava.setBigTime(8); // 거대화 지속 시간을 8로 설정
        
        // x와 y 좌표를 조정하여 바바 이동 (수치 수정 필요) 
        int newX = vava.getX() + 50; 
        int newY = vava.getY() + 50; 

        vava.setX(newX);
        vava.setY(newY);
    }
}