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
            System.out.println("4. Back");

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
        // TODO: Ask for account type
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
        if (account.getClass() == ChequingAccount.class) {
            chequingAccountMenu(scanner, (ChequingAccount) account);
        } else if (account.getClass() == SavingsAccount.class) {
            savingsAccountMenu(scanner, (SavingsAccount) account);
        } else if (account.getClass() == CreditAccount.class) {
            creditAccountMenu(scanner, (CreditAccount) account);
        } else {
            System.out.println("Unknown account type.");
        }
    }

    private static void chequingAccountMenu(Scanner scanner, ChequingAccount account) {
        while (true) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Back");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> promptDeposit(scanner, account);
                case 2 -> promptWithdraw(scanner, account);
                case 3 -> {return;}
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void savingsAccountMenu(Scanner scanner, SavingsAccount account) {
        while (true) {
            System.out.println("1. Deposit");
            System.out.println("2. Back");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> promptDeposit(scanner, account);
                case 2 -> {return;}
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void creditAccountMenu(Scanner scanner, CreditAccount account) {
        while (true) {
            System.out.println("1. Pay Credit");
            System.out.println("2. Charge Credit");
            System.out.println("3. Withdraw cash (fees may apply)");
            System.out.println("4. Back");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> promptPayCredit(scanner, account);
                case 2 -> promptChargeCredit(scanner, account);
                case 3 -> promptWithdraw(scanner, account);
                case 4 -> {return;}
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

    private static void promptPayCredit(Scanner scanner, CreditAccount account) {
        System.out.println("Enter amount to pay:");
        double payAmount = Double.parseDouble(scanner.nextLine());
        if (account.payCredit(payAmount)) {
            System.out.println("Payment successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Payment failed.");
        }
    }

    private static void promptChargeCredit(Scanner scanner, CreditAccount account) {
        System.out.println("Enter amount to charge:");
        double chargeAmount = Double.parseDouble(scanner.nextLine());
        if (account.chargeCredit(chargeAmount)) {
            System.out.println("Charge successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Charge failed.");
        }
    }
}
