import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class AccountManager {
    private String accountsFile;
    private ArrayList<Account> accounts;


    /**
     * Constructs AccountManager, loads accounts from specified file
     * @param filename
     */
    AccountManager(String filename) {
        accountsFile = filename;
        loadAccounts();
    }


    /**
     * Creates and returns new account with given data
     * @param name Name of account holder
     * @param initialBalance Initial balance
     * @return New instance of Account
     */
    Account createAccount(String name, double initialBalance) {
        long newAccountNumber = generateAccountNumber();
        Account newAccount = new Account(newAccountNumber, name, initialBalance);
        accounts.add(newAccount);
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
     * Searches and returns accounts with matching name
     * @param name Name to match
     * @return Accounts that match name
     */
    List<Account> searchAccountsByName(String name) {
        ArrayList<Account> matchingAccounts = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getName().equalsIgnoreCase(name)) {
                matchingAccounts.add(accounts.get(i));
            }
        }

        return matchingAccounts;
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
                Account account = new Account(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]));
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
                writer.println(account.getNumber() + "," + account.getName() + "," + account.getBalance());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }
}
