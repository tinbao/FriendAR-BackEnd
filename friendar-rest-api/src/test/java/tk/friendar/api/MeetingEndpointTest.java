package tk.friendar.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeetingEndpointTest {

    private HttpServer server;
    private WebTarget target;
    private static boolean userMade = false;

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
        if (userMade == false) {
            createUser_Auth();
        }
        userMade = true;
        Populate();
    }

    private void createUser_Auth() throws Exception{
        String test = "{\"username\": \"Luca@gmail.com\", \"email\": \"luca@gmail.com\", \"usersPassword\": \"harris\",\"fullName\":\"Luca Harris\", \"latitude\": 120, \"longitude\": 120}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        userMade = true;
    }
    public void Populate() throws Exception{
        String test;
        Response msg;

        test = "{\"latitude\":678,\"placeName\":\"Melbourne Central\",\"longitude\":968}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

        test = "{\"latitude\":256,\"placeName\":\"Etihad Stadium\",\"longitude\":1024}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

        test = "{\"latitude\":128,\"placeName\":\"AAMI Park Stadium\",\"longitude\":2048}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

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
    public void A_testPOSTCompleteMeeting() throws Exception {
        //Complete data with lat or long
        String test = "{\"meetingName\": 'Unimelb meeting', \"placeID\": \"3\", \"time\": \"2016-02-03 00:00:00.0\"}";
        Response msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);

    }

    @Test
    public void B_testPOSTCompleteMeetingAnother() throws Exception {
        //Complete data with lat or long
        String test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"1 \", \"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);

    }

    @Test
    public void C_testPOSTwithoutPlaceID() throws Exception {
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
    public void D_testGetAllMeetings() {
        //getting entire list of users in form of a string
        String allUsers = target.path("meetings").request().get(String.class);
        assertNotNull(allUsers);
        assert allUsers.toLowerCase().contains("meetings: ".toLowerCase());
    }

    @Test
    public void E_testGetParticularMeeting() {
        //getting a specific user in form of a string
        String user = target.path("meetings").path("1").request().get(String.class);
        assertNotNull(user);
        assert (user.toLowerCase().indexOf("place".toLowerCase()) != -1);
    }

    @Test
    public void F_testGetParticularMeeting_empty() {
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
    public void G_testPutUpdateJustTime() throws Exception {
        //updating an existing entry
        String test = "{\"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meetings").path("1").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);
    }

    @Test
    public void H_testPutUpdateAllFields() throws Exception {
        //updating a non-existing entry
        String test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"3\", \"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meetings").path("2").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);
    }

    @Test
    public void I_testPutUpdateAllFields_InvalidMeeting() throws Exception {
        //updating a non-existing entry
        String test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"6\", \"time\": \"2017-12-12 00:00:00.0\"}";
        Response msg = target.path("meeting").path("20").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase(""));
    }

}