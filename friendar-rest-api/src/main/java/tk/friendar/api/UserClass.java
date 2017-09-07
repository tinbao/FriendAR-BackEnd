package tk.friendar.api;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import java.io.Serializable;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserClass implements Serializable {
 
	public UserClass() {
		
	};
 
	public UserClass(String email, String Password){
		this.email = email;
		this.userName = email;
		this.userPassword = Password;
 
	};
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userName, email, userPassword;
	private String fullName;
	
	private int userID;
	private double latitude, longtitude;
	
	public int getUserID(){
		return userID;
	}
	
	public String getName(){
		return fullName;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public int getPassword(){
		return this.userPassword;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	
	
}