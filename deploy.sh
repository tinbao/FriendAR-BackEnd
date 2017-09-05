#!/bin/bash
set -e # Exit with nonzero exit code if anything fails
set -o

ssh -V
sudo apt-add-repository 'deb http://archive.ubuntu.com/ubuntu yakkety main universe multiverse' && sudo apt-get -qq update -y && sudo apt-get -qq install openssh-server -y>
ssh -V
ssh -i ssh_key -o StrictHostKeyChecking=no -fnNT -L $(pwd)/docker.sock:/var/run/docker.sock
export DOCKER_HOST=$(pwd)/docker.sock
docker-compose build --pull --no-cache --force-rm && docker-compose up --remove-orphans -d
