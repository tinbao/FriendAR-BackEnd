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
    public List<MessageDB> get() {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            return session.createCriteria(MessageDB.class).list();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MessageDB create(String messageJson) throws JSONException {
        JSONObject json = new JSONObject(messageJson);
        MessageDB message = new MessageDB();

        try {
            message.setContent(json.getString("content"));
            message.setMeetingID(json.getInt("meetingID"));
            message.setUserID(json.getInt("userID"));
        } catch (Exception e) {
            System.out.print(e.toString());
        }

        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            session.beginTransaction();
            session.save(message);
            session.getTransaction().commit();
            return message;
        }

    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MessageDB get(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            return session.get(MessageDB.class, Integer.valueOf(id));
        }
    }
}