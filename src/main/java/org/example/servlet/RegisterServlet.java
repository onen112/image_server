package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.example.dao.UserDAO;
import org.example.util.DBUtil;
import org.example.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String msg = "注册成功！";
        Map<String,Object> map = new HashMap<>();
        int state = 200;
        //判断用户名是否已经存在
        if (username != null && !username.equals("") && password != null && !password.equals("")){
            if(UserDAO.isRegister(username)){
                msg = "当前用户名已存在!";
                state = 100;
            }else{
                Date d = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = dateFormat.format(d);
                UserDAO.register(username,password,email,time);
                File file  = new File("/Users/onen/Desktop/photo/" + username);
                if(!file.exists()){
                    file.mkdir();
                }

            }
        }else{
            msg = "用户名或密码格式不合法，请重试！";
            state = 101;
        }


        map.put("msg",msg);
        map.put("state",state);
        String json = Util.serialize(map);
        resp.getWriter().println(json);

    }


}
