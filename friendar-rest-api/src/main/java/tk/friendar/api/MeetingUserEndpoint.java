package tk.friendar.api;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "meetingusers" path)
 */
@Path("meetingusers")
public class MeetingUserEndpoint {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() throws JSONException {
        try{
            Session session = SessionFactorySingleton.getInstance().openSession();
            try{
                List<MeetingUserDB> meetingUsersDB = session.createCriteria(MeetingUserDB.class).list();
                JSONObject json = new JSONObject();
                for (MeetingUserDB meetingUser : meetingUsersDB) {
                    json.append("meetingUsers: ", meetingUser.toJson(true));
                }
                return json.toString();
            } catch (Exception e){
                return  e.toString();
            } finally {
                session.close();
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String meetinguserJson) throws JSONException {
        try {
            Session session = SessionFactorySingleton.getInstance().openSession();
            JSONObject json = new JSONObject(meetinguserJson);
            MeetingUserDB meetingUser = new MeetingUserDB();

            meetingUser.setMeetingID(json.getInt("meetingID"));
            meetingUser.setUserID(json.getInt("userID"));

            try {
                session.beginTransaction();
                session.save(meetingUser);
                String response = meetingUser.toJson(false).toString();
                session.getTransaction().commit();
                return response;
            } catch (Exception e){
                return  e.toString();
            } finally {
                session.close();
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        try {
            Session session = SessionFactorySingleton.getInstance().openSession();
            try {
                String result = session.get(MeetingUserDB.class, Integer.valueOf(id)).toJson(false).toString();
                return result;
            } catch (Exception e) {
                return e.toString();
            } finally {
                session.close();
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String id) {
        try {
            Session session = SessionFactorySingleton.getInstance().openSession();
            try {
                session.beginTransaction();
                MeetingUserDB meetingUser = session.get(MeetingUserDB.class, Integer.valueOf(id));
                session.delete(meetingUser);
                session.getTransaction().commit();
                return meetingUser.toJson(false).toString();
            } catch (HibernateException | JSONException e) {
                return e.toString();
            } finally {
                session.close();
            }
        } catch (Exception e){
            return e.toString();
        }
    }
}