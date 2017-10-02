package tk.friendar.api;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "users" path)
 */
@Path("users")
public class UsersEndpoint {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDB> get() throws JSONException {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            return session.createCriteria(UserDB.class)
                    .setFetchMode("friends", FetchMode.DEFAULT)
                    .list();
        }catch(Exception e){
            throw new JSONException(e.toString());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDB create(String userJson) throws JSONException {
        try {

            JSONObject json = new JSONObject(userJson);
            UserDB user = new UserDB();

            user.setFullName(json.getString("fullName"));
            user.setUsersname(json.getString("usersname"));
            user.setUsersPassword(json.getString("userspassword"));
            user.setEmail(json.getString("email"));
            user.setLatitude(json.getDouble("latitude"));
            user.setLongitude(json.getDouble("longitude"));

            try (Session session = SessionFactorySingleton.getInstance().openSession()) {
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
                return user;
            }
        } catch (Exception e) {
            throw new JSONException(e);
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserDB get(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            UserDB user1 =  session.get(UserDB.class, Integer.valueOf(id));
            //user1.friends;
            return user1;
        }
    }
}