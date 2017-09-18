package tk.friendar.api;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
public class MeetingDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingID; //not null
    private String meetingName;
    private Timestamp timeDate;
	
	@OneToMany
	private ArrayList<MeetingUserDB> meetingUsers = new ArrayList<MeetingUserDB>();

    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    public Timestamp getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(Timestamp timeDate) {
        this.timeDate = timeDate;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

	public ArrayList<MeetingUserDB> getMeetingUsers() {
		return meetingUsers;
	}

	public void setMeetingUsers(ArrayList<MeetingUserDB> meetingUsers) {
		this.meetingUsers = meetingUsers;
	}
}