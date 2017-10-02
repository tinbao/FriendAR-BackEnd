package tk.friendar.api;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "places")
public class PlaceDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placeid", nullable = false)
    private int placeID; //not null

    private String placeName;
    private double longitude, latitude;

    @OneToMany (targetEntity = MeetingDB.class, mappedBy = "place", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    public Collection<MeetingDB> meetings = new ArrayList<MeetingDB>();

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

	public Collection<MeetingDB> getMeetings() {
		return meetings;
	}

	public void setMeetings(ArrayList<MeetingDB> meetings) {
		this.meetings = meetings;
	}
}