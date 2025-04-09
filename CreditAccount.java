class CreditAccount extends Account {
    private double limit;
    private double interestRate;
    
    /**
     * Constructor for CreditAccount
     * @param number Account number
     * @param userId User ID
     * @param balance Initial balance
     * @param limit Credit limit
     * @param interestRate Interest rate for the credit account
     */
    CreditAccount(long number, long userId, double balance, double limit, double interestRate) {
        super(number, userId, balance);

        if (limit <= 0) {
            throw new IllegalArgumentException("Credit limit must be positive.");
        }

        if (interestRate < 0 || interestRate > 1) {
            throw new IllegalArgumentException("Interest rate must be between 0 and 1.");
        }
        
        this.limit = limit;
        this.interestRate = interestRate;
    }

    // Additional getter methods:
    public double getLimit() { return limit; }
    public double getInterestRate() { return interestRate;}

    /**
     * Applies interest to the credit balance, if positive
     */
    void applyInterest() {
        if (balance <= 0) {
            return;
        }
        
        double interest = balance * interestRate;
        balance += interest;
    }

    @Override
    boolean transfer(Account target, double amount) {
        throw new UnsupportedOperationException("Transfer from credit account is not supported.");
    }

    @Override
    boolean withdraw(double amount) {
        // TODO: Apply fee for withdrawal.
        return chargeCredit(amount);
    }

    @Override
    boolean deposit(double amount) {
        return payCredit(amount);
    }

    /**
     * Pays credit balance by specified amount
     * @param amount Amount to pay
     * @return Whether the payment was successful
     */
    boolean payCredit(double amount) {
        if (amount <= 0) {
            System.out.println("Payment amount must be positive.");
            return false;
        }
        
        balance -= amount;
        return true;
    }

    /**
     * Charges credit by specified amount
     * @param amount Amount to charge
     * @return
     */
    boolean chargeCredit(double amount) {
        if (amount <= 0) {
            System.out.println("Charge amount must be positive.");
            return false;
        }

        if (balance + amount > limit) {
            System.out.println("Charge exceeds credit limit.");
            return false;
        }
        
        balance += amount;
        return true;
    }

    @Override
    void prettyPrint() {
        super.prettyPrint();
        System.out.println("Credit Limit: " + limit);
        System.out.println("Interest Rate: " + interestRate);
        System.out.println("Current Balance: " + getBalance());
    }
}
