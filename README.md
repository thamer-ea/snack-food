# Snack-Food

Snack Food Service

## Requirements

- Docker

## Docker

```
$ cd snack-food
$ docker-compose -f docker-compose.yml up -d
```

## API Guide

The [`api.yaml`](server/src/main/resources/docs/swaggger/v1/api.yaml) is Rest APIs documentation source file in
[`SWAGGER`](https://swagger.io/solutions/api-documentation/) format.
After starting the application, the rendered documentation, in `YAML` format, can be found at:

[`http://localhost:8080/api/v1/api-docs`](http://localhost:8080/api/v1/api-docs)

Use [`SWAGGER-EDITOR`](https://swagger.io/tools/swagger-editor/) for a better visualization.

## Considerations

To follow the microservices pattern, I would use [`Kong`](https://github.com/Kong/kong) as API Gateway.
It is very easy to configure, just create the [`Service`](https://docs.konghq.com/0.14.x/admin-api/#service-object) directing to the server and the [`Route`](https://docs.konghq.com/0.14.x/admin-api/#route-object) directing to the endpoints of the server.
It is also possible to use plugins, such as [`JWT`](https://docs.konghq.com/hub/kong-inc/jwt/) for authentication guarantee.

Also, to get an IAM, I would use [`Keycloak`](https://www.keycloak.org/index.html).