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
    public String get() throws JSONException {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {

            List<PlaceDB> placesDB = session.createCriteria(PlaceDB.class).list();
            JSONObject json = new JSONObject();
            for (PlaceDB place : placesDB) {
                json.append("places: ", place.toJson(true));
            }
            session.close();
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
            place.setLatitude(json.getDouble("latitude"));
            place.setLongitude(json.getDouble("longitude"));

            try (Session session = SessionFactorySingleton.getInstance().openSession()) {
                session.beginTransaction();
                session.save(place);
                String response = place.toJson(true).toString();
                session.getTransaction().commit();
                session.close();
                return response;
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
                String result = session.get(PlaceDB.class, Integer.valueOf(id)).toJson(true).toString();
                session.close();
                return result;
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
                PlaceDB place = session.get(PlaceDB.class, Integer.valueOf(id));
                session.delete(place);
                session.getTransaction().commit();
                session.close();
                return place.toJson(true).toString();
            } catch (Exception e) {
                return e.toString();
            }
        }
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

                if(place == null){
                    throw new IllegalArgumentException();
                }
                JSONObject json = new JSONObject(placeJson);
                if(json.has("placeName")){
                    place.setPlaceName(json.getString("placeName"));
                }
                if(json.has("latitude")){
                    place.setLatitude(json.getDouble("latitude"));
                }
                if(json.has("longitude")){
                    place.setLongitude(json.getDouble("longitude"));
                }
                session.update(place);
                session.getTransaction().commit();
                String response = place.toJson(true).toString();
                session.close();
                return response;
            } catch (Exception e) {
                return e.toString();
            }
        }
    }
}