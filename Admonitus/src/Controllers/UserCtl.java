package Controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import models.User;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.validator.routines.EmailValidator;

import toolbox.ActivationEmail;
import toolbox.AdmonitusEmail;
import toolbox.JSONResponse;
import toolbox.Sha;
import MVC.JSONController;

/**
 * @author Alexander
 *
 */
public class UserCtl extends JSONController {

    @Override
    public void doRequest(String actionName, Integer id) throws Exception {

        EntityManager em = this.getServletContext().getEM();
        
        if(actionName.equals("isLoggedIn"))
        {
            JSONResponse response;
            if(this.getRequest().getSession().getAttribute("user") == null)
            {
                response = new JSONResponse(true, null, false);
            }
            else
            {
                response = new JSONResponse(true, null, this.getRequest().getSession().getAttribute("user"));
            }
            
            this.respond(response);
        }
        
        if(actionName.equals("logout"))
        {
            JSONResponse response;
            try
            {
                this.logOut();
                response = new JSONResponse(true, null, null);
            }
            catch(Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }
            
            try
            {
                em.flush();
            }
            catch(Exception ex)
            {}
            
            this.respond(response);
        }
        
        if(actionName.equals("register"))
        {
            JSONResponse response;
            
            if(this.getRequest().getParameter("password1") == null || this.getRequest().getParameter("password2") == null)
            {
                response = new JSONResponse("No password1 or password2 supplied");
                this.respond(response);
                return;
            }
            
            if(!(this.getRequest().getParameter("password1").length() > 0))
            {
                response = new JSONResponse("Password cannot be empty");
                this.respond(response);
                return;
            }
            
            if(!this.getRequest().getParameter("password1").equals(this.getRequest().getParameter("password2")))
            {
                response = new JSONResponse("Passwords do not match");
                this.respond(response);
                return;
            }

            if(this.getRequest().getParameter("email") == null)
            {
                response = new JSONResponse("No email address supplied");
                this.respond(response);
                return;
            }

            if(!EmailValidator.getInstance().isValid(this.getRequest().getParameter("email")))
            {
                response = new JSONResponse("The email address is invalid");
                this.respond(response);
                return;
            }


            try
            {
                String key = UUID.randomUUID().toString();
                User newUser = new User();
                newUser.setEmail(this.getRequest().getParameter("email"));
                newUser.setPassword(Sha.hash256(this.getRequest().getParameter("password1")));
                newUser.setCreationDate(new Date());
                newUser.setActivationKey(key);
                
                em.getTransaction().begin();
                em.persist(newUser);
                em.getTransaction().commit();

                AdmonitusEmail email = new AdmonitusEmail(newUser.getEmail(), "Account activation", null);
                email.setContentProvider(new ActivationEmail(key));
                email.send();
                response = new JSONResponse(true, null, null);
            }
            catch (Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }

            this.respond(response);
        }
        
        if(actionName.equals("login"))
        {
            JSONResponse response = null;
            try
            {
                Query qry = em.createNamedQuery("User.findByEmailAndPassword");
                qry.setParameter("email", this.getRequest().getParameter("email"));
                qry.setParameter("password", Sha.hash256(this.getRequest().getParameter("password")));
                User user = (User) qry.getSingleResult();
                this.logIn(user);
                response = new JSONResponse(user);
            }
            catch (NoResultException ex)
            {
                response = new JSONResponse("Invalid email address and/or password, or account has not yet been activated");
            }
            catch (Exception ex)
            {
                response = new JSONResponse(ex.getMessage());
            }
            
            this.respond(response);
        }

        if(actionName.equals("getPicture"))
        {
            
            User u = this.getLoggedInUser();
            byte[] picture;
            toolbox.Image image;
            
            String defaultPicturePath = Paths.get(this.getRequest().getSession().getServletContext().getRealPath("/img/defaultpicture.png")).toString();
            
            
            if((picture = u.getPicture()) == null)
            {
                File pictureFile = new File(defaultPicturePath);
                image = new toolbox.Image(
                    "defaultpicture",
                    new FileInputStream(defaultPicturePath),
                    (int) pictureFile.length()
                );
            }
            else
            {
                image = new toolbox.Image("userpicture", new ByteArrayInputStream(picture), picture.length);
            }

             this.getResponse().setHeader("Content-Type", this.getServletContext().getServletContext().getMimeType(image.getFilename()));
             this.getResponse().setHeader("Content-Length", String.valueOf(image.getSize()));
             this.getResponse().setHeader("Content-Disposition", "inline; filename=\"" + image.getFilename() + "\"");
    
             BufferedInputStream input = null;
             BufferedOutputStream output = null;
    
             try
             {
                 input = new BufferedInputStream(image.getContent());
                 output = new BufferedOutputStream(this.getResponse().getOutputStream());
                 byte[] buffer = new byte[8192];
                 for (int length = 0; (length = input.read(buffer)) > 0;) {
                     output.write(buffer, 0, length);
                 }
             }
             finally
             {
                 if (output != null)
                 {
                     try
                     {
                         output.close();
                     }
                     catch (IOException logOrIgnore)
                     {}
                 }
                 if (input != null)
                 {
                     try
                     {
                         input.close();
                     }
                     catch (IOException logOrIgnore)
                     {}
                 }
             }
        }
        
        if(actionName.equals("setPicture"))
        {
            if(!loggedIn())
            {
                return;
            }

            if (!ServletFileUpload.isMultipartContent(this.getRequest()))
            {
                this.respond(new JSONResponse("Form data is not multipart"));
                return;
            }
            
            User u = this.getLoggedInUser();
            ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

            try
            {
                List<FileItem> items = upload.parseRequest(this.getRequest());
                Iterator<FileItem> iterator = items.iterator();
                while (iterator.hasNext())
                {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField())
                    {
                        continue;
                    }
                    // Assume the first file is the picture
                    InputStream filecontent = item.getInputStream();
                    
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    while ((bytesRead = filecontent.read(buffer)) != -1)
                    {
                        output.write(buffer, 0, bytesRead);
                    }
                    
                    u.setPicture(output.toByteArray());

                    em.getTransaction().begin();
                    em.merge(u);
                    em.getTransaction().commit();

                    return;
                }
            }
            catch (Exception ex)
            {
                this.respond(new JSONResponse(ex.getMessage().toString()));
            }
        }
        
    }

}
