import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

class Inventory {
    private Map<String, Integer> rooms;

    public Inventory() {
        rooms = new HashMap<>();
        rooms.put("Deluxe", 1);
        rooms.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return rooms.getOrDefault(type, 0) > 0;
    }

    public void book(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void rollback(String type) {
        rooms.put(type, rooms.get(type) + 1);
    }

    public void display() {
        System.out.println("Inventory:");
        for (String key : rooms.keySet()) {
            System.out.println(key + ": " + rooms.get(key));
        }
    }
}

class BookingManager {
    private Map<String, Reservation> reservations;
    private Stack<String> rollbackStack;
    private Inventory inventory;

    public BookingManager(Inventory inventory) {
        this.inventory = inventory;
        reservations = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    public void book(String id, String type) {
        if (!inventory.isAvailable(type)) {
            System.out.println("Booking failed for " + id);
            return;
        }
        String roomId = type + "_R1";
        inventory.book(type);
        reservations.put(id, new Reservation(id, type, roomId));
        System.out.println("Booked: " + id);
    }

    public void cancel(String id) {
        if (!reservations.containsKey(id)) {
            System.out.println("Invalid cancellation for " + id);
            return;
        }

        Reservation r = reservations.remove(id);
        rollbackStack.push(r.getRoomId());
        inventory.rollback(r.getRoomType());

        System.out.println("Cancelled: " + id);
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack:");
        for (String s : rollbackStack) {
            System.out.println(s);
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        BookingManager manager = new BookingManager(inventory);

        manager.book("RES1", "Deluxe");
        manager.book("RES2", "Suite");

        manager.cancel("RES1");
        manager.cancel("RES3");

        inventory.display();
        manager.showRollbackStack();
    }
}