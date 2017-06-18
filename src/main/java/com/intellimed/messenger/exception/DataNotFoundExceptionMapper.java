package com.intellimed.messenger.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.intellimed.messenger.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), "404", "Documentation related to this error goes here..");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		//return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
	}

}
