package creditcard.controller;

import creditcard.controller.processor.CreditCardValidateProcessor;
import creditcard.model.request.CreditCardInfo;
import creditcard.model.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/creditcards")
public class CreditCardServiceController extends BaseHttpController {

    private final String CREDIT_CARD_VALIDATE = "/validate";

    @Autowired
    CreditCardValidateProcessor creditCardValidateProcessor;

    @RequestMapping(value = CREDIT_CARD_VALIDATE, method = RequestMethod.POST)
    public ResponseEntity<ServiceResponse<?>> validate(@RequestBody CreditCardInfo creditCardInfo) {
        return createFullServiceResponse(creditCardValidateProcessor.checkCreditCardValidity(creditCardInfo));
    }
}