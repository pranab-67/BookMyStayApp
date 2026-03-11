import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Requested: " + roomType);
    }
}

class BookingQueue {
    private Queue<Reservation> queue;

    BookingQueue() {
        queue = new LinkedList<>();
    }

    void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    void displayQueue() {
        System.out.println("Current Booking Requests (FIFO):");
        for (Reservation res : queue) {
            res.displayReservation();
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        BookingQueue bookingQueue = new BookingQueue();

        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        System.out.println("Book My Stay App - Hotel Booking System v5.0");
        System.out.println("--------------------------------------------");
        bookingQueue.displayQueue();
    }
}