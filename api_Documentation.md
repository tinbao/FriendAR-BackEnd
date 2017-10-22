# API Documentation

## User:

GET /users

    By passing in the appropriate id, you can search for a user in the database.

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an optional id to look up user

    Responses:

            200: search results matching criteria

                    {userID: Int, fullName: String, username: String, email: String, latitude&quot;: double, longitude: double, lastUpdatedLocation: Timestamp}

            400: bad input parameter

            500: Request Failed

Post /users

    Creates a new user in the database

    Consumes: Application/JSON

    Produces: Application/JSON
    
            Name:              Description:

            Id (String)        pass an optional id to look up user

    Responses:

            201: user made

                    {userID: Int, fullName: String, username: String, email: String, latitude: double, longitude: double, lastUpdatedLocation: Timestamp}

            400: bad input parameter

            409: User already exists

            500: Request Failed

PUT /users

    Updates a user in the database

    Consumes: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an id to look up user

    Responses:

            200: user updated.

            400: bad input parameter

            500: Request Failed

DELETE /users

        Deletes a user in the database

        Produces: Application/JSON

        Parameters:

                Name:              Description:

                Id (String)        pass an id to delete user

        Responses:

                200: user deleted

                        {userID: Int, fullName: String, username: String, email: String, latitude: double, longitude: double, lastUpdatedLocation: Timestamp}

                400: bad input parameter

                500: Request Failed


                

                
## Places:

GET /places

    By passing in the appropriate id, you can search for a place/landmark in the database.

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an optional id to look up landmark

    Responses:

            200: search results matching criteria

                {placeID: Int, placeName: String, latitude: double, longitude:double}

            400: bad input parameter

            500: Request Failed

Post /places

    Creates a new landmark in the database

    Consumes: Application/JSON

    Produces: Application/JSON
    
    Parameters:
    
            Name:              Description:

            Id (String)        pass an optional id to look up landmark

    Responses:

            201: place saved

                {placeID: Int, placeName: String, latitude: double, longitude:double}

            400: bad input parameter

            409: Place already exists

            500: Request Failed

PUT /places

    Updates a place/landmark in the database

    Consumes: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an id to look up place

    Responses:

            200: search results matching criteria

            400: bad input parameter

            500: Request Failed

DELETE /places

        Deletes a landmark/place in the database

        Produces: Application/JSON

        Parameters:

                Name:              Description:

                Id (String)        pass an id to delete place

        Responses:

                200: request succeeded

                    {placeID: Int, placeName: String, latitude: double, longitude:double}

                400: bad input parameter

                500: Request Failed




                
## Friendships:

GET /friendship

    By passing in the appropriate id, you can search for a friendship in the database.

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an optional id to look up a friendship

    Responses:

            200: search results matching criteria

            400: bad input parameter

            500: Request Failed

Post /friendships

    Creates a new friendship in the database

    Consumes: Application/JSON

    Produces: Application/JSON
    
    Parameters:
    
            Name:              Description:

            Id (String)        pass an optional id to look up a friendship

    Responses:

            201: friendship saved

            400: bad input parameter

            500: Request Failed




DELETE /friendships

    Deletes a friendship in the database

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an id to delete friendship

    Responses:

            200: request succeeded

            400: bad input parameter

            500: Request Failed
            
            



## Meetings:

GET /meetings

    By passing in the appropriate id, you can search for a meeting in the database.

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an optional id to look up a meeting

    Responses:

            200: search results matching criteria

                {meetingID: Int, placeName: String, place: Place object, latitude: double, longitude:double}

            400: bad input parameter

            500: Request Failed

Post /meetings

    Creates a new friendship in the database

    Consumes: Application/JSON

    Produces: Application/JSON
    
    Parameters:
    
            Name:              Description:

            Id (String)        pass an optional id to look up a meeting

    Responses:

            201: meeting saved

                {meetingID: Int, placeName: String, place: Place object, latitude: double, longitude:double}

            400: bad input parameter

            500: Request Failed

PUT /meetings

    Updates a meeting in the database

    Consumes: Application/JSON

    Produces: Application/JSON
    
    Parameters:
    
            Name:              Description:

            Id (String)        pass an optional id to look up a meeting

    Responses:

            200: Meeting Updated

            400: bad input parameter

            500: Request Failed

DELETE /meetings

    Deletes a friendship in the database

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an id to delete meeting

    Responses:

            200: request succeeded

            400: bad input parameter

            500: Request Failed



## MeetingUsers:

GET /meetingusers

    By passing in the appropriate id, you can search for a user in a meeting in the database.

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an optional id to look up a user in a meeting

    Responses:

            200: search results matching criteria

            400: bad input parameter

                    500: Request Failed

Post /meetingusers

    Creates a new user in a meeting in the database

    Consumes: Application/JSON

    Produces: Application/JSON
    
    Parameters:
    
            Name:              Description:

            Id (String)        pass an optional id to look up user in a meeting

    Responses:

            201: meetinguser saved

            400: bad input parameter

            409: meetinguser already exists

            500: Request Failed







DELETE /meetingusers

    Deletes a user from a meeting in the database

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an id to delete user from a meeting

    Responses:

            200: request succeeded

            400: bad input parameter

            500: Request Failed



## Messages:

GET /messages

    By passing in the appropriate id, you can search for a message sent by a user in the database.

    Produces: Application/JSON

    Parameters:

            Name:              Description:

            Id (String)        pass an optional id to look up message

    Responses:

            200: search results matching criteria

            400: bad input parameter

            500: Request Failed

Post /messages

    Creates a new message in the database

    Consumes: Application/JSON

    Produces: Application/JSON
    
    Parameters:
    
            Name:              Description:

            Id (String)        pass an optional id to look up a message

    Responses:

            201: message saved

            {messageID: Int, content: String, user: user object, timeSent: TimeStamp}

            400: bad input parameter

            500: Request Failed
