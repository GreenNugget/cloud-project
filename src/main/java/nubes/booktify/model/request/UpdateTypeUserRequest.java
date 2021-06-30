package nubes.booktify.model.request;

import javax.validation.constraints.NotNull;

import nubes.booktify.model.Type;

public class UpdateTypeUserRequest {

    @NotNull
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
