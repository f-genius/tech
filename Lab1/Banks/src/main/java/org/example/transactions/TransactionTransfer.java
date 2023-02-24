package org.example.transactions;

import org.example.accounts.Account;

import java.util.Calendar;

/**
 * class for working with transfer transactions
 */
public class TransactionTransfer implements Transaction {
    private long sum;
    private long commissionFrom;
    private long commissionTo;
    private Account from;
    private Account to;
    private Calendar time;
    private boolean canceled;

    public TransactionTransfer(
            Account _from,
            Account _to,
            Calendar _time,
            long _sum,
            long _commissionFrom,
            long _commissionTo,
            boolean _canceled
    ) {
        if (_sum < 0)
            throw new IllegalArgumentException("The sum can't be negative");
        if (_from.getId().equals(_to.getId()))
            throw new IllegalArgumentException("Accounts for transfer are equal");

        from = _from;
        to = _to;
        time = _time;
        sum = _sum;
        commissionFrom = _commissionFrom;
        commissionTo = _commissionTo;
        canceled = _canceled;
    }

    /**
     * returns the ID of the account from which the money was transferred
     *
     * @return id of the account (from)
     */
    public Account getFrom() {
        return from;
    }

    /**
     * returns the ID of the account to which the money was transferred
     *
     * @return id of the account (to)
     */
    public Account getTo() {
        return to;
    }

    /**
     * returns the time of the transfer
     *
     * @return time of the transfer
     */
    public Calendar getTime() {
        return time;
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
     * returns money to the account from which the money was withdrawn (with commission),
     * and withdraws money from the account to which the money was transferred (less commission)
     * makes the transaction canceled
     */
    public void cancel() {
        from.addMoney(sum + commissionFrom);
        to.reduceMoney(sum - commissionTo);
        canceled = true;
    }
}
