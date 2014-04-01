/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import Domain.User;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Steven
 */
@Path("users")
@Transactional(dontRollbackOn = {BadRequestException.class,NotFoundException.class})
public class Users {
    
  @PersistenceContext
  EntityManager em;
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<User>getAllUsers(@QueryParam("first")@DefaultValue("0")int first, @QueryParam("results")@DefaultValue("10")int results)
  {
      TypedQuery<User>queryFindAll = em.createNamedQuery("User.findAll",User.class);
      queryFindAll.setFirstResult(first);
      queryFindAll.setMaxResults(results);
      return queryFindAll.getResultList();
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addUser(User user)
  {
      if(em.find(User.class, user.getUsername())!=null)
      {
          throw new BadRequestException("username al in gebruik");
      }
      //validator toevoegen
      em.persist(user);
      
      return Response.created(URI.create("/"+user.getUsername())).build();
      
  }
  
  @Path("{username}")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateUser(@PathParam("username")String username, InputStream input)
  {
      User user = em.find(User.class, username);
      
      if(user == null){
          throw new NotFoundException("Gebruiker niet gevonden");
      }
      try(JsonReader jsonInput = Json.createReader(input))
      {
          JsonObject jsonUser = jsonInput.readObject();
          
          String password = jsonUser.getString("password",null);
          if(password!=null)
          {
              if(password.trim().length() < 8)
              {
                  throw new BadRequestException("Paswoord ongeldig");
              }
              else{
                  user.setPassword(password.trim());
              }
              
          }
          String fullname = jsonUser.getString("fullname",null);
          if(fullname!=null)
          {
              user.setFullName(fullname.trim());
          }
          
          String email = jsonUser.getString("email",null);
          if(email!=null)
          {
              if(!(email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$.")))
              {
                  throw new BadRequestException("E-mail ongeldig");
              }
              else
              {
                  user.setEmail(email.trim());
              }
          }
          
      }
      catch(JsonException|ClassCastException e)
      {
          throw new BadRequestException("ongeldige JSON invoer "+e.getMessage());
      }
      em.merge(user);
  }
  
  @Path("{username}")
  @DELETE
  public void removeUser(@PathParam("username")String username)
  {
      User u = em.find(User.class,username);
      if(u == null)
      {
          throw new NotFoundException("Gebruiker niet gevonden");
      }
      em.remove(u);
  }
  
    
}
