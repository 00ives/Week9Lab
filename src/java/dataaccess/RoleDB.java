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

/**
 *
 * @author ivorl
 */
public class RoleDB {

    public List<Role> getAll() throws Exception {

        List<Role> roles = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM role;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int roleId = rs.getInt(1);
                String roleName = rs.getString(2);
                Role role = new Role(roleId, roleName);
                roles.add(role);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return roles;
    }

    public Role get(int id) throws Exception {

        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM role WHERE role_id = ?";

        Role role = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {

                int role_id = rs.getInt(1);
                String role_name = rs.getString(2);

                role = new Role(role_id, role_name);
            }
        } catch (Exception e) {
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeResultSet(rs);
            cp.freeConnection(con);
        }

        return role;
    }

    public void insert(Role role) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO role (role_id,role_name) VALUES (?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, role.getId());
            ps.setString(2, role.getName());
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void update(Role role) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE role SET role_id = ?, role_name = ? WHERE role_id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(2, role.getName());
            ps.setInt(2, role.getId());
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(Role role) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM role WHERE role_id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, role.getId());
            ps.executeUpdate();
        } catch (Exception e) {
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}
