package org.example.servlet;

import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.ImageDAO;
import org.example.model.Image;
import org.example.model.Image_ex;
import org.example.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 1. @WebServlet
 * 2. 继承HttpServlet
 * 3. 重写doXXX方法
 */
@WebServlet("/image")
@MultipartConfig
public class ImageServlet extends HttpServlet {
    public static final String IMAGE_DIR = "/Users/onen/Desktop/photo/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求和响应的数据编码

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        //1. 获取请求数据
        try {
            Object user = req.getSession().getAttribute("user");
            Map<String,Object> map = null;
            Integer id = null;
            String uname = null;
            if(user!=null){
                map = (Map)user;
            }else{

            }

            Part p = req.getPart("uploadImage");
            String name = p.getSubmittedFileName();
            String contentType = p.getContentType();
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uploadTime = df.format(date);
            if(map != null){
                uname = (String) map.get("username");
                id = (Integer)map.get("userid");
            }

            long size = p.getSize();

            InputStream inp = p.getInputStream();

            String md5 = DigestUtils.md5Hex(inp);

            int num = ImageDAO.queryCount(md5);
            if(num >= 1){
                throw new RuntimeException();
            }
            //2. 业务代码部分
            p.write(IMAGE_DIR+"/" + md5);
            Image image = new Image();
            image.setContentType(contentType);
            image.setMd5(md5);
            image.setUploadTime(uploadTime);
            image.setImageName(name);
            image.setSize(size);
            image.setPath("/" + md5);
            image.setUserId(id);
            int i = ImageDAO.insert(image);
        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500);
            //可以向前端返回错误信息
        }
        ok(resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String id = req.getParameter("imageId");
        Object o = null;

        if(id == null){
            //查询所有图片信息 o=List<Image>
            List<Image_ex> list = ImageDAO.queryAll();
            System.out.println(list);
            o = list;

            if(req.getSession().getAttribute("user") == null){
                Cookie cookie = new Cookie("username",null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                resp.addCookie(cookie);
                System.out.println("删除了");
            }
        }else{
            //查询指定图片 o=image
            Image image = ImageDAO.queryOne(Integer.parseInt(id));
            o = image;

        }
        String json = Util.serialize(o);
        resp.getWriter().println(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("imageId");
        Image image = ImageDAO.queryOne(Integer.parseInt(id) );

        //数据库删除图片数据
        int n = ImageDAO.delete(Integer.parseInt(id));
        //本地删除图片文件
        //TODO:数据库删除和本地最好通过事务绑定
        String path = IMAGE_DIR + image.getPath();
        File f = new File(path);
        if(f.exists()){
            f.delete();
        }
    }
    public static void ok(HttpServletResponse resp) throws IOException {
        resp.getWriter().println("{\"ok\":true}");
    }
}
