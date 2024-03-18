# D&D Beyond Character Service
Manage hit points and damage for Dungeons and Dragons characters.

# Project Description
The purpose of this project is to showcase my technical skills for the D&D Beyond back-end developer coding challenge.

The service is built so that additional character-related functionality can easily be added, but at the moment the main focus is on hit points and damage.

Technical highlights include:
- Low code duplication
  - Healing and damage using the same function in the service layer
  - Immunity, resistance, and vulnerability are elegantly handled with enums
- Testable code
  - Business logic is extracted into separate classes
- High quality tests
  - Tests go beyond basic coverage and are based on use cases
  - Tests output is organized, formatted, and nested to be human-readable

# Running the Code
## Prerequisites
Install the following on your machine before you begin:
- Java
- Maven

## Starting the Service
```bash
cd hit-point-service
mvn package
java -jar target/hit-point-service-0.0.1.jar server config.yml
```
The service will be running on `localhost:8080`

## Running Tests
In the `/hit-point-service` directory run:
```bash
java -jar target/hit-point-service-0.0.1.jar server config.yml
```

# Documentation


## API Endpoints

### Get All Characters


- **URL**: `/characters`

- **Method**: `GET`

- **Description**: Retrieves all player characters.


### Get Character by ID


- **URL**: `/characters/{id}`

- **Method**: `GET`

- **Description**: Retrieves the specified player character.

### Damage

- **URI**: `/characters/{id}/damage`
- **Method**: `POST`
- **Description**: Deals damage to the specified player character.
- **Request Body**:
  ```json
  {
    "damageType": "Fire",
    "amount": 5
  }
  ```
### Heal

- **URL**: `/characters/{id}/heal`
- **Method**: `POST`
- **Description**: Heals the specified player character.
- **Request Body**:
  ```json
  {
    "amount": 5
  }
  ```
### Add Temporary Hit Points

- **URL**: `/characters/{id}/temp-hp`
- **Method**: `POST`
- **Description**: Adds temporary hit points to the specified player character.
- **Request Body**:
  ```json
  {
    "amount": 5
  }
  ```
### Remove Temporary Hit Points

- **URL**: `/characters/{id}/temp-hp`
- **Method**: `DELETE`
- **Description**: Removes all temporary hit points from the specified player character.
