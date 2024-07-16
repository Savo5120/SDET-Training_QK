package eventplanner;

import java.util.ArrayList;
import java.util.List;

public class EventSorter {
    public static void bubbleSortByDate(ArrayList<Event> events) {
        int n = events.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (events.get(j).getDate().isAfter(events.get(j + 1).getDate())) {
                    Event temp = events.get(j);
                    events.set(j, events.get(j + 1));
                    events.set(j + 1, temp);
                }
            }
        }
    }

    public static void selectionSortByLocation(ArrayList<Event> events) {
        int n = events.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (events.get(j).getLocation().compareTo(events.get(minIndex).getLocation()) < 0) {
                    minIndex = j;
                }
            }
            Event temp = events.get(minIndex);
            events.set(minIndex, events.get(i));
            events.set(i, temp);
        }
    }

    public static ArrayList<Event> mergeSortByDate(List<Event> allEvents) {
        if (allEvents.size() <= 1) {
            return (ArrayList<Event>) allEvents;
        }
        int middle = allEvents.size() / 2;
        ArrayList<Event> left = new ArrayList<>(allEvents.subList(0, middle));
        ArrayList<Event> right = new ArrayList<>(allEvents.subList(middle, allEvents.size()));

        left = mergeSortByDate(left);
        right = mergeSortByDate(right);

        return merge(left, right);
    }

    private static ArrayList<Event> merge(ArrayList<Event> left, ArrayList<Event> right) {
        ArrayList<Event> result = new ArrayList<>();
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).getDate().isBefore(right.get(rightIndex).getDate())) {
                result.add(left.get(leftIndex++));
            } else {
                result.add(right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            result.add(left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            result.add(right.get(rightIndex++));
        }

        return result;
    }

    public static ArrayList<Event> quickSortByName(List<Event> allEvents) {
        ArrayList<Event> sortedEvents = new ArrayList<>(allEvents);
        quickSortByNameHelper(sortedEvents, 0, sortedEvents.size() - 1);
        return sortedEvents;
    }

    private static void quickSortByNameHelper(ArrayList<Event> events, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(events, left, right);
            quickSortByNameHelper(events, left, pivotIndex - 1);
            quickSortByNameHelper(events, pivotIndex + 1, right);
        }
    }

    private static int partition(ArrayList<Event> events, int left, int right) {
        String pivot = events.get(right).getTitle();
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (events.get(j).getTitle().compareTo(pivot) <= 0) {
                i++;
                Event temp = events.get(i);
                events.set(i, events.get(j));
                events.set(j, temp);
            }
        }

        Event temp = events.get(i + 1);
        events.set(i + 1, events.get(right));
        events.set(right, temp);

        return i + 1;
    }
}

