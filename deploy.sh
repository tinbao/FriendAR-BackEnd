#!/bin/bash
set -ex # Exit with nonzero exit code if anything fails
set -o

ssh -V
sudo apt-add-repository 'deb http://archive.ubuntu.com/ubuntu yakkety main universe multiverse'
sudo apt-get -qq update -y
sudo apt-get -qq install --no-install-recommends -y openssh-server
ssh -V
ssh -i ssh_key -o StrictHostKeyChecking=no -fnNT -L $(pwd)/docker.sock:/var/run/docker.sock ubuntu@01.server.friendar.tk.
export DOCKER_HOST=$(pwd)/docker.sock
ls -al
docker info
docker ps
docker images
docker-compose -H $(pwd)/docker.sock  build --pull --no-cache --force-rm && docker-compose up --remove-orphans -d
