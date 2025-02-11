package org.fleep.hearye.kafka.listeners;

import jakarta.transaction.Transactional;
import org.fleep.hearye.kafka.records.NotificationRequestedEvent;
import org.fleep.hearye.repository.NotificationTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class NotificationRequestEventHandler {
    private static final Logger log = LoggerFactory.getLogger(NotificationRequestEventHandler.class);

    private final NotificationTypeRepository notificationTypeRepository;

    public NotificationRequestEventHandler(NotificationTypeRepository notificationTypeRepository) {
        this.notificationTypeRepository = notificationTypeRepository;
    }

    @KafkaListener(topics = "notification-requested", groupId = "hearye")
    public void handle(NotificationRequestedEvent event) {
        log.info("Received notification request: {} : {}", event.notificationTypeid(), event.message());
    }
}
