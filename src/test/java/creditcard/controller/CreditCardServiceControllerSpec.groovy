package creditcard.controller

import creditcard.controller.processor.CreditCardValidateProcessor
import creditcard.model.error.ServiceError
import creditcard.model.request.CreditCardInfo
import creditcard.model.response.CreditCardValidateResponse
import creditcard.model.response.ServiceResponse
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class CreditCardServiceControllerSpec extends Specification {

    CreditCardServiceController creditCardServiceController = new CreditCardServiceController()
    CreditCardValidateProcessor creditCardValidateProcessor = Mock(CreditCardValidateProcessor)


    MockMvc mockMvc

    def setup() {
        creditCardServiceController.creditCardValidateProcessor = creditCardValidateProcessor;
        mockMvc = standaloneSetup(creditCardServiceController).build()
    }

    def "test valid credit card number"() {
        when:
        def response = mockMvc.perform(post('/creditcards/validate').contentType(MediaType.APPLICATION_JSON).content("{\"creditCardNumber\":\"1111111111111111\"}")).andReturn().response

        then:
        1 * creditCardValidateProcessor.checkCreditCardValidity(_ as CreditCardInfo) >> new ServiceResponse<CreditCardValidateResponse>(new CreditCardValidateResponse(true))

        and:
        response != null
        response.status == 200
        response.getHeader("content-type") == "application/json"
        response.contentAsString != null
        response.contentAsString == "{\"data\":{\"valid\":true},\"error\":null}"
    }

    def "test invalid credit card number"() {
        when:
        def response = mockMvc.perform(post('/creditcards/validate').contentType(MediaType.APPLICATION_JSON).content("{\"creditCardNumber\":\"1111111111111111\"}")).andReturn().response

        then:
        1 * creditCardValidateProcessor.checkCreditCardValidity(_ as CreditCardInfo) >> new ServiceResponse<CreditCardValidateResponse>(new CreditCardValidateResponse(false))

        and:
        response != null
        response.status == 200
        response.getHeader("content-type") == "application/json"
        response.contentAsString != null
        response.contentAsString == "{\"data\":{\"valid\":false},\"error\":null}"
    }

    def "test short credit card number"() {
        when:
        def response = mockMvc.perform(post('/creditcards/validate').contentType(MediaType.APPLICATION_JSON).content("{\"creditCardNumber\":\"1\"}")).andReturn().response

        then:
        1 * creditCardValidateProcessor.checkCreditCardValidity(_ as CreditCardInfo) >> new ServiceResponse<CreditCardValidateResponse>(ServiceError.INVALID_CREDIT_CARD_NUMBER_PROVIDED)

        and:
        response != null
        response.status == 400
        response.getHeader("content-type") == "application/json"
        response.contentAsString != null
        response.contentAsString == "{\"data\":null,\"error\":{\"code\":\"INVALID_CREDIT_CARD_NUMBER_PROVIDED\",\"message\":\"The requested credit card number is of invalid type\"}}"
    }

    def "test empty credit card number"() {
        when:
        def response = mockMvc.perform(post('/creditcards/validate').contentType(MediaType.APPLICATION_JSON).content("{\"creditCardNumber\":\"1\"}")).andReturn().response

        then:
        1 * creditCardValidateProcessor.checkCreditCardValidity(_ as CreditCardInfo) >> new ServiceResponse<CreditCardValidateResponse>(ServiceError.EMPTY_CREDIT_CARD_VALIDATION_PAYLOAD)

        and:
        response != null
        response.status == 400
        response.getHeader("content-type") == "application/json"
        response.contentAsString != null
        response.contentAsString == "{\"data\":null,\"error\":{\"code\":\"EMPTY_CREDIT_CARD_VALIDATION_PAYLOAD\",\"message\":\"Empty payload provided for credit card validation\"}}"
    }

}
