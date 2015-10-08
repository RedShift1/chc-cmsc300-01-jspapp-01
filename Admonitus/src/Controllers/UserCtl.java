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
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import toolbox.ActivationEmail;
import toolbox.AdmonitusEmail;
import toolbox.JSONResponse;
import toolbox.Sha;
import toolbox.emailConfirmation;
import MVC.Controller;
import flexjson.JSONSerializer;

/**
 * @author Alexander
 *
 */
public class UserCtl extends Controller {

    @Override
    public void doRequest(String actionName, Integer id) throws Exception {

        EntityManager em = this.getServletContext().getEM();
        JSONSerializer serializer = new JSONSerializer();

        if (actionName.equals("get")) {
            List<?> list = em.createNamedQuery("User.findAll").getResultList();

            
            this.getRequest().setAttribute("json",
                    serializer.serialize(list));

            forward("/json.jsp");

        }
        
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
            
            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");
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
            
            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");
        }
        
        if(actionName.equals("register"))
        {
            JSONResponse response;
            
            if(this.getRequest().getParameter("password1") == null || this.getRequest().getParameter("password2") == null)
            {
                response = new JSONResponse("No password1 or password2 supplied");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }
            
            if(!(this.getRequest().getParameter("password1").length() > 0))
            {
                response = new JSONResponse("Password cannot be empty");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }
            
            if(!this.getRequest().getParameter("password1").equals(this.getRequest().getParameter("password2")))
            {
                response = new JSONResponse("Passwords do not match");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }
            if(this.getRequest().getParameter("email") == null)
            {
                response = new JSONResponse("No email address supplied");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
                return;
            }
            if(!(this.getRequest().getParameter("email").length() > 0))
            {
                response = new JSONResponse("No email address supplied");
                this.getRequest().setAttribute("json", serializer.serialize(response));
                forward("/json.jsp");
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

            this.getRequest().setAttribute("json", serializer.serialize(response));
            this.forward("/json.jsp");
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
            
            this.getRequest().setAttribute("json", serializer.serialize(response));
            forward("/json.jsp");
        }

        if(actionName.equals("getPicture"))
        {
            
            User u = this.getLoggedInUser();
            byte[] picture;
            Controllers.Image image;
            
            String defaultPicturePath = Paths.get(this.getRequest().getSession().getServletContext().getRealPath("/commons/defaultpicture.png")).toString();
            
            
            if((picture = u.getPicture()) == null)
            {
                File pictureFile = new File(defaultPicturePath);
                image = new Controllers.Image(
                    "defaultpicture",
                    new FileInputStream(defaultPicturePath),
                    (int) pictureFile.length()
                );
            }
            else
            {
                image = new Controllers.Image("userpicture", new ByteArrayInputStream(picture), picture.length);
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
            
            User u = (User) this.getRequest().getSession().getAttribute("user");
            
            boolean isMultipart = ServletFileUpload.isMultipartContent(this.getRequest());
            
            if (isMultipart) {
                    // Create a factory for disk-based file items
                    FileItemFactory factory = new DiskFileItemFactory();

                    // Create a new file upload handler
                    ServletFileUpload upload = new ServletFileUpload(factory);
     
                try {
                    // Parse the request
                    List /* FileItem */ items = upload.parseRequest(this.getRequest());
                    Iterator iterator = items.iterator();
                    while (iterator.hasNext()) {
                        FileItem item = (FileItem) iterator.next();
                        if (!item.isFormField()) {
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
                } catch (FileUploadException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("No multipart");
            }
            
        }
        
    }

}
