/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Steven
 */
@Entity
@Table(name="category")
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
})
public class Category {
    
    @Id
    @GeneratedValue
    private int id;
    @NotNull(message="ongeldige categorie naam")
    @Size(max = 50)
    private String naam;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Photo> photos = new HashSet<Photo>();

    
    
    
    public Category(){};
    
    public Category(int id,String naam)
    {
        this.id = id;
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public int getId() {
        return id;
    }

    public Set<Photo> getPhotos() {
        return Collections.unmodifiableSet(photos);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
     
    
}
