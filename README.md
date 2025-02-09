# HearYe

This is a coding calisthenics project to set up a Java spring boot app
end to end, with individual component scaling and messaging. It uses
Docker for local development and hopefully for deployment.

It represents a notification API that can take in messages from approved
clients and transform/broker them to any of a number of preconfigured
destinations like e-mail, in-app notification on various sites/clients,
messaging services, etc.

This doesn't fill a specific need and isn't intended really for distribution.
It exists primarily to build my muscle at configuring, building, and deploying
services from the ground up.

## Prerequisites
For the best development experience
1. install Docker Desktop on your development machine.
2. use the IntelliJ IDE

Neither are strictly required, but the repo is optimized for these tools.

## Points of interest
- [/docker-compose.yaml](./docker-compose.yaml): Docker configuration for local deployment.
  - Defines two service containers:
    - **api**: which maps to the [/hearye-api](./hearye-api) module and containerizes the service there.
    - **db**: which logically maps to the [/hearye-db](./hearye-db) module and configures/runs an
      instance of PostgreSQL DB. See the [Official Postgres Docker Image Docs](https://hub.docker.com/_/postgres)
      for info on how this works.
- [/bin](./bin): useful development scripts will go here
- [/hearye-api](./hearye-api): a module containing the Spring Boot API application.
- [/hearye-db](./hearye-db): a module that contains database initialization and utility scripts. 

## Development Guide
### Starting the application locally
To start the application locally using docker, EITHER:
1. In the command line, from the project root, run 
   ```bash
   docker compose up
   ``` 

2. OR: open the project in IntelliJ, open [/docker-compose.yaml](./docker-compose.yaml), and
   click the "Run" button in the gutter at the top of the file, or near the service
   definition you want to launch.

### Hitting the API
The API is available on port 8000 (see [/docker-compose.yaml](./docker-compose.yaml) for
port bindings).

You can test that it's running by hitting the "home" of the API at http://localhost:8000

### Rebuilding Java
By default, the local `hearye-api/build/libs` directory is "bind mounted" to the container
instance for ease of development. This means that each time you rebuild, the latest
version is automatically available for your container.

HOWEVER, currently this rebuild will break the running application. After you rebuild,
restart the API container and it will restart the app with the latest build.
