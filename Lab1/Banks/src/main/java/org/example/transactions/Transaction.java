package org.example.transactions;

/**
 * interface defining methods for working with transactions
 */
public interface Transaction {
    /**
     * returns true if the transaction is cancelled, false if not
     *
     * @return boolean canceled
     */
    boolean getCanceled();

    /**
     * cancels the transaction
     */
    void cancel();
}
