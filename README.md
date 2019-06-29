# Customer Statement Processor - Assignment for Rabobank

This application is developed to validate customer statement records.

# Technical Details

## Server
The application is hosted on port `8080` and the servlet context path is `/`

## Security
Application is secured with basic authentication with users `user:password`.
Security is enabled by default

# Usage

## Input
The format of the file is a simplified format of the MT940 format. The format is as follows:

| Field | Description |
| ---- | ---- |
| Transaction reference | A numeric value |
| Account number | An IBAN  |
| Start Balance | The starting balance in Euros  |
| Mutation | Either an addition (+) or a deduction (-) |
| Description | Free text  |
| End Balance | The end balance in Euros |

## Output
There are two validations:
* all transaction references should be unique
* the end balance needs to be validated

At the end of the processing, a report needs to be created which will display both the transaction reference and description of each of the failed records.

## How to use
First, the server should be started as a Spring Boot project. The server can be started up via the following command:

`mvn spring-boot:run`

Secondly, the jar file of the project could be used in a Containerized environment.

`java -jar target/customer-statement-processor-0.0.1-SNAPSHOT.jar`

## The service
The service is a REST API to validate CSV and XML files and can be used with `POST` request.
The XML or CSV file which contains transactions can be posted to the service and gets validated. Files in other format will return 400 error code.

## Using the service

### Via the UI
To access the web page to use the service, navigate to the root (`/`). For example: `http://localhost:8080/statement`
Via the web page, file uploader could be used to validate files. If the security is enabled, authentication should be required on the browser.
Then, `username: user` and `password: password` should be entered.

## Via Postman
Either PostMan or an equivalent applications can be used to use the service.

## Via cURL
The endpoint can be used via `cURL` using the following command:

```bash
curl -X POST "http://localhost:8080/statement" -H "accept: */*" -H "Content-Type: multipart/form-data" -F
"statementFile=@instructions.html;type=text/html"

```
## Via SwaggerUI

http://localhost:8080/swagger-ui.html

## Testing
The tests can be performed via following Maven command:

`mvn clean test`

## Documentation
The JavaDoc is configured can be generated via the following command:

`mvn javadoc:javadoc`

## Improvements

1. Basic authentication: user/password are hardcoded
2. Scope of improvement in validation framework can be added validation for IBAN no.
3. Static code analysis
4. Test case coverage to increase

## Contributor
Utpal Kumar