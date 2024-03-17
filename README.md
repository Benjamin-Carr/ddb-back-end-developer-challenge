# DDB Back End Developer Challenge

# Overview
This project is a showcase of technical skills for the D&D Beyond back-end developer challenge. 

The goal was to create an API for managing a player character's Hit Points (HP) within our game. The API enables clients to perform various operations related to HP, including dealing damage of different types, considering character resistances and immunities, healing, and adding temporary Hit Points. 

For the purposes of this task, the service interacts with HP data provided in the briv.json file and persists throughout the application's lifetime.

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
