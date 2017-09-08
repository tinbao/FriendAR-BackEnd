package tk.friendar.api;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "UsersDB" path)
 */
@Path("UsersEndpoint")
public class UsersEndpoint {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UsersDB> get() {
        try (Session session = HibernateSingletonFactory.getInstance().openSession()) {
            return session.createCriteria(UsersDB.class).list();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UsersDB create(String userJson) throws JSONException {
        JSONObject json = new JSONObject(userJson);
        UsersDB user = new UsersDB();

        user.setFullName(json.getString("fullName"));
        user.setUsersname(json.getString("usersname"));
        user.setUserspassword(json.getString("userspassword"));
        user.setSalt(json.getString("salt"));
        user.setEmail(json.getString("email"));
        user.setLatitude(json.getDouble("latitude"));
        user.setLongtitude(json.getDouble("longtitude"));

        try (Session session = HibernateSingletonFactory.getInstance().openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return user;
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UsersDB get(@PathParam("id") String id) {
        try (Session session = HibernateSingletonFactory.getInstance().openSession()) {
            return session.get(UsersDB.class, Integer.valueOf(id));
        }
    }
}