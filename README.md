# targetSvc
This is a microservice for Targets.

## Dependencies
This service depends on MongoDB. ENV Variable: MONGO_URL

## Development

To start your application local:

``` 
mvn clean install 
java -jar -DMONGO_URL=localhost:27017 target/target-svc-*.jar
```
*** Please change localhost and port with the mongo db url

Note: If you are running in IDE, please add ENV variable `MONGO_URL`

The service will start on port 8090. To check if application is started clean, run the below command:

```curl -X GET http://localhost:8090/management/health```


## Create Docker

To build docker image run:


``` 
mvn clean install 
mvn verify -P docker
 ```

This will create a docker image with repository as `registry.avenueone.com/avo/target-svc` and `$VERSION` as tag.


## Run using Docker Compose
If you want to run both Mongo and Target Svc using docker compose, run the below command

``` 
docker-compose -f docker/docker-compose.yml  up -d
```
The application will be  started on port 8090. To check if application is started clean, run the below command:

```curl -X GET http://localhost:8090/management/health```

## Run standalone docker

To run the docker image locally:

```docker run -p 8090:8090 -e MONGO_URL=localhost:27017 -d registry.avenueone.com/avo/target-svc:0.0.1-SNAPSHOT ```

The application will be  started on port 8090. To check if application is started clean, run the below command:

```curl -X GET http://localhost:8090/management/health```

## Testing

Coming soon..

### Code quality

Coming soon..

## Continuous Integration (optional)

Coming soon..

## Production

Coming soon..
