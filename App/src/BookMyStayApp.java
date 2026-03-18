import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String id;
    private String roomType;

    public Reservation(String id, String roomType) {
        this.id = id;
        this.roomType = roomType;
    }

    public String toString() {
        return id + " - " + roomType;
    }
}

class Inventory implements Serializable {
    private Map<String, Integer> rooms;

    public Inventory() {
        rooms = new HashMap<>();
        rooms.put("Deluxe", 2);
        rooms.put("Suite", 1);
    }

    public void book(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Integer> rooms) {
        this.rooms = rooms;
    }

    public void display() {
        System.out.println("Inventory:");
        for (String key : rooms.keySet()) {
            System.out.println(key + ": " + rooms.get(key));
        }
    }
}

class DataStore implements Serializable {
    List<Reservation> reservations;
    Map<String, Integer> inventory;

    public DataStore(List<Reservation> reservations, Map<String, Integer> inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "data.ser";

    public void save(DataStore data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(data);
        } catch (Exception e) {
            System.out.println("Save failed");
        }
    }

    public DataStore load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (DataStore) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found");
            return null;
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        DataStore loaded = service.load();

        List<Reservation> reservations;
        Inventory inventory = new Inventory();

        if (loaded != null) {
            reservations = loaded.reservations;
            inventory.setRooms(loaded.inventory);
            System.out.println("Data restored");
        } else {
            reservations = new ArrayList<>();
            System.out.println("Starting fresh");
        }

        reservations.add(new Reservation("RES1", "Deluxe"));
        inventory.book("Deluxe");

        reservations.add(new Reservation("RES2", "Suite"));
        inventory.book("Suite");

        System.out.println("Reservations:");
        for (Reservation r : reservations) {
            System.out.println(r);
        }

        inventory.display();

        service.save(new DataStore(reservations, inventory.getRooms()));
        System.out.println("Data saved");
    }
}