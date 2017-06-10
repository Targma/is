package si.fri.demo.is.core.jpa.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.jpa.utility.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="\"user\"")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class User extends BaseEntity<User> {

    @Column(length = Constants.DEF_STRING_LEN)
    private String email;

    @Column(length = Constants.DEF_STRING_LEN)
    private String authenticationId;


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
}
