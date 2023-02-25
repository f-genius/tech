package org.example.accountImpl;

import org.example.accounts.DebitAccount;
import org.example.transactions.Transaction;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Class for working with a debit account
 */
public class DebitAccountImpl implements DebitAccount {
    private final ArrayList<Transaction> transactions;
    private final ArrayList<Long> remainders;
    private long percent;
    private UUID id;
    private long balance;
    private boolean isDoubtful;

    public DebitAccountImpl (
            long percent,
            long balance,
            boolean isDoubtful
    ) {
        if (balance < 0)
            throw new IllegalArgumentException("The balance can't be negative");

        this.balance = balance;
        this.percent = percent;
        this.isDoubtful = isDoubtful;
        transactions = new ArrayList<>();
        remainders = new ArrayList<>();
        id = UUID.randomUUID();
    }

    /**
     * returns a list of all transactions made with the account (read-only)
     * @return list of all transactions
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    /**
     * Returns debit account ID
     * @return UUID unique identification
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the current debit account balance
     * @return long balance
     */
    public long getBalance() {
        return balance;
    }
    /**
     * Returns true if the account is doubtful(the client does not have an address or passport), false otherwise
     * @return boolean doubtful account or not
     */
    public boolean getIsDoubtful() {
        return isDoubtful;
    }

    /**
     * adds a specified amount to the current balance
     * @param sum accrued amount
     */
    public void addMoney(long sum) {
        balance += sum;
    }

    /**
     * debits the specified amount from the account
     * @param sum amount to be debited
     * @throws IllegalArgumentException if the transferred amount is negative or if we want to write off more than we have on the account
     */
    public void reduceMoney(long sum) {
        if (sum < 0)
            throw new IllegalArgumentException("The sum can't be negative");
        if (balance < sum)
            throw new IllegalArgumentException("Insufficient funds");

        balance -= sum;
    }

    /**
     * adds a new completed transaction to the list of account transactions
     * @param transaction new transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * calculates daily interest on the account
     */
    public void update() {
        remainders.add(balance * percent / 100);
    }

    /**
     * pays monthly interest on the account
     */
    public void handler() {
        balance += remainders.stream().mapToLong(Long::longValue).sum();
        remainders.clear();
    }
}
