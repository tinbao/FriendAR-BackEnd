#!/bin/bash
set -e # Exit with nonzero exit code if anything fails
set -o

ssh -V
apt-get update -y -qq && apt-get install openssh-server -y -qq
ssh -V
ssh -i ssh_key -o StrictHostKeyChecking=no -fnNT -L $(pwd)/docker.sock:/var/run/docker.sock
export DOCKER_HOST=$(pwd)/docker.sock
docker-compose build --pull --no-cache --force-rm && docker-compose up --remove-orphans -d
