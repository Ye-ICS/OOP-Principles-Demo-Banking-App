/**
 * Class to represent a basic bank account
 */
class Account {
    long number;
    double balance;
    String name;

    

    boolean deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        } else {
            System.out.println("Deposit amount must be positive.");
            return false;
        }
    }

    boolean withdraw(double amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    void prettyPrint() {
        System.out.println("Account Number: " + this.number);
        System.out.println("Name: " + this.name);
        System.out.println("Balance: " + this.balance);
    }
}
