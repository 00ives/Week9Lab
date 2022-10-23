package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;

import models.User;
import services.UserService;

/**
 *
 * @author ivorl
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
//        session.getAttribute("message2");
        UserService userService = new UserService();
        String addOrEdit;
        List<User> users = null;

        try {
            users = userService.getAll();
//            req.setAttribute("userList", users);
            session.setAttribute("userList", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("noUsersFound", "No Users Found");

        }
        String editUser = req.getParameter("editUser");
        if (editUser != null && !editUser.equals("")) {
            addOrEdit = "Edit User";
            int editIndex = Integer.parseInt(editUser);
            User selectedUser = users.get(editIndex);
//            User selectedUser = new User(users.get(editIndex));
            req.setAttribute("selectedUser", selectedUser);
        } else {
            editUser = null;
            addOrEdit = "Add User";
        }
        session.setAttribute("addOrEdit", addOrEdit);
        req.setAttribute("editUser", editUser);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        UserService userService = new UserService();
        List<User> users = null;
        try {
            users = userService.getAll();
//            req.setAttribute("userList", users);
            session.setAttribute("userList", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("noUsersFound", "No Users Found");

        }
        
      
        String deleteIndex = req.getParameter("delete");
        String editUser = req.getParameter("editUser");
        req.setAttribute("deleteIndex", deleteIndex);
        
        String action = req.getParameter("action");
        String email = req.getParameter("emailInput");
        String firstName = req.getParameter("firstNameInput");
        String lastName = req.getParameter("lastNameInput");
        String password = req.getParameter("passwordInput");
        String roleInput = req.getParameter("roleInput");
        
//        Role role = new Role(Integer.parseInt(roleInput));
        String addOrEdit = (String) session.getAttribute("addOrEdit");
        String message = "";
        if(deleteIndex == null && (email.equals("") || firstName.equals("") ||
                                   lastName.equals("") || password.equals(""))){
            //getting a null pointer error here for some reason
            
           message = "Please fill out all fields";
        }else if ( action.equals("Add user")){
            try {
                userService.insert(email,firstName,lastName,password,Integer.parseInt(roleInput));
                /*
                convert role here to the role object
                */
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if ( action.equals("deleteUser")){
            try {
                User deleteUser = users.get(Integer.parseInt(deleteIndex));
                userService.delete(deleteUser);
                /*
                convert role here to the role object
                */
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        req.setAttribute("message", message);
                
        try {
            users = userService.getAll();
//            req.setAttribute("userList", users);
            session.setAttribute("userList", users);
            String message2 = "";
            if (users.isEmpty()){
                 message2 = "No users found. Please add a user";
            }else {
                message2 = "";
            }
            session.setAttribute("message2", message2);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("noUsersFound", "No Users Found");

        }
//        session.setAttribute("users", users);
//        try {
//            users = userService.getAll();
//            req.setAttribute("userList", users);
//        } catch (Exception ex) {
//            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            req.setAttribute("noUsersFound", "No Users Found");
//            
//        }
        /*
for tomorrow when clicking delete make the userservice delete the user, and 
then call get all users again to repopulate the table 
same with the add button make sure to grab all the values and do an insert
with the all the values and then re call the getall users
and same with edit a user add all the information to edit and then use the update
query to update an entry.. make sure to CRUD them in both tables. 
         */
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(req, res);
    }

}
