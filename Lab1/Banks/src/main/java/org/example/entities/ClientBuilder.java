package org.example.entities;

import org.example.services.Passport;

/**
 * interface for adding an address and passport to a client and creating an object
 */
public interface ClientBuilder {

    /**
     * adds an address to the client
     *
     * @param address client address (maybe missing)
     * @return interface for further creation
     */
    ClientBuilder withAddress(String address);

    /**
     *
     *
     * @param passport client passportadds a passport to the client (maybe missing)
     * @return interface for further creation
     */
    ClientBuilder withPassport(Passport passport);

    /**
     * creates a client with the set parameters
     *
     * @return a new client
     */
    Client build();
}
