package nubes.booktify.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReadListRequest {

  @NotNull
  private Integer userId;

  @NotEmpty
  private String name;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
