package creditcard.controller.processor

import creditcard.model.error.ServiceError
import creditcard.model.request.CreditCardInfo
import creditcard.model.response.CreditCardValidateResponse
import creditcard.model.response.ServiceResponse
import spock.lang.Shared
import spock.lang.Specification

class CreditCardValidateProcessorSpec extends Specification {

    CreditCardValidateProcessor creditCardValidateProcessor = new CreditCardValidateProcessor()

    @Shared
    ServiceResponse<CreditCardValidateResponse> validResponse = new ServiceResponse<>(new CreditCardValidateResponse(true))

    @Shared
    ServiceResponse<CreditCardValidateResponse> invalidResponse = new ServiceResponse<>(new CreditCardValidateResponse(false))

    @Shared
    ServiceResponse<ServiceError> emptyCreditCardNumberError = new ServiceResponse<>(ServiceError.EMPTY_CREDIT_CARD_VALIDATION_PAYLOAD)

    @Shared
    ServiceResponse<ServiceError> invalidCreditCardNumberError = new ServiceResponse<>(ServiceError.INVALID_CREDIT_CARD_NUMBER_LENGTH)

    def "test legit credit card numbers"() {
        setup:
        CreditCardInfo creditCardInfo = new CreditCardInfo(creditCardNumber)

        when:
        ServiceResponse<CreditCardValidateResponse> response = creditCardValidateProcessor.checkCreditCardValidity(creditCardInfo)

        then:
        response != null
        response == expectedResponse

        where:
        creditCardNumber   | expectedResponse
        "1111111111111111" | invalidResponse
        "3010101010101011" | invalidResponse
        "3010101010101010" | validResponse
        "0000000000000000" | validResponse
        "0000000000001082" | validResponse
        "1082000000000000" | validResponse
        "0000108200000000" | validResponse
        "0000000010820000" | validResponse
    }

    def "test erroneous credit card numbers"() {
        setup:
        CreditCardInfo creditCardInfo = new CreditCardInfo(creditCardNumber)

        when:
        ServiceResponse<CreditCardValidateResponse> response = creditCardValidateProcessor.checkCreditCardValidity(creditCardInfo)

        then:
        response != null
        response == expectedResponse

        where:
        creditCardNumber | expectedResponse
        ""               | emptyCreditCardNumberError
        "1"              | invalidCreditCardNumberError
    }
}
