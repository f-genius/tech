package org.example.banksService;

import org.example.services.AccountFactoryImpl;
import org.example.banks.Bank;
import org.example.banks.CentralBankImpl;
import org.example.accounts.Account;
import org.example.entities.Client;
import org.example.models.DepositItem;
import org.example.models.RussianPassport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * testing class
 */
public class BanksService {
    /**
     * creates a customer and a bank, registers the customer with the bank,
     * creates a debit account for him, checks that he is now registered with the bank
     */
    @Test
    public void createClientAccountsAndBank() {
        Bank bank = CentralBankImpl.getInstance().createBank(
                150000,
                200000,
                new ArrayList<DepositItem>(Arrays.asList(
                        new DepositItem(0, 50000, 3),
                        new DepositItem(50000, 100000, 4),
                        new DepositItem(100000, 10000000, 5)
                )),
                30000,
                10,
                20,
                Period.of(0, 0, 365)
        );
        Client client = Client.builder().
                withName("biba").
                withSurname("boba").
                withAddress("fgdfgsd").
                withPassport(new RussianPassport("1234", "123456")).
                build();
        bank.registerClient(client);
        Account debit = bank.createAccount(new AccountFactoryImpl(), client, "Debit", 1000000);
        Assertions.assertTrue(bank.getClientAccounts().contains(debit));
    }

    /**
     *  creates two banks, registers a client in them,
     *  creates an account in each bank and transfers money from one to another
     */
    @Test
    public void TransferMoneyAndDifferentBanks() {
        Bank bank1 = CentralBankImpl.getInstance().createBank(
                150000,
                200000,
                new ArrayList<DepositItem>(Arrays.asList(
                        new DepositItem(0, 50000, 3),
                        new DepositItem(50000, 100000, 4),
                        new DepositItem(100000, 10000000, 5)
                )),
                30000,
                10,
                20,
                Period.of(0, 0, 365)
        );
        Bank bank2 = CentralBankImpl.getInstance().createBank(
                200000,
                400000,
                new ArrayList<DepositItem>(Arrays.asList(
                        new DepositItem(0, 60000, 6),
                        new DepositItem(60000, 700000, 7),
                        new DepositItem(700000, 10000000, 10)
                )),
                100000,
                20,
                10,
                Period.of(0, 0, 200)
        );
        Client client = Client.builder().
                withName("biba").
                withSurname("boba").
                withAddress("street").
                withPassport(new RussianPassport("1234", "123456")).
                build();
        bank1.registerClient(client);
        bank2.registerClient(client);

        Account debit = bank1.createAccount(new AccountFactoryImpl(), client, "Debit", 100000);
        Account debit2 = bank2.createAccount(new AccountFactoryImpl(), client, "Debit", 300000);

        CentralBankImpl.getInstance().transferMoney(bank1.getId(), bank2.getId(), debit.getId(), debit2.getId(), 1000);

        Assertions.assertEquals(99000, debit.getBalance());
        Assertions.assertEquals(301000, debit2.getBalance());
    }
}
