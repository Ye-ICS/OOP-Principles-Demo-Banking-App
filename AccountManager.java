import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class AccountManager {
    private UserManager userMan;
    private String accountsFile;
    private ArrayList<Account> accounts;


    /**
     * Constructs AccountManager, loads accounts and users from specified files
     * @param accountsFile
     * @param usersFile
     */
    AccountManager(String accountsFile, String usersFile) {
        this.accountsFile = accountsFile;
        userMan = new UserManager(usersFile);   // Load users first so accounts can be linked to users
        loadAccounts();
    }

    UserManager getUserManager() {
        return userMan;
    }


    /**
     * Creates and returns new account with given data
     * @param user User who owns the account
     * @param initialBalance Initial balance
     * @return New instance of Account
     */
    Account createAccount(User user, double initialBalance) {
        long newAccountNumber = generateAccountNumber();
        Account newAccount = new Account(newAccountNumber, user.getId(), initialBalance);
        accounts.add(newAccount);
        user.addAccount(newAccount);
        return newAccount;
    }

    /**
     * Generate new account number
     * @return Unique number not used by any existing account
     */
    private long generateAccountNumber() {
        // TODO: Not secure. Use random in future.
        long highestAccountNumber = 0;
        for (Account account : accounts) {
            if (highestAccountNumber < account.getNumber()) {
                highestAccountNumber = account.getNumber();
            }
        }

        return highestAccountNumber + 1;
    }

    /**
     * Returns account with given number
     * @param accountNumber Account number
     * @return Account with given account number, or null if not found
     */
    Account getAccountByNumber(long accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getNumber() == accountNumber) {
                return accounts.get(i);
            }
        }

        return null;    // Otherwise, nothing found
    }

    /**
     * Loads accounts from storage
     */
    private void loadAccounts() {
        accounts = new ArrayList<Account>();

        try {
            Scanner scanner = new Scanner(new File(accountsFile));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                Account account = new Account(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Double.parseDouble(parts[2]));
                
                // Check if linked user exists
                User user = userMan.getUserById(account.getUserId());
                if (user == null) {
                    System.err.println("User with ID " + account.getUserId() + " not found for account " + account.getNumber());
                    continue; // Skip this account
                }

                user.addAccount(account);
                accounts.add(account);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

    /**
     * Saves accounts to storage
     */
    void saveAccounts() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(accountsFile));
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                String accountType = "ch";  // TODO: Replace with each account's actual type
                writer.println(account.getNumber() + "," + account.getUserId()+ "," + account.getBalance() + "," + accountType);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }
}
