package org.example.servlet;

import lombok.SneakyThrows;
import org.example.dao.UserDAO;
import org.example.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String msg = "";
        int state = -1;
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Map<String,Object> map = new HashMap<>();
        if(username != null && !username.equals("") || password != null && !password.equals("")){
            if(UserDAO.login(username,password)) {
                //登录成功！
                msg = "登录成功！";
                state = 200;
            }else {
                //不存在
                msg = "用户名或密码错误，请重试！";
                state = 100;
            }
        }
        map.put("msg",msg);
        map.put("state",state);
        String json = Util.serialize(map);
        resp.getWriter().println(json);
    }
}
