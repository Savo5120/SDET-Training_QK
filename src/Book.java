import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

 
public class Book {
    private String title;
    private String author;
    private String genre;
    private String publicationDate;
    private String isbn;
    private int noOfCopies;  // Change to int for easier manipulation
 
    public Book(String title, String author, String genre, String publicationDate, String isbn, String noOfCopies) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.noOfCopies = Integer.parseInt(noOfCopies);
    }
 
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getAuthor() {
        return author;
    }
 
    public void setAuthor(String author) {
        this.author = author;
    }
 
    public String getGenre() {
        return genre;
    }
 
    public void setGenre(String genre) {
        this.genre = genre;
    }
 
    public String getPublicationDate() {
        return publicationDate;
    }
 
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
 
    public String getisbn() {
        return isbn;
    }
 
    public void setisbn(String isbn) {
        this.isbn = isbn;
    }
 
    public int getNoOfCopies() {
        return noOfCopies;
    }
 
    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }    
 
    @Override
    public String toString() {
        return String.format("Title: %s, Author: %s, Genre: %s, Publication Date: %s, ISBN: %s, No. Of Copies: %s",
                              title, author, genre, publicationDate, isbn, noOfCopies);
    }
    
    public boolean borrowBook() {
        if (noOfCopies > 0) {
            noOfCopies--;
            return true;
        } else {
            return false;
        }
    }
    
    public void returnBook() {
        noOfCopies++;
    }
 
    // Method to read CSV file and create list of Book objects
    public static List<Book> readBooksFromCSV(String fileName) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstline = true; //flag to skip the first line
            while ((line = br.readLine()) != null) {
            	if (firstline) {
            		firstline = false;
            		continue; // skip the header line
            	}
                String[] attributes = line.split(",");
                Book book = new Book(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5]);
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
 
    // Main method to test reading from CSV file
    public static void main(String[] args) {
        List<Book> books = readBooksFromCSV("BooksData.csv");
        for (Book book : books) {
            System.out.println(book);
        }
    }	
    
 
	
}