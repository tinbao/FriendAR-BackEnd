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

public class FriendshipEndpointTest {

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
    public void testPOSTCompleteFriendship() throws Exception {
        //Complete data with lat or long
        String test = "{\"userA_ID\": 1, \"userB_ID\": 4}";
        Response msg = target.path("friendships").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("id".toLowerCase()) != -1);
    }

    @Test
    public void testPOSTCompleteFriendshipsAnother() throws Exception {
        //Complete data with lat or long
        String test = "{\"userA_ID\": 1, \"userB_ID\": 3}";
        Response msg = target.path("friendships").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("id".toLowerCase()) != -1);


    }

    @Test
    public void testPOSTwithoutA_ID() throws Exception {
        //Complete data with lat or long
        String test = "{\"userB_ID\": 4}";
        Response msg = target.path("friendships").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("org.json.JSONException: JSONObject[\"userA_ID\"] not found.".toLowerCase()) != -1);
    }


    /******************************* @GET tests *******************************/
    @Test
    public void testGetAllAllFriendships() {
        //getting entire list of users in form of a string
        String allMeetingUsers = target.path("friendships").request().get(String.class);
        assertNotNull(allMeetingUsers);
        assert allMeetingUsers.toLowerCase().contains("friendships: ".toLowerCase());
    }

    @Test
    public void testGetParticularFriendship() {
        //getting a specific user in form of a string
        String friendship = target.path("friendships").path("1").request().get(String.class);
        assertNotNull(friendship);
        assert (friendship.toLowerCase().indexOf("id".toLowerCase()) != -1);
    }

    @Test
    public void testGetParticularMeetingUser_empty() {
        //getting a specific user that doesn't exist
        try {
            //getting a specific user in form of a string
            String invalidMeetingUser = target.path("friendships").path("45").request().get(String.class);
            assertNotNull(invalidMeetingUser);
            assert (invalidMeetingUser.toLowerCase().indexOf("java.lang.NullPointerException".toLowerCase()) != -1);
        } catch (Exception e) {

        }

    }


    /******************************* @Delete tests *******************************/
    @Test
    public void testAValidDelete() throws Exception {
        Response msg;
        String output;
        //Deleting an existing place
        msg = target.path("friendships").path("2").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);
    }

    @Test
    public void testAnInvalidDelete() throws Exception {
        Response msg;
        String output;
        //Deleting an existing place
        msg = target.path("friendships").path("45").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }
}