package com.intellimed.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.intellimed.messenger.model.ErrorMessage;

//@Provider// disable in order to try the WebApplicationException
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
	@Override
	public Response toResponse(Throwable exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), "500", "Documentation related to this error goes here..");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}

}
