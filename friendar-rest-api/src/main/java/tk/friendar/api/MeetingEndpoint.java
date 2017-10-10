package tk.friendar.api;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.List;

/**
 * Root resource (exposed at "meetings" path)
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
    public String get() {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            List<MeetingDB> meetings = session.createCriteria(MeetingDB.class).list();
            JSONObject json = new JSONObject();
            for (MeetingDB meeting : meetings) {
                json.append("meetings", meeting.toJson(true));
            }
            return json.toString();
        } catch (JSONException e) {
            return e.toString();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String meetingJson) throws JSONException {
        JSONObject json = new JSONObject(meetingJson);
        MeetingDB meeting = new MeetingDB();

        try {
            meeting.setMeetingName(json.getString("meetingName"));
            meeting.setPlace(json.getInt("placeID"));
            meeting.setTimeDate(Timestamp.valueOf(json.getString("time")));
        } catch (Exception e) {
            System.out.print(e.toString());
        }

        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            session.beginTransaction();
            session.save(meeting);
            session.getTransaction().commit();
            return meeting.toJson(true).toString();
        }

    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            return session.get(MeetingDB.class, Integer.valueOf(id)).toJson(true).toString();
        } catch (JSONException e) {
            return e.toString();
        }
    }
}