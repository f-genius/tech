package org.example.models;

/**
 * an enum for defining the type of service that subscribers subscribe to
 */
public enum Changes {
    /**
     * change commission for credit accounts
     */
    Commission,

    /**
     * change credit limit for credit accounts
     */
    CreditLimit,

    /**
     * change debit percent for debit accounts
     */
    DebitPercent,

    /**
     * change deposit percent for deposit accounts
     */
    DepositPercent,
}
