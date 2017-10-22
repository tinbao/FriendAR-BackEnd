#!/bin/bash
#set -eox

server(){
  ssh -i ssh_key -o StrictHostKeyChecking=no ubuntu@115.146.85.46 "$@"
}

server "cd /home/ubuntu/friendar && ls "
server "cd /home/ubuntu/friendar && docker-compose down"

echo "remove all containers"
server docker rm -f $(server docker ps -a -q)

echo "remove all images"
server docker rmi $(server docker images -q)

echo  "remove all volumes"
server docker volume rm $(server docker volume ls -f dangling=true -q)

echo "remove dir"
server "rm -rf /home/ubuntu/friendar"
echo "make dir"
server mkdir -p /home/ubuntu/friendar

echo "copy files"

tar cpf - ./ | server "tar xpf - -C /home/ubuntu/friendar/"
server ls -R /home/ubuntu/friendar
echo "start sever"

server "cd /home/ubuntu/friendar && docker-compose build --pull --no-cache --force-rm && docker-compose up --remove-orphans -d"

