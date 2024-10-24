import java.util.HashMap;
import java.util.Map;

class HelpDeskSystem {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Ticket> tickets = new HashMap<>();
    private int nextUserId = 1;
    private int nextTicketId = 1;
    private String currentUserId = null;

    class User {
        String username;
        String password;
        String role; // user, staff, or admin

        User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }

    class Ticket {
        String title;
        String description;
        String status;
        String userId;
        String staffId;

        Ticket(String title, String description, String userId) {
            this.title = title;
            this.description = description;
            this.status = "Open";
            this.userId = userId;
            this.staffId = null;
        }
    }

    void registerUser(String username, String password, String role) {
        String userId = String.valueOf(nextUserId++);
        users.put(userId, new User(username, password, role));
        System.out.println("User registered with ID: " + userId);
    }

    void login(String username, String password) {
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            if (user.username.equals(username) && user.password.equals(password)) {
                currentUserId = entry.getKey();
                System.out.println("Logged in as " + username + " (" + user.role + ")");
                return;
            }
        }
        System.out.println("Invalid username or password");
    }

    void logout() {
        currentUserId = null;
        System.out.println("Logged out");
    }

    void createTicket(String title, String description) {
        if (currentUserId == null) {
            System.out.println("Please log in to create a ticket");
            return;
        }

        User user = users.get(currentUserId);
        if (!"user".equals(user.role)) {
            System.out.println("Only users can create tickets");
            return;
        }

        String ticketId = String.valueOf(nextTicketId++);
        tickets.put(ticketId, new Ticket(title, description, currentUserId));
        System.out.println("Ticket created with ID: " + ticketId);
    }

    void viewTickets() {
        if (currentUserId == null) {
            System.out.println("Please log in to view tickets");
            return;
        }

        User user = users.get(currentUserId);
        if ("user".equals(user.role)) {
            for (Map.Entry<String, Ticket> entry : tickets.entrySet()) {
                Ticket ticket = entry.getValue();
                if (ticket.userId.equals(currentUserId)) {
                    System.out.println("Ticket ID: " + entry.getKey());
                    System.out.println("Title: " + ticket.title);
                    System.out.println("Description: " + ticket.description);
                    System.out.println("Status: " + ticket.status);
                    System.out.println();
                }
            }
        } else if ("staff".equals(user.role)) {
            for (Map.Entry<String, Ticket> entry : tickets.entrySet()) {
                Ticket ticket = entry.getValue();
                if (ticket.staffId != null && ticket.staffId.equals(currentUserId)) {
                    System.out.println("Ticket ID: " + entry.getKey());
                    System.out.println("Title: " + ticket.title);
                    System.out.println("Description: " + ticket.description);
                    System.out.println("Status: " + ticket.status);
                    System.out.println();
                }
            }
        }
    }

    void updateTicketStatus(String ticketId, String status) {
        if (currentUserId == null) {
            System.out.println("Please log in to update a ticket");
            return;
        }
    }
}

       
