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
public class MeetingUserEndpointTest {

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

        test = "{\"username\": \"tin@gmail.com\", \"email\": \"matt@gmail.com\", \"usersPassword\": \"bao\",\"fullName\":\"Tin Bao\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

        test = "{\"username\": \"james\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\"Simon\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

        test = "{\"username\": \"Simon\", \"email\": \"simon@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\"James\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);


        test = "{\"latitude\":678,\"placeName\":\"Melbourne Central\",\"longitude\":968}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

        test = "{\"latitude\":256,\"placeName\":\"Etihad Stadium\",\"longitude\":1024}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

        test = "{\"latitude\":128,\"placeName\":\"AAMI Park Stadium\",\"longitude\":2048}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

        test = "{\"meetingName\": 'Unimelb meeting', \"placeID\": \"3\", \"time\": \"2016-02-03 00:00:00.0\"}";
        msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);

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
    public void A_testPOSTCompleteMeetingUsers() throws Exception {
        //Complete data with lat or long
        String test = "{\"meetingID\": 1, \"userID\": 1}";
        Response msg = target.path("meetingusers").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);

    }

    @Test
    public void B_testPOSTCompleteMeetingUsersAnother() throws Exception {
        //Complete data with lat or long
        String test = "{\"meetingID\": 1, \"userID\": 3}";
        Response msg = target.path("meetingusers").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);


    }

    @Test
    public void C_testPOSTwithoutUserID() throws Exception {
        //Complete data with lat or long
        String test = "{\"meetingID\": 45}";
        Response msg = target.path("meetingusers").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("org.json.JSONException: JSONObject[\"userID\"] not found.".toLowerCase()) != -1);
    }


    /******************************* @GET tests *******************************/
    @Test
    public void D_testGetAllMeetingUsers() {
        //getting entire list of users in form of a string
        String allMeetingUsers = target.path("meetingusers").request().get(String.class);
        assertNotNull(allMeetingUsers);
        assert allMeetingUsers.toLowerCase().contains("meetingUsers: ".toLowerCase());
    }

    @Test
    public void E_testGetParticularMeetingUser() {
        //getting a specific user in form of a string
        String meetingUser = target.path("meetingusers").path("1").request().get(String.class);
        assertNotNull(meetingUser);
        assert (meetingUser.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);
    }

    @Test
    public void F_testGetParticularMeetingUser_empty() {
        //getting a specific user that doesn't exist
        try {
            //getting a specific user in form of a string
            String invalidMeetingUser = target.path("meetingusers").path("45").request().get(String.class);
            assertNotNull(invalidMeetingUser);
            assert (invalidMeetingUser.toLowerCase().indexOf("java.lang.NullPointerException".toLowerCase()) != -1);
        } catch (Exception e) {

        }

    }


    /******************************* @Delete tests *******************************/
    @Test
    public void G_testAValidDelete() throws Exception {
        Response msg;
        String output;
        //Deleting an existing place
        msg = target.path("meetingusers").path("2").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);
    }

    @Test
    public void H_testAnInvalidDelete() throws Exception {
        Response msg;
        String output;
        //Deleting an existing place
        msg = target.path("meetingusers").path("45").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }
}