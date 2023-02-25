package org.example.entities;

import org.example.accounts.Account;
import org.example.services.Notification;
import org.example.services.Observer;
import org.example.services.Passport;

import java.util.ArrayList;
import java.util.UUID;

/**
 * customer service class
 */
public class Client implements Observer {
    private final ArrayList<Account> accounts;
    private ArrayList<Notification> notifications;
    private String name;
    private String surname;
    private UUID id;
    private Passport passport;
    private String address;

    private Client(String name, String surname, String address, Passport passport) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;
        id = UUID.randomUUID();
        accounts = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    /**
     * returns an inner class for creating a client with the desired parameters
     *
     * @return Builder - inner class for building
     */
    public static NameBuilder builder() {
        return new Builder();
    }

    /**
     * returns the client's name
     *
     * @return client's name
     */
    public String getName() {
        return name;
    }

    /**
     * returns the client's surname
     *
     * @return client's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * returns the client's id
     *
     * @return client's id
     */
    public UUID getId() {
        return id;
    }

    /**
     * returns client's passport (full number)
     *
     * @return client's full passport number
     */
    public Passport getPassport() {
        return passport;
    }

    /**
     * returns client's address
     *
     * @return client's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * returns a list of client's accounts
     *
     * @return list of client's accounts
     */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * adds a new account
     *
     * @param newAccount a new client's account
     * @throws IllegalStateException if account is already exists
     */
    public void addAccount(Account newAccount) {
        if (accounts.contains(newAccount))
            throw new IllegalStateException("This account is already exist");
        accounts.add(newAccount);
    }

    /**
     * adds a new notification to the list
     *
     * @param n sent notification
     */
    public void update(Notification n) {
        notifications.add(n);
    }

    /**
     * inner class for creating a client step by step (builder)
     */
    private static class Builder implements NameBuilder, SurnameBuilder, ClientBuilder {
        private String clientName;
        private String clientSurname;
        private String clientAddress;
        private Passport clientPassport;

        /**
         * creates a new client with the given parameters and returns it
         *
         * @return a new client
         */
        public Client build() {
            return new Client(clientName, clientSurname, clientAddress, clientPassport);
        }

        /**
         * adds an address to the client
         *
         * @param address client address (maybe missing)
         * @return
         */
        public ClientBuilder withAddress(String address) {
            clientAddress = address;
            return this;
        }

        /**
         * adds a passport to the client
         *
         * @param passport client passport (maybe missing)
         * @return
         */
        public ClientBuilder withPassport(Passport passport) {
            clientPassport = passport;
            return this;
        }

        /**
         * adds a name to the client
         *
         * @param name client name (can't be missing)
         * @return
         */
        public SurnameBuilder withName(String name) {
            if (name == null || name.isEmpty())
                throw new IllegalArgumentException("Name for client is empty");
            clientName = name;
            return this;
        }

        /**
         * adds a surname to the client
         *
         * @param surname client name (can't be missing)
         * @return
         */
        public ClientBuilder withSurname(String surname) {
            if (surname == null || surname.isEmpty())
                throw new IllegalArgumentException("Surname for client is empty");
            clientSurname = surname;
            return this;
        }
    }
}
