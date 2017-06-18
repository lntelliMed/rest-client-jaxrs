package com.intellimed.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.intellimed.messenger.exception.DataNotFoundException;
import com.intellimed.messenger.model.Message;
import com.intellimed.messenger.resources.beans.MessageFilterBean;
import com.intellimed.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class MessageResource {
	private MessageService messageService = new MessageService();

/*    @GET
    @Produces(MediaType.TEXT_PLAIN)
	public String getMessages(){
		return "Hello World!";
	}
*/
	
	
/*	 
 	@GET
	 //@Produces(MediaType.APPLICATION_XML)
	 //@Produces(MediaType.APPLICATION_JSON)
	 public List<Message> getMessages(@QueryParam("year") int year,
			 						@QueryParam("start") int start,
			 						@QueryParam("size") int size){
		 
		 if (year > 0){
			 return messageService.getAllMessagesForYear(year);
		 }
		 if (start > 0 && size > 0){
			 return messageService.getAllMessagesPaginated(start, size);
		 }
		 return messageService.getAllMessages();
	 }
*/
	 
	
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 public List<Message> getJsonMessages(@BeanParam MessageFilterBean filterBean){
		 System.out.println("Client  Accepts application/json; hence getJsonMessages method has been called!");
		 if (filterBean.getYear() > 0){
			 return messageService.getAllMessagesForYear(filterBean.getYear());
		 }
		 if (filterBean.getStart() > 0 && filterBean.getSize() > 0){
			 return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		 }
		 return messageService.getAllMessages();
	 }
	 
	 @GET
	 @Produces(MediaType.TEXT_XML)
	 public List<Message> getXmlMessages(@BeanParam MessageFilterBean filterBean){
		 System.out.println("Client  Accepts text/xml; hence getXmlMessages method has been called!");
		 if (filterBean.getYear() > 0){
			 return messageService.getAllMessagesForYear(filterBean.getYear());
		 }
		 if (filterBean.getStart() > 0 && filterBean.getSize() > 0){
			 return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		 }
		 return messageService.getAllMessages();
	 }
	 
	 @GET
	 @Path("/{messageId}")
	 //@Produces(MediaType.APPLICATION_XML)
	 //@Produces(MediaType.APPLICATION_JSON)
	 public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo){
		 Message message = messageService.getMessage(messageId);
		 if (message == null){
			 throw new DataNotFoundException("Message with id " + messageId + " not found!");
		 }
		 
		 message.addLink(getUriForSelf(uriInfo, message), "self");
		 message.addLink(getUriForProfile(uriInfo, message), "profile");
		 message.addLink(getUriForComments(uriInfo, message), "comments");
		 return message;
	 }

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(MessageResource.class, "forwardToCommentResource").
				path(CommentResource.class).resolveTemplate("messageId", message.getId()).build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(Long.toString(message.getId())).build().toString();
		return uri;
	}
	 
/*	 @POST
	 //@Consumes(MediaType.APPLICATION_JSON)
	 //@Produces(MediaType.APPLICATION_JSON)
	 public Message addMessage(Message message){
		 return messageService.addMessage(message);
	 }
*/
	
	 @POST
	 public Response addMessage(Message message, @Context UriInfo uriInfo){
		 Message newMessage = messageService.addMessage(message);
		 String newId = String.valueOf(newMessage.getId());
		 URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		 
		 //return messageService.addMessage(message);
		 //return  Response.status(Status.CREATED).entity(newMessage).build();
		 return  Response.created(uri).entity(newMessage).build();
		 
	 }
	 
	 @PUT
	 @Path("/{messageId}")
	 //@Consumes(MediaType.APPLICATION_JSON)
	 //@Produces(MediaType.APPLICATION_JSON)
	 public Message updateMessage(@PathParam("messageId") long messageId, Message message){
		 message.setId(messageId);
		 return messageService.updateMessage(message);
	 }
	 
	 @DELETE
	 @Path("/{messageId}")
	 //@Produces(MediaType.APPLICATION_JSON)
	 public void deleteMessage(@PathParam("messageId") long messageId){
		 	messageService.deleteMessage(messageId);
	 }
	 
	 @Path("/{messageId}/comments")
	 public CommentResource forwardToCommentResource(){
		 return new CommentResource();
	 }
}
