
# Book Review Service

## Project Description
The Book Review Service is a RESTful application for managing reviews of books.  
All users can search for books and their reviews.  
Authorized users can add reviews, update/delete their reviews.  

The service integrates with the Google Books API to retrieve book information.

### Deployment
The book-review-service is deployed on an AWS EC2 instance using Docker. 
The following details outline the deployment environment:

_**Server:**_ AWS EC2  
_**Region:**_ eu-central-1  
_**Instance Type:**_ t3a.small  
_**Operating System:**_ Amazon Linux 2  

**Accessing the Deployed Service**  
Once the service is deployed on AWS, it can be accessed using the EC2 instance  
public IP address (http://18.195.223.8)  
or  
domain name(http://book-review-service.eu-central-1.elasticbeanstalk.com).

**Swagger UI:** http://18.195.223.8/swagger-ui/index.html  
**Actuator Health Check:** http://18.195.223.8/actuator/health

## System Requirements
- Java 17
- Gradle (for building the application)
- Docker (for containerized deployment)
- PostgreSQL (used as the database)

## Installation and Setup Instructions

### Local Setup
1. Clone the repository:
    ```bash
    git clone https://github.com/YuliaSai/book-review-service.git
    cd book-review-service
    ```
2. Configure environment variables in the `src/main/resources/application-local.yml` file or use system variables.

3. Run the application using Gradle:
    ```bash
    ./gradlew bootRun
    ```

### Docker Setup
1. Configure environment variables in the `docker/.env` file or use system variables
    - `AND_DB_URL`: database url for PostgreSQL.
    - `AND_DB_USER`: database user.
    - `AND_DB_PASS`: database password.

2. Run the Docker container:
    ```bash
    docker-compose -f docker/docker-compose.yml up
    ```

## Testing Instructions
To run tests, use the following command:
```bash
./gradlew test
```

To generate a Jacoco coverage report:
```bash
./gradlew jacocoTestReport
```

Reports will be available in the `build/reports` directory.

### Actuator
The actuator is accessible at:
```
http://localhost:8081/actuator
```
To access `info` and `health` endpoints:
- `GET http://localhost:8081/actuator/info`
- `GET http://localhost:8081/actuator/health`

### Swagger UI
The actuator is accessible at:
```
http://localhost:8081/swagger
```

## API Endpoints

### Get all books
**Description**: Returns a list of books.
```http request
GET http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/books?title={title}&author={author}&isbn={isbn}
Accept: application/json
```
_**Query Parameters**_:
- `title` (optional): filter by book title.
- `author` (optional): filter by author.  
- `isbn` (optional): filter by isbn.


  **Example Request**:
```
GET http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/books?title=Java&author=John&isbn=9781471173943
```

### Get book by id
**Description**: Returns a book by its id.
  ```http request
GET http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/books/{{id}}
Accept: application/json
  ```
**Example Request**:
```
GET http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/books/_ojXNuzgHRcC
```

### Get reviews for a book by book id
**Description**: Returns a list of reviews for the specified book.
  ```http request
GET http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews/books/{{bookId}}/reviews
Accept: application/json
  ```
**Example Request**:
```
GET http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews/books/_ojXNuzgHRcC/reviews
```

### Get reviews for a book by reviewer id
**Description**: Returns a list of reviews for the specified reviewer.
  ```http request
GET http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviewers/{{reviewerId}}/reviews
Accept: application/json
  ```
**Example Request**:
```
GET http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews?reviewerId=123/reviews
```

### Add a Review for a Book
**Description**: Adds a new review for a book.  
  ```http request
POST http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews
Authorization: Bearer JWT_TOKEN_PLACEHOLDER
Accept: application/json
  ```
_**Request Body**_:
```json
{
  "bookId": "_ojXNuzgHRcC",
  "review": "Great book for learning Java!"
}
```
**Example Request**:
```
POST http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews
Authorization: Bearer JWT_TOKEN_PLACEHOLDER
Content-Type: application/json
{
  "bookId": "12345",
  "review": "Great book for learning Java!",
}
```

### Update a Review
**Description**: Updates an existing review by its ID.
  ```http request
PUT http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews/{{id}}
Authorization: Bearer JWT_TOKEN_PLACEHOLDER
Accept: application/json
  ```
_**Request Body**_:
```json
{
   "bookId": "iWA-DwAAQBAJ",
   "reviewText": "Updated review"
}
```
**Example Request**:
```
PUT http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews/67
Authorization: Bearer JWT_TOKEN_PLACEHOLDER
Content-Type: application/json
{
  "bookId": "iWA-DwAAQBAJ",
  "review": "Updated review"
}
```

### Delete a Review
**Description**: Deletes a review by its ID.  
  ```http request
DELETE http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews/{{id}}?bookId={bookId}
Authorization: Bearer JWT_TOKEN_PLACEHOLDER
Accept: application/json
  ```
**Example Request**:
```
DELETE http://book-review-service.eu-central-1.elasticbeanstalk.com/api/v1/reviews/67?bookId=iWA-DwAAQBAJ
Authorization: Bearer JWT_TOKEN_PLACEHOLDER
```
