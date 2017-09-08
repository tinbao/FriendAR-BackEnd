package tk.friendar.api;

import java.io.IOException;
import java.net.URI;

import java.io.Serializable;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeetingUserClass implements Serializable {
	private int meetingUserID, //not null
				meetingID, //not null
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