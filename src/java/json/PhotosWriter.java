/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package json;

import Domain.Category;
import Domain.Photo;
import Domain.User;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
public class PhotosWriter implements MessageBodyWriter<List<Photo>>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {

        if (!List.class.isAssignableFrom(type)) {
            return false;
        }

        // Het volgende stukje code controleert of de List wel een List<User> was.
        if (genericType instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) genericType).getActualTypeArguments();
            return arguments.length == 1 && arguments[0].equals(User.class);
        } else {
            return false;
        }
    }

    @Override
    public long getSize(List<Photo> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(List<Photo> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        
        for(Photo p : t)
        {
            JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            jsonObject.add("id", p.getId());
            jsonObject.add("title",p.getTitle());
            jsonObject.add("url",p.getUrl());
            jsonObject.add("ucomment", p.getuComment());
            jsonObject.add("date",p.getDate().getTime());
            
            
            boolean perUser = false;
            
            for (Annotation an : annotations)
            {
                if(an instanceof PerUser)
                {
                    perUser = true;
                }
            }
            if(perUser)
            {
                JsonArrayBuilder categorieArray = Json.createArrayBuilder();
                for(Category c : p.getCategories())
                {
                    categorieArray.add(c.getNaam());
                }
                jsonObject.add("cat", categorieArray);
            }
            else
            {
                jsonObject.add("uId",p.getUser().getId());
            }
            jsonArray.add(jsonObject);
        }
        try(JsonWriter out = Json.createWriter(entityStream))
        {
            out.writeArray(jsonArray.build());
        }
    
    }
    
}
