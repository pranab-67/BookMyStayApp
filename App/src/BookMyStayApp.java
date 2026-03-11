import java.util.*;

class Reservation {
    String guestName;
    String roomType;
    String roomId; // Assigned after allocation

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType + " | Room ID: " + roomId);
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

    boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    void allocateRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    void displayInventory() {
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

class BookingQueue {
    private final Queue<Reservation> queue;

    BookingQueue() {
        queue = new LinkedList<>();
    }

    void addRequest(Reservation res) {
        queue.add(res);
    }

    Reservation getNextRequest() {
        return queue.poll();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomAllocator {
    private final RoomInventory inventory;
    private final HashMap<String, Set<String>> allocatedRooms;

    RoomAllocator(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());
    }

    boolean allocate(Reservation res) {
        if (!inventory.isAvailable(res.roomType)) return false;

        String roomId;
        Set<String> assigned = allocatedRooms.get(res.roomType);

        do {
            roomId = res.roomType.substring(0, 2).toUpperCase() + "-" + (assigned.size() + 1);
        } while (assigned.contains(roomId));

        res.roomId = roomId;
        assigned.add(roomId);
        inventory.allocateRoom(res.roomType);
        return true;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("David", "Single Room"));

        RoomAllocator allocator = new RoomAllocator(inventory);

        System.out.println("Book My Stay App - Hotel Booking System v6.0");
        System.out.println("--------------------------------------------");

        while (!bookingQueue.isEmpty()) {
            Reservation res = bookingQueue.getNextRequest();
            if (allocator.allocate(res)) {
                System.out.println("Reservation Confirmed:");
                res.displayReservation();
            } else {
                System.out.println("Reservation Failed (No availability): " + res.guestName + " - " + res.roomType);
            }
            System.out.println();
        }

        System.out.println("Final Inventory State:");
        inventory.displayInventory();
    }
}