# Test Offer - Project

The test_offer project was developed between December 2021 and January 2022 as part of a level evaluation.

## ***Introduction***

This project consists of a Springboot API. It exposes several services :
- one that allows to register a user
- one that displays the details of all registered users
- one that displays the details of a registered user

A user is defined by a username, a birthday and a country of residence which are mandatory and a phone number and gender which are optional.
There are constraints for the values of these attributes.If there is an incorrect value, appropriate error messages and http reports are returned.


Users are registered in an embedded database (h2).
Unit and integration tests have been performed. A Postman collection is available.
An AOP is used to record the inputs and outputs of each call and the processing time.


## ***Before using APISpringBoot:***

### Java version

The Java version used in this API is jdk-11.0.2

- It can be downloaded at this [address](https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html).

### IDE used

The IDE used for this API is IntelliJ IDEA 2021.3

- It can be downloaded at this [address](https://www.jetbrains.com/fr-fr/idea/download/).

### Downloading and opening the SpringBoot API:

It is possible to download the whole project directly from GitHub. Then you have to unzip it and open it.

You can also clone the project with the Git tool.After installing the tool, we open a GitBash console and enter the command `git clone https://github.com/MelanieMasson/test_offer`.

Then, the project can be opened with the IDE of your choice (IntelliJ, Eclipse, VSCode ...).


## ***SpringBoot API***

### SpringBoot

The version of SpringBoot used for this API is 2.5.6.

### Database

The API uses an embedded H2 database named test_atos. The execution of the data.sql file allows to update and fill it.


## ***Use of the API***

### Starting the embedded database

We start by executing the `data.sql` file located at the base of the `test_offer/src/main/ressources/` directory.

After opening it, we select the whole file, right click and select `Run'. The database will be reset and will contain 3 basic users.


### Starting the project

We will then execute the `TestOfferApplication` file located at the base of the `test_offer/src/main/java/com/atos/test_offer/` directory.

### Available services

The API implements three services:
- The first one displays the details of all registered users.
  This will require entering the URL `http://localhost:8080/api/user` in a web page or on Postman. 
  The query returns all the information of the registered users in the database.


- The second one displays the details of a specific user.
  For this we enter the URL `http://localhost:8080/api/user/{id}` where `{id}` is the user ID of the user whose information we want to retrieve. 
  If no user has this identifier, a 404 error is returned.


- The last one allows to register a user.
  The URL `http://localhost:8080/api/user` is used again. The Post request must be written in JSON format.


A user is defined by a username, date of birth and country of residence which are mandatory and a phone number and gender which are optional.

  - The size of the username must be between 2 and 30 characters. All character types are accepted.
  

  - The country entered must have a minimum length of 2 characters. If the country entered is not France, an error message is returned.


  - The values `France`, `France` or `FRANCE` are accepted without distinction.
  

  - The date of birth must be in the format `yyyy-mm-dd`. If the date is later than the current date or in an incorrect format, an error is returned. Also, the user must be 18 years or older to be registered.
  

  - Gender is not required, the possibilities are: `Male`, `Female` and `Other`. If a value is entered but is invalid, an error message is returned.
  

  - The phone number is not mandatory either. As the user is a French resident, only French phone number formats are considered accepted (example: 0123456789, 01.23.45.67.89, +33-1.23.45.67.89, +33(0) 123 456 789 ... ). If a value is entered but is invalid, an error message is also returned.

## ***Tests***

Les tests unitaires et tests d'intégration sont dans le répertoire `src/test/java/com/atos/test_offer/`.

### Integration testing:

- Testing with the API and Postman

    The SpringBoot API must be executed. We send the Get and Post requests via Postman according to the information given earlier.
  A collection of Postman requests is available in the directory `src/test/java/com/atos/test_offer/` under the name Postman_collection.
  The collection starts with Get requests and then Post requests. If the request is valid, the description corresponds to "Success", and if it is invalid it corresponds to "Error".


- Tests with UserServiceTest and UserRepository


- Tests with UserAPIControllerTest and UserService


### Unit tests :

- Tests with UserEntityTest


- Tests with UserServiceTest


- Tests with UserAPIControllerTest

To run these tests (except the Postman tests), just right click on the file or the whole directory and select Run.
In the console that opens you can see each test that has been executed.   