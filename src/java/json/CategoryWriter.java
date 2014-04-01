/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package json;

import Domain.Category;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 *
 * @author Steven
 */
public class CategoryWriter implements MessageBodyWriter<Category>{
    
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Category.class.isAssignableFrom(type);
    }
    
    @Override
    public long getSize(Category t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }
    
    @Override
    public void writeTo(Category t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        
        jsonObject.add("id", t.getId());
        jsonObject.add("categorienaam",t.getNaam());
        
        try(JsonWriter out = Json.createWriter(entityStream))
        {
            out.writeObject(jsonObject.build());
        }
    }
    
    
}
