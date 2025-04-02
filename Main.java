import java.io.*;
import java.util.Scanner;

class Main {
    static final String ACCOUNTS_FILE = "accounts.txt";
    static final int MAX_ACCOUNTS = 100;
    static Account currentAccount = null;

    public static void main(String[] args) {
        Account[] accounts = new Account[MAX_ACCOUNTS];
        int accountCount = loadAccounts(accounts);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account\n2. Search Account\n3. Access Account\n4. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> accountCount = createAccount(scanner, accounts, accountCount);
                case 2 -> searchAccount(scanner, accounts, accountCount);
                case 3 -> accessAccount(scanner, accounts, accountCount);
                case 4 -> {
                    saveAccounts(accounts, accountCount);
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static int createAccount(Scanner scanner, Account[] accounts, int accountCount) {
        if (accountCount >= MAX_ACCOUNTS) {
            System.out.println("Maximum number of accounts reached.");
            return accountCount;
        }

        System.out.println("Enter account holder's name:");
        String name = scanner.nextLine();
        System.out.println("Enter initial balance:");
        double balance = Double.parseDouble(scanner.nextLine());

        int accountNumber = accountCount + 1;
        accounts[accountCount] = new Account();
        accounts[accountCount].number = accountNumber;
        accounts[accountCount].name = name;
        accounts[accountCount].balance = balance;
        accountCount++;
        System.out.println("Account created successfully. Account Number: " + accountNumber);
        return accountCount;
    }

    static void searchAccount(Scanner scanner, Account[] accounts, int accountCount) {
        System.out.println("Search by:\n1. Name\n2. Account Number");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.println("Enter name:");
                String name = scanner.nextLine();
                for (int i = 0; i < accountCount; i++) {
                    if (accounts[i].name.equalsIgnoreCase(name)) {
                        accounts[i].prettyPrint();
                    }
                }
            }
            case 2 -> {
                System.out.println("Enter account number:");
                int accountNumber = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < accountCount; i++) {
                    if (accounts[i].number == accountNumber) {
                        accounts[i].prettyPrint();
                    }
                }
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    static void accessAccount(Scanner scanner, Account[] accounts, int accountCount) {
        System.out.println("Enter account number:");
        int accountNumber = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].number == accountNumber) {
                while (true) {
                    System.out.println("\n1. Deposit\n2. Withdraw\n3. Check Balance\n4. Exit");
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1 -> {
                            System.out.println("Enter amount to deposit:");
                            double depositAmount = Double.parseDouble(scanner.nextLine());
                            if (accounts[i].deposit(depositAmount))
                                System.out.println("Deposit successful. New balance: " + accounts[i].balance);
                            else
                                System.out.println("Deposit failed");
                        }
                        case 2 -> {
                            System.out.println("Enter amount to withdraw:");
                            double withdrawAmount = Double.parseDouble(scanner.nextLine());
                            if (accounts[i].withdraw(withdrawAmount)) {
                                System.out.println("Withdrawal successful. New balance: " + accounts[i].balance);
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        }
                        case 3 -> System.out.println("Current balance: " + accounts[i].balance);
                        case 4 -> {return;}
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                }
            }
        }
        System.out.println("Account not found.");
    }

    static int loadAccounts(Account[] accounts) {
        int count = 0;
        try {
            Scanner scanner = new Scanner(new File(ACCOUNTS_FILE));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                accounts[count] = new Account();
                accounts[count].number = Integer.parseInt(parts[0]);
                accounts[count].name = parts[1];
                accounts[count].balance = Double.parseDouble(parts[2]);
                count++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return count;
    }

    static void saveAccounts(Account[] accounts, int accountCount) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE));
            for (int i = 0; i < accountCount; i++) {
                Account account = accounts[i];
                writer.println(account.number + "," + account.name + "," + account.balance);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }
}
