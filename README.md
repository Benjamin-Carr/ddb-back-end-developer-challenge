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
    "damageType": "necrotic",
    "amount": -10 // must be negative
  }
  ```
  <details>
  <summary><b>Example curl</b></summary>

  ```bash
  curl --location 'localhost:8080/characters/briv/damage' \
  --header 'Content-Type: application/json' \
  --data '{
  "amount": -10,
  "damageType": "necrotic"
  }'
  ```
  </details>
  <details>
  <summary><b>Example response body</b></summary>
  <i>Assuming that briv had 25/25 HP</i>

  ```json
  {
      "code": 200,
      "message": "Hit points updated.",
      "data": {
          "maxHitPoints": 25,
          "tempHitPoints": 0,
          "tempHitPointsDelta": 0,
          "currentHitPoints": 15,
          "currentHitPointsDelta": -10,
          "multiplier": 1.0,
          "overflow": 0
      }
  }
  ```
  </details>
### Heal

- **URL**: `/characters/{id}/heal`
- **Method**: `POST`
- **Description**: Heals the specified player character.
- **Request Body**:
  ```json
  {
    "amount": 8 // must be positive
  }
  ```
    <details>
  <summary><b>Example curl</b></summary>

  ```bash
  curl --location 'localhost:8080/characters/briv/heal' \
  --header 'Content-Type: application/json' \
  --data '{
  "amount": 8
  }'
  ```
  </details>
  <details>
  <summary><b>Example response body</b></summary>
  <i>Assuming that briv had 15/25 HP</i>
  
  ```json
  {
      "code": 200,
      "message": "Hit points updated.",
      "data": {
          "maxHitPoints": 25,
          "tempHitPoints": 0,
          "tempHitPointsDelta": 0,
          "currentHitPoints": 23,
          "currentHitPointsDelta": 8,
          "multiplier": 1.0,
          "overflow": 0
      }
  }
  ```
</details>

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
    <details>
  <summary><b>Example curl</b></summary>

  ```bash
  curl --location 'localhost:8080/characters/briv/temp-hp' \
  --header 'Content-Type: application/json' \
  --data '{
  "amount": 5
  }'
  ```
  </details>
  <details>
  <summary><b>Example response body</b></summary>
  <i>Assuming that briv had 23/25 HP and no temp HP</i>

  ```json
  {
      "code": 200,
      "message": "Hit points updated.",
      "data": {
          "maxHitPoints": 25,
          "tempHitPoints": 5,
          "tempHitPointsDelta": 5,
          "currentHitPoints": 23,
          "currentHitPointsDelta": 0,
          "multiplier": 1.0,
          "overflow": 0
      }
  }
  ```
</details>

### Remove Temporary Hit Points

- **URL**: `/characters/{id}/temp-hp`
- **Method**: `DELETE`
- **Description**: Removes all temporary hit points from the specified player character.
    <details>
  <summary><b>Example curl</b></summary>

  ```bash
  curl --location --request DELETE 'localhost:8080/characters/briv/temp-hp' \
  --header 'Content-Type: application/json'
  ```
  </details>
  <details>
  <summary><b>Example response body</b></summary>
  <i>Assuming that briv had 23/25 HP and 5 temp HP</i>

  ```json
  {
      "code": 200,
      "message": "Hit points updated.",
      "data": {
          "maxHitPoints": 25,
          "tempHitPoints": 0,
          "tempHitPointsDelta": -5,
          "currentHitPoints": 23,
          "currentHitPointsDelta": 0,
          "multiplier": 1.0,
          "overflow": 0
      }
  }
  ```
</details>