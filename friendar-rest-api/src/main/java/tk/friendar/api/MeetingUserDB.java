package tk.friendar.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class MeetingUserDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingUserID; //not null

    private int meetingID, //not null
            userID; // not nulll

    public int getMeetingUserID() {
        return meetingUserID;
    }

    public void setMeetingUserID(int meetingUserID) {
        this.meetingUserID = meetingUserID;
    }

    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}