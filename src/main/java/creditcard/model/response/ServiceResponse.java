package creditcard.model.response;

import creditcard.model.error.ServiceError;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class ServiceResponse<T> {

    @Getter
    @Setter
    private T data;

    @Getter
    @Setter
    private ServiceError error;


    /**
     * Create a new successful response object
     *
     * @param data The service result
     */
    public ServiceResponse(T data) {
        this.data = data;
    }

    /**
     * Create a new error response object with {@link ServiceError} details
     *
     * @param error The 'error' object containing details of the service failure
     */
    public ServiceResponse(ServiceError error) {
        this.error = error;
        this.data = null;
    }

    /**
     * @return true if a {@link ServiceError} has been assigned, false if no error exists
     */
    public boolean hasError() {
        return error != null;
    }
}
