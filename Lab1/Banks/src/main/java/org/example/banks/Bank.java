package org.example.banks;

import org.example.accounts.Account;
import org.example.models.Changes;
import org.example.models.SimpleNotification;
import org.example.services.AccountFactory;
import org.example.services.Notification;
import org.example.services.Observer;
import org.example.transactions.*;
import org.example.entities.Client;
import org.example.models.DepositItem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Period;
import java.util.*;

/**
 * class for working with banks, defines the main methods
 */
public class Bank implements org.example.services.Observable {
    private final ArrayList<Account> clientAccounts;
    private final ArrayList<Client> clients;
    private final ArrayList<DepositItem> depositPercents;
    private final Map<Changes, org.example.services.Observer> observers;
    private long limitTransferForDoubtful;
    private long limitWithdrawalForDoubtful;
    private long creditLimit;
    private long fixPercentForDebit;
    private long commission;
    private Period termForDeposit;
    private UUID id;

    public Bank (
            long limitTransferForDoubtful,
            long limitWithdrawalForDoubtful,
            ArrayList<DepositItem> depositPercents,
            long creditLimit,
            long fixPercentForDebit,
            long commission,
            Period term
    ) {

        this.limitTransferForDoubtful = limitTransferForDoubtful;
        this.limitWithdrawalForDoubtful = limitWithdrawalForDoubtful;
        this.depositPercents = depositPercents;
        this.creditLimit = creditLimit;
        this.fixPercentForDebit = fixPercentForDebit;
        this.commission = commission;
        id = UUID.randomUUID();
        termForDeposit = term;
        clients = new ArrayList<>();
        clientAccounts = new ArrayList<>();
        observers = new HashMap<>();
    }

    /**
     * returns the amount, more than which cannot be transferred from a doubtful account
     * @return limit for transfer
     */
    public long getLimitTransferForDoubtful() {
        return limitTransferForDoubtful;
    }

    /**
     * returns the amount that cannot be withdrawn from a doubtful account
     * @return limit for withdrawal
     */
    public long getLimitWithdrawalForDoubtful() {
        return limitWithdrawalForDoubtful;
    }

    /**
     * returns limit for credits, more than which you can not go into the red
     * @return credit limit
     */
    public long getCreditLimit() {
        return creditLimit;
    }

    /**
     * returns percentage of a debit account that is charged daily
     * @return percent for debit accounts
     */
    public long getFixPercentForDebit() {
        return fixPercentForDebit;
    }

    /**
     * returns commission for credit accounts charged for a negative amount on the account
     * @return commission
     */
    public long getCommission() {
        return commission;
    }

    /**
     * returns the period during which the deposit account is valid
     * @return period
     */
    public Period getTermForDeposit() {
        return termForDeposit;
    }

    /**
     * returns bank ID
     * @return bank id
     */
    public UUID getId() {
        return id;
    }

    /**
     * returns a list of percents on the deposit (lower limit, upper limit, percentage)
     * @return a list of percents
     */
    public ArrayList<DepositItem> getDepositPercents() {
        return depositPercents;
    }

    /**
     * returns a list of client accounts in this bank
     * @return list of client accounts (read-only)
     */
    public ArrayList<Account> getClientAccounts() {
        return clientAccounts;
    }

