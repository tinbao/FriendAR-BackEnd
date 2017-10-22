#!/bin/bash
set -ex
set -o pipefail

runTest(){
	docker-compose down # ensure we are fresh and clean.
	docker-compose run -T web sh -c "$1" | grep -v 'Download.* http' | tee out.txt || true; grep -q "BUILD SUCCESS" out.txt; rm out.txt

	# ensure we are fresh and clean.
	docker-compose rm -v
	docker-compose down
}

docker-compose down # ensure we are fresh and clean.
docker-compose build | grep -v 'Download.* http'
runTest "mvn pmd:cpd-check && mvn pmd:check"
runTest "mvn clean test -Dtest=ChatEndpointTest"
runTest "mvn clean test -Dtest=FriendshipEndpointTest"
runTest "mvn clean test -Dtest=MeetingEndpointTest"
runTest "mvn clean test -Dtest=MeetingUserEndpointTest"
runTest "mvn clean test -Dtest=PlacesEndpointTest"
runTest "mvn clean test -Dtest=UsersEndpointTest"
