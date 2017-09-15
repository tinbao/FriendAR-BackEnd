package tk.friendar.api;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "places")
public class PlaceDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int placeID; //not null
    private String placeName;
    private double longitude, latitude;
	
	@OneToMany
	private ArrayList<MeetingDB> meetings = new ArrayList<MeetingDB>();

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

	public ArrayList<MeetingDB> getMeetings() {
		return meetings;
	}

	public void setMeetings(ArrayList<MeetingDB> meetings) {
		this.meetings = meetings;
	}
}