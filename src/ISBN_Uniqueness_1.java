import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 

public class ISBN_Uniqueness_1 {
    private Set<String> isbnSet;
    private String csvFilePath;
 
    public ISBN_Uniqueness_1(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        isbnSet = new HashSet<>();
        readISBNsFromCSV();
    }
 
    private void readISBNsFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(",");
                String isbn = attributes[4]; // Assuming ISBN is at index 4 in the CSV
                isbnSet.add(isbn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public boolean isISBNUnique(String isbn) {
        return isbnSet.contains(isbn);
    }
 
    // Main method (for testing)
    public static void main(String[] args) {
        // Specify the path to the existing CSV file containing books
        String csvFilePath = "BooksData.csv";
 
        // Create a BookManager object and populate the ISBN set from the CSV file
        ISBN_Uniqueness_1 bookManager = new ISBN_Uniqueness_1(csvFilePath);
 
        // Test the uniqueness of an ISBN
        String newISBN = "97803337916767";
        boolean isUnique = bookManager.isISBNUnique(newISBN);
        System.out.println("Is ISBN unique? " + isUnique);
    }
}