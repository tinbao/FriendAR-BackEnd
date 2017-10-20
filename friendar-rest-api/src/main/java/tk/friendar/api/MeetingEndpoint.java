package tk.friendar.api;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.List;

/**
 * Root resource (exposed at "meetings  " path)
 */
@Path("meetings")
public class MeetingEndpoint {

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

            List<MeetingDB> meetingsDB = session.createCriteria(MeetingDB.class).list();
            JSONObject json = new JSONObject();
            for (MeetingDB meeting : meetingsDB) {
                json.append("meetings: ", meeting.toJson(true));
            }

            return json.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String meetingJson) throws JSONException {
        try {

            JSONObject json = new JSONObject(meetingJson);
            MeetingDB meeting = new MeetingDB();

            meeting.setMeetingName(json.getString("meetingName"));
            meeting.setPlace(json.getInt("placeID"));
            meeting.setTimeDate(Timestamp.valueOf(json.getString("time")));

            try (Session session = SessionFactorySingleton.getInstance().openSession()) {
                session.beginTransaction();
                session.save(meeting);
                String test = meeting.toJson(true).toString();
                session.getTransaction().commit();
                return test;
            }
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            try {
                return session.get(MeetingDB.class, Integer.valueOf(id)).toJson(true).toString();
            } catch (Exception e) {
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
                session.beginTransaction();
                MeetingDB meeting = session.get(MeetingDB.class, Integer.valueOf(id));

                JSONObject json = new JSONObject(userJson);
                if(json.has("time")){
                    meeting.setTimeDate(Timestamp.valueOf(json.getString("time")));
                }
                if(json.has("placeID")){
                    meeting.setPlace(json.getInt("placeID"));
                }
                if(json.has("meetingName")){
                    meeting.setMeetingName(json.getString("meetingName"));
                }
                session.save(meeting);
                String test = meeting.toJson(true).toString();
                session.getTransaction().commit();
                return test;
            } catch (Exception e) {
                return e.toString();
            }
        }
    }
}