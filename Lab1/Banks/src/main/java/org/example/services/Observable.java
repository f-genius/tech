package org.example.services;

import org.example.models.Changes;

/**
 * Interface providing methods for working with the observable object
 */
public interface Observable {
    /**
     * method for adding a new subscriber
     * @param o a new subscriber (observer)
     * @param changes changes that the new subscriber will observe
     */
    void addObserver(Observer o, Changes changes);

    /**
     * method for notifying subscribers about changes that have occurred
     * @param changes the changes that have taken place
     * @param value the new value of the observed parameter
     */
    void notify(Changes changes, long value);
}
