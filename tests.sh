#!/bin/bash
set -ex
set -o pipefail

docker-compose down # ensure we are fresh and clean.
docker-compose build --force-rm --no-cache --pull

docker-compose run -T web sh -c "mvn pmd:cpd-check && mvn pmd:check"

docker-compose down # ensure we are fresh and clean.
docker-compose rm -v
docker-compose run web sh -c "mvn clean test -Dtest=ChatEndpointTest"
docker-compose down # ensure we are fresh and clean.
docker-compose rm -v
docker-compose run web sh -c "mvn clean test -Dtest=FriendshipEndpointTest"
docker-compose down # ensure we are fresh and clean.
docker-compose rm -v
docker-compose run web sh -c "mvn clean test -Dtest=MeetingEndpointTest"
docker-compose down # ensure we are fresh and clean.
docker-compose rm -v
docker-compose run web sh -c "mvn clean test -Dtest=MeetingUserEndpointTest"
docker-compose down # ensure we are fresh and clean.
docker-compose rm -v
docker-compose run web sh -c "mvn clean test -Dtest=PlacesEndpointTest"
docker-compose down # ensure we are fresh and clean.
docker-compose rm -v
docker-compose run web sh -c "mvn clean test -Dtest=UsersEndpointTest"
docker-compose down # ensure we are fresh and clean.
docker-compose rm -v
