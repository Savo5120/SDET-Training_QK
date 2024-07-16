import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
 
public class BookManager {
    private Set<String> isbnSet;
    private List<Book> books;
    private List<Transaction> transactionLog;
    private static final int OVERDUE_DAYS = 14; // Grace period before a book becomes overdue
 
    public BookManager(List<Book> existingBooks) {
        isbnSet = new HashSet<>();
        books = new ArrayList<>();
        transactionLog = new ArrayList<>();
        if (existingBooks != null) {
            for (Book book : existingBooks) {
                isbnSet.add(book.getisbn());
                books.add(book);
            }
        }
    }
 
    public int uploadBooks(String filePath) {
        int booksAdded = 0;
        int skippedBooks = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(",");
                if (attributes.length >= 6) {
                    String isbn = attributes[4];
                    if (!isbnSet.contains(isbn)) {
                        Book newBook = new Book(attributes[0], attributes[1], attributes[2], attributes[3], isbn, attributes[5]);
                        addBook(newBook);
                        booksAdded++;
                    } else {
                        skippedBooks++;
                    }
                } else {
                    System.out.println("Invalid input format: " + line);
                    skippedBooks++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Books added: " + booksAdded);
        System.out.println("Skipped books (due to duplicate ISBNs or invalid format): " + skippedBooks);
        return booksAdded;
    }
 
    public void addBook(Book newBook) {
        isbnSet.add(newBook.getisbn());
        books.add(newBook);
    }
 
    public void removeBook(Book bookToRemove) {
        isbnSet.remove(bookToRemove.getisbn());
        books.remove(bookToRemove);
    }
 
    public List<Book> searchByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }
 
