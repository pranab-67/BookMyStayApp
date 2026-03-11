=import java.util.HashMap;

abstract class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void displayRoomDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: " + price);
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

class RoomInventory {
    private final HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    HashMap<String, Integer> getInventorySnapshot() {
        return new HashMap<>(inventory);  // Return a copy for read-only access
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {new SingleRoom(), new DoubleRoom(), new SuiteRoom()};

        System.out.println("Book My Stay App - Hotel Booking System v4.0");
        System.out.println("--------------------------------------------");
        System.out.println("Available Rooms:");

        HashMap<String, Integer> availabilitySnapshot = inventory.getInventorySnapshot();

        for (Room room : rooms) {
            int availableCount = availabilitySnapshot.getOrDefault(room.type, 0);
            if (availableCount > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + availableCount);
                System.out.println();
            }
        }
    }
}