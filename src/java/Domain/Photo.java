/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Steven
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Photo.findByUser", query = "SELECT p FROM Photo p WHERE p.user.id = :id")
   
}) 
public class Photo {
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @Pattern(regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", message="ongeldig url")
    private String url;
    @NotNull(message="titel is verplicht")
    @Size(min = 5, max=50,message = "titel moet minimum 5 tekens bevatten")
    private String title;
    
    private String uComment;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    @ManyToMany(mappedBy = "categories",fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    public Photo(int id, String url, String title, String uComment,Date date,User u) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.uComment = uComment;
        this.date = date;
    }

    public Photo() {
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getuComment() {
        return uComment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        if(title!=null&&title.trim().length()>=5)
            this.title = title;
        else
            this.title = null;
    }

    public void setUrl(String url) {
        if(url!=null)
            this.url = url;
        else
            this.url = null;
    }

    public void setuComment(String uComment) {
        if(uComment!=null)
            this.uComment = uComment;
        else
            this.uComment = null;
    }

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public int getId() {
        return id;
    }


    
}
