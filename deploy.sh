#!/bin/bash
#set -e # Exit with nonzero exit code if anything fails

server(){
  ssh -i ssh_key -o StrictHostKeyChecking=no ubuntu@01.server.friendar.tk. "$@"
}
server mkdir -p /home/ubuntu/friendar
server "cd /home/ubuntu/friendar && ls "
server "cd /home/ubuntu/friendar && docker-compose down"

echo "remove all containers"
server docker rm -f $(server docker ps -a -q)

echo "remove all images"
server docker rmi $(server docker images -q)

echo  "remove all volumes"
server docker volume rm $(docker volume ls -f dangling=true -q)

echo "remove dir"
server "rm -rf /home/ubuntu/friendar"

echo "copy files"

scp -i ssh_key -r ./  ubuntu@01.server.friendar.tk:/home/ubuntu/friendar

echo "start sever"

server "cd /home/ubuntu/friendar && docker-compose build --pull --no-cache --force-rm && docker-compose up --remove-orphans -d"