    public List<Book> searchByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }
 
    public List<Book> searchByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getisbn().equals(isbn))
                .collect(Collectors.toList());
    }
 
    public List<Book> searchByFilters(String genre, String publicationDate) {
        return books.stream()
                .filter(book -> (genre == null || book.getGenre().equalsIgnoreCase(genre)) && (publicationDate == null || book.getPublicationDate().equals(publicationDate)))
                .collect(Collectors.toList());
    }
 
    public void displayBookDetails(Book book) {
        System.out.println("----------------------------------------------------");
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Genre: " + book.getGenre());
        System.out.println("Publication Date: " + book.getPublicationDate());
        System.out.println("ISBN: " + book.getisbn());
        System.out.println("Number of Copies: " + book.getNoOfCopies());
        int availableCopies = book.getNoOfCopies();
        if (availableCopies > 0) {
            System.out.println("Status: Available Copies: " + availableCopies);
        } else {
            System.out.println("Status: Out of Stock");
        }
        System.out.println("----------------------------------------------------");
    }
 
    public void borrowBookByIsbn() {
Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your User ID:");
        String userId = scanner.nextLine();
        System.out.println("Enter the ISBN of the book you wish to borrow:");
        String isbn = scanner.nextLine();
        if (isbn.length() != 10 && isbn.length() != 13) {
            System.out.println("Invalid ISBN format. ISBN should be 10 or 13 characters long.");
            return;
        }
        List<Book> searchResults = searchByIsbn(isbn);
        if (searchResults.isEmpty()) {
            System.out.println("No book found with the given ISBN.");
        } else {
            Book book = searchResults.get(0);
            System.out.println("You have selected: " + book.getTitle() + " by " + book.getAuthor());
            if (book.getNoOfCopies() == 0) {
                System.out.println("Sorry, this book is currently out of stock.");
                System.out.println("Would you like to return to the main menu or perform another search? (menu/search)");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("menu")) {
                    return;
                } else if (choice.equalsIgnoreCase("search")) {
                    searchAndDisplayCLI();
                    return;
                } else {
                    System.out.println("Invalid input. Returning to main menu.");
                    return;
                }
            }
            System.out.println("Proceed to borrow this book? (yes/no)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                if (book.borrowBook()) {
                    System.out.println("You have successfully borrowed the book: " + book.getTitle());
                    System.out.println("Remaining copies: " + book.getNoOfCopies());
                    Transaction transaction = new Transaction(userId, book.getisbn(), LocalDate.now(), LocalDate.now().plusDays(OVERDUE_DAYS), "borrow");
                    transactionLog.add(transaction);
                } else {
                    System.out.println("Unable to borrow the book. Please try again.");
                }
            } else {
                System.out.println("Borrowing process cancelled.");
            }
        }
    }
    
    public void returnBookByIsbn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your User ID:");
        String userId = scanner.nextLine();
        System.out.println("Enter the ISBN of the book you wish to return:");
        String isbn = scanner.nextLine();

        List<Transaction> userTransactions = transactionLog.stream()
                .filter(transaction -> transaction.getUserId().equals(userId) && transaction.getIsbn().equals(isbn) && transaction.getType().equals("borrow"))
                .collect(Collectors.toList());

        if (userTransactions.isEmpty()) {
            System.out.println("No record found of you borrowing this book.");
            return;
        }

        List<Book> searchResults = searchByIsbn(isbn);
        if (searchResults.isEmpty()) {
            System.out.println("No book found with the given ISBN.");
        } else {
            Book book = searchResults.get(0);
            System.out.println("You are returning: " + book.getTitle() + " by " + book.getAuthor());
            System.out.println("Proceed to return this book? (yes/no)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                book.returnBook();
                System.out.println("You have successfully returned the book: " + book.getTitle());
                System.out.println("Available copies: " + book.getNoOfCopies());
                // Log the return transaction
                Transaction transaction = new Transaction(userId, book.getisbn(), LocalDate.now(), LocalDate.now().plusDays(OVERDUE_DAYS), "return");
                transactionLog.add(transaction);
            } else {
                System.out.println("Return cancelled.");
            }
        }
    }

    
    public void viewTransactionLog() {
        if (transactionLog.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction Log:");
            for (Transaction transaction : transactionLog) {
                System.out.println(transaction);
            }
        }
    }
 
    public void searchAndDisplayCLI() {
Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your search criteria (title, author, genre, publication date):");
        System.out.println("Title:");
        String title = scanner.nextLine();
        System.out.println("Author:");
        String author = scanner.nextLine();
        System.out.println("Genre:");
        String genre = scanner.nextLine();
        System.out.println("Publication Date:");
        String publicationDate = scanner.nextLine();
        List<Book> searchResults = searchByFilters(genre, publicationDate);
        if (title != null && !title.isEmpty()) {
            searchResults = searchResults.stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(title))
                    .collect(Collectors.toList());
        }
        if (author != null && !author.isEmpty()) {
            searchResults = searchResults.stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                    .collect(Collectors.toList());
        }
        if (searchResults.isEmpty()) {
            System.out.println("No books found matching your criteria.");
        } else {
            System.out.println("Search results:");
            for (Book book : searchResults) {
                displayBookDetails(book);
            }
        }
    }
 
    // Overdue management
    public void checkAndNotifyOverdueBooks() {
        LocalDate today = LocalDate.now();
        for (Transaction transaction : transactionLog) {
            if (transaction.getDueDate().isBefore(today) && !transaction.isNotified()) {
                System.out.println("User ID: " + transaction.getUserId() + " has an overdue book. ISBN: " + transaction.getIsbn());
                transaction.setNotified(true);
            }else {
            	System.out.println("No Overdue Book");
            }
        }
    }
    
 // Library statistics dashboard
    public void displayLibraryStatistics() {
        System.out.println("Library Statistics Overview:");
        System.out.println("--------------------------------------");
        System.out.println("Total Books: " + books.size());
        long availableBooks = books.stream().filter(book -> book.getNoOfCopies() > 0).count();
        System.out.println("Available Books: " + availableBooks);
        long borrowedBooks = books.size() - availableBooks;
        System.out.println("Borrowed Books: " + borrowedBooks);
        System.out.println("--------------------------------------");
    }
    
    // Display total book count
    public void displayTotalBookCount() {
        int totalBooks = books.size();
        System.out.println("Total Number of Books: " + totalBooks);
    }
    
    // Display number of currently borrowed books
    public void displayCurrentlyBorrowedBookCount() {
        long borrowedBooks = transactionLog.stream()
                .filter(transaction -> transaction.getType().equals("borrow"))
                .count();
        System.out.println("Number of Currently Borrowed Books: " + borrowedBooks);
    }
 
    // Display list of titles of all borrowed books
    public void displayBorrowedBookTitles() {
        System.out.println("List of Borrowed Books:");
        System.out.println("------------------------");
        transactionLog.stream()
                .filter(transaction -> transaction.getType().equals("borrow"))
                .map(Transaction::getIsbn)
                .distinct()
                .forEach(isbn -> {
                    List<Book> borrowedBooks = searchByIsbn(isbn);
                    borrowedBooks.forEach(book -> System.out.println(book.getTitle()));
                });
        System.out.println("------------------------");
    }
    
    // Method to display the most popular books
    public void displayMostPopularBooks(int topN) {
        Map<String, Long> borrowCountMap = transactionLog.stream()
                .filter(transaction -> transaction.getType().equals("borrow"))
                .collect(Collectors.groupingBy(Transaction::getIsbn, Collectors.counting()));
 
        List<Map.Entry<String, Long>> sortedBooks = borrowCountMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(topN)
                .collect(Collectors.toList());
 
        if (sortedBooks.isEmpty()) {
            System.out.println("No borrowed books found.");
        } else {
            System.out.println("Top " + topN + " Most Popular Books:");
            for (Map.Entry<String, Long> entry : sortedBooks) {
                String isbn = entry.getKey();
                long borrowCount = entry.getValue();
                Book book = books.stream().filter(b -> b.getisbn().equals(isbn)).findFirst().orElse(null);
                if (book != null) {
                    System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Borrow Count: " + borrowCount);
                }
            }
        }        	
        
    }
    
    public void displayBorrowingTrends(String timeframe) {
        Map<String, Integer> trendMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (Transaction transaction : transactionLog) {
            if ("borrow".equals(transaction.getType())) {
                LocalDate transactionDate = LocalDate.parse(transaction.getTransactionDate(), formatter);
                String key = "";
                switch (timeframe.toLowerCase()) {
                    case "monthly":
                        key = transactionDate.getYear() + "-" + transactionDate.getMonthValue();
                        break;
                    case "quarterly":
                        int quarter = (transactionDate.getMonthValue() - 1) / 3 + 1;
                        key = transactionDate.getYear() + "-Q" + quarter;
                        break;
                    case "yearly":
                        key = String.valueOf(transactionDate.getYear());
                        break;
                    	
                    default:
                        System.out.println("Invalid timeframe specified. Use 'monthly', 'quarterly', or 'yearly'.");
                        return;
                }
                trendMap.put(key, trendMap.getOrDefault(key, 0) + 1);
            }
        }
 
        System.out.println("Borrowing Trends (" + timeframe + "):");
        for (Map.Entry<String, Integer> entry : trendMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " borrows");
        }
    }
    
    // Method to display trending genres based on borrow counts
    public void displayTrendingGenres(int topN) {
        Map<String, Long> genreBorrowCount = new HashMap<>();
 
        for (Transaction transaction : transactionLog) {
            if ("borrow".equals(transaction.getType())) {
                Book book = books.stream().filter(b -> b.getisbn().equals(transaction.getIsbn())).findFirst().orElse(null);
                if (book != null) {
                    String genre = book.getGenre();
                    genreBorrowCount.put(genre, genreBorrowCount.getOrDefault(genre, 0L) + 1);
                }
            }
        }
 
        List<Map.Entry<String, Long>> sortedGenres = genreBorrowCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(topN)
                .collect(Collectors.toList());
 
        System.out.println("Trending Genres:");
        for (Map.Entry<String, Long> entry : sortedGenres) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " borrows");
        }
    }
    
    // Method to display trending authors based on total borrow counts of their books
    public void displayTrendingAuthors(int topN) {
        Map<String, Long> authorBorrowCount = new HashMap<>();
 
        for (Transaction transaction : transactionLog) {
            if ("borrow".equals(transaction.getType())) {
                Book book = books.stream().filter(b -> b.getisbn().equals(transaction.getIsbn())).findFirst().orElse(null);
                if (book != null) {
                    String author = book.getAuthor();
                    authorBorrowCount.put(author, authorBorrowCount.getOrDefault(author, 0L) + 1);
                }
            }
        }
 
        List<Map.Entry<String, Long>> sortedAuthors = authorBorrowCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(topN)
                .collect(Collectors.toList());
 
        System.out.println("Trending Authors:");
        for (Map.Entry<String, Long> entry : sortedAuthors) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " borrows");
        }
    }
    
    // Method to handle CLI commands
    public void handleCLI() {
Scanner scanner = new Scanner(System.in);
        while (true) {
        	System.out.println("Available commands:");
            System.out.println(" Books - Display most popular books ");
            System.out.println("Trends - Display borrowing trends");
            System.out.println("Genres - Display trending genres");
            System.out.println("Authors - Display trending authors");
            System.out.println(" exit - Exit the program ");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("exit")) {
                break;
            } else if (command.equalsIgnoreCase("Books")) {
                System.out.println("Enter the number of top books to display:");
                int topN = Integer.parseInt(scanner.nextLine());
                displayMostPopularBooks(topN);
            } else if (command.equalsIgnoreCase("Trends")) {
                System.out.println("Enter the timeframe (monthly, quarterly, yearly):");
                String timeframe = scanner.nextLine();
                displayBorrowingTrends(timeframe);
            } else if (command.equalsIgnoreCase("Genres")) {
                System.out.println("Enter the number of top genres to display:");
                int topN = Integer.parseInt(scanner.nextLine());
                displayTrendingGenres(topN);
            }else if (command.equalsIgnoreCase("Authors")) {
                System.out.println("Enter the number of top authors to display:");
                int topN = Integer.parseInt(scanner.nextLine());
                displayTrendingAuthors(topN);
            }
            // Handle other commands here
        }
        scanner.close();
    }
 
    public static void main(String[] args) {
        List<Book> books = Book.readBooksFromCSV("BooksData.csv");
        BookManager manager = new BookManager(books);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Upload Books");
            System.out.println("2. Search and Display Books");
            System.out.println("3. Borrow Book by ISBN");
            System.out.println("4. Return Book by ISBN");
            System.out.println("5. Check Overdue Books");
            System.out.println("6. View Library Statistics");
            System.out.println("7. View Total Book Count");
            System.out.println("8. Currently Borrowed Book Count");
            System.out.println("9. Currently Borrowed Book Titles");
            System.out.println("10. Most Popular Books");
            System.out.println("11. Trending Genres");
            System.out.println("12. Trending Authors");
            System.out.println("13. Borrowing Trends");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    System.out.print("Enter the file path to upload books: ");
                    String filePath = scanner.nextLine();
                    manager.uploadBooks(filePath);
                    break;
                case 2:
                    manager.searchAndDisplayCLI();
                    break;
                case 3:
                    manager.borrowBookByIsbn();
                    break;
                case 4:
                    manager.returnBookByIsbn();
                    break;
                case 5:
                    manager.checkAndNotifyOverdueBooks();
                    break;
                case 6:
                    manager.displayLibraryStatistics();
                    break;
                case 7:
                    manager.displayTotalBookCount();
                    break;
                case 8:
                    manager.displayCurrentlyBorrowedBookCount();
                    break;
                case 9:
                    manager.displayBorrowedBookTitles();
                    break;
                case 10:
                	System.out.println("Enter the number of top books to display:");
                    int topN = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    manager.displayMostPopularBooks(topN);
                    break; 
                case 11:
                	System.out.println("Enter the number of top Genres to display:");
                    int topN1 = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    manager.displayTrendingGenres(topN1);
                    break;  
                case 12:
                	System.out.println("Enter the number of top Authors to display:");
                    int topN2 = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    manager.displayTrendingAuthors(topN2);
                    break;
                case 13:
                    System.out.print("Enter the timeframe (monthly, quarterly, yearly): ");
                    String timeframe = scanner.nextLine();
                    manager.displayBorrowingTrends(timeframe);
                    break;   
                case 14:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        
    }
}