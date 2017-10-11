package tk.friendar.api;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friendships")
public class FriendshipDB implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendshipid", nullable = false)
    private int friendshipID; //not null

    @ManyToOne
    @JoinColumn(name="usera_id")
    private UserDB userA_ID; //not null

    @ManyToOne
    @JoinColumn(name = "userB_id")
    private UserDB userB_ID;


    public int getFriendshipID() {
        return friendshipID;
    }

    public void setFriendshipID(int friendshipID) {
        this.friendshipID = friendshipID;
    }


    public UserDB getUserA_ID() {
        return userA_ID;
    }

    public void setUserA_ID(UserDB userA_ID) {
        this.userA_ID = userA_ID;
    }


    public UserDB getUserB_ID() {
        return userB_ID;
    }

    public void setUserB_ID(UserDB userB_ID) {
        this.userB_ID = userB_ID;
    }

    JSONObject toJson() throws JSONException {
        JSONObject friendshipJSON = new JSONObject();
        friendshipJSON.put("id", this.getFriendshipID());
        friendshipJSON.put("userA_ID", this.getUserA_ID());
        friendshipJSON.put("userB_ID", this.getUserB_ID());
        return friendshipJSON;

    }
}