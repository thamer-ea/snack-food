## Requirements

- Java 1.8+
- MongoDB

## Environment Variables

The configuration of this application is grouped by both *Required* and *Optional* environment variables.

### Required Configuration

| Name | Description | Default |
| ---- | ----------- | ------- |
| SNACK_MONGODB_URI | URI of the mongo database. | mongodb://localhost:27017/snack_food |

### Optional Configuration

| Name | Description | Default |
| ---- | ----------- | ------- |
| SNACK_SERVER_PORT | Server HTTP port. | 8080 |
| SNACK_LOG_PATH | Set the relative path where the *app.log* file will be generated. | logs |
| SNACK_LOG_LEVEL | Set the app log level. | INFO |

## Building

    $ ./mvn clean package

## Running

The build stage generates a `jar` file that can be executed using `java -jar` command:

    $ java -jar target/snack-food-1.0-SNAPSHOT.jar

## Docker

```
$ cd snack-food/server
$ docker build -t snack-food-server .
$ docker run --rm -d -p 8080:8080 --name snack_food -e SNACK_MONGODB_URI="mongodb://localhost:27017/snack_food" snack-food-server
```
