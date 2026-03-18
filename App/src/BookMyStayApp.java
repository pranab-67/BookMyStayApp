import java.util.*;

class BookingRequest {
    private String requestId;
    private String roomType;

    public BookingRequest(String requestId, String roomType) {
        this.requestId = requestId;
        this.roomType = roomType;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRoomType() {
        return roomType;
    }
}

class Inventory {
    private Map<String, Integer> rooms;

    public Inventory() {
        rooms = new HashMap<>();
        rooms.put("Deluxe", 1);
        rooms.put("Suite", 1);
    }

    public synchronized boolean bookRoom(String type) {
        if (rooms.getOrDefault(type, 0) > 0) {
            rooms.put(type, rooms.get(type) - 1);
            return true;
        }
        return false;
    }

    public void display() {
        System.out.println("Final Inventory:");
        for (String key : rooms.keySet()) {
            System.out.println(key + ": " + rooms.get(key));
        }
    }
}

class BookingQueue {
    private Queue<BookingRequest> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

class BookingProcessor extends Thread {
    private BookingQueue queue;
    private Inventory inventory;

    public BookingProcessor(BookingQueue queue, Inventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) {
                break;
            }

            boolean success = inventory.bookRoom(request.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " booked " + request.getRoomType() +
                        " for " + request.getRequestId());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed booking for " + request.getRequestId());
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {

        BookingQueue queue = new BookingQueue();
        Inventory inventory = new Inventory();

        queue.addRequest(new BookingRequest("REQ1", "Deluxe"));
        queue.addRequest(new BookingRequest("REQ2", "Deluxe"));
        queue.addRequest(new BookingRequest("REQ3", "Suite"));
        queue.addRequest(new BookingRequest("REQ4", "Suite"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        inventory.display();
    }
}