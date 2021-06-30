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
    @Column(name = "type_user_id")
    private Integer typeUserId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    public TypeUser() {}

    public Integer getId() {
        return typeUserId;
    }

    public void setId(Integer typeUserId) {
        this.typeUserId = typeUserId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }  
}
