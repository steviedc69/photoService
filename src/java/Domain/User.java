/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain;

import java.util.Objects;
import javax.enterprise.inject.Typed;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Steven
 */
@Entity
@Table(name = "user")
@NamedQueries(
{
    @NamedQuery(name="User.findAll", query="SELECT u FROM User u")
        
})
public class User {
 
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @Size(min=6,max=255,message="username moet minstens 6 tekens bevatten")
    private String username;
    @Size(min=6,max=255, message = "fullname moet minstens 6 tekens bevatten")
    private String fullName;
    @NotNull
    @Size(min=6,max=255,message="password moet minstens 6 tekens bevatten")
    private String password;
    @Email
    private String email;
    
    public User()
    {
        
    }
    public User(int id, String username, String fullname, String email)
    {
        this.id = id;
        this.email = email;
        this.fullName = fullname;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        
        this.email = email;
    }

    public void setFullName(String fullName) {
        if(fullName!=null&& fullName.trim().length()>=6)
            this.fullName = fullName;
        else
            this.fullName = null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        if(password!=null&&password.trim().length()>=6)
            this.password = password;
        else
            this.password = null;
    }

    public void setUsername(String username) {
        if(username!=null&&username.trim().length()>=6)
            this.username = username;
        else
            this.username = null;
    }
    
    
        @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }
}
