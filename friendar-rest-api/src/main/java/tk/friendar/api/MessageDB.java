package tk.friendar.api;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class MessageDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageid", nullable=false)
    private int messageID; //not null

    @ManyToOne
    @JoinColumn(name="meetingID")
    private MeetingDB meeting; //not null

    @ManyToOne
    @JoinColumn(name="userid")
    private UserDB user; //not null

    private String content;
    private Timestamp timeSent;

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public MeetingDB getMeeting() {
        return meeting;
    }

    public void setMeeting(int meetingID) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            this.meeting = session.get(MeetingDB.class,meetingID);
        }
    }

    public UserDB getUser() {
        return user;
    }

    public void setUser(int userID) {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            this.user = session.get(UserDB.class,userID);
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Timestamp timeSent) {
        this.timeSent = timeSent;
    }

    JSONObject toJson(Boolean nextLevelDeep) throws JSONException {
        JSONObject messageJson = new JSONObject();
        messageJson.put("messageID", this.getMessageID());
        messageJson.put("content", this.getContent());
        messageJson.put("meeting", this.getMeeting());
        messageJson.put("time sent", this.getTimeSent());
        messageJson.put("user", this.getUser());
        return messageJson;

    }
}