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
public class UnitTests {

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


    /******************************* @POST tests *******************************/
    @Test
    public void A_testUserEndpointPost(){
        String test, output;
        Response msg;

        /**** User Posts ****/

        //Complete data with lat or long
        test = "{\"username\": \"Luca@gmail.com\", \"email\": \"luca@gmail.com\", \"usersPassword\": \"harris\",\"fullName\":\"Luca Harris\", \"latitude\": 120, \"longitude\": 120}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("username".toLowerCase()) != -1);

        //Complete data without lat or long
        test = "{\"username\": \"tin@gmail.com\", \"email\": \"matt@gmail.com\", \"usersPassword\": \"bao\",\"fullName\":\"Tin Bao\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("username".toLowerCase()) != -1);

        //Another Complete data without lat or long
        test = "{\"username\": \"Gold@gmail.com\", \"email\": \"matt@gmail.com\", \"usersPassword\": \"Stone\",\"fullName\":\"Tin Bao\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("username".toLowerCase()) != -1);

        //Another valid Post
        test = "{\"username\": \"james\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\"Simon\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);

        test = "{\"username\": \"james\", \"email\": \"user01@yahoo.com\", \"usersPassword\": \"user01\",\"fullName\":\"User 01\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);

        test = "{\"username\": \"lucaM@gmail.com\", \"email\": \"lucaM@gmail.com\", \"usersPassword\": \"Morandini\",\"fullName\":\"Luca Morandini\"}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);

        /**** Incomplete data ****/
        test = "{\"username\": \"Ashkan Habibi\", \"email\": \"asf@gsddf.com\",\"fullName\":\'Simon\', \"latitude\": 123, \"longitude\": 123}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("org.json.JSONException: JSONObject[\"usersPassword\"] not found.".toLowerCase()) != -1);

