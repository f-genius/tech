package org.example.transactions;

import org.example.accounts.Account;

import java.util.Calendar;

/**
 * class for working with replenishment transactions
 */
public class TransactionReplenishment implements Transaction {
    private Account account;
    private long sum;
    private long commission;
    private Calendar time;
    private boolean canceled;

    public TransactionReplenishment (
            Account account,
            long sum,
            Calendar time,
            long commission,
            boolean canceled
    ) {
        this.account = account;
        this.sum = sum;
        this.time = time;
        this.canceled = canceled;
        this.commission = commission;
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
     * cancels the replenishment, returns the money to the account (minus the commission)
     * makes the transaction canceled
     */
    public void cancel() {
        account.reduceMoney(sum - commission);
        canceled = true;
    }
}
