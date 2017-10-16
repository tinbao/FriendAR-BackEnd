package tk.friendar.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlacesEndpointTest {

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
        if(userMade == false){
            createUser_Auth();
        }
        userMade = true;

    }

    private void createUser_Auth() throws Exception{
        String test = "{\"username\": \"Luca@gmail.com\", \"email\": \"luca@gmail.com\", \"usersPassword\": \"harris\",\"fullName\":\"Luca Harris\", \"latitude\": 120, \"longitude\": 120}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        userMade = true;
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */

    /********************* @POST tests *********************/

    @Test
    public void A_testPost_CompletePlace() throws Exception {
        Response msg;
        String output;
        //Complete tests
        String test = "{\"latitude\":678,\"placeName\":\"Melbourne Central\",\"longitude\":968}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);
    }

    @Test
    public void B_testPost_CompletePlaceAnother() throws Exception {
        Response msg;
        String output;
        String test = "{\"latitude\":256,\"placeName\":\"Etihad Stadium\",\"longitude\":1024}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        System.out.println("MSG: " + output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);
    }

    @Test
    public void C_testPost_CompletePlaceOther() throws Exception {
        Response msg;
        String output;
        String test = "{\"latitude\":128,\"placeName\":\"AAMI Park Stadium\",\"longitude\":2048}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);
    }


    @Test
    public void D_testPost_Unimelb() throws Exception {
        Response msg;
        String output;
        String test = "{\"latitude\":12,\"placeName\":\"University of Melbourne\",\"longitude\":24}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);
    }


    @Test
    public void E_testPost_Maccas() throws Exception {
        Response msg;
        String output;
        String test = "{\"latitude\":10,\"placeName\":\"Swanston Maccas\",\"longitude\":35}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);
    }

    @Test
    public void F_testPost_IncompleteNoLat() throws Exception {
        Response msg;
        String output;
        String test = "{\"placeName\":\"Etihad Stadium\",\"longitude\":123}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }


    @Test
    public void G_testPost_IncompleteNoLong() throws Exception {
        Response msg;
        String output;
        String test = "{\"placeName\":\"Etihad Stadium\",\"latitude\":123}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }

    @Test
    public void H_testPost_IncompleteNoName() throws Exception {
        Response msg;
        String output;
        String test = "{\"latitude\":\"12\",\"longitude\":123}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }

    /********************* @GET tests *********************/

    @Test
    public void I_testGetItAll() {
        //getting entire list of places in form of a string
        String responseMsg = target.path("places").request().get(String.class);
        assertNotNull(responseMsg);
        assert responseMsg.toLowerCase().contains("places: ".toLowerCase());

    }

    @Test
    public void J_testGetASpecificPlace() {
        //getting a specific entry list of places in form of a string
        String responseMsg = target.path("places").path("1").request().get(String.class);
        assertNotNull(responseMsg);
        assert responseMsg.toLowerCase().contains("placeName".toLowerCase());
    }

    @Test
    public void K_testGetInvalidPlace() {
        //getting a specific entry list of places in form of a string
        try{
            String responseMsg = target.path("places").path("10").request().get(String.class);
        } catch (Exception e){
            assert e.toString().equalsIgnoreCase("javax.ws.rs.InternalServerErrorException: HTTP 500 Internal Server Error");
        }
    }


    /********************* @PUT tests *********************/

    @Test
    public void L_testPut_FullData() throws Exception {
        Response msg;
        String output;

        //updating an existing entry
        String test_01 = "{\"latitude\":678,\"placeName\":\"Melbourne Central\",\"longitude\":10}";
        msg = target.path("places").path("1").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test_01));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("placeName".toLowerCase());
    }

    @Test
    public void M_testPut_LatOnly() throws Exception {
        Response msg;
        String output;

        //updating an existing entry
        String test_01 = "{\"longitude\":678}";
        msg = target.path("places").path("2").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test_01));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("placeName".toLowerCase());
    }


    @Test
    public void N_testPut_InvalidID() throws Exception {
        Response msg;
        String output;

        //updating an existing entry
        String test_01 = "{\"longitude\":678}";
        msg = target.path("places").path("15").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test_01));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("java.lang.IllegalArgumentException"));
    }



    /********************* @DELETE tests *********************/

    @Test
    public void O_testDelete() throws Exception {
        Response msg;
        String output;
        //Deleting an exisiting place
        msg = target.path("places").path("5").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);
    }

    @Test
    public void P_testDelete_invalidPlace() throws Exception {
        Response msg;
        String output;
        //Deleting an exisiting place
        msg = target.path("places").path("15").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }
}
