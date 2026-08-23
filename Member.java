import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Member {
    String member_name;
    String member_Id;
    
    // Your exact original database structure
    Map<String, Object> Members = new HashMap<>();

    // Constructor with parameters matching your original design
    public Member(String memberN, String mId) {
        this.member_name = memberN;
        this.member_Id = mId;
    }

    void addMember(Scanner s) {
        System.out.println("What is the member name?");
        member_name = s.nextLine();
        System.out.println("What is the memberId?");
        member_Id = s.nextLine();
        
        // Validation: Prevent duplicate Member IDs
        if (Members.containsKey(member_Id)) {
            System.out.println("Validation Error: Member ID already exists!");
            return;
        }

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("M_name", member_name);
        memberMap.put("member_Id", member_Id);
        
        Members.put(member_Id, memberMap);
        System.out.println("Member added: " + member_name + "\nID: " + member_Id);
    }

    void RemoveB(Scanner Search) {
        System.out.println("Which Member do you want to delete? Enter its id.");
        String SearchId = Search.nextLine();
        
        if (Members.containsKey(SearchId)) {
            System.out.println("Member found.\n " + Members.get(SearchId));
            Members.remove(SearchId);
            System.out.println("Member removed successfully.");
        } else {
            System.out.println("Member not found. Try again");
        }
    }

    // View all library members
    void viewAllMembers() {
        System.out.println("------View All Members--------");
        if (Members.isEmpty()) {
            System.out.println("No registered members found.");
        } else {
            for (String id : Members.keySet()) {
                System.out.println("ID: " + id + " -> " + Members.get(id));
            }
        }
    }

    // Search for a specific member by ID or Name
    void searchMember(Scanner sc) {
        System.out.println("Enter Member ID or Name to search:");
        String query = sc.nextLine().toLowerCase();
        boolean found = false;

        for (Object obj : Members.values()) {
            Map<String, Object> m = (Map<String, Object>) obj;
            String name = ((String) m.get("M_name")).toLowerCase();
            String id = ((String) m.get("member_Id")).toLowerCase();

            if (id.equals(query) || name.contains(query)) {
                System.out.println("Found Member: " + m);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Member not found.");
        }
    }
}
