package tk.friendar.api;

//import org.hibernate.annotations.Table;
import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meetingusers")
public class MeetingUserDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meetinguserid", nullable=false)
    private int meetingUserID; //not null

    @ManyToOne
    @JoinColumn(name="meetingid")
    private MeetingDB meetingID; //not null

    @ManyToOne
    @JoinColumn(name="userid")
    private UserDB userID; // not nulll*/

    public int getMeetingUserID() {
        return meetingUserID;
    }

    public void setMeetingUserID(int meetingUserID) {
        this.meetingUserID = meetingUserID;
    }

    public MeetingDB getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {

        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            this.meetingID = session.get(MeetingDB.class,meetingID);
        }
    }

    public UserDB getUserID() {
        return userID;
    }

    public void setUserID(int userID)
    {
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            this.userID = session.get(UserDB.class,userID);
        }
    }

    JSONObject toJson(Boolean nextLevelDeep) throws JSONException {
        JSONObject messageJson = new JSONObject();
        messageJson.put("meetingUserID", this.getMeetingUserID());
        if(nextLevelDeep){
            messageJson.put("meeting", this.getMeetingID().toJson(false).toString());
        }
        messageJson.put("user", this.getUserID().toJson(false).toString());
        return messageJson;

    }

}