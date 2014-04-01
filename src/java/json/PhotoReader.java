/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import Domain.Photo;
import Domain.User;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;


/**
 *
 * @author Steven
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class PhotoReader implements MessageBodyReader<Photo>{

    @PersistenceContext
    EntityManager em;
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Photo.class.isAssignableFrom(type);
    }

    @Override
    public Photo readFrom(Class<Photo> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
         try(JsonReader in = Json.createReader(entityStream))
         {
             JsonObject jsonPhoto = in.readObject();
            Photo p = new Photo();
            p.setId(jsonPhoto.getInt("id"));
            p.setTitle(jsonPhoto.getString("title",null));
            p.setUrl(jsonPhoto.getString("url",null));
            p.setuComment(jsonPhoto.getString("uComment",null));
            p.setDate(new Date(jsonPhoto.getJsonNumber("date").longValue()));
            User u = em.find(User.class, jsonPhoto.getString("uId"));
            p.setUser(u);
            
            return p;
         }
         catch (JsonException | ClassCastException ex) {
            throw new BadRequestException("Ongeldige JSON invoer");
        }
    }
    
}
