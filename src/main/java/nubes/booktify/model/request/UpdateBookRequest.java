package nubes.booktify.model.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookRequest {
    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String content;
}
