GameFlix Auth Service

This project is a simple backend for GameFlix that lets users register and log in. It was built with Spring Boot and containerized using Docker.

How to Run It

Build the Docker image:

docker build -t gameflix-backend .


Run the container:

docker run --rm -p 8085:8080 --name gameflix gameflix-backend


The app runs at http://localhost:8085

Testing the API (Postman)

Register

POST http://localhost:8085/api/auth/register


Body:

{
"username": "gameuser",
"password": "gameflix123"
}


Expected:
{"message": "User registered successfully"}
Status: 201

Login

POST http://localhost:8085/api/auth/login


Body:

{
"username": "gameuser",
"password": "gameflix123"
}


Expected:
{"message": "Login successful"}
Status: 200

Notes

Uses H2 database (data resets every time you restart).

Works with Postman on port 8085.

I tested registration and login successfully inside Docker.