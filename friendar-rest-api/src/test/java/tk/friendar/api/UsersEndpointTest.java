package tk.friendar.api;

import org.glassfish.grizzly.http.server.HttpServer;
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
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
   /* @Test
    public void testGetIt() {
        String responseMsg = target.path("users").request().get(String.class);
        assertNotNull(responseMsg);
    }*/

    @Test
    public void testPost() throws Exception {

        String test_user = "{\"latitude\":678,\"fullname\":\"James Stone\",\"email\":\"jStone@gmail.com\",\"username\":\"JHappy\",\"longitude\":968, \"userpassword\": \"password\", \"salt\": \"salt\"}";
        //StringEntity value = new StringEntity(test_user);
        //JSONObject test_user = new JSONObject();
        Response msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test_user), Response.class);
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        System.out.print("MSG: " + output);
    }
}
