package org.example.servlet;

import org.example.dao.ImageDAO;
import org.example.model.Image;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/imageShow")
public class ImageShowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("imageId");
        Image image = ImageDAO.queryOne(Integer.parseInt(id));
        String path = ImageServlet.IMAGE_DIR + image.getPath();
        //返回数据的格式
        resp.setContentType(image.getContentType());
        //输入流读本地文件
        FileInputStream fis = new FileInputStream(path);
        OutputStream os = resp.getOutputStream();
        byte[] bytes = new byte[1024*8];
        int len;
        while((len = fis.read(bytes)) != -1){
            os.write(bytes,0,len);
        }
        os.flush();
        fis.close();
        os.close();
        resp.getWriter().println();
    }
}
