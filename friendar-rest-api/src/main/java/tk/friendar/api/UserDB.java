package tk.friendar.api;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class UserDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID; //not null
    private String fullName,
            usersname, //not null
            userspassword, //not null
            salt, //not null, not setter or getter yet
            email; //not null
    private double latitude, longtitude;
    private Timestamp locationLastUpdated;

    public void setSalt(String newSalt) {
        this.salt = newSalt;
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

    public String getUsersname() {
        return usersname;
    }

    public void setUsersname(String userName) {
        this.usersname = userName;
    }

    public void setUserspassword(String userPassword) {
        this.userspassword = userPassword;
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