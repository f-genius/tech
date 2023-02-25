package org.example.entities;

/**
 * interface for adding a client surname
 */
public interface SurnameBuilder {
    /**
     * adds a surname to the client
     *
     * @param surname client name (can't be missing)
     * @return interface (ClientBuilder) for further creation
     */
    ClientBuilder withSurname(String surname);
}
