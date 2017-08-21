#!/bin/bash
set -ex
mvn(){
	docker run -it --rm --user="$(id -u):$(id -g)" -v "$(pwd)/friendar-rest-api":/usr/src/mymaven -w /usr/src/mymaven maven:alpine mvn -B "$@" | grep -v 'Download.* http'
}
mvn pmd:check
mvn pmd:cpd-check
mvn clean test
