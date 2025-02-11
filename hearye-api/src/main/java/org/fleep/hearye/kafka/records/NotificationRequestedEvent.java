package org.fleep.hearye.kafka.records;

public record NotificationRequestedEvent(int notificationTypeid, String message) {}
