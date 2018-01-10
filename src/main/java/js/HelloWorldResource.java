package js;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Path("/")
public class HelloWorldResource {
	
	public HelloWorldResource() {
		
	}

	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public String helloWorld() {
		System.out.println("Inside helloworld");
		return "Hello";
	}
	
	@GET
	@Path("/yo")
	@Produces("text/html")
	public String hello() {
		return "Yo yo yo";
	}
	
	@GET
	@Path("/meetings")
	@Produces("text/html")
	public String helloAustin(){
		String response = "<table><tr><td>Year</td><td>Number of Meetings</td></tr>";
		for(int year = 2010; year <= 2017; year++) {
			response += "<tr><td>"+ year + "</td><td>" + countMeetings("solum_team_meeting", year) + "</td></tr>";
		}
		response += "</table>";
		return response;
	}
	
	public int countMeetings(String proj, int year){
		try {
			Document html = Jsoup.connect("http://eavesdrop.openstack.org/meetings/"+proj+"/"+year+"/").timeout(5000).get();
			return (html.select("td").select("a").size()-1)/4;
		}catch(IOException e) {
			return 0;
		}
	}

}
