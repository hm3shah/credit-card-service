package creditcard.model.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class CreditCardValidateResponse {

    @Getter
    @Setter
    private boolean isValid;

}
