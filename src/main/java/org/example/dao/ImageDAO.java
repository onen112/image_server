package org.example.dao;

import org.example.model.Image;
import org.example.util.DBUtil;
import org.example.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO {


    public static int queryCount(String md5) throws SQLException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet set = null;
        try {
            //1.获取connection对象
            con = DBUtil.getConnection();
            //2. 获取statement对象
            String sql = "select count(0) as c from image_table where md5 = ?";
            st = con.prepareStatement(sql);
            st.setString(1,md5);
            //3.执行sql语句
            set = st.executeQuery();
            //4.处理查询结果
            set.next();
            return set.getInt("c");
        } catch (SQLException e) {
            throw new RuntimeException("查询图片md5失败" + md5);
        }finally {
            //归还连接
            DBUtil.close(st,con,set);
        }
    }

    public static int insert(Image image) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            // * 1. 获取Connection对象
            con = DBUtil.getConnection();
            // * 2. 获取操作命令对象
            String sql = "insert into image_table values(null,?,?,?,?,?,?)";
            statement = con.prepareStatement(sql);
            statement.setString(1,image.getImageName());
            statement.setLong(2,image.getSize());
            statement.setString(3,image.getUploadTime());
            statement.setString(4,image.getMd5());
            statement.setString(5,image.getContentType());
            statement.setString(6,image.getPath());
            // * 3. 执行sql语句
           return statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("上传图片md5失败" + image.getMd5());
        } finally {
            // * 5. 释放资源
            DBUtil.close(statement,con);
        }
    }


    public static List<Image> queryAll() {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            con = DBUtil.getConnection();
            String sql = "select * from image_table";
            statement = con.prepareStatement(sql);
            set = statement.executeQuery();
            List<Image> list = new ArrayList<>();

            while(set.next()){
                Image image = new Image();
                image.setImageId(set.getInt("image_id"));
                image.setUploadTime(set.getString("upload_time"));
                image.setSize(set.getLong("size"));
                image.setMd5(set.getString("md5"));
                image.setPath(set.getString("path"));
                image.setImageName(set.getString("image_name"));
                image.setContentType(set.getString("content_type"));
                list.add(image);
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException("查询所有图片出错",e);
        }finally {
            Util.close(con,statement);
        }
    }
    public static Image queryOne(int id) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            con = DBUtil.getConnection();
            String sql = "select * from image_table where image_id=?";

            statement = con.prepareStatement(sql);
            statement.setInt(1,id);
            set = statement.executeQuery();

            set.next();
            Image image = new Image();
            image.setImageId(set.getInt("image_id"));
            image.setUploadTime(set.getString("upload_time"));
            image.setSize(set.getLong("size"));
            image.setMd5(set.getString("md5"));
            image.setPath(set.getString("path"));
            image.setImageName(set.getString("image_name"));
            image.setContentType(set.getString("content_type"));
            return image;
        }catch (SQLException e){
            throw new RuntimeException("查询所有图片出错",e);
        }finally {
            Util.close(con,statement);
        }
    }

    public static int delete(int id) {
        Connection con = null;
        PreparedStatement statement = null;

        try{
            con = DBUtil.getConnection();
            String sql = "delete from image_table where image_id = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1,id);
            return statement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("删除图片错误" + id + " "+ e);
        }finally {
            Util.close(con,statement);
        }
    }
}
