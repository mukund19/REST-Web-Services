package org.mukund.messenger.resources;

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
import javax.ws.rs.core.MediaType;

import org.eclipse.persistence.annotations.DeleteAll;
import org.mukund.messenger.model.Message;
import org.mukund.messenger.resources.beans.MessageFilterBean;
import org.mukund.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService =new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@BeanParam MessageFilterBean filterbean) {
		if(filterbean.getYear() > 0)
			return messageService.getAllMessagesForYear(filterbean.getYear());
		
		if(filterbean.getStart()>=0 && filterbean.getSize() >=0)
			return messageService.getAllMessagesPaginated(filterbean.getStart(), filterbean.getSize());
		
		return  messageService.getAllMessages();
	}
	
	
	@POST
	public Message addMessage(Message message) {
		
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId) {
		
		return messageService.getMessage(messageId);
		
	}
	
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
