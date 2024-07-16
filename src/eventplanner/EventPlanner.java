package eventplanner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventPlanner {
    private ArrayList<Event> events;

    public EventPlanner() {
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public void deleteEvent(int index) {
        if (index >= 0 && index < events.size()) {
            events.remove(index);
        } else {
            System.out.println("Index is out of bounds. Cannot delete event.");
        }
    }

    public List<Event> getEvents() {
        return events;
    }

    public void updateEvent(int index, Event updatedEvent) {
        if (index >= 0 && index < events.size()) {
            events.set(index, updatedEvent);
        } else {
            System.out.println("Index is out of bounds. Cannot update event.");
        }
    }

    public List<Event> searchEvents(String searchTerm, String searchType) {
        ArrayList<Event> searchResults = new ArrayList<>();

        switch (searchType.toLowerCase()) {
            case "name":
                for (Event event : events) {
                    if (event.getTitle().equalsIgnoreCase(searchTerm)) {
                        searchResults.add(event);
                    }
                }
                break;
            case "date":
                LocalDate searchDate;
                try {
                    searchDate = LocalDate.parse(searchTerm);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please use yyyy-mm-dd.");
                    return searchResults;
                }
                for (Event event : events) {
                    if (event.getDate().equals(searchDate)) {
                        searchResults.add(event);
                    }
                }
                break;
            case "location":
                for (Event event : events) {
                    if (event.getLocation().equalsIgnoreCase(searchTerm)) {
                        searchResults.add(event);
                    }
                }
                break;
            default:
                System.out.println("Invalid search type. Please use 'name', 'date', or 'location'.");
        }

        return searchResults;
    }
    
    public void sortEvents(String sortType) {
        switch (sortType.toLowerCase()) {
            case "name":
                EventSorter.quickSortByName(events);
                break;
            case "date":
                EventSorter.mergeSortByDate(events);
                break;
            case "location":
                EventSorter.selectionSortByLocation(events);
                break;
            default:
                System.out.println("Invalid sort type. Please use 'name', 'date', or 'location'.");
        }
    }
    
    public void measureAlgorithmPerformance() {
        // Creating a sample list of events for comparison
//        ArrayList<Event> sampleEvents = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            sampleEvents.add(new Event("Event " + i, "2024-01-01", "10:00", "11:00", "Location " + i));
//        }
//
//        // Measure time taken by each sorting algorithm
//        long startTime, endTime;
//
//        // Merge Sort by Date
//        startTime = System.nanoTime();
//        EventSorter.mergeSortByDate(sampleEvents);
//        endTime = System.nanoTime();
//        long mergeSortTime = endTime - startTime;
//
//        // Quick Sort by Name
//        startTime = System.nanoTime();
//        EventSorter.quickSortByName(sampleEvents);
//        endTime = System.nanoTime();
//        long quickSortTime = endTime - startTime;
//
//        // Bubble Sort by Date
//        startTime = System.nanoTime();
//        EventSorter.bubbleSortByDate(sampleEvents);
//        endTime = System.nanoTime();
//        long bubbleSortTime = endTime - startTime;
//
//        // Selection Sort by Location
//        startTime = System.nanoTime();
//        EventSorter.selectionSortByLocation(sampleEvents);
//        endTime = System.nanoTime();
//        long selectionSortTime = endTime - startTime;
//
//        // Displaying results
//        System.out.println("Comparison of sorting algorithms:");
//        System.out.println("Merge Sort by Date: " + mergeSortTime + " nanoseconds");
//        System.out.println("Quick Sort by Name: " + quickSortTime + " nanoseconds");
//        System.out.println("Bubble Sort by Date: " + bubbleSortTime + " nanoseconds");
//        System.out.println("Selection Sort by Location: " + selectionSortTime + " nanoseconds");
    }

    public long measureAlgorithmPerformance(List<Event> events, String searchTerm, String searchType, boolean isSorting) {
        long startTime = System.nanoTime();

        if (isSorting) {
            switch (searchType.toLowerCase()) {
                case "merge":
                    EventSorter.mergeSortByDate((ArrayList<Event>) events);
                    break;
                case "quick":
                    EventSorter.quickSortByName((ArrayList<Event>) events);
                    break;
                case "bubble":
                    EventSorter.bubbleSortByDate((ArrayList<Event>) events);
                    break;
                case "selection":
                    EventSorter.selectionSortByLocation((ArrayList<Event>) events);
                    break;
                default:
                    System.out.println("Invalid sorting algorithm.");
            }
        } else {
            searchEvents(searchTerm, searchType);
        }

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static Event findEventByName(ArrayList<Event> events, String name) {
        for (Event event : events) {
            if (event.getTitle().equals(name)) {
                return event;
            }
        }
        return null;
    }

    public static Event findEventByDate(ArrayList<Event> events, LocalDate date) {
        mergeSortByDate(events, 0, events.size() - 1);
        int index = binarySearchByDate(events, date);
        if (index != -1) {
            return events.get(index);
        }
        return null;
    }

    // Merge sort by date
    private static void mergeSortByDate(ArrayList<Event> events, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortByDate(events, left, mid);
            mergeSortByDate(events, mid + 1, right);
            merge(events, left, mid, right);
        }
    }

    private static void merge(ArrayList<Event> events, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<Event> leftArr = new ArrayList<>(events.subList(left, mid + 1));
        ArrayList<Event> rightArr = new ArrayList<>(events.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArr.get(i).getDate().compareTo(rightArr.get(j).getDate()) <= 0) {
                events.set(k++, leftArr.get(i++));
            } else {
                events.set(k++, rightArr.get(j++));
            }
        }

        while (i < n1) {
            events.set(k++, leftArr.get(i++));
        }

        while (j < n2) {
            events.set(k++, rightArr.get(j++));
        }
    }

    // Binary search by date
    private static int binarySearchByDate(ArrayList<Event> events, LocalDate date) {
        int left = 0;
        int right = events.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (events.get(mid).getDate().equals(date)) {
                return mid;
            } else if (events.get(mid).getDate().isBefore(date)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public void displayMenu() {
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

    public int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
        }
        return choice;
    }

    public void displayEventList() {
        if (events.isEmpty()) {
            System.out.println("No events to display.");
        } else {
            System.out.println("Event List:");
            for (int i = 0; i < events.size(); i++) {
                System.out.println((i + 1) + ". " + events.get(i));
            }
        }
    }
}
