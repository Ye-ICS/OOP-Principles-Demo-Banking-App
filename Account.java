/**
 * Class to represent a basic bank account
 */
class Account {
    private long number;
    private double balance;
    private String name;


    /**
     * Basic constructor, initializes using given values
     */
    Account(long number, String name, double balance) {
        this.number = number;
        this.balance = balance;
        this.name = name;
    }


    // Getter methods:
    long getNumber() {
        return this.number;
    }

    String getName() {
        return this.name;
    }
    
    double getBalance() {
        return this.balance;
    }
    
    boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }

        this.balance += amount;
        return true;
    }

    boolean withdraw(double amount) {
        if (amount > this.balance) {
            System.out.println("Insufficient funds.");
            return false;
        }
        
        this.balance -= amount;
        return true;
    }

    void prettyPrint() {
        System.out.println("Account Number: " + this.number);
        System.out.println("Name: " + this.name);
        System.out.println("Balance: " + this.balance);
    }
}
