package tk.friendar.api;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("friendships")
public class FriendshipEndpoint {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a application/json response.
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FriendshipDB create(String friendshipJson) throws JSONException {
        JSONObject json = new JSONObject(friendshipJson);
        FriendshipDB friendship = new FriendshipDB();

        try {
            try (Session session = SessionFactorySingleton.getInstance().openSession()){
                UserDB userA = (UserDB) session.createCriteria(UserDB.class).add(Restrictions.eq("id", json.getInt("userA_ID"))).uniqueResult();
                UserDB userB = (UserDB) session.createCriteria(UserDB.class).add(Restrictions.eq("id", json.getInt("userB_ID"))).uniqueResult();
                friendship.setUserA_ID(userA);
                friendship.setUserB_ID(userB);
            }

        } catch (Exception e) {
            System.out.print(e.toString());
        }

        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            session.beginTransaction();
            session.save(friendship);
            session.getTransaction().commit();
            return friendship;
        }

    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String id) {
        // Do a call to a DAO Implementation that does a JDBC call to delete resource from  Mongo based on JSON
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            try {
                session.beginTransaction();
                FriendshipDB friendship = session.get(FriendshipDB.class, Integer.valueOf(id));
                session.delete(friendship);
                session.getTransaction().commit();
                return friendship.toJson().toString();
            } catch (HibernateException | JSONException e) {
                return e.toString();
            }
        }
    }
}
