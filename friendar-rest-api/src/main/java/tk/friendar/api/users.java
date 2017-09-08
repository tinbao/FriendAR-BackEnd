package tk.friendar.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserClass> get() {
        try (Session session = HibernateSingletonFactory.getInstance().openSession()) {
            return session.createCriteria(UserClass.class).list();
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserClass get(@PathParam("id") String id) {
        try (Session session = HibernateSingletonFactory.getInstance().openSession()) {
            return session.get(UserClass.class, Integer.valueOf(id));
        }
    }
}