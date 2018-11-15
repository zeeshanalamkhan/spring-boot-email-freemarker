package com.boot.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.boot.dto.MailRequest;
import com.boot.dto.MailResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EMailService {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration config;

	public MailResponse sendEmail(MailRequest req, Map<String, Object> model) {

		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
			Template t = config.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			helper.setTo(req.getTo());
			helper.setText(html, true);
			helper.setSubject(req.getSubject());
			helper.setFrom(req.getFrom());
			sender.send(message);

			response.setMessage("mail send to " + req.getTo());
			response.setStatus(Boolean.TRUE);
		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("mail sending failure: " + e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response; 
	}

}
