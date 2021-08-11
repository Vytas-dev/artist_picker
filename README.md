# artist_picker
Simple application to search and pick a favorite artist and its Top 5 albums. 

Artist data is provided by iTunes API. 
**Note:** iTunes API has 100 req / hour limit! 

Top albums for existing favorite artists are refreshed by nightly job (at 00:00).

## Endpoints
Exposed endpoints:
* GET http://localhost:8080/search/artist?term={artist_name} - to search for artist by the `term`, data provided by ITunes API.
* POST http://localhost:8080/user/{id}/artist - to save a favorite artist of the user.

  `{id}` - user identifier, if not exists new user is created.

  `{Request body}` - one specific artist returned by search endpoint http://localhost:8080/search/artist?term={artist_name}
* GET http://localhost:8080/user/{id}/albums - provides the Top 5 albums of user's favorite artist.

  `{id}` - user identifier, if not exists exception is thrown.

## Run the application

#### Run using gradle
Use IDE to run application or use gradle wrapper:
```
./gradlew bootRun
```

Since developer tools already included into project for hot-reload changes just rebuild project.
For example in Intellij IDEA: `Build -> Build Project` or shortcut `CTRL + F9`.

#### Run using Docker
Build docker image using gradle build plugin:
```
./gradlew bootBuildImage
```
Check new docker image tag from build logs or listing all images: `docker images`

Run docker image:
```
docker run -p 8080:8080 -t artist-selector:0.0.1-SNAPSHOT
```


## TODOs:
* Create production profile to disable devTools, etc.
* Enable endpoints (at least top albums endpoint) caching in case application is used with regular, not H2 in memory DB.
* Integrate Swagger to generate endpoints API docs.
* Handle exceptions properly, extend logging.


