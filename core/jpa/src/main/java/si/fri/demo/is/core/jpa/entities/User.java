package si.fri.demo.is.core.jpa.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.utility.Constants;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;

@Entity
@Table(name="\"user\"")
@Cacheable(true)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class User extends BaseEntity<User> {

    @Column(length = Constants.DEF_STRING_LEN)
    private String name;

    @Column(length = Constants.DEF_STRING_LEN)
    private String email;

    @Column(length = Constants.DEF_STRING_LEN)
    private String authenticationId;

    @JsonIgnore
    protected boolean baseSkip(Field field){
        boolean skip = super.baseSkip(field);
        if(skip){
            return skip;
        } else {
            switch (field.getName()) {
                case "email":
                case "authenticationId":
                    return true;
                default:
                    return false;
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
