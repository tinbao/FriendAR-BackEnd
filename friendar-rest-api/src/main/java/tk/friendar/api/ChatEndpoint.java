package tk.friendar.api;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Root resource (exposed at "messages" path)
 */
@Path("messages")
public class ChatEndpoint {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() throws JSONException {
        try {
            Session session = SessionFactorySingleton.getInstance().openSession();
            try{
                List<MessageDB> messagesDB = session.createCriteria(MessageDB.class).list();
                JSONObject json = new JSONObject();
                for (MessageDB place : messagesDB) {
                    json.append("messages: ", place.toJson(true));
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
    public String create(String messageJson) throws JSONException {
        try {
            Session session = SessionFactorySingleton.getInstance().openSession();
            JSONObject json = new JSONObject(messageJson);
            MessageDB message = new MessageDB();
            message.setContent(json.getString("content"));
            message.setMeeting(json.getInt("meetingID"));
            message.setUser(json.getInt("userID"));
            message.setTimeSent(new Date());

            try {
                session.beginTransaction();
                session.save(message);
                session.getTransaction().commit();
                String response = message.toJson(true).toString();
                session.close();
                return response;
            } catch (Exception e){
                return e.toString();
            } finally {
                session.close();
            }
        } catch (Exception e) {
            throw new JSONException(e);
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        try {
            Session session = SessionFactorySingleton.getInstance().openSession();
            try {
                String result = session.get(MessageDB.class, Integer.valueOf(id)).toJson(false).toString();
                session.close();
                return result;
            } catch (Exception e) {
                return e.toString();
            } finally {
                session.close();
            }
        }catch (Exception e) {
            return e.toString();
        }
    }
}