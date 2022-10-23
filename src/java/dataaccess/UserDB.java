/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author ivorl
 */
public class UserDB {
    
    public List<User> getAll() throws Exception{
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM user;";
        
        try {
            ps = con.prepareStatement(sql);
//            ps.setString(1,hmmwhatisthisvlaue);
            rs = ps.executeQuery();
            while (rs.next()){
                String email = rs.getString(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String password = rs.getString(4);
                int role = rs.getInt(5);// need to replace this value with the value gained from the other table same as get method below
                
//                Role role = null;
//                if(roleValue == 1){
//                    role = new Role(roleValue,"system admin");
//                }else if(roleValue == 2){
//                    role = new Role(roleValue,"egular user");
//                }
//                (1, 'system admin');
//                (2, 'regular user');
             
                User user = new User(email, firstName, lastName, password, role);
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return users;
    }
    
    public User get(String email) throws Exception{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM user where email = ?";
        User user = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,email);
            rs =ps.executeQuery();
            if (rs.next()){
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                String password = rs.getString(4);
                int role = rs.getInt(5);// this is where i want to replace the value
                
//                Role role = null;
//                if(roleValue == 1){
//                    role = new Role(roleValue,"system admin");
//                }else if(roleValue == 2){
//                    role = new Role(roleValue,"egular user");
//                }
//                (1, 'system admin');
//                (2, 'regular user');
             
               user = new User(email, firstName, lastName, password, role);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
        return user;
    }
    
    public void insert(User user) throws Exception{
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user (email,first_name,last_name,password,role) VALUES (?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole());
            ps.executeUpdate();
             /*
            also need to change the role here to set it in 
            both tables at the same time so the values match
            */
        } catch (Exception e) {
        }finally{
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
    
    public void update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE user SET first_name = ?, last_name = ?, password = ?, role = ? WHERE email = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole());
            ps.setString(5, user.getEmail());
            ps.executeUpdate();
            /*
            also need to change the role here to set it in 
            both tables at the same time so the values match
            */
        } catch (Exception e) {
        }finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
    
    public void delete(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM user WHERE email = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
        }finally{
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }
}
