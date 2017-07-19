## Read a big CSV file as an input for sending emails

Given is a big CSV file (size: multiple gigabytes, encoding: UTF-8) with email, firstName and lastName separated with ; and " as quotes. 
The application reads the CSV file and sends emails to all recipients in an effective way. 
*Note: The application do not send any emails, it implements a mock email sending service that waits for half a second to mock the sending time needed 
and then log a success message mentioning the recipient.*

## Implementation

The application is reading the CSV file by streaming through the file, and it's using a thread pool of workers to perform the tasks of sending the emails.

## Example Data

Input CSV File:
```
jon@xxx.com;Jon;Bernard
chris@xxx.com;Chris;Burgh
alex@xxx.com;Alex;Jonson
bob@xxx.com;Bob;Marley
```

Response:
```
Email was successfully sent to the recipient with email: jon@xxx.com, first name: Jon, last name: Bernard
Email was successfully sent to the recipient with email: chris@xxx.com, first name: Chris, last name: Burgh
Email was successfully sent to the recipient with email: bob@xxx.com, first name: Bob, last name: Marley
Email was successfully sent to the recipient with email: alex@xxx.com, first name: Alex, last name: Jonson
```

## Requirements

* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
* [Maven 3.0+](http://maven.apache.org/download.cgi)

## Build with Maven

* [Welcome to Apache Maven](https://maven.apache.org/)
* [Building Java Projects with Maven](https://spring.io/guides/gs/maven/)

## Build and run tests with Maven

* cd into project-root-folder using the terminal.

* Run this maven command:
 
``` 
mvn clean test
``` 

## Run the service

* cd into project-root-folder using your terminal.

* Using Maven you can run the application using **mvn spring-boot:run**. 
  Or you can build an executable JAR file with **mvn clean package** and run the JAR by typing:

```
  java -jar target/send-emails-1.0.0.jar
```