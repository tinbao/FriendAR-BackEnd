package tk.friendar.api;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "chats" path)
 */
@Path("chats")
public class ChatEndpoint {

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
            List<MessageDB> messages = session.createCriteria(MessageDB.class).list();
            JSONObject json = new JSONObject();
            for (MessageDB message : messages) {
                json.append("messages", message.toJson(true));
            }
            return json.toString();
        } catch (JSONException e) {
            return e.toString();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String messageJson) throws JSONException {
        JSONObject json = new JSONObject(messageJson);
        MessageDB message = new MessageDB();

        try {
            message.setContent(json.getString("content"));
            message.setMeeting(json.getInt("meetingID"));
            message.setUser(json.getInt("userID"));
        } catch (Exception e) {
            return e.toString();
        }

        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            session.beginTransaction();
            session.save(message);
            session.getTransaction().commit();
            JSONObject messageJSON = new JSONObject(message);
            return messageJson.toString();
        }

    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            return session.get(MessageDB.class, Integer.valueOf(id)).toJson(true).toString();
        } catch (JSONException e) {
            return e.toString();
        }
    }
}