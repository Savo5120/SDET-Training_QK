import java.time.LocalDate;
 
public class Transaction {
    private String userId;
    private String isbn;
    private LocalDate borrowingDate;
    private LocalDate dueDate;
    private String type;
    private boolean notified;
    private String transactionDate;
 
    public Transaction(String userId, String isbn, LocalDate borrowingDate, LocalDate dueDate, String type) {
        this.userId = userId;
        this.isbn = isbn;
        this.borrowingDate = borrowingDate;
        this.dueDate = dueDate;
        this.type = type;
        this.notified = false;
        this.transactionDate = borrowingDate.toString();
    }
 
    public String getUserId() {
        return userId;
    }
 
    public String getIsbn() {
        return isbn;
    }
 
    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }
 
    public LocalDate getDueDate() {
        return dueDate;
    }
 
    public String getType() {
        return type;
    }
 
    public boolean isNotified() {
        return notified;
    }
 
    public void setNotified(boolean notified) {
        this.notified = notified;
    }
    
    public String getTransactionDate() { 
    	return transactionDate; }
 
    @Override
    public String toString() {
        return "Transaction{" +
                "userId='" + userId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", Borrowing Date=" + borrowingDate +
                ", dueDate=" + dueDate +
                ", type='" + type + '\'' +
                ", notified=" + notified +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }
}