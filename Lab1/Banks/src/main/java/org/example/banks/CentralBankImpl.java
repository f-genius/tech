package org.example.banks;

import org.example.accounts.Account;
import org.example.services.CentralBank;
import org.example.transactions.Transaction;
import org.example.transactions.TransactionTransfer;
import org.example.models.DepositItem;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

/**
 * the class describing the work with the central bank is a singleton
 */
public class CentralBankImpl implements CentralBank {
    private static CentralBankImpl instance;
    private final ArrayList<Bank> banks;

    private CentralBankImpl() {
        banks = new ArrayList<>();
    }

    /**
     * returns the state of the object. if not already initialized, initializes it
     *
     * @return instance
     */
    public static CentralBankImpl getInstance() {
        CentralBankImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (CentralBankImpl.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new CentralBankImpl();
            }
        }
        return localInstance;
    }

    /**
     * returns a list of banks managed by the central bank
     * @return list of registered banks
     */
    public ArrayList<Bank> getBanks() {
        return banks;
    }

    /**
     * creates a new bank and returns it
     *
     * @param limitTransferForDoubtful   transfer limit for doubtful accounts
     * @param limitWithdrawalForDoubtful withdrawal limit for doubtful accounts
     * @param depositPercents            percents for deposit accounts
     * @param creditLimit                limit for credit accounts
     * @param fixPercentForDebit         fix percent for debit accounts
     * @param commission                 commission for credit accounts
     * @param term                       period during which deposit accounts are valid
     * @return a new bank
     */
    public Bank createBank(
            long limitTransferForDoubtful,
            long limitWithdrawalForDoubtful,
            ArrayList<DepositItem> depositPercents,
            long creditLimit,
            long fixPercentForDebit,
            long commission,
            Period term
    ) {
        Bank bank = new Bank(
                limitTransferForDoubtful,
                limitWithdrawalForDoubtful,
                depositPercents,
                creditLimit,
                fixPercentForDebit,
                commission,
                term
        );
        banks.add(bank);
        return bank;
    }

    /**
     * transfers money from one bank account to another bank account, creates a transfer transaction
     *
     * @param bankIdFrom ID of the bank from which the transfer is made
     * @param bankIdTo   ID of the bank to which you are transferring
     * @param accFrom    id of the account from which the transfer is made
     * @param accTo      ID of the account to which you are transferring
     * @param sum        transfer amount
     */
    public void transferMoney(UUID bankIdFrom, UUID bankIdTo, UUID accFrom, UUID accTo, long sum) {
        if (accFrom.equals(accTo))
            throw new IllegalArgumentException("Accounts for transfer are equal");
        if (sum < 0)
            throw new IllegalArgumentException("The sum can't be negative");

        Optional<Bank> bankOptionalFrom = getBanks().stream()
                .filter(i -> i.getId() == bankIdFrom)
                .findFirst();
        Optional<Bank> bankOptionalTo = getBanks().stream()
                .filter(i -> i.getId() == bankIdTo)
                .findFirst();

        if (bankOptionalFrom.isEmpty() || bankOptionalTo.isEmpty())
            throw new NullPointerException();

        Bank bankFrom = bankOptionalFrom.get();
        Bank bankTo = bankOptionalTo.get();

        Optional<Account> accountOptionalFrom = bankFrom.getClientAccounts().stream()
                .filter(i -> i.getId() == accFrom)
                .findFirst();
        Optional<Account> accountOptionalTo = bankTo.getClientAccounts().stream()
                .filter(i -> i.getId() == accTo)
                .findFirst();

        if (accountOptionalFrom.isEmpty() || accountOptionalTo.isEmpty())
            throw new NullPointerException();
        if (accountOptionalFrom.get().getIsDoubtful() && sum > bankFrom.getLimitTransferForDoubtful())
            throw new IllegalStateException();

        Account accountFrom = accountOptionalFrom.get();
        Account accountTo = accountOptionalTo.get();

        long newSumFrom = sum, newSumTo = sum;
        if (accountFrom.getBalance() < 0)
            newSumFrom -= bankFrom.getCommission();
        if (accountTo.getBalance() < 0)
            newSumTo = newSumFrom - bankTo.getCommission();

        accountFrom.reduceMoney(newSumFrom);
        accountTo.addMoney(newSumTo);
        Transaction transferTransaction = new TransactionTransfer(
                accountFrom,
                accountTo,
                Calendar.getInstance(),
                newSumFrom,
                sum - newSumFrom,
                newSumFrom - newSumTo,
                false);
        accountFrom.addTransaction(transferTransaction);
        accountTo.addTransaction(transferTransaction);
    }
}
