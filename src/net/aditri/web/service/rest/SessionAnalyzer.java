package net.aditri.web.service.rest;
import java.util.Map;
import javax.ws.rs.GET;  
import javax.ws.rs.Path;  
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.prp.models.SessionObject;
import com.prp.models.Group;
import com.prp.models.UserSession;

import net.aditri.dao.DBResponse;  
@Path("/session") 
public class SessionAnalyzer {
	  //https://www.javatpoint.com/jax-rs-example-jersey
	  @GET
	  @Path("/ug")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Group getUG(@QueryParam("usrname") String name,@QueryParam("rptname") String report)
	  {
		  Group o =new Group();
		  o.setGroupName(name);
		  o.setId(Long.getLong("1"));
		  o.setDescription("");
		  o.setCreatedBy("");
		  o.setModifiedBy("");
		  return o;
	  }
	  @GET
	  @Path("/hasaccess")
	  @Produces(MediaType.APPLICATION_JSON)
	  public DBResponse isLoggedInAndHasRptAccess(@QueryParam("rptName") String rptName,@QueryParam("sKey") String sKey,@QueryParam("uKey") String uKey)
	  {
		  DBResponse o =new DBResponse();
		  boolean hasAccess = false;
		  String message="";
		  Map<String, UserSession> sessions = SessionObject.getActivesessions();
		  for (Map.Entry<String, UserSession> entry : sessions.entrySet())
		  {
			  message+="Key: "+entry.getKey()+" Value:"+ entry.getValue().getUserName();
			  if(entry.getKey().equals(sKey) && entry.getValue().getUserName().equals(uKey))
			  {
				  hasAccess = true;
				  break;
			  }
		  }
		  
		  if(hasAccess)
		  {
			  // find Report Access with rptName, sKey & uKey
			  
		  }
		  o.setSuccess(hasAccess);
		  o.setMessage(message);
		  return o;
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  @GET  
	  @Produces(MediaType.TEXT_PLAIN)  
	  public String sayPlainTextHello() {  
	    return "Hello Jersey Plain"+":"+com.prp.models.SessionObject.getActivesessions().size();  
	  }  
	  // This method is called if XML is requested  
	  @GET  
	  @Produces(MediaType.TEXT_XML)  
	  public String sayXMLHello() {  
	    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";  
	  } 
	  // This method is called if HTML is requested  
	  @GET  
	  @Produces(MediaType.TEXT_HTML)  
	  public String sayHtmlHello() {  
	    return "<html> " + "<title>" + "Hello Jersey" + "</title>"  
	        + "<body><h1>" + "Hello Jersey HTML" + "</h1></body>" + "</html> ";  
	  }  
}

