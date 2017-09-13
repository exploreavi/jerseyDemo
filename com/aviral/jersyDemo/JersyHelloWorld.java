package com.aviral.jersyDemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.jms.JMSException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/helloWorld")
public class JersyHelloWorld {
	
	public JersyHelloWorld() throws JMSException {
		ActiveMQConnectionFactory q = new ActiveMQConnectionFactory("vm://mybroker?broker.useJmx=false");
		ActiveMQConnectionFactory q1 = new ActiveMQConnectionFactory("vm://yourbroker");
		System.out.println(q.createConnection());
		System.out.println(q1.createQueueConnection());
		
	}
	@GET
	@Produces("application/json")
	public Response getMessage() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Welcome Message", "Hello Welcome to Jersy Webservices.");
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON).entity(jsonObject.toString()).build();
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile( @FormDataParam("file") InputStream uploadedInputStream,
	@FormDataParam("file") FormDataContentDisposition fileDetail) {

	String uploadedFileLocation = "//home//aviral//" + fileDetail.getFileName();

	// save it
	writeToFile(uploadedInputStream, uploadedFileLocation);

	String output = "File uploaded to : " + uploadedFileLocation;

	return Response.status(200).entity(output).build();

	}
	
	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

			try {
				OutputStream out = new FileOutputStream(new File(
						uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];

				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				out.flush();
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
}
