package com.intellimed.rest;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("{pathParam}/test")
//@Singleton
public class MyResource {

	@PathParam("pathParam") private String pathParam;
	@QueryParam("queryParam") private String queryParam;
	
	private int count; 
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testMethod(){
		++count;
		return "It works! This method was called " + count
				+ " times. The path param is " + pathParam + ", and the query param is " + queryParam;
	}
	
	@GET
	@Produces(value={MediaType.TEXT_PLAIN, "text/shortdate"})
	@Path("writer")
	public Date testMessageBodyWriterForDate(){
		return Calendar.getInstance().getTime();
	}
	
}
