# Overview

This application implements most of the features specified in the swagger specification.

The technology stack is based on Java 8 with SpringBoot, JDBC/JPA and H2 in-memory database.

The app comes with an in-memory database with a few patient records already created.

### To run this app:
- Checkout from GIT
- Import as Maven project in Eclipse
- Right click on DialoguemdApplication.java class and select 'Run as Java Application'.


### REST api usage:
- Using Postman:
	- Retrieve pagenated list of all patients via GET request:
	
			http://localhost:8080/tickets
	
			http://localhost:8080/v1/patients?page=0&size=10

	- Retrieve a specific patient via GET request:
	
			http://localhost:8080/v1/patients/{id}

	- Delete a specific patient via DELETE request:
	
			http://localhost:8080/v1/patients/{id}

	- Save a patient via POST request:
	
			http://localhost:8080/v1/patients 
			
		with patient info in body: 
		
			{"email":"john_wick@hotmail.com","firstName":"John","lastName":"Wick","birthDate":"1980-10-11","sex":"male"}

### TODO:
- Https
- Authentication
- Hostname
- Unit tests
