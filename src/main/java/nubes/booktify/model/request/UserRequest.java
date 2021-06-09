package nubes.booktify.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {
    
    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Size(min=6, message = "La contraseña debe tener al menos 6 caracteres.")
    @Pattern(regexp = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z]).*", message = "La contraseña debe contener al menos una minúscula, una mayúscula y un dígito.")
    private String password;

    @NotEmpty
    @NotNull
    private String fullname;

    @NotEmpty
    @NotNull
    private String lastname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
