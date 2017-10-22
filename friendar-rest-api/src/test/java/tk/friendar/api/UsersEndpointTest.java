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
public class UsersEndpointTest {

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

    /*
     * Test to see that the message "Got it!" is sent in the response.
     */


    /******************************* @POST tests *******************************/
    @Test
    public void A_testPOSTCompleteUser() throws Exception {
        //Complete data with lat or long
        String test = "{\"username\": \"Luca@gmail.com\", \"email\": \"luca@gmail.com\", \"usersPassword\": \"harris\",\"fullName\":\"Luca Harris\", \"latitude\": 120, \"longitude\": 120}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("username".toLowerCase());

    }

    @Test
    public void B_testPOSTCompleteUser_NoLatLong() throws Exception {
        //Complete data without lat or long
        String test = "{\"username\": \"tin@gmail.com\", \"email\": \"matt@gmail.com\", \"usersPassword\": \"bao\",\"fullName\":\"Tin Bao\"}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("username".toLowerCase());

    }

    @Test
    public void C_testPOSTCompleteAnotherUser_NoLatLong() throws Exception {
        //Complete data without lat or long
        String test = "{\"username\": \"Gold@gmail.com\", \"email\": \"matt@gmail.com\", \"usersPassword\": \"Stone\",\"fullName\":\"Tin Bao\"}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("username".toLowerCase());

    }

    @Test
    public void D_testAnotherPOST() throws Exception {
        String test = "{\"username\": \"james\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\"Simon\"}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);

    }

    @Test
    public void E_testAnotherPOST_NewUser() throws Exception {
        String test = "{\"username\": \"james\", \"email\": \"user01@yahoo.com\", \"usersPassword\": \"user01\",\"fullName\":\"User 01\"}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);

    }

    @Test
    public void F_testAnotherPOST_tutor() throws Exception {
        String test = "{\"username\": \"lucaM@gmail.com\", \"email\": \"lucaM@gmail.com\", \"usersPassword\": \"Morandini\",\"fullName\":\"Luca Morandini\"}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);

    }

    @Test
    public void G_testPOSTwithoutPassword() throws Exception {
        String test = "{\"username\": \"Ashkan Habibi\", \"email\": \"asf@gsddf.com\",\"fullName\":\'Simon\', \"latitude\": 123, \"longitude\": 123}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("org.json.JSONException: JSONObject[\"usersPassword\"] not found.".toLowerCase());
    }

    @Test
    public void H_testPOSTmissingEmail() throws Exception {
        //A user with incomplete data
        String test = "{\"username\": \"Tin\", \"usersPassword\": \"Bao\",\"fullName\":\'ashkan Habibi\'}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.equals("org.json.JSONException: JSONObject[\"email\"] not found.");
    }

    @Test
    public void I_testPOSTmissingEmailAgain() throws Exception {
        String test_04 = "{\"username\": \"Simon\", \"usersPassword\": \"harris\",\"fullName\":\'ashkan Habibi\'}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test_04), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.equals("org.json.JSONException: JSONObject[\"email\"] not found.");
    }


    /******************************* @GET tests *******************************/
    @Test
    public void J_testGetAllUsers() {
        //getting entire list of users in form of a string
        String allUsers = target.path("users").request().get(String.class);
        assertNotNull(allUsers);
        assert allUsers.toLowerCase().contains("users: ".toLowerCase());
    }

    @Test
    public void K_testGetParticularUser() {
        //getting a specific user in form of a string
        String user = target.path("users").path("4").request().get(String.class);
        assertNotNull(user);
        assert user.toLowerCase().contains("fullName".toLowerCase());
    }

    @Test
    public void L_testGetParticularUser_empty() {
        //getting a specific user that doesn't exist
        String user = target.path("users").path("30").request().get(String.class);
        assertNotNull(user);
        assert user.equalsIgnoreCase("java.lang.NullPointerException");
    }


    /******************************* @PUT tests *******************************/
    @Test
    public void M_testPutUpdateJustLatLng() throws Exception {
        //updating an existing entry
        String test = "{\"latitude\": 465, \"longitude\": 123}";
        Response msg = target.path("users").path("2").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("username".toLowerCase());
    }

    @Test
    public void N_testPutUpdateAllFields() throws Exception {
        //updating a non-existing entry
        String test = "{\"username\": \"james\", \"email\": \"asf.com\", \"usersPassword\": \"stone\",\"fullName\":\'Simon\', \"latitude\": 123, \"longitude\": 123}";
        Response msg = target.path("users").path("3").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("username".toLowerCase());
    }

    @Test
    public void O_testPutUpdateAllFields_InvalidUser() throws Exception {
        //updating a non-existing entry
        String test = "{\"username\": \"james\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\'Simon\', \"latitude\": 123, \"longitude\": 123}";
        Response msg = target.path("users").path("20").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("java.lang.NullPointerException"));
    }


    /******************************* @DELETE tests *******************************/
    @Test
    public void P_testAValidDelete() throws Exception {
        Response msg;
        String output;
        //Deleting an existing place
        msg = target.path("users").path("5").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("username".toLowerCase());
    }

    @Test
    public void Q_testAnotherValidDelete() throws Exception {
        Response msg = target.path("users").path("4").request().accept(MediaType.APPLICATION_JSON).delete();
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("username".toLowerCase());
    }

    @Test
    public void R_testAnInValidDelete() throws Exception {
        //Deleting a non-existing place
        Response msg = target.path("users").path("60").request().accept(MediaType.APPLICATION_JSON).delete();
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }
}
