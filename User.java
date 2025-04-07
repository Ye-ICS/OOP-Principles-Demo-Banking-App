import java.util.ArrayList;

class User {
    private long id;
    private String name;
    private String dob;
    private int sin;
    private ArrayList<Account> accounts;

    User(long id, String name, String dob, int sin) {
        // if (name == null || name.trim().equals("")) {
        //     throw new IllegalArgumentException("'name' must not be empty or null");
        // }

        this.id = id;
        this.name = name;
        this.dob = dob;
        this.sin = sin;
        accounts = new ArrayList<>();
    }

    // Getters
    long getId() { return id; }
    String getName() { return name; }
    String getDob() { return dob; }
    int getSin() { return sin; }
    ArrayList<Account> getAccounts() { return accounts; }

    void addAccount(Account account) {
        // if (account.getUserId() != id) {
        //     throw new IllegalArgumentException("Account user ID mismatch when attempting to add to user with ID: " + id);
        // }

        for (Account existingAccount: accounts) {
            if (existingAccount.getNumber() == account.getNumber()) {
                throw new IllegalArgumentException("Account with same number has already been added: " + existingAccount.getNumber());
            }
        }

        accounts.add(account);
    }

    /**
     * Returns account with given number
     * @param accountNumber
     * @return Account with given account number, or null if not found
     */
    Account getAccountByNumber(long accountNumber) {
        for (Account account : accounts) {
            if (account.getNumber() == accountNumber) {
                return account;
            }
        }

        return null;    // Otherwise, nothing found
    }

    void prettyPrint() {
        System.out.println("User ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dob);
        System.out.println("SIN: " + sin);
    }

    void prettyPrintAccounts() {
        System.out.println("Accounts under user " + id + ":");
        for (Account account : accounts) {
            account.prettyPrint();
        }
    }
}