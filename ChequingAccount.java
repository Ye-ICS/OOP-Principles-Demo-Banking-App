class ChequingAccount extends Account {
    private static double withdrawalAlertAmount = 1000;
    private static double depositAlertAmount = 10_000;


    ChequingAccount(long number, long userId, double balance) {
        super(number, userId, balance);
    }
    
    
    @Override
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

    @Override
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
}
