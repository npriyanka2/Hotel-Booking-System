import java.io.*;
import java.util.*;

public class HotelBookingSystem {

    // Store available rooms and booked rooms using collections
    private static List<Integer> availableRooms = new ArrayList<>();
    private static Map<String, Integer> bookings = new HashMap<>();

    // File to store bookings
    private static final String BOOKINGS_FILE = "bookings.txt";

    // Initialize hotel with 10 available rooms (Room numbers 101 to 110)
    public HotelBookingSystem() {
        for (int i = 101; i <= 110; i++) {
            availableRooms.add(i);
        }
        loadBookingsFromFile();
    }

    // Method to display available rooms
    public void showAvailableRooms() {
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            System.out.println("Available rooms: " + availableRooms);
        }
    }

    // Method to book a room
    public void bookRoom(String guestName) {
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available to book.");
            return;
        }

        // Assign the first available room to the guest
        int room = availableRooms.remove(0);
        bookings.put(guestName, room);
        saveBookingsToFile();
        System.out.println("Room " + room + " has been booked for " + guestName + ".");
    }

    // Method to cancel a booking
    public void cancelBooking(String guestName) {
        if (!bookings.containsKey(guestName)) {
            System.out.println("No booking found for " + guestName + ".");
            return;
        }

        int room = bookings.remove(guestName);
        availableRooms.add(room); // Return the room to the available list
        saveBookingsToFile();
        System.out.println("Booking for " + guestName + " has been canceled. Room " + room + " is now available.");
    }

    // Method to check a booking
    public void checkBooking(String guestName) {
        if (bookings.containsKey(guestName)) {
            System.out.println(guestName + " has booked room " + bookings.get(guestName) + ".");
        } else {
            System.out.println(guestName + " has no booking.");
        }
    }

    // Save bookings to file
    private void saveBookingsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Map.Entry<String, Integer> booking : bookings.entrySet()) {
                writer.println(booking.getKey() + ":" + booking.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings to file.");
        }
    }

    // Load bookings from file
    private void loadBookingsFromFile() {
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(":");
                String guestName = parts[0];
                int room = Integer.parseInt(parts[1]);
                bookings.put(guestName, room);
                availableRooms.remove(Integer.valueOf(room)); // Remove booked room from available list
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings from file.");
        }
    }

    public static void main(String[] args) {
        HotelBookingSystem hotel = new HotelBookingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Hotel Booking System ---");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Check Booking");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    hotel.showAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();
                    hotel.bookRoom(guestName);
                    break;
                case 3:
                    System.out.print("Enter guest name: ");
                    guestName = scanner.nextLine();
                    hotel.cancelBooking(guestName);
                    break;
                case 4:
                    System.out.print("Enter guest name: ");
                    guestName = scanner.nextLine();
                    hotel.checkBooking(guestName);
                    break;
                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
