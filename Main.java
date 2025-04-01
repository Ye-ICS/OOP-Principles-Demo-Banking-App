import java.io.*;
import java.util.Scanner;

class Main {
    static final String ACCOUNTS_FILE = "accounts.txt";
    static final int MAX_ACCOUNTS = 100;
    static int currentAccountIndex = -1;

    public static void main(String[] args) {
        int[] accountNumbers = new int[MAX_ACCOUNTS];
        String[] names = new String[MAX_ACCOUNTS];
        double[] balances = new double[MAX_ACCOUNTS];
        int accountCount = loadAccounts(accountNumbers, names, balances);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account\n2. Search Account\n3. Access Account\n4. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> accountCount = createAccount(scanner, accountNumbers, names, balances, accountCount);
                case 2 -> searchAccount(scanner, accountNumbers, names, balances, accountCount);
                case 3 -> accessAccount(scanner, accountNumbers, names, balances, accountCount);
                case 4 -> {
                    saveAccounts(accountNumbers, names, balances, accountCount);
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static int createAccount(Scanner scanner, int[] accountNumbers, String[] names, double[] balances, int accountCount) {
        if (accountCount >= MAX_ACCOUNTS) {
            System.out.println("Maximum number of accounts reached.");
            return accountCount;
        }

        System.out.println("Enter account holder's name:");
        String name = scanner.nextLine();
        System.out.println("Enter initial balance:");
        double balance = Double.parseDouble(scanner.nextLine());

        int accountNumber = accountCount + 1;
        accountNumbers[accountCount] = accountNumber;
        names[accountCount] = name;
        balances[accountCount] = balance;
        accountCount++;
        System.out.println("Account created successfully. Account Number: " + accountNumber);
        return accountCount;
    }

    static void searchAccount(Scanner scanner, int[] accountNumbers, String[] names, double[] balances, int accountCount) {
        System.out.println("Search by:\n1. Name\n2. Account Number");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.println("Enter name:");
                String name = scanner.nextLine();
                for (int i = 0; i < accountCount; i++) {
                    if (names[i].equalsIgnoreCase(name)) {
                        printAccount(i, accountNumbers, names, balances);
                    }
                }
            }
            case 2 -> {
                System.out.println("Enter account number:");
                int accountNumber = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < accountCount; i++) {
                    if (accountNumbers[i] == accountNumber) {
                        printAccount(i, accountNumbers, names, balances);
                    }
                }
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    static void accessAccount(Scanner scanner, int[] accountNumbers, String[] names, double[] balances, int accountCount) {
        System.out.println("Enter account number:");
        int accountNumber = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < accountCount; i++) {
            if (accountNumbers[i] == accountNumber) {
                while (true) {
                    System.out.println("\n1. Deposit\n2. Withdraw\n3. Check Balance\n4. Exit");
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1 -> {
                            System.out.println("Enter amount to deposit:");
                            double depositAmount = Double.parseDouble(scanner.nextLine());
                            if (deposit(i, balances, depositAmount))
                                System.out.println("Deposit successful. New balance: " + balances[i]);
                            else
                                System.out.println("Deposit failed");
                        }
                        case 2 -> {
                            System.out.println("Enter amount to withdraw:");
                            double withdrawAmount = Double.parseDouble(scanner.nextLine());
                            if (withdraw(i, balances, withdrawAmount)) {
                                System.out.println("Withdrawal successful. New balance: " + balances[i]);
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        }
                        case 3 -> System.out.println("Current balance: " + balances[i]);
                        case 4 -> {return;}
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                }
            }
        }
        System.out.println("Account not found.");
    }

    static boolean deposit(int index, double[] balances, double amount) {
        if (amount > 0) {
            balances[index] += amount;
            return true;
        } else {
            System.out.println("Deposit amount must be positive.");
            return false;
        }
    }
    

    static boolean withdraw(int index, double[] balances, double amount) {
        if (amount <= balances[index]) {
            balances[index] -= amount;
            return true;
        } else {
            return false;
        }
    }

    static int loadAccounts(int[] accountNumbers, String[] names, double[] balances) {
        int count = 0;
        try {
            Scanner scanner = new Scanner(new File(ACCOUNTS_FILE));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                accountNumbers[count] = Integer.parseInt(parts[0]);
                names[count] = parts[1];
                balances[count] = Double.parseDouble(parts[2]);
                count++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
        return count;
    }

    static void saveAccounts(int[] accountNumbers, String[] names, double[] balances, int accountCount) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE));
            for (int i = 0; i < accountCount; i++) {
                writer.println(accountNumbers[i] + "," + names[i] + "," + balances[i]);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    static void printAccount(int index, int[] accountNumbers, String[] names, double[] balances) {
        System.out.println("Account Number: " + accountNumbers[index]);
        System.out.println("Name: " + names[index]);
        System.out.println("Balance: " + balances[index]);
    }
}
