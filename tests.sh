#!/bin/bash
set -ex
set -o pipefail

docker-compose down # ensure we are fresh and clean.
docker-compose build --force-rm --no-cache --pull | grep -v 'Download.* http'
docker-compose run web sh -c "mvn pmd:cpd-check && mvn pmd:check"

docker-compose run web sh -c "mvn clean test -Dtest=ChatEndpointTest"
docker-compose run web sh -c "mvn clean test -Dtest=FriendshipEndpointTest"
docker-compose run web sh -c "mvn clean test -Dtest=MeetingEndpointTest"
docker-compose run web sh -c "mvn clean test -Dtest=MeetingUserEndpointTest"
docker-compose run web sh -c "mvn clean test -Dtest=PlacesEndpointTest"
docker-compose run web sh -c "mvn clean test -Dtest=UsersEndpointTest"