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
                .credentials("habibia", "habibia")
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
    @Test
    public void testGetIt() {
        //getting entire list of places in form of a string
        String responseMsg = target.path("users").request().get(String.class);
        assertNotNull(responseMsg);

        //getting a specific entry list of places in form of a string
        responseMsg = target.path("users").path("1").request().get(String.class);
        assertNotNull(responseMsg);
    }

    @Test
    public void testPOST() throws Exception {
        //Complete data
        String test = "{\"username\": \"james\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\'James Stone\'}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
    }

    @Test
    public void testAnotherPOST() throws Exception {
        String test = "{\"username\": \"james\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\'Simon\'}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);

    }

    @Test
    public void testPOSTwithLatnLng() throws Exception {
        String test = "{\"username\": \"Luca Harris\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\'Simon\', \"latitude\": 123, \"longitude\": 123}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
    }

    @Test
    public void testPOSTmissingEmail() throws Exception {
        //A user with incomplete data
        String test = "{\"username\": \"Tin\", \"usersPassword\": \"Bao\",\"fullName\":\'ashkan Habibi\'}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.equals("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>");
    }

    @Test
    public void testPOSTmissingEmailAgain() throws Exception {
        String test_04 = "{\"username\": \"Luca\", \"usersPassword\": \"harris\",\"fullName\":\'ashkan Habibi\'}";
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test_04), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.equals("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>");
    }

    @Test
    public void testAValidDelete() throws Exception {
        Response msg;
        String output;
        //Deleting an existing place
        msg = target.path("users").path("2").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
    }

    @Test
    public void testAnotherValidDelete() throws Exception {
        Response msg = target.path("users").path("4").request().accept(MediaType.APPLICATION_JSON).delete();
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
    }

    @Test
    public void testAnInValidDelete() throws Exception {
        //Deleting a non-existing place
        Response msg = target.path("users").path("15").request().accept(MediaType.APPLICATION_JSON).delete();
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }

    @Test
    public void testPutUpdateJustLatLng() throws Exception {
        //updating an existing entry
        String test = "{\"latitude\": 123, \"longitude\": 123}";
        Response msg = target.path("users").path("1").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
    }

    @Test
    public void testPutUpdateAllFields() throws Exception {
        //updating a non-existing entry
        String test = "{\"username\": \"james\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\'Simon\', \"latitude\": 123, \"longitude\": 123}";
        Response msg = target.path("users").path("15").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
    }
}
