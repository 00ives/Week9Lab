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
import services.RoleService;
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
        UserService userService = new UserService();// pretty sure i dont need this line anymore
        RoleService roleService = new RoleService();
        String addOrEdit;
        List<User> users = null;
        List<Role> roles = null;

//        try {
//            users = userService.getAll();
////            req.setAttribute("userList", users);
//            session.setAttribute("userList", users);
//        } catch (Exception ex) {
//            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            req.setAttribute("noUsersFound", "No Users Found");
//
//        }
        users = getUsers();
        try {
            roles = roleService.getAll();
            
            
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.setAttribute("roles", roles);
        session.setAttribute("userList", users);
        
        String editUser = req.getParameter("editUser");
        if (editUser != null && !editUser.equals("")) {
            addOrEdit = "Edit User";
            int editIndex = Integer.parseInt(editUser);
            User selectedUser = users.get(editIndex);
//            User selectedUser = new User(users.get(editIndex));
//            req.setAttribute("selectedUser", selectedUser);
            session.setAttribute("selectedUser", selectedUser);
            session.setAttribute("editIndex", editIndex);
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

//        try {
//            users = userService.getAll();
////            req.setAttribute("userList", users);
//            session.setAttribute("userList", users);
//        } catch (Exception ex) {
//            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            req.setAttribute("noUsersFound", "No Users Found");
//
//        }
        users = getUsers();
        session.setAttribute("userList", users);

        String deleteIndex = req.getParameter("delete");
        req.setAttribute("deleteIndex", deleteIndex);

        String action = req.getParameter("action");
        String email = req.getParameter("emailInput");
        String firstName = req.getParameter("firstNameInput");
        String lastName = req.getParameter("lastNameInput");
        String password = req.getParameter("passwordInput");
        String roleInput = req.getParameter("roleInput");
        int editIndex;

//        Role role = new Role(Integer.parseInt(roleInput));
        String addOrEdit = (String) session.getAttribute("addOrEdit");
        String message = "";

        if (action.equals("Update")) {

            editIndex = (int) session.getAttribute("editIndex");
            email = users.get(editIndex).getEmail();

        }

        if (deleteIndex == null && (email.equals("") || firstName.equals("")
                || lastName.equals("") || password.equals(""))) {

            req.setAttribute("email", email);
            req.setAttribute("firstName", firstName);
            req.setAttribute("lastName", lastName);
            message = "Please fill out all fields";

        } else if (action.equals("Add user")) {

            try {
                userService.insert(email, firstName, lastName, password, Integer.parseInt(roleInput));
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (action.equals("Update")) {

            try {
                userService.update(email, firstName, lastName, password, Integer.parseInt(roleInput));
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (action.equals("deleteUser")) {

            try {
                User deleteUser = users.get(Integer.parseInt(deleteIndex));
                userService.delete(deleteUser);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        req.setAttribute("message", message);

//        try {
//            users = userService.getAll();
////            req.setAttribute("userList", users);
//            session.setAttribute("userList", users);
//            String message2 = "";
//            if (users.isEmpty()) {
//                message2 = "No users found. Please add a user";
//            } else {
//                message2 = "";
//            }
//            session.setAttribute("message2", message2);
//        } catch (Exception ex) {
//            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
//            req.setAttribute("noUsersFound", "No Users Found");
//
//        }
        users = getUsers();
        session.setAttribute("userList", users);

        String message2 = "";

        if (users.isEmpty()) {
            message2 = "No users found. Please add a user";
        } else {
            message2 = "";
        }
        session.setAttribute("message2", message2);

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(req, res);
    }

    public List<User> getUsers() {
        List<User> users = null;
        UserService userService = new UserService();
        try {
            users = userService.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

}
