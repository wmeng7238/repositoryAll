package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();

    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 分页查询数据，封装为pageBean对象
     * @param cid
     * @param currentPage
     * @param rows
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int rows, String rname) {

        PageBean<Route> routePageBean = new PageBean<>();

        int totalCount = routeDao.findtotalCount(cid,rname);

        int start = (currentPage - 1) * rows;
        List<Route> list = routeDao.findByPage(cid, start, rows,rname);

        int totalPage = totalCount % rows == 0 ? totalCount / rows : (totalCount / rows) + 1;

        routePageBean.setCurrentPage(currentPage);
        routePageBean.setRows(rows);
        routePageBean.setTotalCount(totalCount);
        routePageBean.setTotalPage(totalPage);
        routePageBean.setList(list);

        return routePageBean;
    }

    /**
     * 根据rid查询route对象所有信息
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //获取route基本信息
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //获取route相关图片信息，封装进route对象内
        List<RouteImg> list = routeImgDao.findAllByRid(route.getRid());
        route.setRouteImgList(list);
        //获取route对应商家信息，封装进route对象内
        Seller seller = sellerDao.findOneBySid(route.getSid());
        route.setSeller(seller);

        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        //返回route对象
        return route;
    }

    /**
     * 查询最新的旅游路线
     * @return
     */
    @Override
    public List<Route> findRouteByDate() {
        List<Route> list = routeDao.findByDate();
        return list;
    }
}
