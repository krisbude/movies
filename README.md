# movies-api

All requests need Basic Authentication
(see SecurityConfig for more details)

######Build the application.
`mvn clean install`

######Run the application in DEV profile for spring boot so it uses the mocked services.
 `mvn spring-boot:run -Dspring.profiles.active=dev`
 
 or in the target directory:
 
 `java -jar movie-api-1.0.0-SNAPSHOT.jar --spring.profiles.active=dev`
 

examples of request:

#####POST [http://localhost:8080/movie-api/movies](http://localhost:8080/movie-api/movies)

######body:
`
{
  "title" : "this is a movie",
  "description" : "this is the description"
}
`

#####POST [http://localhost:8080/movie-api/comments](http://localhost:8080/movie-api/comments)

######body:
`
{
  "movieId" : 1,
  "message" : "this is a comment"
}
`

#####GET [http://localhost:8080/movie-api/movies/1](http://localhost:8080/movie-api/movies/1)

#####GET [http://localhost:8080/movie-api/movies/1/comments](http://localhost:8080/movie-api/movies/1/comments)