package creditcard.controller.processor;

import creditcard.model.error.ServiceError;
import creditcard.model.request.CreditCardInfo;
import creditcard.model.response.CreditCardValidateResponse;
import creditcard.model.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreditCardValidateProcessor {

    public ServiceResponse<?> checkCreditCardValidity(CreditCardInfo creditCardInfo) {
        if (creditCardInfo.getCreditCardNumber() == null || creditCardInfo.getCreditCardNumber().isEmpty()) {
            log.debug("Invalid input: {}", creditCardInfo);
            return new ServiceResponse<CreditCardValidateResponse>(ServiceError.EMPTY_CREDIT_CARD_VALIDATION_PAYLOAD);
        } else if (creditCardInfo.getCreditCardNumber().length() < 16) {
            log.debug("Invalid credit card number length:{}", creditCardInfo);
            return new ServiceResponse<CreditCardValidateResponse>(ServiceError.INVALID_CREDIT_CARD_NUMBER_PROVIDED);
        } else {
            return new ServiceResponse<CreditCardValidateResponse>(checkCreditCardValidity(creditCardInfo.getCreditCardNumber()));
        }
    }

    /**
     * The validation algorithm follows these steps:
     * • Double the value of every second digit beginning from the right.
     * That is, the last digit is unchanged; the second-to-last digit is doubled;
     * the third-to-last digit is unchanged; and so on. For example, [1,3,8,6] becomes [2,3,16,6].
     * • Add the digits of the doubled values and the undoubled digits from the original number.
     * For example, [2,3,16,6] becomes 2+3+1+6+6 = 18.
     * • Calculate the remainder when the sum is divided by 10.
     * For the above example, the remainder would be 8. If the result equals 0, then the number is valid.
     *
     * @param creditCardNumber - credit card number to check validity for
     * @return boolean
     */
    private CreditCardValidateResponse checkCreditCardValidity(String creditCardNumber) {
        char[] creditCardNumberChar = creditCardNumber.toCharArray();
        int length = creditCardNumberChar.length;
        int sum = 0;
        for (int i = length - 1; i >= 0; i--) {
            sum += Character.getNumericValue(creditCardNumberChar[i]);
            --i;
            if (i >= 0) {
                sum += (Character.getNumericValue(creditCardNumberChar[i]) * 2);
            }

        }
        return new CreditCardValidateResponse(sum % 10 == 0 ? true : false);
    }
}
