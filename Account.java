/**
 * Class to represent a basic bank account
 */
class Account {
    private long number;
    private long userId;
    protected double balance;


    /**
     * Basic constructor, initializes using given values
     */
    Account(long number, long userId, double balance) {
        this.number = number;
        this.balance = balance;
        this.userId = userId;
    }


    // Getter methods:
    long getNumber() {
        return this.number;
    }

    long getUserId() {
        return this.userId;
    }
    
    double getBalance() {
        return this.balance;
    }

    /**
     * Deposits money into account
     * @param amount
     * @return
     */
    boolean deposit(double amount) {balance += amount; return true;}

    /**
     * Withdraws money from account
     * @param amount
     * @return
     */
    boolean withdraw(double amount) {balance -= amount; return true;}

    void prettyPrint() {
        System.out.println("Account Number: " + this.number);
        System.out.println("Account holder user ID: " + this.userId);
        System.out.println("Balance: " + this.balance);
    }
}
