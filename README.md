# Task
Create a java spring boot web application that has a User domain entity only.

Create REST endpoints for user creation (POST request to localhost:8888/users) and all user retrieval (GET request to localhost:8888/users).

The underlying storage should be a Postgres relation database.
The application architecture style is a layered architecture that consists of a presentation layer that sends request(s) to the persistence layer.

Unit tests should be written for UserController create and getAll methods.

# Notes
- App: http://localhost:8888
- DB container should expose port: 5433