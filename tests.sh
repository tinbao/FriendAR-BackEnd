#!/bin/bash
set -ex
set -o pipefail
mvn(){
	docker run -it --rm -v "$(pwd)/friendar-rest-api":/usr/src/mymaven -w /usr/src/mymaven maven:alpine mvn -B "$@" | grep -v 'Download.* http'
}
docker-compose down # ensure we are fresh and clean.
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up --build -d db
mvn clean test
docker-compose down
docker-compose build --force-rm --no-cache --pull | grep -v 'Download.* http'
mvn pmd:check
mvn pmd:cpd-check
