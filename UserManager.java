import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class UserManager {
    private String usersFile;
    private ArrayList<User> users;
    
    /**
     * Constructs UserManager. Automatically loads users from specified file
     * @param filename
     */
    UserManager(String filename) {
        usersFile = filename;
        loadUsers();
    }

    /**
     * Returns user with given ID
     * @param id User ID
     * @return User with given ID, or null if not found
     */
    User getUserById(long id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return users.get(i);
            }
        }

        return null;    // Otherwise, nothing found
    }

    /**
     * Searches and returns users with matching name
     * @param name Name to match
     * @return Users that match name
     */
    ArrayList<User> searchUsersByName(String name) {
        ArrayList<User> matchingUsers = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equalsIgnoreCase(name)) {
                matchingUsers.add(users.get(i));
            }
        }

        return matchingUsers;
    }

    /**
     * Creates a new user and adds it to the list
     * @param name User's name
     * @param dob User's date of birth
     * @param sin User's social insurance number
     * @return The created user
     */
    User createUser(String name, String dob, int sin) {
        long id = generateUserId();  // Generate a new ID for the user
        User newUser = new User(id, name, dob, sin);
        users.add(newUser);
        return newUser;
    }

    /**
     * Generates a new user ID
     * @return New user ID
     */
    private long generateUserId() {
        long maxId = 0;

        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }

        return maxId + 1;
    }
    
    /**
     * Loads users from storage
     */
    private void loadUsers() {
        users = new ArrayList<User>();

        try {
            Scanner scanner = new Scanner(new File(usersFile));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                User user = new User(Long.parseLong(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]));
                users.add(user);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    /**
     * Saves users to storage
     */
    void saveUsers() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(usersFile));
            for (User user : users) {
                writer.println(user.getId() + "," + user.getName() + "," + user.getDob() + "," + user.getSin());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found: " + usersFile);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}

