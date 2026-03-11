import java.util.HashMap;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.get(roomType);
    }

    void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    void displayInventory() {
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("Book My Stay App - Hotel Booking System v3.1");
        System.out.println("--------------------------------------------");

        inventory.displayInventory();
    }
}