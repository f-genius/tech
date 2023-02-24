package org.example.accounts;

import org.example.transactions.Transaction;

import java.util.UUID;

/**
 * interface that defines common methods for working with accounts
 */
public interface Account {
    /**
     * Returns account ID
     * @return UUID unique identification
     */
    UUID getId();
    /**
     * Returns true if the account is doubtful(the client does not have an address or passport), false otherwise
     * @return boolean doubtful account or not
     */
    boolean getIsDoubtful();

    /**
     * Returns the current credit account balance
     * @return long balance
     */
    long getBalance();

    /**
     * adds a specified amount to the current balance
     * @param sum accrued amount
     */
    void addMoney(long sum);

    /**
     * debits the specified amount from the account
     * @param sum amount to be debited
     */
    void reduceMoney(long sum);

    /**
     * adds a new completed transaction to the list of account transactions
     * @param transaction new transaction
     */
    void addTransaction(Transaction transaction);

    /**
     * for daily interest calculation
     */
    void update();

    /**
     * for monthly interest calculation
     */
    void handler();
}
