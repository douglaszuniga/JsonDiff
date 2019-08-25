# JsonDiff

JsonDiff is a Spring Boot application exposing a couple of rest endpoints, two endpoints to store two Strings using an id as identifier and one to compare and result the difference

## Requirements
 
Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints

- <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
- The provided data needs to be diff-ed and the results shall be available on a third end
 point
- <host>/v1/diff/<ID>
- The results shall provide the following info in JSON format
    - If equal return that
    - If not of equal size just return that
    - If of same size provide insight in where the diffs are, actual diffs are not needed.
- So mainly offsets + length in the data

## Assumptions

- The input for the left and right endpoints is going to be a plain text, Base64 string
- The application is going to save in the database the decoded version of the base64 string
- The application MUST receive a VALID base64 string
- the diff is going to be done using the decoded string

## Endpoint Responses

- on `<host>:8080/v1/diff/<ID>/left` or `<host>:8080/v1/diff/<ID>/right`:
    - `HTTP 202 Accepted` => success request
    - `HTTP 400 Bad Request` => either input is blank or id is negative
- on `<host>:8080/v1/diff/<ID>`
    - `HTTP 404 Not Found` => the ID has no record in the database
    - `HTTP 200 OK` => success
    
    
## Possible Improvements

- use real database like mongo or postgresql to persist data after the app goes down
- add swagger page with api doc
- create dockerfile to create images
- use reactive programming
- add a process to prepare the diff in the background, this way we can reduce the duration of the endpoint

## Implementation details

The workflow of `<host>:8080/v1/diff/<ID>/left|right` is:

- first validation run against the ID and input
- the base64 string is decoded
- if the app has a record saved for that ID in the database then it updates or insert into that record
- returns an Accepted HTTP status

The workflow of `<host>:8080/v1/diff/<ID>` is:

- first validation run against the ID
- find the record in the database by ID
- run a diff process that will return equal, not-equal-size or equal-size
- if the result is equal-size, then an extra step runs
    - get the insights or hints indicating where the differences could be

### Tech Stack

- Java 12
- Spring Boot 2.1.7
- Spring Web Starter
- Lombok 1.18, help to remove some verbosity
- Google Guava 28.0, adding some extra input validations
- Apache Commons 3.9, mostly used for `StringUtils`
- JUnit 5
- Mockito 3
- Maven 

### How to setup lombok in the IDE

In order to to use lombok in the IDE follow the instructions found in this links:

- [Intellij](https://projectlombok.org/setup/intellij)
- [Eclipse](https://projectlombok.org/setup/eclipse)

## How to build

build and run the tests:

```shell script
mvn clean build test
```

generate and package into a fat jar

```shell script
mvn package
```
 
run the application

```shell script
java -jar target/JsonDiff-0.0.1-SNAPSHOT.jar
```