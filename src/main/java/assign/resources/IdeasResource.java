//use for input validation and response generation
/*Perform following checks on the input data:
 * In the call to create a project (POST /projects), ensure that name and description are not empty (“”).
 * In the call to update a project (PUT /projects), ensure that name and description are not empty.
 * If the input fails validation in any of the above cases, return HTTP 400 Bad Request response.
 * In the calls to GET a project, PUT a project and DELETE a project, 
 * if projectId that does not exist in your system is passed, return HTTP 404 Not Found response.
*/


package assign.resources;

import java.io.InputStream;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import assign.domain.Date;
import assign.services.DBLoader;

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
	
	@GET
	@Path("/index.html")
	public void doGet() {
	}
	
	@GET
	@Path("dates/all")
	@Produces("text/html")
	public String allDates() {
		String list = "";
		List<Date> all = loader.getAllDates();
		if (all == null){
			return "no entries";
		} else {
			for(int i = 0; i < all.size(); i++) {
				list += all.get(i).getTitle() + "\n";
			}
			return list; 
		}
	}	
	
	@GET
	@Path("dates/complete")
	@Produces("text/html")
	public String allCompleted() {
		String list = "";
		List<Date> all = loader.getByCompletion(true);
		if (all == null){
			return "no entries";
		} else {
			for(int i = 0; i < all.size(); i++) {
				list += loader.newTableEntry(all.get(i), i);
			}
			return list; 
		}
	}
	@GET
	@Path("dates/incomplete")
	@Produces("text/html")
	public String allIncomplete() {
		String list = "";
		List<Date> all = loader.getByCompletion(false);
		if (all == null){
			return "no entries";
		} else {
			for(int i = 0; i < all.size(); i++) {
				list += loader.newTableEntry(all.get(i), i);
			}
			return list; 
		}
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
	@Path("/dates")
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

/*	@PUT
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
*/	
	@DELETE
	@Path("/dates/{id}")
	public Response deleteDate(@PathParam("id") Long id) throws Exception {
		Date date = loader.getDateById(id);
	      if(date == null) {
	    	  	return Response.status(404).build();
	      } else {
	    	  	loader.deleteDate(id);
	    	  	return Response.status(200).build();
	      }
	}
	
	//parses xml and stores data in a new Date object
	protected Date readNewDate(InputStream is) {
	      try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			Date date = new Date();
			
			Queue<Element> q = new LinkedList<Element>();
	 		q.add(root);
	 		
	 		while(!q.isEmpty()) {
	 			Element e = (Element) q.remove();			
		 		if (e.getTagName().equals("title")) {
	            		date.setTitle(e.getTextContent());
	            		String nodeValue = e.getTextContent();
		 		    	System.out.println("Node value:" + nodeValue);
	            }
	            else if (e.getTagName().equals("description")) {
	               date.setDateDescription(e.getTextContent());
	               String nodeValue = e.getTextContent();
	               System.out.println("Node value:" + nodeValue);
	            }
	            else if (e.getTagName().equals("completed")) {
	            		if(e.getTextContent().toLowerCase().equals("true"))
	            			date.setCompleted(true);
	            		else if (e.getTextContent().toLowerCase().equals("false"))
	            			date.setCompleted(false);
	            		else 
	            			throw new WebApplicationException();
	            		String nodeValue = e.getTextContent();
		 		    	System.out.println("Node value:" + nodeValue);
	            }
	            else if(e.getTagName().equals("fecha")) {
	            		date.setDate(e.getTextContent());
	            		String nodeValue = e.getTextContent();
		 		    	System.out.println("Node value:" + nodeValue);
	            }
	 			
	 			NodeList nodes = e.getChildNodes();
	 			for(int i=0; i<nodes.getLength(); i++) {
	 				Node node = nodes.item(i);
	 				if(node instanceof Element) {
	 					q.add((Element) node);
	 				}
	 			}
	 		}
	         return date;
	      }
	      catch (Exception e) {
	    	  	
	         throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
	      }
	   }
}
