package creditcard.controller;

import creditcard.model.error.ServiceError;
import creditcard.model.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
public class BaseHttpController {

    /**
     * Create a full {@link ResponseEntity} object based on supplied {@link ServiceResponse}
     *
     * @param serviceResponse An already constructed {@link ServiceResponse}
     * @return A {@link ResponseEntity} containing body, headers and HTTP status of the requested operation
     */
    protected ResponseEntity<ServiceResponse<?>> createFullServiceResponse(ServiceResponse<?> serviceResponse) {
        // check for an error
        if (serviceResponse.hasError()) {
            return createError(serviceResponse.getError());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");

        return new ResponseEntity<ServiceResponse<?>>(serviceResponse, headers, HttpStatus.OK);
    }

    protected ResponseEntity<ServiceResponse<?>> createError(ServiceError error) {

        // empty response object, set error headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");
        headers.set("error-code", error.getCode());
        headers.set("error-message", error.getMessage());

        // construct service response object with no data
        return new ResponseEntity<ServiceResponse<?>>(new ServiceResponse(error), headers, error.getStatus());
    }


    /**
     * @return A JSON error indicating BAD_REQUEST due to payload, parameterization, etc, with ZSR specific error headers
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ServiceResponse<?>> handlePayloadException() {
        log.debug("Empty payload provided for validate request");
        return createError(ServiceError.EMPTY_CREDIT_CARD_VALIDATION_PAYLOAD);
    }

}
