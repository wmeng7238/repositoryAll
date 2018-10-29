package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import cn.itcast.travel.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();

    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页条件查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String rowsStr = request.getParameter("rows");
        String rname = request.getParameter("rname");
        //处理参数
        //将get方式发送的中文rname转码
        if (!StringUtils.isEmpty(rname)){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }

        int cid = 0;
        if (!StringUtils.isEmpty(cidStr) && !"null".equalsIgnoreCase(cidStr)){
            cid =Integer.parseInt(cidStr);
        }
        int currentPage = 0;
        if (!StringUtils.isEmpty(currentPageStr)){
            currentPage =Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        int rows = 0;
        if (!StringUtils.isEmpty(rowsStr)){
            rows =Integer.parseInt(rowsStr);
        }else {
            rows = 5;
        }

        PageBean<Route> pageBean = routeService.pageQuery(cid,currentPage,rows,rname);
        //将pageBean序列化为json格式，并写回前台
        writeValue(pageBean,response);
    }

    /**
     * 根据rid查询单个route对象所有信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String rid = request.getParameter("rid");
        //调用service层
        Route route = routeService.findOne(rid);
        //以json格式响应
        writeValue(route,response);
    }

    /**
     * 根据rid和uid查询是否收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String rid = request.getParameter("rid");

        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user==null){
            uid=0;
        }else {
            uid=user.getUid();
        }
        //调用service层
        boolean flag = favoriteService.isFavorite(rid,uid);
        //以json格式响应
        writeValue(flag,response);
    }

    /**
     * 添加收藏信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String rid = request.getParameter("rid");

        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user==null){
           return;
        }else {
            uid=user.getUid();
        }
        //调用service层
        favoriteService.add(rid,uid);
    }

    /**
     * 根据uid查询用户收藏旅游线路信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPageStr = request.getParameter("currentPage");
        String rowsStr = request.getParameter("rows");

        int currentPage = 0;
        if (!StringUtils.isEmpty(currentPageStr)){
            currentPage =Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        int rows = 0;
        if (!StringUtils.isEmpty(rowsStr)){
            rows =Integer.parseInt(rowsStr);
        }else {
            rows = 12;
        }

        User user = (User) request.getSession().getAttribute("user");

        int uid;
        if (user==null){
            uid = 0;
        }else {
            uid=user.getUid();
        }

        //调用service层
        PageBean<Route> pageBean = favoriteService.findFavoriteByUid(uid, currentPage, rows);

        writeValue(pageBean,response);
    }

    /**
     * 分页条件查询被收藏过的旅游路线表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void allFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String currentPageStr = request.getParameter("currentPage");
        String rowsStr = request.getParameter("rows");
        String rname = request.getParameter("rname");
        String lpriceStr = request.getParameter("lprice");
        String hpriceStr = request.getParameter("hprice");
        //处理参数
        int currentPage = 0;
        if (!StringUtils.isEmpty(currentPageStr)){
            currentPage =Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        int rows = 0;
        if (!StringUtils.isEmpty(rowsStr)){
            rows =Integer.parseInt(rowsStr);
        }else {
            rows = 8;
        }
        //将get方式发送的中文rname转码
        if (!StringUtils.isEmpty(rname)){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }
        int lprice = 0;
        if (!StringUtils.isEmpty(lpriceStr)&&!"null".equalsIgnoreCase(lpriceStr)){
            lprice =Integer.parseInt(lpriceStr);
        }
        int hprice = 100000000;
        if (!StringUtils.isEmpty(hpriceStr)&&!"null".equalsIgnoreCase(hpriceStr)){
            hprice =Integer.parseInt(hpriceStr);
        }

        PageBean<Route> pb = favoriteService.findAllFavorite(currentPage,rows,rname,lprice,hprice);

        writeValue(pb,response);
    }

    /**
     * 查询最有人气的旅游路线
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findPopularity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Route> list = favoriteService.findPopularity();
        writeValue(list,response);
    }

    /**
     * 查询最新的旅游路线
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findNewest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Route> list = routeService.findRouteByDate();
        writeValue(list,response);
    }

}

