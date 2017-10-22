package tk.friendar.api;

import org.json.JSONException;
import org.json.JSONObject;

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

    JSONObject toJson(Boolean nextLevelDeep) throws JSONException {
        JSONObject placeJson = new JSONObject();
        placeJson.put("id", this.getPlaceID());
        placeJson.put("placeName", this.getPlaceName());
        placeJson.put("latitude", this.getLatitude());
        placeJson.put("longitude", this.getLongitude());
        if (nextLevelDeep) {
            for (MeetingDB meeting : this.getMeetings()) {
                placeJson.append("meetings: ", meeting.toJson(nextLevelDeep));
            }
        }
        return placeJson;

    }
}