package org.example.accountImpl;
import org.example.accounts.CreditAccount;
import org.example.transactions.Transaction;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Class for working with a credit account
 */
public class CreditAccountImpl implements CreditAccount {
    private ArrayList<Transaction> transactions;
    private UUID id;
    private boolean isDoubtful;
    private long balance;

    public CreditAccountImpl (boolean isDoubtful, long balance) {
        if (_balance < 0)
            throw new IllegalArgumentException("The balance can't be negative");

        transactions = new ArrayList<>();
        this.balance = balance;
        this.isDoubtful = _isDoubtful;
        id = UUID.randomUUID();
    }

    /**
     * Returns credit account ID
     * @return UUID unique identification
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns true if the account is doubtful(the client does not have an address or passport), false otherwise
     * @return boolean doubtful account or not
     */
    public boolean getIsDoubtful() {
        return isDoubtful;
    }

    /**
     * Returns the current credit account balance
     * @return long balance
     */
    public long getBalance() {
        return balance;
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
     */
    public void reduceMoney(long sum) {
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
     * for daily interest calculation, not implemented in credits
     */
    public void update() { }

    /**
     * for monthly interest calculation, not implemented in loans
     */
    public void handler() { }
}
