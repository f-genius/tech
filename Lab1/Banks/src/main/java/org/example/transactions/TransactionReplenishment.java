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
            Account _account,
            long _sum,
            Calendar _time,
            long _commission,
            boolean _canceled
    ) {
        account = _account;
        sum = _sum;
        time = _time;
        canceled = _canceled;
        commission = _commission;
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
