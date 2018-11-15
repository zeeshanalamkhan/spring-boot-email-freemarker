package com.boot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boot.dto.MailRequest;
import com.boot.dto.MailResponse;
import com.boot.service.EMailService;

@SpringBootApplication
@RestController
public class SpringBootEmailFreemarkerApplication {

	@Autowired
	private EMailService service;

	@PostMapping("/sendingEmail")
	public MailResponse sendEmail(@RequestBody MailRequest request) {
		
		Map<String,Object>model = new HashMap<>();
		model.put("Name", request.getName() );
		model.put("Location", "Hyderabad");
		
		return service.sendEmail(request, model);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmailFreemarkerApplication.class, args);
	}
}
