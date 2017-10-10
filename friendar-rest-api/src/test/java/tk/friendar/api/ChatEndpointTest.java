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

public class ChatEndpointTest {

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
        clientConfig.register(feature) ;
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
        String responseMsg = target.path("messages").request().get(String.class);
        assertNotNull(responseMsg);
        if(responseMsg.toLowerCase().indexOf("messages") != -1){
            System.out.println("Successfully connected to Database, here's a list of all messages: ");
        } else if (responseMsg.equalsIgnoreCase("[]")){
            System.out.println("No messages are recorded.");
        }
        System.out.println(responseMsg);

        //getting a specific entry list of places in form of a string
        responseMsg = target.path("messages").path("1").request().get(String.class);
        assertNotNull(responseMsg);
        if (responseMsg.equalsIgnoreCase("[]")){
            System.out.println("No such message is recorded.");
        }
        System.out.println(responseMsg);
    }

    @Test
    public void testPost() throws Exception {
        Response msg;
        String output;
        //Complete data
        String test_01 = "{\"meetingID\": '1', \"userID\": \"1\", \"content\": \"HIIIII\"}";
        msg = target.path("messages").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test_01), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        System.out.println("MSG_01: " + output);

        //A message with incomplete data
        String test_03 = "{\"userID\": \"1\", \"content\": \"HIIIII\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test_03), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert  output.equals("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>");
        System.out.println("MSG_03: " + output);

        String test_04 = "{\"meetingID\": '56', \"userID\": \"1\", \"content\": \"HIIIII\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test_04), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert  output.equals("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>");
        System.out.println("MSG_04: " + output);
    }
}
