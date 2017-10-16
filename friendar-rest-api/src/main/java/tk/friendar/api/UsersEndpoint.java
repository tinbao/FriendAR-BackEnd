package tk.friendar.api;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
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
    public String get() throws JSONException {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {

            List<UserDB> usersDB = session.createCriteria(UserDB.class).list();
            JSONObject json = new JSONObject();
            for (UserDB user : usersDB) {
                json.append("users: ", user.toJson(true));
            }

            return json.toString();
        } catch (Exception e) {
            System.err.println(e.toString());
            return e.toString();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String userJson) throws JSONException {
        try {

            JSONObject json = new JSONObject(userJson);
            UserDB user = new UserDB();
            boolean update = false;

            user.setFullName(json.getString("fullName"));
            user.setUsername(json.getString("username"));
            user.setUsersPassword(json.getString("usersPassword"));
            user.setEmail(json.getString("email"));
            if (json.has("latitude")) {
                user.setLatitude(json.getDouble("latitude"));
                update = true;
            }
            if (json.has("longitude")) {
                user.setLongitude(json.getDouble("longitude"));
                update = true;
            }

            if (update) {
                user.setLocationLastUpdated(new Date());
            }

            try (Session session = SessionFactorySingleton.getInstance().openSession()) {
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
                JSONObject returnJson = new JSONObject(user);
                returnJson.remove("usersPassword");
                return returnJson.toString();
            }
        } catch (Exception e) {
            System.err.println(e.toString());
            return e.toString();
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            try {
                return session.get(UserDB.class, Integer.valueOf(id)).toJson(true).toString();
            } catch (Exception e) {
                return e.toString();
            }
        }
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            try {
                session.beginTransaction();
                UserDB user = session.get(UserDB.class, Integer.valueOf(id));
                session.delete(user);
                session.getTransaction().commit();
                return user.toJson(true).toString();
            } catch (HibernateException | JSONException e) {
                return e.toString();
            }
        }
    }

    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String put(@PathParam("id") String id, String userJson) {
        // Do a call to a DAO Implementation that does a JDBC call to delete resource from  Mongo based on JSON
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            try {
                Boolean update = false;
                session.beginTransaction();
                UserDB user = session.get(UserDB.class, Integer.valueOf(id));

                JSONObject json = new JSONObject(userJson);
                if(json.has("fullName")){
                    user.setFullName(json.getString("fullName"));
                }
                if(json.has("username")){
                    user.setUsername(json.getString("username"));
                }
                if(json.has("usersPassword")){
                    user.setUsersPassword(json.getString("usersPassword"));
                }
                if(json.has("email")){
                    user.setEmail(json.getString("email"));
                }

                if (json.has("latitude")) {
                    user.setLatitude(json.getDouble("latitude"));
                    update = true;
                }
                if (json.has("longitude")) {
                    user.setLongitude(json.getDouble("longitude"));
                    update = true;
                }

                if (update) {
                    user.setLocationLastUpdated(new Date());
                }

                session.update(user);
                session.getTransaction().commit();
                JSONObject returnJson = new JSONObject(user);
                returnJson.remove("usersPassword");
                return returnJson.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }
    }
}