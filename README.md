# Credit Card Service
- Running instruction
    - Clone repository : git clone https://github.com/hm3shah/credit-card-service.git
    - Run 'mvn clean package' from project's root directory
    - Run 'mvn spring-boot:run' to start application. By default application runs on 8080 port number. So make sure that port number is not being used by any other application. Service port number is configurable by setting server.port property in application.properties file
    
- Example API calls:
    - Credit card validation
    
    ```
    curl -X POST -H "Content-Type: application/json" \
    -d '{"creditCardNumber":"3010101010101010"}' "http://localhost:8080/creditcards/validate"
    
    Response:
    {"data":{"valid":true},"error":null}
    
    curl -X POST -H "Content-Type: application/json" \
    -d '{"creditCardNumber":"301010101010101"}' "http://localhost:8080/creditcards/validate"
   
    Response:   
    {"data":null,"error":{"code":"INVALID_CREDIT_CARD_NUMBER_LENGTH","message":"The requested credit card number length is invalid"}}

    curl -X POST -H "Content-Type: application/json" "http://localhost:8080/creditcards/validate"
   
    Response:
    {"data":null,"error":{"code":"EMPTY_CREDIT_CARD_VALIDATION_PAYLOAD","message":"Empty payload provided for credit card validation"}}
    ```
   
- Improvements
    - This is naive implementation of credit card validation system from security stand point. One of simple solution could be using HTTPS and not just plain HTTP. So that is something that could be improved easily.
    - We could add some analytics to monitor validation request's origin. This system to made as sophisticated as needed to smart detect fraud based on request patterns by detecting outliers.
    - The validation process here is simple enough, but if there were service calls to downstream systems or database systems then we could add caching layer for faster system response. But that would depend on feature and SLA requirement of system.