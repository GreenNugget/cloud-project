package nubes.booktify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "type_user")
public class TypeUser {
    @Id
    @JsonIgnore
    private Integer type_user_id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    public TypeUser(Integer id, Type type) {
        this.type_user_id = id;
        this.type = type;
    }

    public TypeUser() {}

    public Integer getId() {
        return type_user_id;
    }

    public void setId(Integer type_user_id) {
        this.type_user_id = type_user_id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }  
}
