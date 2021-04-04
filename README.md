# simple springboot app

### Prerequisites

The following are required to build application:
- Maven
- JDK 1.8+

To run the application:
- JVM 1.8+ or JDK 1.8+

### Basic information about the application
1. Springboot application, runs by default on port 9651
2. For DB migrations flyway is used
3. Default DB is h2 in-mem
4. Swagger is configured and can be accessed at: `http://localhost:9651/swagger-ui/`
5. To access the api's, Spring security with Basic Auth is used - `user: hello` and `password: welcome`
6. Management endpoints are exposed under the path - `{application-url}/appinfo/actuator` with following endpoints
 - 6.1 http://localhost:9651/appinfo/actuator/health
 - 6.2 http://localhost:9651/appinfo/actuator/metrics
 - 6.3 http://localhost:9651/appinfo/actuator/prometheus


## Setting up a local environment
1. The easiest way to run the app locally is to run it on default profile which runs on an in-mem h2 db
2. After importing the project in your IDE run the main method in `SimpleSpringbootMainApplication.java`


## Setting up a docker image
- Dockerfile is included in the project, to build and run the docker image-
1. Docker must be pre-installed
2. Go to the root directory of the project & open terminal
3. Run - `docker build -t enovation-spring-boot-docker .`
4. Run - `docker run -p9666:9651 enovation-spring-boot-docker:latest`
5. Can be accessed now - `http://localhost:9666/swagger-ui/`

## Rest docs
- To view the Rest Docs
1. Go to the root directory of the project
2. Open terminal and run - `mvn clean package`
3. Go to `target/generated-docs` and open the `index.html` in a browser


 


