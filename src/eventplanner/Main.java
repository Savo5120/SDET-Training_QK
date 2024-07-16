package eventplanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EventPlanner eventPlanner = new EventPlanner();
        Scanner scanner = new Scanner(System.in);

        int choice = 0;
        while (choice != 8) {
            displayMenu();
            choice = getUserInput(scanner);

            switch (choice) {
                case 1:
                    System.out.println("Adding an event...");
                    Event eventToAdd = getEventDataFromUser(scanner);
                    eventPlanner.addEvent(eventToAdd);
                    break;
                case 2:
                    System.out.println("Updating an event...");
                    eventPlanner.displayEventList();
                    int eventIndexToUpdate = getEventIndexFromUsertoUpdate(scanner, eventPlanner.getEvents().size());
                    Event updatedEvent = getEventDataFromUser(scanner);
                    eventPlanner.updateEvent(eventIndexToUpdate, updatedEvent);
                    break;
                case 3:
                    System.out.println("Deleting an event...");
                    eventPlanner.displayEventList();
                    int eventIndexToDelete = getEventIndexFromUsertoDelete(scanner, eventPlanner.getEvents().size());
                    eventPlanner.deleteEvent(eventIndexToDelete);
                    break;
                case 4:
                    System.out.println("Searching for events...");
                    System.out.print("Enter search term: ");
                    String searchTerm = scanner.next();
                    System.out.print("Enter search type ('name', 'date', or 'location'): ");
                    String searchType = scanner.next();
                    eventPlanner.searchEvents(searchTerm, searchType);
                    break;
                case 5:
                    System.out.println("Sorting events...");
                    System.out.print("Enter sort type ('name', 'date', or 'location'): ");
                    String sortType = scanner.next();
                    eventPlanner.sortEvents(sortType);
                    break;
                case 6:
                    System.out.println("Comparing sorting algorithms...");
                    eventPlanner.measureAlgorithmPerformance();
                    break;
                case 7:
                    System.out.println("Displaying Event List...");
                    eventPlanner.displayEventList();
                    break; 
                case 8:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 7.");
                    break;
            }
            eventPlanner.displayEventList();
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\nEvent Planner Menu:");
        System.out.println("1. Add an event");
        System.out.println("2. Update an event");
        System.out.println("3. Delete an event");
        System.out.println("4. Search for events");
        System.out.println("5. Sort events");
        System.out.println("6. Compare sorting algorithms");
        System.out.println("7. Display Event List");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserInput(Scanner scanner) {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear the input buffer
        }
        return choice;
    }

    private static Event getEventDataFromUser(Scanner scanner) {
        System.out.print("Enter New Event Name: ");
        String eventName = scanner.next();
        System.out.print("Enter Date (yyyy-mm-dd): ");
        String date = scanner.next();
        System.out.print("Enter Start Time (HH:MM): ");
        String startTime = scanner.next();
        System.out.print("Enter End Time (HH:MM): ");
        String endTime = scanner.next();
        System.out.print("Enter Location: ");
        String location = scanner.next();
        return new Event(eventName, date, startTime, endTime, location);
    }

    private static int getEventIndexFromUsertoUpdate(Scanner scanner, int eventCount) {
        int index = -1;
        while (index < 0 || index >= eventCount) {
            System.out.print("Enter the index of the event to update : ");
            index = scanner.nextInt() - 1;
            if (index < 0 || index >= eventCount) {
                System.out.println("Invalid index. Please enter a valid index.");
            }
        }
        return index;
    }
    
    private static int getEventIndexFromUsertoDelete(Scanner scanner, int eventCount) {
        int index = -1;
        while (index < 0 || index >= eventCount) {
            System.out.print("Enter the index of the event to delete : ");
            index = scanner.nextInt() - 1;
            if (index < 0 || index >= eventCount) {
                System.out.println("Invalid index. Please enter a valid index.");
            }
        }
        return index;
    }
}
