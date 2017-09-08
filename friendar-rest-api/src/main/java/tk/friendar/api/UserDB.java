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
    private double latitude, longitude;
    private Timestamp locationLastUpdated;

    public int getUserID() {
        return userID;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getLocationLastUpdated() {
        return locationLastUpdated;
    }

    public void setLocationLastUpdated(Timestamp locationLastUpdated) {
        this.locationLastUpdated = locationLastUpdated;
    }

    public boolean validPassword(String password) {
        return password.matches(securePasswordHash(this.userspassword, this.salt));
    }

    private String securePasswordHash(String password, String salt) {
        return null; // todo deliberately fails for now, as hasn't been implemented.
    }
}