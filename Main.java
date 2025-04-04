import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Main {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static Account currentAccount = null;

    public static void main(String[] args) {
        ArrayList<Account> accounts = loadAccounts();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account\n2. Search Account\n3. Access Account\n4. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> currentAccount = createAccount(scanner, accounts);
                case 2 -> searchAccount(scanner, accounts);
                case 3 -> accessAccount(scanner, accounts);
                case 4 -> {
                    saveAccounts(accounts);
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static Account createAccount(Scanner scanner, ArrayList<Account> accounts) {
        System.out.println("Enter account holder's name:");
        String name = scanner.nextLine();
        System.out.println("Enter initial balance:");
        double balance = Double.parseDouble(scanner.nextLine());

        int accountNumber = accounts.size() + 1;
        Account newAccount = new Account(accountNumber, name, balance);
        accounts.add(newAccount);
        System.out.println("Account created successfully. Account Number: " + accountNumber);
        return newAccount;
    }

    private static void searchAccount(Scanner scanner, ArrayList<Account> accounts) {
        System.out.println("Search by:\n1. Name\n2. Account Number");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.println("Enter name:");
                String name = scanner.nextLine();
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getName().equalsIgnoreCase(name)) {
                        accounts.get(i).prettyPrint();
                    }
                }
            }
            case 2 -> {
                System.out.println("Enter account number:");
                int accountNumber = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getNumber() == accountNumber) {
                        accounts.get(i).prettyPrint();
                    }
                }
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    private static void accessAccount(Scanner scanner, ArrayList<Account> accounts) {
        System.out.println("Enter account number:");
        int accountNumber = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getNumber() == accountNumber) {
                while (true) {
                    System.out.println("\n1. Deposit\n2. Withdraw\n3. Check Balance\n4. Exit");
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1 -> {
                            System.out.println("Enter amount to deposit:");
                            double depositAmount = Double.parseDouble(scanner.nextLine());
                            if (accounts.get(i).deposit(depositAmount))
                                System.out.println("Deposit successful. New balance: " + accounts.get(i).getBalance());
                            else
                                System.out.println("Deposit failed");
                        }
                        case 2 -> {
                            System.out.println("Enter amount to withdraw:");
                            double withdrawAmount = Double.parseDouble(scanner.nextLine());
                            if (accounts.get(i).withdraw(withdrawAmount)) {
                                System.out.println("Withdrawal successful. New balance: " + accounts.get(i).getBalance());
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        }
                        case 3 -> System.out.println("Current balance: " + accounts.get(i).getBalance());
                        case 4 -> {return;}
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                }
            }
        }
        System.out.println("Account not found.");
    }

    private static ArrayList<Account> loadAccounts() {
        ArrayList<Account> accounts = new ArrayList<Account>();

        try {
            Scanner scanner = new Scanner(new File(ACCOUNTS_FILE));
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

        return accounts;
    }

    private static void saveAccounts(ArrayList<Account> accounts) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE));
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
