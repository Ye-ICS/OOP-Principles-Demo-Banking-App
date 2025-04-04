/**
 * Class to represent a basic bank account
 */
class Account {
    private static double withdrawalAlertAmount = 1000;
    private static double depositAlertAmount = 10_000;

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
        
        if (amount >= depositAlertAmount) {
            System.out.println("Alert: Client depositing a suspect amount. May require investigation.");
        }

        this.balance += amount;
        return true;
    }

    boolean withdraw(double amount) {
        if (amount > this.balance) {
            System.out.println("Insufficient funds.");
            return false;
        }

        if (amount >= withdrawalAlertAmount) {
            System.out.println("Alert: Client withdrawing a suspect amount. May require investigation.");
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
