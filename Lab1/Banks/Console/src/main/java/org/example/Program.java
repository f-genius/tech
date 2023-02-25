package org.example;

import org.example.accounts.Account;
import org.example.banks.Bank;
import org.example.banks.CentralBankImpl;
import org.example.entities.Client;
import org.example.models.Changes;
import org.example.models.DepositItem;
import org.example.models.RussianPassport;
import org.example.services.AccountFactory;
import org.example.services.AccountFactoryImpl;
import org.example.services.Passport;

import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

/**
 * class to launch console
 */
public class Program {
    /**
     * class to launch console
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Print end for exit");
        Test();
    }

    /**
     * the main function for working with the console, allows from the console:
     * 1. create a bank
     * 2. register a client
     * 3. create an account
     * 4. subscribe the client to notifications
     * 5. make transactions with accounts
     */
    public static void Test() {
        Scanner in = new Scanner(System.in);
        String end = "end";
        while (!in.nextLine().equals(end)) {
            System.out.println("What you can do (choose number):");
            System.out.println("1. Create bank");
            System.out.println("2. Register client");
            System.out.println("3. Create account");
            System.out.println("4. Subscribe to notifications");
            System.out.println("5. Make transactions with accounts");
            String com = in.nextLine();
            int command;
            try {
                command = Integer.parseInt(com);
            } catch (NumberFormatException e) {
                System.out.println("Entered not a number");
                return;
            }

            switch (command) {
                case 1:
                    System.out.println("enter the parameters for the new bank:");
                    System.out.println("Enter limit transfer for doubtful accounts: ");
                    long limitTransfer = in.nextLong();
                    System.out.println("Enter limit withdraw for doubtful accounts: ");
                    long limitWithdraw = in.nextLong();
                    System.out.println("Enter credit limit: ");
                    long creditLimit = in.nextLong();
                    System.out.println("Enter debit percent: ");
                    long debitPercent = in.nextLong();
                    System.out.println("Enter commission for credits: ");
                    long commission = in.nextLong();
                    System.out.println("Enter deposit percents and bounds for them: ");

                    String buf = in.nextLine();
                    String inputString = in.nextLine();
                    ArrayList<DepositItem> items = new ArrayList<>();
                    while (!inputString.equals(end)) {
                        String[] param = inputString.split("\\s+", 3);
                        DepositItem item;
                        try {
                            item = new DepositItem(Integer.parseInt(param[0]), Integer.parseInt(param[1]), Integer.parseInt(param[2]));
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                            return;
                        }
                        items.add(item);
                        inputString = in.nextLine();
                    }

                    Period period;
                    System.out.println("Enter term for deposit (years, months, days): ");
                    buf = in.nextLine();
                    try {
                        String[] periodItems = in.nextLine().split("\\s+", 3);
                        period = Period.of(Integer.parseInt(periodItems[0]),
                                Integer.parseInt(periodItems[1]),
                                Integer.parseInt(periodItems[2]));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        period = Period.of(0, 0, 365);
                    }

                    Bank bank = CentralBankImpl.getInstance().createBank(
                            limitTransfer,
                            limitWithdraw,
                            items,
                            creditLimit,
                            debitPercent,
                            commission,
                            period
                    );
                    System.out.println("The bank has been successfully created, its id: " + bank.getId().toString());
                    break;
                case 2:
                    System.out.println("enter the parameters for the client:");
                    System.out.println("Enter name: ");
                    String name = in.nextLine();
                    System.out.println("Enter surname: ");
                    String surname = in.nextLine();
                    System.out.println("Enter address: ");
                    String address = in.nextLine();
                    System.out.println("Enter series and number for passport: ");
                    Passport passport;
                    try {
                        String[] data = in.nextLine().split(" ", 2);
                        passport = new RussianPassport(data[0], data[1]);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        passport = null;
                    }

                    Client client = Client.builder()
                            .withName(name)
                            .withSurname(surname)
                            .withAddress(address)
                            .withPassport(passport)
                            .build();
                    System.out.println("Enter bank id for registration");
                    UUID id = UUID.fromString(in.nextLine());
                    Optional<Bank> optionalBank = CentralBankImpl.getInstance().getBanks().stream()
                            .filter(i -> i.getId().equals(id))
                            .findFirst();
                    if (optionalBank.isEmpty())
                        throw new NullPointerException("There is no such bank");

                    Bank clientBank = optionalBank.get();
                    clientBank.registerClient(client);
                    System.out.println("The client has been successfully registered, its id: " + client.getId());
                    break;
                case 3:
                    AccountFactory factory = new AccountFactoryImpl();
                    System.out.println("Enter bank id for account");
                    id = UUID.fromString(in.nextLine());
                    Optional<Bank> optionalAccountBank = CentralBankImpl.getInstance().getBanks().stream()
                            .filter(i -> i.getId().equals(id))
                            .findFirst();
                    if (optionalAccountBank.isEmpty())
                        throw new NullPointerException("There is no such bank");

                    Bank accountBank = optionalAccountBank.get();

                    System.out.println("Enter client id for account");
                    UUID clientId = UUID.fromString(in.nextLine());
                    Optional<Client> optionalClient = accountBank.getClients().stream()
                            .filter(i -> i.getId().equals(clientId))
                            .findFirst();

                    if (optionalClient.isEmpty())
                        throw new NullPointerException("There is no such client");

                    Client accountClient = optionalClient.get();
                    System.out.println("Enter account type (Debit, Deposit, Credit)");
                    String accountType = in.nextLine();
                    System.out.println("Enter sum for account");
                    long sum = in.nextLong();
                    Account account = accountBank.createAccount(factory, accountClient, accountType, sum);
                    break;
                case 4:
                    System.out.println("Enter bank id for subscribing");
                    UUID bankSubscribeId = UUID.fromString(in.nextLine());
                    Optional<Bank> bankOptionalSubscribe = CentralBankImpl.getInstance().getBanks().stream()
                            .filter(i -> i.getId().equals(bankSubscribeId))
                            .findFirst();
                    if (bankOptionalSubscribe.isEmpty())
                        throw new NullPointerException("There is no such bank");

                    Bank bankSubscribe = bankOptionalSubscribe.get();

                    System.out.println("Enter client id for account");
                    UUID clientSubscribeId = UUID.fromString(in.nextLine());
                    Optional<Client> optionalSubscribeClient = bankSubscribe.getClients().stream()
                            .filter(i -> i.getId().equals(clientSubscribeId))
                            .findFirst();

                    if (optionalSubscribeClient.isEmpty())
                        throw new NullPointerException("There is no such client");

                    Client subscribeClient = optionalSubscribeClient.get();
                    System.out.println("Enter notification type (Commission, CreditLimit, DebitPercent, DepositPercent)");
                    String notificationType = in.nextLine();
                    bankSubscribe.addObserver(subscribeClient, Changes.valueOf(notificationType));
                    break;
                case 5:
                    System.out.println("What do you want to do? (choose number):");
                    System.out.println("1. Replenishment");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Transfer (one bank)");
                    System.out.println("4. Transfer (different banks)");
                    int accountCommand = in.nextInt();

                    System.out.println("Enter bank id for operation");
                    UUID bankOperationId = UUID.fromString(in.nextLine());
                    Optional<Bank> bankOptionalOperation = CentralBankImpl.getInstance().getBanks().stream()
                            .filter(i -> i.getId().equals(bankOperationId))
                            .findFirst();
                    if (bankOptionalOperation.isEmpty())
                        throw new NullPointerException("There is no such bank");

                    Bank operationBank = bankOptionalOperation.get();

                    System.out.println("Enter account id for operation");
                    UUID accountOperationId = UUID.fromString(in.nextLine());
                    System.out.println("Enter sum for operation");
                    long sumOperation = in.nextLong();

                    switch (accountCommand) {
                        case 1:
                            operationBank.replenishment(sumOperation, accountOperationId);
                            break;
                        case 2:
                            operationBank.withdrawMoney(sumOperation, accountOperationId);
                            break;
                        case 3:
                            System.out.println("Enter another account id for operation");
                            UUID secondId = UUID.fromString(in.nextLine());
                            operationBank.transferMoney(sumOperation, accountOperationId, secondId);
                            break;
                        case 4:
                            System.out.println("Enter another account id for operation");
                            UUID secondAccountId = UUID.fromString(in.nextLine());
                            System.out.println("Enter another bank id for operation");
                            UUID secondBankId = UUID.fromString(in.nextLine());

                            CentralBankImpl.getInstance().transferMoney(bankOperationId, secondBankId, accountOperationId, secondAccountId, sumOperation);
                            break;
                        default:
                            System.out.println("There is no such command");
                            break;
                    }
                    break;
            }
        }
    }
}