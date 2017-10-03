package tk.friendar.api;

import org.hibernate.Session;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "meetings")
public class MeetingDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meetingid", nullable = false)
    private int meetingID; //not null

    private String meetingName;
    private Timestamp timeDate;

    @OneToMany (targetEntity = MeetingUserDB.class, mappedBy = "meetingID", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	public Collection<MeetingUserDB> meetingUsers = new ArrayList<MeetingUserDB>();

    @OneToMany (targetEntity = MessageDB.class, mappedBy = "meeting", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    public Collection<MessageDB> messages = new ArrayList<MessageDB>();

    @ManyToOne
    @JoinColumn(name = "placeid")
    PlaceDB place;

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

	public Collection<MeetingUserDB> getMeetingUsers() {
		return meetingUsers;
	}

	public void setMeetingUsers(ArrayList<MeetingUserDB> meetingUsers) {
		this.meetingUsers = meetingUsers;
	}

    public void setPlace(int placeID){
        try (Session session = SessionFactorySingleton.getInstance().openSession()) {
            this.place = session.get(PlaceDB.class,placeID);
        }
    }
    public PlaceDB getPlace(){
        return this.place;
    }
}