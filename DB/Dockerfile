FROM postgres:alpine
ENV POSTGRES_USER rest-api-server
ENV POSTGRES_PASSWORD password
ENV POSTGRES_DB docker
ADD init.sql /docker-entrypoint-initdb.d/

