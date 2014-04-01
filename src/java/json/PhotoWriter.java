/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import Domain.Category;
import Domain.Photo;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.json.Json;
import javax.json.JsonArray;
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
public class PhotoWriter implements MessageBodyWriter<Photo>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Photo.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(Photo t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Photo t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        
        jsonObject.add("id", t.getId());
        jsonObject.add("title", t.getTitle());
        jsonObject.add("url",t.getUrl());
        jsonObject.add("uComment", t.getuComment());
        jsonObject.add("date",t.getDate().getTime());
        jsonObject.add("uId", t.getUser().getId());
        JsonArrayBuilder catArray = Json.createArrayBuilder();
        for(Category c : t.getCategories())
        {
            catArray.add(c.getNaam());
        }
        jsonObject.add("cat",catArray);
        
        try(JsonWriter out = Json.createWriter(entityStream))
        {
            out.writeObject(jsonObject.build());
        }
    
    }
    
    
}
