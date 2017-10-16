package tk.friendar.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;

public class MeetingEndpointTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
                .nonPreemptive()
                .credentials("Luca@gmail.com", "harris")
                .build();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(feature);
        Client client = ClientBuilder.newClient(clientConfig);
        target = client.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */


    /******************************* @POST tests *******************************/
    @Test
    public void testPOSTCompleteMeeting() throws Exception {
        //Complete data with lat or long
        String test = "{\"meetingName\": 'Unimelb meeting', \"placeID\": \"3\", \"time\": \"2016-02-03 00:00:00.0\"}";
        Response msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);

    }

    @Test
    public void testPOSTCompleteMeetingAnother() throws Exception {
        //Complete data with lat or long
        String test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"1 \", \"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);

    }

    @Test
    public void testPOSTwithoutPlaceID() throws Exception {
        //Complete data with lat or long
        String test = "{\"meetingName\": 'Graduation meeting', \"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("org.json.JSONException: JSONObject[\"placeID\"] not found.".toLowerCase()) != -1);
    }


    /******************************* @GET tests *******************************/
    @Test
    public void testGetAllMeetings() {
        //getting entire list of users in form of a string
        String allUsers = target.path("meetings").request().get(String.class);
        assertNotNull(allUsers);
        assert allUsers.toLowerCase().contains("meetings: ".toLowerCase());
    }

    @Test
    public void testGetParticularMeeting() {
        //getting a specific user in form of a string
        String user = target.path("meetings").path("1").request().get(String.class);
        assertNotNull(user);
        assert (user.toLowerCase().indexOf("place".toLowerCase()) != -1);
    }

    @Test
    public void testGetParticularMeeting_empty() {
        //getting a specific user that doesn't exist
        try {
            String user = target.path("meetings").path("30").request().get(String.class);
            assertNotNull(user);
            assert (user.equalsIgnoreCase("java.lang.NullPointerException"));
        } catch (Exception e) {

        }

    }


    /******************************* @PUT tests *******************************/
    @Test
    public void testPutUpdateJustTime() throws Exception {
        //updating an existing entry
        String test = "{\"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meetings").path("1").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);
    }

    @Test
    public void testPutUpdateAllFields() throws Exception {
        //updating a non-existing entry
        String test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"3\", \"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meetings").path("2").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);
    }

    @Test
    public void testPutUpdateAllFields_InvalidMeeting() throws Exception {
        //updating a non-existing entry
        String test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"6\", \"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meeting").path("20").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase(""));
    }

}