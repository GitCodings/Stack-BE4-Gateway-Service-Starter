# CS122B Backend 4 - The Gateway Service

#### [Application Settings](#application-settings)

## Application Settings

This file is already provided for you and configure Spring Boot's settings

##### `application.yml`

```yml
spring:
  application:
    name: GatewayService

server:
  address: 0.0.0.0
  port: 8084
  error: # These settings are for debugging
    include-exception: true
    include-message: always

logging:
  file:
    name: ./GatewayService.log
   
gateway:
  idm: http://localhost:8081
  movies: http://localhost:8082
  billing: http://localhost:8083
  authenticate-path: /authenticate
``` 
