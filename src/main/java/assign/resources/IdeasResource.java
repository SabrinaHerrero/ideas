//use for input validation and response generation
/*Perform following checks on the input data:
 * In the call to create a project (POST /projects), ensure that name and description are not empty (“”).
 * In the call to update a project (PUT /projects), ensure that name and description are not empty.
 * If the input fails validation in any of the above cases, return HTTP 400 Bad Request response.
 * In the calls to GET a project, PUT a project and DELETE a project, 
 * if projectId that does not exist in your system is passed, return HTTP 404 Not Found response.
*/


package assign.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import assign.domain.Date;
import assign.services.DBLoader;
import javassist.bytecode.analysis.ControlFlow.Node;

@Path("/")
public class IdeasResource {
	
	DBLoader loader;

	public IdeasResource(@Context ServletContext servletContext) {
		this.loader = new DBLoader();		
	}
	
	
	
	
	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public String helloWorld() {
		return "Hello world";	
	}	
	/*
	@GET
	@Path("/projects/{id}")
	public Response getProject(@PathParam("id") Long id) throws Exception {
		Project newProject = loader.getProject(id);
		 if(newProject == null) {
	    	  	return Response.status(404).build();
	      } 
		 return Response.ok(printProject(newProject)).build();
		
	}	
	protected String printProject(Project newProject) {
		return "<project id="+newProject.getId()+"><name>"+newProject.getProjectName()+"</name><description>"+newProject.getProjectDescription()+"</description><meetings>"+printMeetings(newProject)+"</meetings></project>";
		
	}

	protected String printMeetings(Project newProject) {
		String meetings = "";
		for(Meeting i: newProject.getMeetings()) {
			meetings += "<meeting id=m"+i.getId()+"><name>"+i.getName()+"</name><year>"+i.getYear()+"</year></meeting>";
		}
		return meetings;
	}*/
	@POST
	@Path("/date")
	@Consumes("application/xml")
	public Response createProject(InputStream is) throws Exception {
		Date newDate = readNewDate(is);
	    Long projID = loader.addDate(newDate);
		if(projID == null) {
			return Response.status(400).build();
		} else {
		  	return Response.created(URI.create("/dates/"+ newDate.getId())).build();
		}
	}
/*
	@POST
	@Path("/projects/{id}/meetings")
	@Consumes("application/xml")
	public Response createMeeting(@PathParam("id") Long id, InputStream is) throws Exception {
		Project project = loader.getProject(id);
		if(project == null) {
			return Response.status(404).build();
		}
		Meeting newMeeting = readNewMeeting(is, project); 
		Long meetingID = loader.addMeeting(newMeeting);
		if(meetingID == null) {
			return Response.status(400).build();
		} else {
		  	return Response.created(URI.create("/projects/"+ project.getId() + "/meetings/m" + newMeeting.getId())).build();
		}
	}
	@PUT
	@Path("/projects/{projId}/meetings/{meetingId}")
	@Consumes("application/xml")
	public Response editMeeting(@PathParam("projId") Long projId, @PathParam("meetingId") String id, InputStream is) throws Exception {
		Project project = loader.getProject(projId);
		 if(project == null) {
	    	  	return Response.status(404).build();
	      }
		 Meeting update;
		 Long meetingId;
		 try {
			 update = readNewMeeting(is, project);
			 meetingId = Long.parseLong(id.substring(1));
			 if(loader.getMeeting(meetingId) == null || !projId.equals(loader.getMeeting(meetingId).getProject().getId())) {
				 System.out.println(projId == loader.getMeeting(meetingId).getProject().getId());
				 System.out.println(projId);
				 System.out.println(loader.getMeeting(meetingId).getProject().getId().getClass().getName());
		    	  	return Response.status(404).build();
		      }
		 } catch (Exception e) {
			 return Response.status(404).build();
		 }
		 update = loader.updateMeeting(meetingId, update, project.getId());
	      if(update == null) {
	    	  	return Response.status(400).build();
	      } else {
	    	  	return Response.status(200).build();
	      }
	}
	@DELETE
	@Path("/projects/{id}")
	public Response deleteProject(@PathParam("id") Long id) throws Exception {
		Project project = loader.getProject(id);
	      if(project == null) {
	    	  	return Response.status(404).build();
	      } else {
	    	  	loader.deleteProject(id);
	    	  	return Response.status(200).build();
	      }
	}*/
	/*protected void outputProjects(OutputStream os, Projects projects) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Projects.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(projects, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}*/
	
//	protected void outputProjects(OutputStream os, Project project) throws IOException {
//		try { 
//			JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//	 
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			jaxbMarshaller.marshal(project, os);
//		} catch (JAXBException jaxb) {
//			jaxb.printStackTrace();
//			throw new WebApplicationException();
//		}
//	}
//	
	/*protected void outputProjects(OutputStream os, NotFound notFound) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(NotFound.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(notFound, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}	
	*/
	protected Date readNewDate(InputStream is) {
	      try {
	         DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	         Document doc = builder.parse(is);
	         Element root = doc.getDocumentElement();
	         Date date = new Date();
	         NodeList nodes = root.getChildNodes();
	         System.out.println(nodes.item(0).getTextContent());
	         for (int i = 0; i < nodes.getLength(); i++) {
	            Element element = (Element) nodes.item(i);
	            System.out.println(element.getTagName());
//	            if (element.getName()  .getTagName().equals("title")) {
//	            		date.setTitle(element.getTextContent());
//	            }
//	            else if (element.getTagName().equals("description")) {
//	               date.setDateDescription(element.getTextContent());
//	            }
//	            else if (element.getTagName().equals("completed")) {
//	            		if(element.getTextContent().toLowerCase().equals("true"))
//	            			date.setCompleted(true);
//	            		else if (element.getTextContent().toLowerCase().equals("false"))
//	            			date.setCompleted(false);
//	            		else 
//	            			throw new WebApplicationException();
//	            }
//	            else if(element.getTagName().equals("fecha")) {
//	            		date.setDate(element.getTextContent());
//	            }
	         }
	         return date;
	      }
	      catch (Exception e) {
	    	  	
	         throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
	      }
	   }
}
