/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Steven
 */
@Entity
@Table(name="comments")
@NamedQueries({
    @NamedQuery(name = "Comment.findCommentsByPhoto", query = "SELECT c FROM Comment c WHERE c.photo.id = :id")
})
public class Comment {
    
    @Id
    private int id;
    @NotNull(message = "commenttext is verplicht")
    @Size(min = 5, max=500,message="Commenttext moet minimum 5 tekens bevatten")
    private String commentText;
    
    @ManyToOne
    @NotNull(message = "User is verplicht")
    private User user;
    @ManyToOne
    @NotNull(message="Photo is verplicht")
    private Photo photo;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datum;

    public Comment() {
    }

    public Comment(int id, String commentText, User user, Photo photo, Date datum) {
        this.id = id;
        this.commentText = commentText;
        this.user = user;
        this.photo = photo;
        this.datum = datum;
    }

    public String getCommentText() {
        return commentText;
    }

    public Date getDatum() {
        return datum;
    }

    public int getId() {
        return id;
    }

    public Photo getPhoto() {
        return photo;
    }

    public User getUser() {
        return user;
    }

    public void setCommentText(String commentText) {
        if(commentText!=null&&commentText.trim().length()>=5)
            this.commentText = commentText;
        else
            this.commentText = null;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhoto(Photo photo) {
        if(photo!=null)
            this.photo = photo;
        else
            this.photo = null;
    }

    public void setUser(User user) {
        if(user!=null)
            this.user = user;
        else
            this.user = null;
    }
    
    
    
}
