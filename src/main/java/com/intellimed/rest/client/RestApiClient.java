package com.intellimed.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.intellimed.messenger.model.Message;

public class RestApiClient {

	public static void main(String[] args) {

		Client client = ClientBuilder.newClient();
		
/*		WebTarget target = client.target("http://localhost:8080/advanced-jaxrs-01/webapi/messages/1");
		Builder builder = target.request();
		Response response = builder.get();
		Message message = response.readEntity(Message.class);
		System.out.println(message.getMessage());
*/
		
		
/*
		Message message1 = client
			.target("http://localhost:8080/advanced-jaxrs-01/webapi/messages/1")
			.request(MediaType.APPLICATION_JSON)
			.get(Message.class);		
*/
		
		WebTarget baseTarget = client.target("http://localhost:8080/advanced-jaxrs-01/webapi/");
		WebTarget messagesTarget = baseTarget.path("messages");
		//WebTarget singleMessageTarget = messagesTarget.path("1");
		WebTarget singleMessageTarget = messagesTarget.path("{messageId}");
		
		Message message1 = singleMessageTarget
				.resolveTemplate("messageId", "1")
				.request(MediaType.APPLICATION_JSON)
				.get(Message.class);
		
		Message message2 = singleMessageTarget
				.resolveTemplate("messageId", "2")
				.request(MediaType.APPLICATION_JSON)
				.get(Message.class);
		
		System.out.println(message1.getMessage());
		System.out.println(message2.getMessage());

		
	}

}
