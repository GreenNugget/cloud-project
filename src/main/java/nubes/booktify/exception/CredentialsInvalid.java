package nubes.booktify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class CredentialsInvalid extends RuntimeException{
    
    public CredentialsInvalid() {
        super("Las credenciales son inválidas");
    }

    public CredentialsInvalid(String msg) {
        super(msg);
    }
}
