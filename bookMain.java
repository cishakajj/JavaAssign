import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class bookMain {
    String bookName;
    String bookId;
    String bookAuthor;
    boolean isAvailable = true;
    
    Map<String, Map<String, Object>> Books = new HashMap<>();

    public bookMain(String bName, String bId, String bAuthor, boolean isAvailable) {
        this.bookName = bName;
        this.bookId = bId;
        this.bookAuthor = bAuthor;
        this.isAvailable = isAvailable;
    }

    void AddN(Scanner sc) {
        System.out.println("Enter the book name:");
        bookName = sc.nextLine();
        
        System.out.println("Enter the book id:");
        bookId = sc.nextLine();
        
        // Validation: Prevent duplicate Book IDs
        if (Books.containsKey(bookId)) {
            System.out.println("Validation Error: A book with this ID already exists!");
            return;
        }

        System.out.println("Enter the book Author:");
        bookAuthor = sc.nextLine();

        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("bookName", bookName);
        bookMap.put("bookId", bookId);
        bookMap.put("bookAuthor", bookAuthor);
        bookMap.put("isAvailable?:", true); // the book is initially available
        
        Books.put(bookId, bookMap);
        System.out.println("Book successfully added!");
    }

    // Method to get to search for a book(which is a map too) in the books map. That is why the return value is a map too.
    Map<String, Object> findBookById(Map<String, Map<String, Object>> bookDB, String SearchId) {
        return bookDB.get(SearchId);
    }

    void RemoveB(Scanner sc) {
        System.out.println("------Book Removal Process--------");
        System.out.println("Enter the ID of the book you want to remove:");
        String searchId = sc.nextLine();
        
        Map<String, Object> bookFound = findBookById(Books, searchId);
        if (bookFound != null) {
            System.out.println("Book found: " + Books.get(searchId));
            Books.remove(searchId);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found");
        }
    }


    void viewAllB() {
        System.out.println("------View All Books--------");
        if (Books.isEmpty()) {
            System.out.println("No books available in the system.");
        } else {
            for (String id : Books.keySet()) {
                System.out.println("ID: " + id + " -> Details: " + Books.get(id));
            }
        }
    }

    //Searching a book by either the title, the author or the ID.
    void searchBookFlexible(Scanner sc) {
        System.out.println("Enter search term (Title, Author, or ID):");
        String query = sc.nextLine().toLowerCase();
        boolean found = false;

        for (Map<String, Object> b : Books.values()) {
            String name = ((String) b.get("bookName")).toLowerCase();
            String id = ((String) b.get("bookId")).toLowerCase();
            String author = ((String) b.get("bookAuthor")).toLowerCase();

            if (id.equals(query) || name.contains(query) || author.contains(query)) {
                System.out.println("Found: " + b);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching books found.");
        }
    }

    void Availability(Scanner sc) {
        System.out.println("-------Book Availability process--------");
        System.out.println("Which book do you want to check? Enter its id:");
        String searchId = sc.nextLine();
        
        Map<String, Object> bookFound = findBookById(Books, searchId);
        if (bookFound != null) {
            System.out.println("Book found.\n " + Books.get(searchId));
            System.out.println("Book Available (not taken?): " + bookFound.get("isAvailable?:"));
        } else {
            System.out.println("Book not found.");
        }
    }
}
