FROM maven:3.5-jdk-8 AS build

WORKDIR /builddir

COPY src src/
COPY pom.xml ./
COPY m2 m2/

RUN mvn -s m2/settings.xml clean package

FROM openjdk:8-jre-alpine AS release

WORKDIR /snack-food

# Gets jar builded from build image
COPY --from=build /builddir/target/snack-food-*.jar  ./snack-food.jar
COPY docker-entrypoint.sh /usr/local/bin/

EXPOSE 8080

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["snack-food"]