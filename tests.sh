#!/bin/bash
set -ex
set -o pipefail

docker-compose down # ensure we are fresh and clean.
docker-compose build --force-rm --no-cache --pull | grep -v 'Download.* http'
docker-compose run web sh -c "mvn pmd:cpd-check && mvn pmd:check" | grep -v 'Download.* http'

docker-compose down # ensure we are fresh and clean.
docker-compose run web sh -c "mvn clean test -Dtest=ChatEndpointTest"  2>/dev/null
docker-compose down # ensure we are fresh and clean.
docker-compose run web sh -c "mvn clean test -Dtest=FriendshipEndpointTest" 2>/dev/null
docker-compose down # ensure we are fresh and clean.
docker-compose run web sh -c "mvn clean test -Dtest=MeetingEndpointTest" 2>/dev/null
docker-compose down # ensure we are fresh and clean.
docker-compose run web sh -c "mvn clean test -Dtest=MeetingUserEndpointTest" 2>/dev/null
docker-compose down # ensure we are fresh and clean.
docker-compose run web sh -c "mvn clean test -Dtest=PlacesEndpointTest" 2>/dev/null
docker-compose down # ensure we are fresh and clean.
docker-compose run web sh -c "mvn clean test -Dtest=UsersEndpointTest" 2>/dev/null
docker-compose down # ensure we are fresh and clean for next time