package nubes.booktify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntity extends RuntimeException {
    public UnprocessableEntity() {
        super("La entidad no pudo ser procesada.");
    }

    public UnprocessableEntity(String mensaje) {
        super(mensaje);
    }
}
