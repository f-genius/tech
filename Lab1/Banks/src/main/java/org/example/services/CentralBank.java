package org.example.services;

import org.example.banks.Bank;
import org.example.models.DepositItem;

import java.time.Period;
import java.util.ArrayList;
import java.util.UUID;

/**
 * interface defining methods for working with the central bank (singleton)
 */
public interface CentralBank {
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
    Bank createBank(
            long limitTransferForDoubtful,
            long limitWithdrawalForDoubtful,
            ArrayList<DepositItem> depositPercents,
            long creditLimit,
            long fixPercentForDebit,
            long commission,
            Period term);

    /**
     * transfers money from one bank account to another bank account, creates a transfer transaction
     *
     * @param bankIdFrom ID of the bank from which the transfer is made
     * @param bankIdTo   ID of the bank to which you are transferring
     * @param accFrom    id of the account from which the transfer is made
     * @param accTo      ID of the account to which you are transferring
     * @param sum        transfer amount
     */
    void transferMoney(UUID bankIdFrom, UUID bankIdTo, UUID accFrom, UUID accTo, long sum);
}
