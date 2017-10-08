package tk.friendar.api;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.html.parser.Entity;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "users" path)
 */
@Path("places")
public class PlacesEndpoint {

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

            List<PlaceDB> placesDB = session.createCriteria(PlaceDB.class).list();
            JSONObject json = new JSONObject();
            for (PlaceDB place : placesDB) {
                json.append("places", place.toJson(true));
            }

            return json.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String placeJson) throws JSONException {
        try {

            JSONObject json = new JSONObject(placeJson);
            PlaceDB place = new PlaceDB();
            place.setPlaceName(json.getString("placeName"));
            //place.setPlaceName(json.getString("placeName"));
            place.setLatitude(json.getDouble("latitude"));
            place.setLongitude(json.getDouble("longitude"));

            try (Session session = SessionFactorySingleton.getInstance().openSession()) {
                session.beginTransaction();
                session.save(place);
                session.getTransaction().commit();
                JSONObject returnJson = new JSONObject(place);
                return returnJson.toString();
            }
        } catch (Exception e) {
            throw new JSONException(e);
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            try {
                return session.get(PlaceDB.class, Integer.valueOf(id)).toJson(true).toString();
            } catch (JSONException e) {
                return e.toString();
            }
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
                PlaceDB user = session.get(PlaceDB.class, Integer.valueOf(id));
                session.delete(user);
                session.getTransaction().commit();
            } catch (HibernateException e) {
                return e.toString();
            }
        }
        return null;
    }


    @Path("{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String put(@PathParam("id") String id, String placeJson) {
        // Do a call to a DAO Implementation that does a JDBC call to delete resource from  Mongo based on JSON
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            try {
                session.beginTransaction();
                PlaceDB place = session.get(PlaceDB.class, Integer.valueOf(id));

                JSONObject json = new JSONObject(placeJson);
                place.setPlaceName(json.getString("placeName"));
                //place.setPlaceName(json.getString("placeName"));
                place.setLatitude(json.getDouble("latitude"));
                place.setLongitude(json.getDouble("longitude"));
                session.update(place);
                session.getTransaction().commit();
                JSONObject returnJson = new JSONObject(place);
                return returnJson.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }
        //return null;
    }
}