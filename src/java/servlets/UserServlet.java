package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        UserService userService = new UserService();
        String addOrEdit;
        List<User> users = null;
        try {
            users = userService.getAll();
            req.setAttribute("userList", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("noUsersFound", "No Users Found");
            
        }
     String editUser = req.getParameter("editUser");
        if (editUser != null && !editUser.equals("")){
            addOrEdit = "Edit User";
            int editIndex = Integer.parseInt(editUser);
            User selectedUser = users.get(editIndex);
//            User selectedUser = new User(users.get(editIndex));
            req.setAttribute("selectedUser", selectedUser);
        }else{
            editUser = null;
            addOrEdit = "Add User";
        }
            req.setAttribute("addOrEdit", addOrEdit);
            req.setAttribute("editUser", editUser);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(req,res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(req, res);
    }

}
