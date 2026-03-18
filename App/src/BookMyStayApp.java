import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (rooms.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        validateAvailability(roomType);
        rooms.put(roomType, rooms.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        String[] requests = {"Deluxe", "Suite", "Suite", "InvalidType"};

        for (String roomType : requests) {
            try {
                System.out.println("Booking request for: " + roomType);
                inventory.bookRoom(roomType);
                System.out.println("Booking successful");
            } catch (InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }

        inventory.displayInventory();
    }
}