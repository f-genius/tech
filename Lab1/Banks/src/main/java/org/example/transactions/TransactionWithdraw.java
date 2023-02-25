package org.example.transactions;

import org.example.accounts.Account;

import java.util.Calendar;

/**
 * class for working with withdrawal transactions
 */
public class TransactionWithdraw implements Transaction {
    private Calendar time;
    private long sum;
    private long commission;
    private boolean canceled;
    private Account from;

    public TransactionWithdraw(
            Account from,
            Calendar time,
            long sum,
            long commission,
            boolean canceled
    ) {
        if (sum < 0)
            throw new IllegalArgumentException("The sum can't be negative");

        this.from = from;
        this.time = time;
        this.sum = sum;
        this.commission = commission;
        this.canceled = canceled;
    }

    /**
     * returns true if the transaction is cancelled, false if not
     *
     * @return boolean canceled
     */
    public boolean getCanceled() {
        return canceled;
    }

    /**
     * returns the ID of the account from which the money was withdrawn
     *
     * @return id of the account (from)
     */
    public Account getFrom() {
        return from;
    }

    /**
     * cancels the withdrawals, returns the money to the account
     * makes the transaction canceled
     */
    public void cancel() {
        from.addMoney(sum + commission);
        canceled = true;
    }
}
