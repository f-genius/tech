package org.example.services;

/**
 * interface defining methods for working with subscribers
 */
public interface Observer {
    /**
     * method for updating subscriber status due to changes (sends a notification)
     *
     * @param n sent notification
     */
    void update(Notification n);
}
