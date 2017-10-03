package tk.friendar.api;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
@Table(name = "users")
public class UserDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", nullable=false)
    public int userID; //not null

    public String fullName,
            usersname, //not null
            usersPassword, //not null
            email; //not null
    private double latitude, longitude;
    private Timestamp locationLastUpdated;

    private static final int iterations = 5;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;
    private byte[] salt;
    private static char[] passChar;

    @OneToMany (targetEntity = FriendshipDB.class, mappedBy = "userA_ID", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    //@OneToMany (targetEntity = FriendshipDB.class, mappedBy = "userB_ID", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    public List<FriendshipDB> friends = new ArrayList<>();

    @OneToMany (targetEntity = MeetingUserDB.class, mappedBy = "userID", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    public Collection<MeetingUserDB> meetings = new ArrayList<MeetingUserDB>();

    @OneToMany (targetEntity = MessageDB.class, mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    public Collection<MessageDB> messages = new ArrayList<MessageDB>();

    public int getUserID() {
        return userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsersname() {
        return usersname;
    }

    public void setUsersname(String userName) {
        this.usersname = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getLocationLastUpdated() {
        return locationLastUpdated;
    }

    public void setLocationLastUpdated(Timestamp locationLastUpdated) {
        this.locationLastUpdated = locationLastUpdated;
    }

    public List<UserDB> getFriends() {
        List<UserDB> friends = new ArrayList<>();
        for (FriendshipDB friend :this.friends){
            friends.add(friend.getUserB_ID());
        }
        return friends;
    }
    public void setFriends(List<FriendshipDB> friends) {
        this.friends = friends;
    }

    public Collection<MeetingUserDB> getMeetings() {
        return meetings;
    }

    public void setMeetings(ArrayList<MeetingUserDB> meetingUsers) {
        this.meetings = meetingUsers;
    }

    public Collection<MessageDB> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageDB> messages) {
        this.messages = messages;
    }

    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) throws Exception{
        this.usersPassword = setUserPassword(usersPassword);
    }

    //to be uncommented when deploying authentication

    public boolean validPassword(String password) throws Exception{
        return usersPassword.matches(checkPassword(password, salt));
    }

    private String checkPassword(String usersPassword, byte[] salt) throws Exception{
        passChar = usersPassword.toCharArray();
        return String.valueOf(hashPas(passChar, salt, iterations, desiredKeyLen));
    }

    private String setUserPassword(String password) throws Exception {
        if(password == null || password.length() == 0){
            throw new IllegalArgumentException("Empty passwords are not supported.");
        }
        passChar = password.toCharArray();
        this.salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        return String.valueOf(hashPas(passChar, salt, iterations, desiredKeyLen));
    }

    private static char[] hashPas(char[] password, byte[] salt, int iterationNum, int keyLen)throws Exception{
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterationNum, keyLen );
            SecretKey key = skf.generateSecret( spec );
            return Base64.encodeBase64String(key.getEncoded()).toCharArray();

        } catch( NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException( e );
        }
    }

    JSONObject toJson(Boolean nextLevelDeep) throws JSONException {
        JSONObject userJSON = new JSONObject();
        userJSON.put("id", this.getUserID());
        userJSON.put("username", this.getUsersname());
        userJSON.put("fullName", this.getFullName());
        userJSON.put("email", this.getEmail());
        userJSON.put("latitude", this.getLatitude());
        userJSON.put("longitude", this.getLongitude());
        userJSON.put("locationLastUpdated", this.getLocationLastUpdated());
        if (nextLevelDeep) {
            for (UserDB friend : this.getFriends()) {
                userJSON.append("friends", friend.toJson(false));
            }
        }
        return userJSON;

    }
}