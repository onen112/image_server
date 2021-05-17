package org.example.dao;

import org.example.util.DBUtil;

import java.sql.*;

public class UserDAO {
    private static Connection con = null;
    private static PreparedStatement statement = null;
    private static ResultSet set = null;
    public static int register(String username, String password,String email,String rigTime) throws SQLException {

        try{
            con = DBUtil.getConnection();
            String sql = "insert into user_table values(null,?,?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setString(3,email);
            statement.setString(4,rigTime);
            System.out.println(statement);
            return statement.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException("注册出错" + e);
        }finally {
            DBUtil.close(statement,con);
        }
    }

    public static boolean login(String username, String password) throws SQLException {
        try{
            con = DBUtil.getConnection();
            String sql = "select * from user_table where user_name=? and user_password = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            set = statement.executeQuery();
            return set.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询登录信息失败" + e);
        }finally {
            DBUtil.close(statement,con,set);
        }
    }
}
