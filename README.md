# reactive-play
Kotlin + reactive server application using HTTP/RSocket + FX Java Client

- reactive directory contains reactive server web application
- reactive-sprint-project is a gradle multi-module project
  - reactive-client - contains code that communicates with reactive web application
  - fx-client - is a non web SprintBootApplication, it runs a FXJava application and uses the Spring Benefits like DI/Profiles/Autoconfiguration (see resources/META-INF/spring.factories)
