package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    private static final ObjectMapper M = new ObjectMapper();

    private static final MysqlDataSource DS = new MysqlDataSource();

    static {
        DS.setUrl("jdbc:mysql://47.108.145.19:3306/image_system");
        DS.setUser("root");
        DS.setPassword("222369");
        DS.setUseSSL(false);
        DS.setEncoding("UTF-8");
    }

    public static String serialize(Object o){
        try {
            return M.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化java对象失败"+o,e);
        }
    }

    public static Connection getConnection(){
        try {
            return DS.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接失败",e);
        }
    }

    public static void close(Connection c, Statement s, ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
            if(s != null){
                s.close();
            }
            if(c != null){
                c.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void close(Connection c, Statement s){
        close(c,s,null);
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(DS.getConnection());
    }
}
