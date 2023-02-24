package org.example.transactions;

/**
 * a class that describes the operation of a transaction cancellation transaction
 */
public class TransactionCancel implements Transaction {
    private Transaction transaction;
    private boolean canceled;

    public TransactionCancel(Transaction _transaction) {
        transaction = _transaction;
        canceled = false;
    }

    /**
     * returns the transaction that was canceled
     *
     * @return canceled transaction
     */
    public Transaction getTransaction() {
        return transaction;
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
     * cancels the transaction
     */
    public void cancel() {
        throw new IllegalStateException("it is not possible to cancel this transaction");
    }
}
