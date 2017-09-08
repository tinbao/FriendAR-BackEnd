FROM maven:alpine

# application placed into /opt/app
RUN mkdir -p /opt/app
WORKDIR /opt/app

# selectively add the POM file and
# install dependencies
COPY ./friendar-rest-api/pom.xml /opt/app/
RUN mvn install -Dmaven.test.skip=true

# rest of the project
COPY ./friendar-rest-api/src /opt/app/src
RUN mvn package -Dmaven.test.skip=true

# local application port
EXPOSE 80

# execute it
CMD ["mvn", "exec:java"]

