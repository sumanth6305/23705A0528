# URL Shortener Microservice

## Overview
This project is a Spring Boot-based HTTP URL Shortener Microservice developed as part of Affordmed's Campus Hiring Evaluation. It provides a scalable solution to shorten long URLs, manage their validity, track usage statistics, and handle redirections efficiently. The microservice is designed to meet production-ready standards with robust error handling and custom logging.

## Features
- **URL Shortening**: Convert long URLs into unique shortcodes with optional custom shortcodes.
- **Validity Management**: Set expiration times for short links (default 30 minutes, configurable).
- **Redirection**: Redirect users from shortcodes to original URLs with click tracking.
- **Statistics**: Retrieve usage statistics (click count, click data) for each shortcode.
- **Error Handling**: Custom exceptions for invalid inputs, expired links, and collisions.
- **Logging**: Mandatory custom logging middleware using SLF4J.

## Technologies
- **Framework**: Spring Boot 3.1.5
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Language**: Java 17

- **Dependencies**: Spring Data JPA, Lombok, HikariCP

## Installation
1. **Clone the Repository**:


git clone <your-repo-url>
cd 23705A0528</your-repo-url>
text2. **Configure PostgreSQL**:
- Install PostgreSQL and create a database named `urlshortenerdb`.
- Update `src/main/resources/application.properties` with your database credentials:
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortenerdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
text3. **Build the Project**:
mvn clean install
text4. **Run the Application**:
mvn spring-boot:run
text- Default port is 8081 (configurable in `application.properties`).

# API Endpoints
- **Create Short URL**:
- Method: `POST /shorturls`
- Payload:
 ```json
<img width="1920" height="1080" alt="shorturls_post" src="https://github.com/user-attachments/assets/c6fd1824-e958-4ee2-aab9-bdc9874b79dc" />
<img width="1899" height="989" alt="shorturls_get" src="https://github.com/user-attachments/assets/5fe5bbbc-a4ee-4102-a574-01797ac644ec" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/a591ab58-0165-498a-87f4-dbece65a2bed" />



 {
   "url": "https://www.example.com/very-long-path",
   "validity": 45,
   "shortcode": "xyz12"
 }

Response: 201 Created with shortLink and expiry.
Get Statistics:

Method: GET /shorturls/{shortCode}
Response: JSON with originalUrl, clickCount, and clickData.


Redirect:

Method: GET /{shortCode}
Redirects to the original URL.



Database Schema
The microservice uses a short_urls table:

id (SERIAL PRIMARY KEY)
original_url (VARCHAR)
short_code (VARCHAR, UNIQUE)
expiry_date (TIMESTAMP)
created_at (TIMESTAMP)

Sample data can be added via src/main/resources/initial-data.sql.
Development Notes

Custom Logging: Implemented via a LoggingFilter class.
Error Handling: Custom exceptions (ShortcodeCollisionException, etc.) with @ControllerAdvice support.
Scalability: Designed as a single microservice, extensible for distributed systems.

Contribution
This project was developed for Affordmed's evaluation. For enhancements, fork the repository and submit pull requests.
License
[Add license if applicable, e.g., MIT License]
Contact

Author:M Sumanth Reddy
GitHub: sumanth6305

