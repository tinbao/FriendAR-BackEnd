package tk.friendar.api;


import java.sql.Timestamp;
import java.io.Serializable;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserClass implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID; //not null
	private String fullName,
			userName, //not null
			userPassword, //not null
			salt, //not null, not setter or getter yet
			email; //not null
	private double latitude, longtitude;
	private Timestamp locationLastUpdated;
	
	public void setSalt(String newSalt){
		this.salt = newSalt;
	}
	public string getSalt(){
		return this.salt;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public Timestamp getLocationLastUpdated() {
		return locationLastUpdated;
	}
	public void setLocationLastUpdated(Timestamp locationLastUpdated) {
		this.locationLastUpdated = locationLastUpdated;
	}
}