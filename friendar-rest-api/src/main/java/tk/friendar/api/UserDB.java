package tk.friendar.api;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.commons.codec.binary.Base64;

@Entity
@Table(name = "users")
public class UserDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID; //not null
    private String fullName,
            usersname, //not null
            usersPassword, //not null
            email; //not null
    private double latitude, longitude;
    private Timestamp locationLastUpdated;
    
	private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;
	private static byte[] salt;
	private static char[] passChar;
	
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

	public String getUsersPassword() {
		return usersPassword;
	}

	public void setUsersPassword(String usersPassword) throws Exception{
		this.usersPassword = setUserPassword(usersPassword);
	}
	
	//to be uncommented when deploying authentication
	
    public boolean validPassword(String password) throws Exception{
        return usersPassword.matches(checkPassword(password, salt));
    }
	
	private String checkPassword(String usersPassword, byte[] salt) throws Exception{
		// TODO Auto-generated method stub
		passChar = usersPassword.toCharArray();
		return String.valueOf(hashPas(passChar, salt, iterations, desiredKeyLen));
	}

	private String setUserPassword(String password) throws Exception {
		if(password == null || password.length() == 0){
			throw new IllegalArgumentException("Empty passwords are not supported.");
		}
		passChar = password.toCharArray();
		salt = createSalt(salt, saltLen);
		return String.valueOf(hashPas(passChar, salt, iterations, desiredKeyLen));
	}
	
	private static byte[] createSalt(byte[] emptySalt, int saltLen)throws Exception{
		emptySalt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
		return emptySalt;
	}
	
	private static char[] hashPas(char[] password, byte[] salt, int iterationNum, int keyLen)throws Exception{
		try {
           SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
           PBEKeySpec spec = new PBEKeySpec( password, salt, iterationNum, keyLen );
           SecretKey key = skf.generateSecret( spec );
            return Base64.encodeBase64String(key.getEncoded()).toCharArray();
 
       } catch( NoSuchAlgorithmException | InvalidKeySpecException e) {
           throw new RuntimeException( e );
       }
	}
}