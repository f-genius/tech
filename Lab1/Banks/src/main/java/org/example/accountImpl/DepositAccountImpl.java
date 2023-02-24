package org.example.accountImpl;

import org.example.accounts.DepositAccount;
import org.example.transactions.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Class for working with a deposit account
 */
public class DepositAccountImpl implements DepositAccount {
    private ArrayList<Transaction> transactions;
    private ArrayList<Long> remainders;
    private long percent;
    private UUID id;
    private boolean isDoubtful;
    private long balance;
    private Calendar time;

    public DepositAccountImpl (
            long _percent,
            long _balance,
            Calendar _time,
            boolean _isDoubtful
    ) {
        if (_balance < 0)
            throw new IllegalArgumentException("The balance can't be negative");

        transactions = new ArrayList<>();
        remainders = new ArrayList<>();
        balance = _balance;
        percent = _percent;
        isDoubtful = _isDoubtful;
        time = _time;
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
     * Returns the current deposit account balance
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
