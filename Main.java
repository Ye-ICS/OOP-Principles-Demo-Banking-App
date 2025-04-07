import java.util.List;
import java.util.Scanner;

class Main {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) {
        AccountManager accountMan = new AccountManager(ACCOUNTS_FILE, USERS_FILE);
        Scanner scanner = new Scanner(System.in);

        mainMenu(scanner, accountMan);
        accountMan.saveAccounts();
        accountMan.getUserManager().saveUsers();
    }

    private static void mainMenu(Scanner scanner, AccountManager accountManager) {
        while (true) {
            System.out.println("Welcome to the Bank Management System");
            System.out.println("1. Find user by user ID");
            System.out.println("2. Search users by name");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> selectUser(scanner, accountManager);
                case 2 -> searchUsersByName(scanner, accountManager);
                case 3 -> {return;}
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void selectUser(Scanner scanner, AccountManager accountManager) {
        System.out.println("Enter user ID:");
        long userId = Long.parseLong(scanner.nextLine());

        User foundUser = accountManager.getUserManager().getUserById(userId);
        if (foundUser == null) {
            System.out.println("User not found.");
            return;
        }

        foundUser.prettyPrint();
        userMenu(scanner, foundUser, accountManager);
        accountManager.saveAccounts();
    }

    private static void searchUsersByName(Scanner scanner, AccountManager accountManager) {
        System.out.println("Enter name to search:");
        String name = scanner.nextLine();

        List<User> users = accountManager.getUserManager().searchUsersByName(name);
        for (User user : users) {
            user.prettyPrint();
        }
    }

    private static void userMenu(Scanner scanner, User user, AccountManager accountManager) {
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Print Accounts");
            System.out.println("3. Select Account");
            System.out.println("4. Exit");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> createAccount(scanner, user, accountManager);
                case 2 -> user.prettyPrintAccounts();
                case 3 -> selectAccount(scanner, user);
                case 4 -> {return;}
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static Account createAccount(Scanner scanner, User user, AccountManager accountManager) {
        System.out.println("Enter initial balance:");
        double balance = Double.parseDouble(scanner.nextLine());

        Account newAccount = accountManager.createAccount(user,  balance);
        System.out.println("Account created successfully. Account Number: " + newAccount.getNumber());
        accountManager.saveAccounts();
        return newAccount;
    }

    private static void selectAccount(Scanner scanner, User user) {
        System.out.println("Enter account number:");
        int accountNumber = Integer.parseInt(scanner.nextLine());

        Account foundAccount = user.getAccountByNumber(accountNumber);
        if (foundAccount == null) {
            System.out.println("Account not found under user " + user.getId());
            return;
        }

        foundAccount.prettyPrint();
        accountMenu(scanner, foundAccount);
    }

    private static void accountMenu(Scanner scanner, Account account) {
        while (true) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Exit");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> promptDeposit(scanner, account);
                case 2 -> promptWithdraw(scanner, account);
                case 3 -> {return;}
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void promptDeposit(Scanner scanner, Account account) {
        System.out.println("Enter amount to deposit:");
        double depositAmount = Double.parseDouble(scanner.nextLine());
        if (account.deposit(depositAmount))
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        else
            System.out.println("Deposit failed");
    }

    private static void promptWithdraw(Scanner scanner, Account account) {
        System.out.println("Enter amount to withdraw:");
        double withdrawAmount = Double.parseDouble(scanner.nextLine());
        if (account.withdraw(withdrawAmount)) {
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}
