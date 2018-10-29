package cn.itcast.travel.web.un;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 激活用户
 */

public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        //调用service层，处理数据
        UserService service = new UserServiceImpl();
        boolean flag = service.active(code);
        //处理service层返回的数据
        String text=null;
        if (flag){
            text = "激活成功,请<a href='login.html'>登录</a>";
        }else {
            text ="激活失败,请联系管理员";
        }
        //响应结果
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(text);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
