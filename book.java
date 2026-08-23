import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class book {
    // Tracks transaction log: Key = Book ID, Value = Member ID who borrowed it
    private static Map<String, String> transactions = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Instantiate your bookMain and Member objects with default constructor values
        bookMain bookManager = new bookMain("", "", "", true);
        Member memberManager = new Member("", "");

        int mainChoice;

        do {
            System.out.println("\n=========================================");
            System.out.println("       LIBRARY MANAGEMENT SYSTEM        ");
            System.out.println("=========================================");
            System.out.println("1. Book Management");
            System.out.println("2. Member Management");
            System.out.println("3. Book Transaction Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            // Validation: Handle invalid structural inputs cleanly
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and 4.");
                sc.next();
            }
            mainChoice = sc.nextInt();
            sc.nextLine(); // Clear scanner buffer

            switch (mainChoice) {
                case 1:
                    runBookMenu(sc, bookManager);
                    break;
                case 2:
                    runMemberMenu(sc, memberManager);
                    break;
                case 3:
                    runTransactionMenu(sc, bookManager, memberManager);
                    break;
                case 4:
                    System.out.println("Exiting System... Task complete. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please select 1-4.");
            }
        } while (mainChoice != 4);

        sc.close();
    }

    private static void runBookMenu(Scanner sc, bookMain bm) {
        System.out.println("\n--- Book Management Sub-Menu ---");
        System.out.println("1. Add a new book");
        System.out.println("2. Remove a book");
        System.out.println("3. View all books");
        System.out.println("4. Search book (Title/Author/ID)");
        System.out.println("5. Track book availability");
        System.out.print("Choice: ");
        int choice = sc.nextInt(); sc.nextLine();

        switch (choice) {
            case 1: bm.AddN(sc); break;
            case 2: bm.RemoveB(sc); break;
            case 3: bm.viewAllB(); break;
            case 4: bm.searchBookFlexible(sc); break;
            case 5: bm.Availability(sc); break;
            default: System.out.println("Invalid selection.");
        }
    }

    private static void runMemberMenu(Scanner sc, Member mm) {
        System.out.println("\n--- Member Management Sub-Menu ---");
        System.out.println("1. Add a new member");
        System.out.println("2. Remove a member");
        System.out.println("3. View all members");
        System.out.println("4. Search member by ID or Name");
        System.out.print("Choice: ");
        int choice = sc.nextInt(); sc.nextLine();

        switch (choice) {
            case 1: mm.addMember(sc); break;
            case 2: mm.RemoveB(sc); break;
            case 3: mm.viewAllMembers(); break;
            case 4: mm.searchMember(sc); break;
            default: System.out.println("Invalid selection.");
        }
    }

    private static void runTransactionMenu(Scanner sc, bookMain bm, Member mm) {
        System.out.println("\n--- Book Transaction Management ---");
        System.out.println("1. Issue a book to a member");
        System.out.println("2. Return a book");
        System.out.println("3. Check whether a book is available");
        System.out.println("4. Maintain/View issue and return records");
        System.out.print("Choice: ");
        int choice = sc.nextInt(); sc.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter Book ID to issue: ");
                String bId = sc.nextLine();
                System.out.print("Enter Member ID: ");
                String mId = sc.nextLine();

                // Paper requirements validations
                Map<String, Object> targetBook = bm.findBookById(bm.Books, bId);
                boolean memberExists = mm.Members.containsKey(mId);

                if (targetBook == null) {
                    System.out.println("Validation Error: Book ID does not exist!");
                } else if (!memberExists) {
                    System.out.println("Validation Error: Member ID does not exist!");
                } else if (!(boolean) targetBook.get("isAvailable?:")) {
                    System.out.println("Validation Error: Prevented! This book is already issued to someone else.");
                } else {
                    targetBook.put("isAvailable?:", false); // Book is now taken
                    transactions.put(bId, mId);             // Log transaction mapping
                    System.out.println("Success! Book '" + targetBook.get("bookName") + "' has been issued.");
                }
                break;

            case 2:
                System.out.print("Enter Book ID to return: ");
                String retBookId = sc.nextLine();
                Map<String, Object> returnBook = bm.findBookById(bm.Books, retBookId);

                if (returnBook == null) {
                    System.out.println("Validation Error: Book does not exist in library records.");
                } else if ((boolean) returnBook.get("isAvailable?:")) {
                    System.out.println("Validation Error: This book is already on the library shelves.");
                } else {
                    returnBook.put("isAvailable?:", true); // Reset status to available
                    transactions.remove(retBookId);        // Erase active loan record
                    System.out.println("Success! Book returned safely to the library shelves.");
                }
                break;

            case 3:
                System.out.print("Enter Book ID: ");
                String checkId = sc.nextLine();
                Map<String, Object> cb = bm.findBookById(bm.Books, checkId);
                if (cb != null) {
                    System.out.println("Book Status: " + (((boolean) cb.get("isAvailable?:")) ? "AVAILABLE" : "NOT AVAILABLE"));
                } else {
                    System.out.println("Book not found.");
                }
                break;

            case 4:
                System.out.println("\n--- Active Issue & Return Records ---");
                if (transactions.isEmpty()) {
                    System.out.println("No active transaction records to show.");
                } else {
                    for (Map.Entry<String, String> entry : transactions.entrySet()) {
                        Map<String, Object> bRecord = bm.Books.get(entry.getKey());
                        Map<String, Object> mRecord = (Map<String, Object>) mm.Members.get(entry.getValue());
                        System.out.println("Book: [" + bRecord.get("bookName") + "] -> Borrowed By: [" + mRecord.get("M_name") + "]");
                    }
                }
                break;
            default:
                System.out.println("Invalid selection.");
        }
    }
}
