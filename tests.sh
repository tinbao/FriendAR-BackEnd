#!/bin/bash
set -ex
set -o pipefail

docker-compose down # ensure we are fresh and clean.
docker-compose build --force-rm --no-cache --pull | grep -v 'Download.* http'
docker-compose run web sh -c "mvn clean test && mvn pmd:cpd-check && mvn pmd:check"
