package tk.friendar.api;

import org.hibernate.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "UserClass" path)
 */
@Path("users")
public class users {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a application/json response.
     */

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserClass get(@PathParam("id") String id) {
        Session session = HibernateSingletonFactory.getInstance();
        UserClass user = session.get(UserClass.class, Integer.valueOf(id));
        session.close();
        return user;
    }
}