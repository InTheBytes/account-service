package com.inthebytes.accountservice.service;

import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

@Service
public class EmailSendService {

	private SesClient client;

	public void send(String sender,
	                 String recipient,
	                 String subject,
	                 String bodyText,
	                 String bodyHTML,
	                 String link,
	                 String button
	) {
		client = SesClient.builder().build();
		JSONObject templateData = new JSONObject();
		templateData.put("title", subject);
		templateData.put("HTMLmessage", bodyHTML);
		templateData.put("TEXTmessage", bodyText);

		if (link != null && !link.isEmpty() && button != null && !button.isEmpty()) {
			JSONObject linkJson = new JSONObject();
			linkJson.put("href", link);
			linkJson.put("button", button);
			templateData.put("link", linkJson);
		}

		SendTemplatedEmailRequest templatedEmailRequest = SendTemplatedEmailRequest.builder()
				.source(sender)
				.template("SLEmailTemplate")
				.configurationSetName("Failure")
				.destination(Destination.builder().toAddresses(recipient).build())
				.templateData(templateData.toString())
				.build();

		this.client.sendTemplatedEmail(templatedEmailRequest);
		client.close();
	}
}