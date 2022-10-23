/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;


/**
 *
 * @author ivorl
 */
public class UserService {

    public List<User> getAll() throws Exception{
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        
      return users;
  }
    
    public void insert(String email, String firstName, String lastName, String password, int role) throws Exception{
        User user = new User(email, firstName, lastName, password, role);
        UserDB userDB = new UserDB();
        userDB.insert(user);
        
    }
    
    public void delete(User user) throws Exception{
        UserDB userDB = new UserDB();
        userDB.delete(user);
    }
    /*
    get and it takes in an int
    */

    public void update(String email, String firstName, String lastName, String password, int role) throws Exception {
        User user = new User(email, firstName, lastName, password, role);
        UserDB userDB = new UserDB();
        userDB.update(user);
    }

}
