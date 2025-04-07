import java.util.List;
import java.util.Scanner;

class Main {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static Account currentAccount = null;

    public static void main(String[] args) {
        AccountManager accountMan = new AccountManager(ACCOUNTS_FILE);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (currentAccount != null) {
                System.out.println("Selected account: ");
                currentAccount.prettyPrint();
                System.out.println("-".repeat(20));
            }

            System.out.println("1. Create Account\n2. Search Account\n3. Select Account\n4. Deposit\n5. Withdraw\n6. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> currentAccount = createAccount(scanner, accountMan);
                case 2 -> searchAccount(scanner, accountMan);
                case 3 -> selectAccount(scanner, accountMan);
                case 4 -> promptDeposit(scanner);
                case 5 -> promptWithdraw(scanner);
                case 6 -> {
                    accountMan.saveAccounts();
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static Account createAccount(Scanner scanner, AccountManager accountManager) {
        System.out.println("Enter account holder's name:");
        String name = scanner.nextLine();
        System.out.println("Enter initial balance:");
        double balance = Double.parseDouble(scanner.nextLine());

        Account newAccount = accountManager.createAccount(name, balance);
        System.out.println("Account created successfully. Account Number: " + newAccount.getNumber());
        return newAccount;
    }

    private static void searchAccount(Scanner scanner, AccountManager accountManager) {
        System.out.println("Search by:\n1. Name\n2. Account Number");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.println("Enter name:");
                String name = scanner.nextLine();

                List<Account> accounts = accountManager.searchAccountsByName(name);
                for (Account account : accounts) {
                    account.prettyPrint();
                }
            }
            case 2 -> {
                // TODO: Redundant. Remove this option.
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    private static void selectAccount(Scanner scanner, AccountManager accountManager) {
        System.out.println("Enter account number:");
        int accountNumber = Integer.parseInt(scanner.nextLine());

        Account foundAccount = accountManager.getAccountByNumber(accountNumber);
        if (foundAccount == null) {
            System.out.println("Account not found.");
            return;
        }

        currentAccount = foundAccount;  // Otherwise, set found account as currently selected
    }

    private static void promptDeposit(Scanner scanner) {
        if (currentAccount == null) {
            System.out.println("Can't. No account selected.");
            return;
        }

        System.out.println("Enter amount to deposit:");
        double depositAmount = Double.parseDouble(scanner.nextLine());
        if (currentAccount.deposit(depositAmount))
            System.out.println("Deposit successful. New balance: " + currentAccount.getBalance());
        else
            System.out.println("Deposit failed");
    }

    private static void promptWithdraw(Scanner scanner) {
        if (currentAccount == null) {
            System.out.println("Can't. No account selected.");
            return;
        }
        
        System.out.println("Enter amount to withdraw:");
        double withdrawAmount = Double.parseDouble(scanner.nextLine());
        if (currentAccount.withdraw(withdrawAmount)) {
            System.out.println("Withdrawal successful. New balance: " + currentAccount.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}
