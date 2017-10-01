package tk.friendar.api;

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
    private MeetingDB meetingID; //not null

    @ManyToOne
    @JoinColumn(name="userid")
    private UserDB userID; //not null

    private String content;
    private Timestamp timeSent;

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public MeetingDB getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(MeetingDB meetingID) {
        this.meetingID = meetingID;
    }

    public UserDB getUserID() {
        return userID;
    }

    public void setUserID(UserDB userID) {
        this.userID = userID;
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
}