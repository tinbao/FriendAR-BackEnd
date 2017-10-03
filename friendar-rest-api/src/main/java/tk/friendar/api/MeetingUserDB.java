package tk.friendar.api;

//import org.hibernate.annotations.Table;
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

    public void setMeetingID(MeetingDB meetingID) {
        this.meetingID = meetingID;
    }

    public UserDB getUserID() {
        return userID;
    }

    public void setUserID(UserDB userID) {
        this.userID = userID;
    }

}