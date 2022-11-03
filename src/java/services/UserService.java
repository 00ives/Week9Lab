/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author ivorl
 */
public class UserService {

    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();

        return users;
    }

    public void insert(String email, String firstName, String lastName, String password, int roleID) throws Exception {
        User user = new User(email, firstName, lastName, password);
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleID);
        user.setRole(role);
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    public void delete(User user) throws Exception {
        UserDB userDB = new UserDB();
        userDB.delete(user);
    }

    public void update(String email, String firstName, String lastName, String password, int roleID) throws Exception {
//        User user = new User(email, firstName, lastName, password);

        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleID);
        user.setRole(role);
        userDB.update(user);
    }

}