        test = "{\"username\": \"Tin\", \"usersPassword\": \"Bao\",\"fullName\":\'ashkan Habibi\'}";
        msg = target.path("users").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.equals("org.json.JSONException: JSONObject[\"email\"] not found.");
    }

    @Test
    public void B_testPlaceEndpointPoint(){
        Response msg;
        String output, test;

        /**** Complete Data ****/
        test = "{\"latitude\":678,\"placeName\":\"Melbourne Central\",\"longitude\":968}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);

        test = "{\"latitude\":256,\"placeName\":\"Etihad Stadium\",\"longitude\":1024}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        System.out.println("MSG: " + output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);

        test = "{\"latitude\":128,\"placeName\":\"AAMI Park Stadium\",\"longitude\":2048}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);

        test = "{\"latitude\":12,\"placeName\":\"University of Melbourne\",\"longitude\":24}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);

        test = "{\"latitude\":10,\"placeName\":\"Swanston Maccas\",\"longitude\":35}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);

        /**** Incomplete data ****/
        test = "{\"placeName\":\"Etihad Stadium\",\"longitude\":123}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));

        test = "{\"placeName\":\"Etihad Stadium\",\"latitude\":123}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));

        test = "{\"latitude\":\"12\",\"longitude\":123}";
        msg = target.path("places").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }

    @Test
    public void C_testMeetingPost(){
        String test, output;
        Response msg;

        /**** complete data ****/
        test = "{\"meetingName\": 'Unimelb meeting', \"placeID\": \"3\", \"time\": \"2016-02-03 00:00:00.0\"}";
        msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);

        test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"1 \", \"time\": \"2017-12-12 00:00:00.0\"}";
        msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);

        /**** Incomplete data ****/
        test = "{\"meetingName\": 'Graduation meeting', \"time\": \"2017-12-12 00:00:00.0\"}";
        msg = target.path("meetings").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("org.json.JSONException: JSONObject[\"placeID\"] not found.".toLowerCase()) != -1);
    }

    @Test
    public void D_testMeetingUserPost(){
        String test, output;
        Response msg;

        /**** Complete data ****/
        test = "{\"meetingID\": 1, \"userID\": 1}";
        msg = target.path("meetingusers").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);

        test = "{\"meetingID\": 1, \"userID\": 3}";
        msg = target.path("meetingusers").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);

        /**** Incomplete data ****/
        test = "{\"meetingID\": 45}";
        msg = target.path("meetingusers").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("org.json.JSONException: JSONObject[\"userID\"] not found.".toLowerCase()) != -1);
    }

    @Test
    public void E_testChatEndpointPost(){
        String test, output;
        Response msg;

        /**** Complete data ****/
        test = "{\"meetingID\": '1', \"userID\": \"1\", \"content\": \"HIIIII\"}";
        msg = target.path("messages").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("messageID".toLowerCase()) != -1);

        test = "{\"meetingID\": '1', \"userID\": \"3\", \"content\": \"IDK :|\"}";
        msg = target.path("messages").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("messageID".toLowerCase()) != -1);

        /**** Incomplete data ****/
        test = "{\"meetingID\": '3', \"content\": \"ok :)\"}";
        msg = target.path("messages").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }

    @Test
    public void F_testFriendshipPost(){
        String test, output;
        Response msg;

        /**** Complete data ****/
        test = "{\"userA_ID\": 1, \"userB_ID\": 2}";
        msg = target.path("friendships").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("id".toLowerCase()) != -1);

        test = "{\"userA_ID\": 1, \"userB_ID\": 3}";
        msg = target.path("friendships").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("id".toLowerCase()) != -1);

        /**** Incomplete data ****/
        test = "{\"userB_ID\": 4}";
        msg = target.path("friendships").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(test), Response.class);
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("org.json.JSONException: JSONObject[\"userA_ID\"] not found.".toLowerCase()) != -1);
    }


    /******************************* @GET tests *******************************/

    @Test
    public void G_testUserGet(){

        //getting entire list of users in form of a string
        String allUsers = target.path("users").request().get(String.class);
        assertNotNull(allUsers);
        assert allUsers.toLowerCase().contains("users: ".toLowerCase());

        //getting a specific user in form of a string
        String user = target.path("users").path("1").request().get(String.class);
        assertNotNull(user);
        assert user.toLowerCase().contains("fullName".toLowerCase());

        //getting a specific user that doesn't exist
        try{
            String userInvalid = target.path("users").path("30").request().get(String.class);
            assertNotNull(userInvalid);
            assert (userInvalid.equalsIgnoreCase("java.lang.NullPointerException"));
        } catch (Exception e){

        }
    }

    @Test
    public void H_testPlaceGet(){

        //getting entire list of places in form of a string
        String responseMsg = target.path("places").request().get(String.class);
        assertNotNull(responseMsg);
        assert responseMsg.toLowerCase().contains("places: ".toLowerCase());

        //getting a specific entry list of places in form of a string
        responseMsg = target.path("places").path("1").request().get(String.class);
        assertNotNull(responseMsg);
        assert responseMsg.toLowerCase().contains("placeName".toLowerCase());

        //getting a specific entry list of places that doesn't exist
        try{
            responseMsg = target.path("places").path("30").request().get(String.class);
        } catch (Exception e){
            assert e.toString().equalsIgnoreCase("java.lang.NullPointerException");
        }
    }

    @Test
    public void I_testMeetingGet(){

        //getting entire list of meetings in form of a string
        String allmeetings = target.path("meetings").request().get(String.class);
        assertNotNull(allmeetings);
        assert allmeetings.toLowerCase().contains("meetings: ".toLowerCase());

        //getting a specific meeting in form of a string
        String meetings = target.path("meetings").path("1").request().get(String.class);
        assertNotNull(meetings);
        assert (meetings.toLowerCase().indexOf("place".toLowerCase()) != -1);

        //getting a specific meeting that doesn't exist
        try {
            meetings = target.path("meetings").path("30").request().get(String.class);
            assertNotNull(meetings);
            assert (meetings.equalsIgnoreCase("java.lang.NullPointerException"));
        } catch (Exception e) {
            assert e.toString().equalsIgnoreCase("java.lang.NullPointerException");
        }
    }

    @Test
    public void J_testMeetingUserGet(){

        //getting entire list of meeting users in form of a string
        String allMeetingUsers = target.path("meetingusers").request().get(String.class);
        assertNotNull(allMeetingUsers);
        assert allMeetingUsers.toLowerCase().contains("meetingUsers: ".toLowerCase());

        //getting a specific meeting user in form of a string
        String meetingUser = target.path("meetingusers").path("1").request().get(String.class);
        assertNotNull(meetingUser);
        assert (meetingUser.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);

        //getting a specific user that doesn't exist
        try {
            //getting a specific user in form of a string
            String invalidMeetingUser = target.path("meetingusers").path("45").request().get(String.class);
            assertNotNull(invalidMeetingUser);
            assert (invalidMeetingUser.toLowerCase().indexOf("java.lang.NullPointerException".toLowerCase()) != -1);
        } catch (Exception e) {
            assert e.toString().equalsIgnoreCase("java.lang.NullPointerException");
        }
    }

    @Test
    public void K_testMessagesGet(){
        //getting entire list of places in form of a string
        String responseMsg = target.path("messages").request().get(String.class);
        assertNotNull(responseMsg);
        assert responseMsg.toLowerCase().contains("messages: ");



        //getting a specific entry list of places in form of a string
        responseMsg = target.path("messages").path("1").request().get(String.class);
        assertNotNull(responseMsg);
        assert (responseMsg.toLowerCase().indexOf("messageID".toLowerCase()) != -1);

    }

    @Test
    public void L_testFriendshipGet(){

        //getting entire list of frienships in form of a string
        String allMeetingUsers = target.path("friendships").request().get(String.class);
        assertNotNull(allMeetingUsers);
        assert allMeetingUsers.toLowerCase().contains("friendships: ".toLowerCase());

        //getting a specific friendship in form of a string
        String friendship = target.path("friendships").path("1").request().get(String.class);
        assertNotNull(friendship);
        assert (friendship.toLowerCase().indexOf("id".toLowerCase()) != -1);

        //getting a specific friendship that doesn't exist
        try {
            //getting a specific user in form of a string
            String invalidMeetingUser = target.path("friendships").path("45").request().get(String.class);
            assertNotNull(invalidMeetingUser);
            assert (invalidMeetingUser.toLowerCase().indexOf("java.lang.NullPointerException".toLowerCase()) != -1);
        } catch (Exception e) {
            assert e.toString().equalsIgnoreCase("java.lang.NullPointerException");
        }
    }

    /******************************* @PUT tests *******************************/

    @Test
    public void M_testUserPut(){

        //updating an existing entry
        String test = "{\"latitude\": 465, \"longitude\": 123}";
        Response msg = target.path("users").path("2").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("username".toLowerCase()) != -1);

        //updating a all fields entry
        test = "{\"username\": \"james\", \"email\": \"asf.com\", \"usersPassword\": \"stone\",\"fullName\":\'Simon\', \"latitude\": 123, \"longitude\": 123}";
        msg = target.path("users").path("3").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);

        //updating a non-existing entry
        test = "{\"username\": \"james\", \"email\": \"asf@gsddf.com\", \"usersPassword\": \"stone\",\"fullName\":\'Simon\', \"latitude\": 123, \"longitude\": 123}";
        msg = target.path("users").path("20").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("java.lang.NullPointerException"));
    }

    @Test
    public void N_testPlacesPut(){

        Response msg;
        String output;

        //updating an existing entry
        String test = "{\"latitude\":678,\"placeName\":\"Melbourne Central\",\"longitude\":10}";
        msg = target.path("places").path("1").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("placeName".toLowerCase());

        //updating an existing entry, just lat
        test = "{\"longitude\":678}";
        msg = target.path("places").path("2").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert output.toLowerCase().contains("placeName".toLowerCase());

        //updating an existing entry
        test = "{\"longitude\":678}";
        msg = target.path("places").path("30").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("java.lang.IllegalArgumentException") || output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }

    @Test
    public void O_testMeetingPut(){

        //updating an existing entry
        String test = "{\"time\": \"2016-12-12 00:00:00.0\"}";
        Response msg = target.path("meetings").path("1").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        String output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);

        //updating a all fields of an entry
        test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"3\", \"time\": \"2017-12-12 00:00:00.0\"}";
        msg = target.path("meetings").path("2").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("place".toLowerCase()) != -1);

        //updating a non-existing entry
        test = "{\"meetingName\": 'Graduation meeting', \"placeID\": \"6\", \"time\": \"2017-12-12 00:00:00.0\"}";
        msg = target.path("meeting").path("20").request().accept(MediaType.APPLICATION_JSON).put(Entity.json(test));
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase(""));
    }

    /******************************* @DELETE tests *******************************/

    @Test
    public void P_testMeetingUserDelete(){

        Response msg;
        String output;
        //Deleting an existing meeting user
        msg = target.path("meetingusers").path("2").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("meetingUserID".toLowerCase()) != -1);

        //Deleting an invalid meeting user
        msg = target.path("meetingusers").path("45").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));

    }

    @Test
    public void Q_testFriendshipDelete(){
        Response msg;
        String output;
        //Deleting an existing friendship
        msg = target.path("friendships").path("2").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("id".toLowerCase()) != -1);

        //Deleting an invalid friendship
        msg = target.path("friendships").path("45").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }

    @Test
    public void R_testPlaceDelete(){
        Response msg;
        String output;
        //Deleting an exisiting place
        msg = target.path("places").path("5").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("placeName".toLowerCase()) != -1);

        //Deleting an exisiting place
        msg = target.path("places").path("15").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }

    @Test
    public void S_testUserDelete(){

        Response msg;
        String output;
        //Deleting an existing place
        msg = target.path("users").path("5").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("username".toLowerCase()) != -1);

        msg = target.path("users").path("4").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.toLowerCase().indexOf("username".toLowerCase()) != -1);

        //Deleting a non-existing place
        msg = target.path("users").path("60").request().accept(MediaType.APPLICATION_JSON).delete();
        output = msg.readEntity(String.class);
        assertNotNull(msg);
        assertNotNull(output);
        assert (output.equalsIgnoreCase("<html><head><title>Grizzly 2.4.0</title><style><!--div.header {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#003300;font-size:22px;-moz-border-radius-topleft: 10px;border-top-left-radius: 10px;-moz-border-radius-topright: 10px;border-top-right-radius: 10px;padding-left: 5px}div.body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:#FFFFCC;font-size:16px;padding-top:10px;padding-bottom:10px;padding-left:10px}div.footer {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#666633;font-size:14px;-moz-border-radius-bottomleft: 10px;border-bottom-left-radius: 10px;-moz-border-radius-bottomright: 10px;border-bottom-right-radius: 10px;padding-left: 5px}BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;}B {font-family:Tahoma,Arial,sans-serif;color:black;}A {color : black;}HR {color : #999966;}--></style> </head><body><div class=\"header\">Request failed.</div><div class=\"body\">Request failed.</div><div class=\"footer\">Grizzly 2.4.0</div></body></html>"));
    }
}
