package org.example.models;

import org.example.services.Notification;

/**
 * class for working with a simple notification (contains a message)
 */
public class SimpleNotification implements Notification {
    private Changes changes;
    private long sum;

    public SimpleNotification(Changes changes, long sum) {
        this.changes = changes;
        this.sum = sum;
    }

    /**
     * returns a message notifying that a parameter has been changed
     *
     * @return message
     */
    public String getMessage() {
        return changes + "was changed to " + sum;
    }
}
