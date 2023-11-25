package inside;

public class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void use(Vava vava) {
        // 아이템 사용 시 수행되는 동작 서술 
        System.out.println(name + " 사용");
    }
}