    /**
     * returns a list of clients in this bank
     * @return list of clients (read-only)
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * creates a new bank account
     * @param factory account creation factory
     * @param client client registering this account
     * @param acc account type (Debit, Deposit, Credit)
     * @param sum starting amount for the account
     * @return new account
     */
    public Account createAccount(AccountFactory factory, Client client, String acc, long sum) {
        try {
            var types = new Class[3];
            types[0] = Bank.class;
            types[1] = Client.class;
            types[2] = long.class;
            Method method = factory.getClass().getMethod("create" + acc, types);
            Account account = (Account) method.invoke(factory,this, client, sum);
            client.addAccount(account);
            clientAccounts.add(account);
            return account;
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * registers a new client in this bank
     * @param newClient a new client
     */
    public void registerClient(Client newClient)
    {
        if (clients.contains(newClient))
            throw new IllegalStateException();
        clients.add(newClient);
    }

    /**
     * transfers a specified amount from one account to another, creates a transfer transaction
     * @param sum amount to transfer
     * @param from id of the account from which the money is transferred
     * @param to id of the account to which the money is being transferred
     */
    public void transferMoney(long sum, UUID from, UUID to) {
        if (sum < 0)
            throw new IllegalArgumentException("The sum can't be negative");
        if (from == to)
            throw new IllegalArgumentException("Accounts for transfer are equal");

        Optional<Account> accountOptionalTo = clientAccounts.stream()
                .filter(i -> i.getId()==to)
                .findFirst();
        Optional<Account> accountOptionalFrom = clientAccounts.stream()
                .filter(i -> i.getId()==from)
                .findFirst();

        if (accountOptionalTo.isEmpty() || accountOptionalFrom.isEmpty())
            throw new NullPointerException();
        if (accountOptionalFrom.get().getIsDoubtful() && sum > limitTransferForDoubtful)
            throw new IllegalArgumentException();

        Account accountTo = accountOptionalTo.get();
        Account accountFrom = accountOptionalFrom.get();

        long newSumFrom = sum;
        long newSumTo = sum;
        if (accountFrom.getBalance() < 0)
            newSumFrom -= commission;
        if (accountTo.getBalance() < 0)
            newSumTo = newSumFrom - commission;
        if (accountFrom.getBalance() < sum && Math.abs(accountFrom.getBalance() - sum) > creditLimit)
            throw new IllegalStateException("Exceeding the credit limit");

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

    /**
     * replenishes the account with the specified amount, creates a replenishment transaction
     * @param sum accrued amount
     * @param to id of the account to which the amount is charged
     */
    public void replenishment(long sum, UUID to) {
        if (sum < 0)
            throw new IllegalArgumentException("The sum can't be negative");
        Optional<Account> accountOptionalTo = clientAccounts.stream()
                .filter(i -> i.getId()==to)
                .findFirst();
        if (accountOptionalTo.isEmpty())
            throw new NullPointerException();

        Account accountTo = accountOptionalTo.get();

        long newSum = sum;
        if (accountTo.getBalance() < 0)
            newSum -= commission;

        accountTo.addMoney(newSum);
        Transaction transaction = new TransactionReplenishment(accountTo, newSum, Calendar.getInstance(), sum - newSum, false);
        accountTo.addTransaction(transaction);
    }

    /**
     * withdraws the specified amount from the account, creates a withdrawal transaction
     * @param sum amount to be debited
     * @param from id of the account from which the amount is debited
     * @return the amount debited
     */
    public long withdrawMoney(long sum, UUID from) {
        if (sum < 0)
            throw new IllegalArgumentException("The sum can't be negative");
        Optional<Account> accountOptionalFrom = clientAccounts.stream()
                .filter(i -> i.getId() == from)
                .findFirst();

        if (accountOptionalFrom.isEmpty())
            throw new NullPointerException();
        if (accountOptionalFrom.get().getIsDoubtful() && sum > limitWithdrawalForDoubtful)
            throw new IllegalStateException();

        Account accountFrom = accountOptionalFrom.get();
        long newSum = sum;
        if (accountFrom.getBalance() < 0)
            newSum -= commission;

        accountFrom.reduceMoney(sum);
        Transaction transaction = new TransactionWithdraw(accountFrom, Calendar.getInstance(), sum, sum - newSum, false);
        accountFrom.addTransaction(transaction);
        return newSum;
    }

    /**
     * cancels a completed transaction, creates a cancel transaction
     * @param transaction transaction to cancel
     */
    public void cancelTransaction(Transaction transaction) {
        if (transaction.getCanceled())
            throw new IllegalArgumentException();
        var transactionCancel = new TransactionCancel(transaction);
        transaction.cancel();
    }

    /**
     * sets a new credit limit and notifies all subscribers about it
     * @param newCreditLimit a new credit limit
     * @throws IllegalStateException if the new and old limits are equal
     * @throws IllegalArgumentException if the new limit is less than 0
     */
    public void changeCreditLimit(long newCreditLimit) {
        if (creditLimit == newCreditLimit)
            throw new IllegalStateException("There is nothing to change: values are equal");
        if (newCreditLimit < 0)
            throw new IllegalArgumentException("Credit limit can't be negative");

        creditLimit = newCreditLimit;
        notify(Changes.CreditLimit, newCreditLimit);
    }

    /**
     * sets a new debit percent and notifies all subscribers about it
     * @param newDebitPercent new debit percent
     * @throws IllegalStateException if the new and old percents are equal
     * @throws IllegalArgumentException if the new percent is less than 0
     */
    public void changeDebitPercent(long newDebitPercent) {
        if (fixPercentForDebit == newDebitPercent)
            throw new IllegalStateException("There is nothing to change: values are equal");
        if (newDebitPercent < 0)
            throw new IllegalArgumentException("Commission can't be negative");

        fixPercentForDebit = newDebitPercent;
        notify(Changes.DebitPercent, newDebitPercent);
    }

    /**
     * sets a new commission for credit accounts and notifies all subscribers about it
     * @param newCommission a new commission
     * @throws IllegalStateException if the new and old commissions are equal
     * @throws IllegalArgumentException if the new commission is less than 0
     */
    public void changeCommission(long newCommission) {
        if (commission == newCommission)
            throw new IllegalStateException("There is nothing to change: values are equal");
        if (newCommission < 0)
            throw new IllegalArgumentException("Commission can't be negative");

        commission = newCommission;
        notify(Changes.Commission, newCommission);
    }

    /**
     * adds a new subscriber
     * @param o a new subscriber (observer)
     * @param changes changes that the new subscriber will observe
     */
    public void addObserver(org.example.services.Observer o, Changes changes) {
        observers.put(changes, o);
    }

    /**
     * notifies subscribers about changes that have occurred
     * @param changes the changes that have taken place
     * @param value the new value of the observed parameter
     */
    public void notify(Changes changes, long value) {
        for (Changes key : observers.keySet()) {
            if (key == changes) {
                Notification notification = new SimpleNotification(changes, value);
                Observer o = observers.get(changes);
                o.update(notification);
            }
        }
    }

    /**
     * speeds up time to see what will happen to invoices after a given period of time
     * @param period period to view changes
     */
    public void speedUpTime(Period period) {
        int months = period.getYears()*12 + period.getMonths();
        int days = period.getDays() + months * 30;
        for (int day = 0; day < days; day++) {
            for (Account acc : clientAccounts) {
                acc.update();
                if (day % 30 == 0)
                    acc.handler();
            }
        }
    }
}
