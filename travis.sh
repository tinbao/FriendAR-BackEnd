#!/bin/bash
set -ex
docker run -it --rm --user="$(id -u):$(id -g)" -v "$(pwd)/friendar-rest-api":/usr/src/mymaven -w /usr/src/mymaven maven:alpine mvn clean test
