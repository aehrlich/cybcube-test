# Building
Single-repository multi-module project setup has been chosen
for developer's convenience as no specific VCS/CI requirements were provided.

Maven Central and DockerHub have to be available.

OpenJDK 11 and Gradle 6.6.1 were used to develop and test the project.

Gradle bootJar task in root project (or one by one in data-collector and social-rating-calculator subprojects)
will build runnable jars.
Running applications within IntelliJ should work, too.

# Running
Issue POST requests to http://localhost:8000/requests in default configuration
(with provided `application.properties`).
Post JSON body with the structure specified in the Test task.

# Custom properties files
Custom `application.properties` files can be specified as usual with Spring Boot.
For example (jar version excluded from filename), `java -jar data-collector.jar --spring.config.location=custom-config/` will point
the application to look for configuration in the `custom-config` directory
outside the jar file.

# Dependent services in Docker
Docker has to be installed and configured as a pre-requisite.

The whole project requires Kafka (and hence also Zookeeper) docker images to run.

If run manually service by service, first execute `docker-compose up -d` in the root directory
to start up Kafka in Docker;
stop Kafka by `docker-compose down -v` run in the same directory
(the `-v` key cleans up container data).

## Kafka
Kafka is intentionally configured with single broker as localhost,
mostly to avoid hostname resolution need.

Topic is created in Java code with hard-coded "1 partition, replication factor 1"
for simplicity.

It is also run as a named ("kafka") image, so take care there are no other
docker containers running with the same name.

### Inspect Kafka internals
Get shell in Kafka container `docker exec -it kafka /bin/bash` and cd to `/opt/kafka/bin`.
The following commands could be helpful to check what is going on:
* `./kafka-topics.sh --bootstrap-server kafka:9092 --list`
* `./kafka-topics.sh --bootstrap-server kafka:9092 --topic social-rating-topic --describe`
* `./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic social-rating-topic --from-beginning`

## Redis
The `social-rating-calculator` subproject requires Redis container.

If run manually service by service, first execute `docker-compose up -d` in the `social-rating-calculator` subproject directory
to start up Redis in Docker;
stop Redis by `docker-compose down -v` run in the same directory
(the `-v` key cleans up container data).

# Storing data in Redis
To demonstrate that Redis data is present `social-rating-calculator` offers a REST controller
accessible at http://localhost:8100/redis/score/ with plain
`GET` against `/redis/score` returning list of all data 
and `/redis/score/{userId}` returning the data for the given userId.
Swagger has not been configured in the project.

# Additional business considerations
Normally issues of this kind are communicated back to analyst and/or orderer of development
to verify if approaches are acceptable and to find out what to do if not.

The controller added to the `social-rating-calculator` was not ordered
initially and opens up a new potential attack vector the orderer shall
at least be aware of.

Age in input is deemed to be provided as `int` and not "arbitrary number".

Score is calculated as `double` *without explicit rounding* and without explicit precision.
Score is printed to console (according to Test task) with default `%f` formatting
while stored in Redis "as is". The difference can be demonstrated e.g. with seed value 0.254 and age value 92:
console output will be 23.368000 while Redis-stored data be 23.368000000000002.

"Username" stored in Redis is calculated as "firstname and lastname separated by a space"
and referred to as `userId` in both Redis-stored data and related REST API.
