package tk.friendar.api;

import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.io.Serializable;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeetingClass implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int meetingID, placeID; //not null
	private String meetingName;
	private Timestamp timeDate;
	
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
	public int getPlaceID() {
		return placeID;
	}
	public void setPlaceID(int placeID) {
		this.placeID = placeID;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
}