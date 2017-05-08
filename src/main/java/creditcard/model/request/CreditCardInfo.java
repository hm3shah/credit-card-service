package creditcard.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class to store all credit card related information(like cc no, expiry date,
 * name, cvv, etc)
 */
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardInfo {

    @Getter
    @Setter
    private String creditCardNumber;
}
