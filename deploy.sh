#!/bin/bash
#set -e # Exit with nonzero exit code if anything fails

ssh -i ssh_key -o StrictHostKeyChecking=no -fnNT -L $(pwd)/docker.sock:/var/run/docker.sock
export DOCKER_HOST=$(pwd)/docker.sock
docker-compose build --pull --no-cache --force-rm && docker-compose up --remove-orphans -d
