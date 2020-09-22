package student.adventure;

public class Item {

    private final String item_name;
    private boolean isMurderWeapon;

    public Item(String newName, boolean isMW) {
        item_name = newName;
        isMurderWeapon = isMW;
    }

    public String getName() {
        return item_name;
    }

    public boolean isMurderWeapon() {
        return isMurderWeapon;
    }

    public void setMurderWeapon(boolean iMW) {
        isMurderWeapon = iMW;
    }

    @Override
    public String toString() {
        return item_name;
    }
}
