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

import au.edu.unsw.soacourse.hrSystem.dao.BooksDao;
import au.edu.unsw.soacourse.hrSystem.model.Book;

@Path("/books")
public class BooksResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of books for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Book> getBooks() {
		List<Book> bs = new ArrayList<Book>();
		bs.addAll( BooksDao.instance.getStore().values() );
		return bs; 
	}
	
	// Return the number of books in the bookstore
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = BooksDao.instance.getStore().size();
		return String.valueOf(count);
	}
	
    // POST to create a book - FIX THIS
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newBook(
			@FormParam("title") String title,
			@FormParam("detail") String detail
	) throws IOException {
		int num = BooksDao.instance.getStore().size()+1;
		String id = Integer.toString(num);
		Book b = new Book(id,title);
		if (detail!=null){
			b.setDetail(detail);
		}
		BooksDao.instance.getStore().put(id, b);
		return Response.ok().entity(b).build();
		//TODO: Fix here so that it returns the new book
	}
	
			
	@GET
	@Path("{book}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Book getBook(@PathParam("book") String id) {
		Book b = BooksDao.instance.getStore().get(id);
		if(b==null)
			throw new RuntimeException("GET: Book with" + id +  " not found");
		return b;
	}
	
	@PUT
	@Path("{book}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response putBook(@PathParam("book") String id, @Context Request req, Book updatedBk) {
		Book b = BooksDao.instance.getStore().get(id);
		
		EntityTag etag = new EntityTag(Integer.toString(b.hashCode()));
		ResponseBuilder builder = request.evaluatePreconditions(etag);
		if(builder != null) // changed since last update (send back 412)
		return builder.build();
		
		BooksDao.instance.getStore().put(getCount()+1,updatedBk);
		builder = Response.noContent();
		return builder.build();
		//TODO: Fix here so that it returns the updated book
	}
	
	@DELETE
	@Path("{book}")
	public void deleteBook(@PathParam("book") String id) {
		Book delb = BooksDao.instance.getStore().remove(id);
		if(delb==null)
			throw new RuntimeException("DELETE: Book with " + id +  " not found");
	}
	
	private Response putAndGetResponse(Book b) {
		Response res;
		if(BooksDao.instance.getStore().containsKey(b.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		BooksDao.instance.getStore().put(b.getId(), b);
		return res;
	}
	
}