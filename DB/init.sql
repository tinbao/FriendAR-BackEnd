CREATE TABLE IF NOT EXISTS Users(
    UserID  SERIAL PRIMARY KEY NOT NULL,
    fullName TEXT,
    Usersname TEXT NOT NULL,
    UsersPassword TEXT NOT NULL,
    salt TEXT NOT NULL,
    email TEXT NOT NULL,
    latitude DECIMAL,
    longtitude DECIMAL,
    locationLastUpdated TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS Places(
    placeID SERIAL PRIMARY KEY NOT NULL,
    placeName TEXT,
    latitude DECIMAL,
    longtitude DECIMAL
);

CREATE TABLE IF NOT EXISTS Meetings(
    meetingID SERIAL PRIMARY KEY NOT NULL,
    placeID SERIAL REFERENCES Places (placeID) NOT NULL,
    meetingName TEXT,
    timeDate TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS MeetingUsers(
    meetingUserID SERIAL PRIMARY KEY NOT NULL,
    meetingID SERIAL REFERENCES Meetings (meetingID) NOT NULL,
    UserID SERIAL REFERENCES Users (UserID) NOT NULL
);

CREATE TABLE IF NOT EXISTS Friendships(
    friendshipID SERIAL PRIMARY KEY NOT NULL,
    UserA_ID SERIAL REFERENCES Users (UserID) NOT NULL,
    UserB_ID SERIAL REFERENCES Users (UserID) NOT NULL
);


CREATE TABLE IF NOT EXISTS Messages(
    messageID SERIAL PRIMARY KEY NOT NULL,
    meetingID SERIAL REFERENCES Users (UserID) NOT NULL,
    UserID SERIAL REFERENCES Users (UserID) NOT NULL
);
