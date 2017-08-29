CREATE TABLE User(
    userID  TEXT PRIMARY KEY NOT NULL,
    fullName TEXT,
    username TEXT NOT NULL,
    userPassword TEXT NOT NULL,
    salt TEXT NOT NULL,
    email TEXT NOT NULL,
    lat DECIMAL,
    long DECIMAL,
    locationLastUpdated TIMESTAMP NOT NULL
);

CREATE TABLE Place(
    placeID TEXT PRIMARY KEY NOT NULL,
    placeName TEXT,
    lat DECIMAL,
    long DECIMAL
);

CREATE TABLE Meeting(
    meetingID TEXT NOT NULL,
    placeID TEXT REFERENCES Place (placeID) NOT NULL,
    meetingName TEXT,
    timeDate TIMESTAMP NOT NULL
    PRIMARY KEY (meetingID, placeID)
);

CREATE TABLE MeetingUser(
    meetingUserID TEXT NOT NULL,
    meetingID TEXT REFERENCES Meeting (meetingID) NOT NULL,
    userID TEXT REFERENCES [User] (userID) NOT NULL,
    PRIMARY KEY (meetingUserID, meetingID, userID)
);

CREATE TABLE Friendship(
    friendshipID TEXT NOT NULL,
    userA_ID TEXT REFERENCES [User] (userID) NOT NULL,
    userB_ID TEXT REFERENCES [User] (userID) NOT NULL,
    PRIMARY KEY(friendshipID, userA_ID, userB_ID)
);


CREATE TABLE Mesage(
    messageID TEXT NOT NULL,
    meetingID TEXT REFERENCES [User] (userID) NOT NULL,
    userID TEXT REFERENCES [User] (userID) NOT NULL,
    PRIMARY KEY(friendshipID, meetingID, userID)
);
