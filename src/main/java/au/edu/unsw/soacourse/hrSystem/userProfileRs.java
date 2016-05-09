package au.edu.unsw.soacourse.hrSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;


import au.edu.unsw.soacourse.hrSystem.dao.UserProfileDao;
import au.edu.unsw.soacourse.hrSystem.model.UserProfile;
@Path("/userProfile")
public class userProfileRs {
	// Return the list of books for client applications/programs
	 UserProfileDao userProfileDao = new UserProfileDao();
		@Context
		UriInfo uriInfo;
		@Context
		Request request;
		@GET
		@Path("{userProfile}")
		@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
		public UserProfile getUserProfile(@PathParam("userProfile") String userID) {
			UserProfile b = userProfileDao.get(userID);
			
		if(b==null) {
			throw new RuntimeException("GET: userProfile with" + userID +  " not found");
		}
		return b;
		}
		
		@PUT
		@Path("{userProfile}")
		@Consumes(MediaType.APPLICATION_XML)
		public Response putUserProfile(UserProfile b) {
			return putAndGetResponse(b);
			//TODO: Fix here so that it returns the updated book
		}
		private Response putAndGetResponse(UserProfile b) {
			Response res;
			if(userProfileDao.get(b.getId()) != null) {
				res = Response.noContent().build();
			} else {
				res = Response.created(uriInfo.getAbsolutePath()).build();
			}
			userProfileDao.put(b);
			return res;
		}

}
