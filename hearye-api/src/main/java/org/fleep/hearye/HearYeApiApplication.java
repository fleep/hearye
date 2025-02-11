package org.fleep.hearye;

import org.fleep.hearye.kafka.Topics;
import org.fleep.hearye.kafka.records.NotificationRequestedEvent;
import org.fleep.hearye.model.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class HearYeApiApplication {

	@Autowired
	private KafkaTemplate<String, NotificationRequestedEvent> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(HearYeApiApplication.class, args);
	}

	@RequestMapping("/")
	public String home() {
		return "Hear Ye, Hear Yo!";
	}

	@PostMapping("/email")
	public String email(@RequestParam String emailAddress, @RequestParam String emailBody) {
		String msg = "Email sent to " + emailAddress + " on " + LocalDateTime.now() + "\n" + emailBody;
		NotificationRequestedEvent event = new NotificationRequestedEvent(NotificationType.TYPE_EMAIL, msg);
		kafkaTemplate.send(Topics.NOTIFICATION_REQUESTED, event);
		return msg;
	}

	@PostMapping("/crm")
	public String crm(@RequestParam String emailAddress, @RequestParam String message) {
		String msg = "Notification to " + emailAddress + " posted to CRM on " + LocalDateTime.now() + "\n" + message;
		NotificationRequestedEvent event = new NotificationRequestedEvent(NotificationType.TYPE_CRM, msg);
		kafkaTemplate.send(Topics.NOTIFICATION_REQUESTED, event);
		return msg;
	}

	@PostMapping("/chat")
	public String chat(@RequestParam String emailAddress, @RequestParam String message) {
		String msg = "Notification to " + emailAddress + " posted to chat on " + LocalDateTime.now() + "\n" + message;
		NotificationRequestedEvent event = new NotificationRequestedEvent(NotificationType.TYPE_CHAT, msg);
		kafkaTemplate.send(Topics.NOTIFICATION_REQUESTED, event);
		return msg;
	}
}
