# Repository Application

## Description

This is a simple Kafka consumer application that exposes a `GET` endpoint `/betvictor/history` on port `8082`, which provides the history of consumed messages.

The application consumes messages from an external broker and saves them to a PostgreSQL database.

The application is configurable to use any Kafka broker, and the Kafka broker's connection details can be provided in the configuration file.
The application is configurable to use any number of concurrent Kafka consumers, and the number of consumers can be provided in the configuration file.

## Prerequisites

To run this application, you need the following:

- **Java 23**: The application requires Java 23 to run. Please ensure that Java 23 is installed on your system.
- **Docker** (to run Postgres in a container)
- **Docker Compose** (to orchestrate the Postgres container)

## Setup

### 1. Start Postgres with Docker Compose

To start the Postgres container, run the following command:

```bash
docker-compose up -d 
```

This will bring up the Postgres container in detached mode.

### 2. Start the Spring Boot Application

The application will listen on http://localhost:8082.


## Testing

To test the /betvictor/history endpoint, make a GET request to:

GET http://localhost:8082/betvictor/history 
