package org.example.entities;

/**
 * interface for adding a client name
 */
public interface NameBuilder {
    /**
     * adds a name to the client
     *
     * @param name client name (can't be missing)
     * @return interface (SurnameBuilder) for further creation
     */
    SurnameBuilder withName(String name);
}
