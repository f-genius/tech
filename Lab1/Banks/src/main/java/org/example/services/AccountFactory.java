package org.example.services;

import org.example.accounts.CreditAccount;
import org.example.accounts.DebitAccount;
import org.example.accounts.DepositAccount;
import org.example.banks.Bank;
import org.example.entities.Client;


/**
 * interface defining methods for working with an abstract factory (for creating accounts)
 */
public interface AccountFactory {
    /**
     * creates a credit account with given parameters
     *
     * @param bank the bank where the account is registered
     * @param client client registering an account
     * @param sum initial account amount
     * @return a new credit account
     */
    CreditAccount createCredit(Bank bank, Client client, long sum);

    /**
     * creates a debit account with given parameters
     *
     * @param bank the bank where the account is registered
     * @param client client registering an account
     * @param sum initial account amount
     * @return a new debit account
     */
    DebitAccount createDebit(Bank bank, Client client, long sum);

    /**
     * creates a deposit account with given parameters
     *
     * @param bank the bank where the account is registered
     * @param client client registering an account
     * @param sum initial account amount
     * @return a new deposit account
     */
    DepositAccount createDeposit(Bank bank, Client client, long sum);
}
