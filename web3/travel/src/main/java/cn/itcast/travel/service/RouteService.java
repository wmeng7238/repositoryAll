package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {

    /**
     * 分页查询数据，封装为pageBean对象
     * @param cid
     * @param currentPage
     * @param rows
     * @param rname
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int rows, String rname);

    /**
     * 根据rid查询route对象所有信息
     * @param rid
     * @return
     */
    Route findOne(String rid);

    /**
     * 查询最新的旅游路线
     * @return
     */
    List<Route> findRouteByDate();

}
