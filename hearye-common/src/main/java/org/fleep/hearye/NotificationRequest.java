package org.fleep.hearye;

import java.util.List;
import java.util.TreeMap;

public class NotificationRequest {
    private NotificationType notificationType;
    private String messageBody;
    private TreeMap<String, Byte[]> attachments;    // Use a treemap to maintain attachment order. See `Attachments`

    private NotificationRequest() {

    }


}
