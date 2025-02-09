package org.fleep.hearye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class HearYeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HearYeApiApplication.class, args);
	}

	@RequestMapping("/")
	public String home() {
		return "Hear Ye, Hear Yo!";
	}

	@PostMapping("/email")
	public String email(@RequestParam String emailAddress, @RequestParam String emailBody) {
		return "Email sent to " + emailAddress + " on " + LocalDateTime.now() + "\n" + emailBody;
	}

	@PostMapping("/crm")
	public String crm(@RequestParam String emailAddress, @RequestParam String message) {
		return "Notification to " + emailAddress + " posted to CRM on " + LocalDateTime.now() + "\n" + message;
	}

	@PostMapping("/chat")
	public String chat(@RequestParam String emailAddress, @RequestParam String message) {
		return "Notification to " + emailAddress + " posted to chat on " + LocalDateTime.now() + "\n" + message;
	}
}
