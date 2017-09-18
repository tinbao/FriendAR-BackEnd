package tk.friendar.api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class FriendshipDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int friendshipID; //not null
           // userA_ID, //not null
           // userB_ID; //not null

    public int getFriendshipID() {
        return friendshipID;
    }

    public void setFriendshipID(int friendshipID) {
        this.friendshipID = friendshipID;
    }

    /*public int getUserA_ID() {
        return userA_ID;
    }

    public void setUserA_ID(int userA_ID) {
        this.userA_ID = userA_ID;
    }

    public int getUserB_ID() {
        return userB_ID;
    }

    public void setUserB_ID(int userB_ID) {
        this.userB_ID = userB_ID;
    }*/

}