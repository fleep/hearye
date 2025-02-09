FROM amazoncorretto:21
WORKDIR /app
# The following line will copy the latest build into the /app/hearye-api.jar directory in the container.
# Using docker-compose will override this with a bind mount (mounting the build/libs/docker/ container directly),
# which helps with development.
COPY ./build/libs/docker/hearye-api.jar hearye-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hearye-api.jar"]