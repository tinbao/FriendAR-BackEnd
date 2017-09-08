package tk.friendar.api;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "places" path)
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
    public List<PlaceDB> get() {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            return session.createCriteria(PlaceDB.class).list();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PlaceDB create(String placeJson) throws JSONException {
        JSONObject json = new JSONObject(placeJson);
        PlaceDB place = new PlaceDB();

        try {
            place.setPlaceName(json.getString("placeName"));
            place.setLatitude(json.getDouble("latitude"));
            place.setLongitude(json.getDouble("longitude"));
        } catch (Exception e) {
            System.out.print(e.toString());
        }

        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            session.beginTransaction();
            session.save(place);
            session.getTransaction().commit();
            return place;
        }

    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlaceDB get(@PathParam("id") String id) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            return session.get(PlaceDB.class, Integer.valueOf(id));
        }
    }
}