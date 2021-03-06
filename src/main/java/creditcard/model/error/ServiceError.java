package creditcard.model.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum ServiceError {

    INVALID_CREDIT_CARD_NUMBER_LENGTH(
            "INVALID_CREDIT_CARD_NUMBER_LENGTH",
            "The requested credit card number length is invalid",
            HttpStatus.BAD_REQUEST
    ),
    EMPTY_CREDIT_CARD_VALIDATION_PAYLOAD(
            "EMPTY_CREDIT_CARD_VALIDATION_PAYLOAD",
            "Empty payload provided for credit card validation",
            HttpStatus.BAD_REQUEST
    );

    @Getter
    private String code;
    @Getter
    private String message;
    @Getter
    @JsonIgnore
    private HttpStatus status;

}
