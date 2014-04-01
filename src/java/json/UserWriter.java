/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package json;

import Domain.User;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Steven
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UserWriter implements MessageBodyWriter<User>{
    
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return User.class.isAssignableFrom(type);
    }
    
    @Override
    public long getSize(User t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }
    
    @Override
    public void writeTo(User user, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
 JsonObjectBuilder jsonUser = Json.createObjectBuilder();
        jsonUser.add("id",user.getId());
        jsonUser.add("username", user.getUsername());
        jsonUser.add("email", user.getEmail());
        if (user.getFullName() != null) {
            jsonUser.add("fullName", user.getFullName());
        }
                
        try (JsonWriter out = Json.createWriter(entityStream)) {
            out.writeObject(jsonUser.build());
        }    }
    
}
