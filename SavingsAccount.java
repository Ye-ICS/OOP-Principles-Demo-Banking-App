class SavingsAccount extends ChequingAccount {
    SavingsAccount(long number, long userId, double balance) {
        super(number, userId, balance);
    }

    @Override
    boolean withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not supported from a savings account.");
    }
}
