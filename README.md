# Initiative Service

This project is the **Initiative Service** component of the **Voters Voice Amendment (VVA)** system. It provides functionality for managing initiatives created by users. These initiatives represent proposals for changes in laws at the state or federal level, and the service handles their creation, update, retrieval, and voting functionality.

## Features

- **Initiative Management**:
    - Create, update, and delete initiatives.
    - Retrieve details for specific initiatives.
    - List all initiatives.

- **User Voting**:
    - Users can vote on initiatives.
    - Retrieve voting results for initiatives.

- **Approval Workflow**:
    - Initiatives must go through an approval process before being displayed.

## Technologies Used

- **Spring Boot** 3.3.4 (Java 23)
    - **Spring Web**: For building RESTful APIs.
    - **Spring Data JPA**: For interacting with the PostgreSQL database.
    - **PostgreSQL**: As the relational database to store initiatives and voting data.

## Project Setup

### Prerequisites

Before running this project, ensure you have the following installed:

- **Java 23**
- **Maven**
- **PostgreSQL** (If running locally)

### Build and Run the Project

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/initiativeservice.git
    cd initiativeservice
    ```

2. **Build the project**:
    ```bash
    mvn clean package
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

The application will start on [http://localhost:8080](http://localhost:8080) by default.

### Dockerization

This service is containerized using Docker. You can build and run the Docker container as follows:

1. **Build the Docker image**:
    ```bash
    docker build -t initiativeservice .
    ```

2. **Run the Docker container**:
    ```bash
    docker run -p 8080:8080 initiativeservice
    ```

### Database Configuration

The project is configured to use **PostgreSQL**. You can set the database credentials in the `application.properties` or use environment variables to override them.

**Example properties**:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/initiative_service
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
