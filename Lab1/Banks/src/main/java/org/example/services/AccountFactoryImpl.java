package org.example.services;

import org.example.accountImpl.CreditAccountImpl;
import org.example.accountImpl.DebitAccountImpl;
import org.example.accountImpl.DepositAccountImpl;
import org.example.accounts.CreditAccount;
import org.example.accounts.DebitAccount;
import org.example.accounts.DepositAccount;
import org.example.banks.Bank;
import org.example.entities.Client;
import org.example.models.DepositItem;

import java.util.Calendar;

/**
 * class for working with an abstract factory (implements the abstract factory interface)
 */
public class AccountFactoryImpl implements AccountFactory {
    /**
     * creates a credit account with given parameters
     *
     * @param bank the bank where the account is registered
     * @param client client registering an account
     * @param sum initial account amount
     * @return new credit account
     */
    public CreditAccount createCredit(Bank bank, Client client, long sum) {
        boolean doubtful = client.getPassport() == null || client.getAddress() == null;
        return new CreditAccountImpl(doubtful, sum);
    }

    /**
     * creates a debit account with given parameters
     *
     * @param bank the bank where the account is registered
     * @param client client registering an account
     * @param sum initial account amount
     * @return new debit account
     */
    public DebitAccount createDebit(Bank bank, Client client, long sum)
    {
        boolean doubtful = client.getPassport() == null || client.getAddress() == null;
        return new DebitAccountImpl(
                bank.getFixPercentForDebit(),
                sum,
                doubtful);
    }

    /**
     * creates a deposit account with given parameters
     *
     * @param bank the bank where the account is registered
     * @param client client registering an account
     * @param sum initial account amount
     * @return new deposit account
     */
    public DepositAccount createDeposit(Bank bank, Client client, long sum)
    {
        boolean doubtful = client.getPassport() == null || client.getAddress() == null;
        long percent = 0;
        for (DepositItem item : bank.getDepositPercents())
        {
            if (sum >= item.getLowerLimit() && sum <= item.getUpperLimit())
                percent = item.getPercent();
        }

        if (percent == 0)
            throw new IllegalArgumentException("Can't find percent for this sum for deposit");

        int days = bank.getTermForDeposit().getDays() +
                bank.getTermForDeposit().getMonths()*30 +
                bank.getTermForDeposit().getYears()*12*30;
        Calendar cur = Calendar.getInstance();
        cur.add(Calendar.DATE, days);

        return new DepositAccountImpl(
                percent,
                sum,
                cur,
                doubtful);
    }
}
