package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.util.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findtotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> parames = new ArrayList<>();
        if (cid != 0){
            sb.append(" and cid = ? ");
            parames.add(cid);
        }
        if (!StringUtils.isEmpty(rname)&&!"null".equalsIgnoreCase(rname)){
            sb.append(" and rname like ? ");
            parames.add("%"+rname+"%");
        }
        sql = sb.toString();
        return template.queryForObject(sql,Integer.class,parames.toArray());
    }

    /**
     * 分页条件查询每页具体信息
     * @param cid
     * @param start
     * @param rows
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int rows, String rname) {
        String sql = "select * from tab_route where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);
        List<Object> parames = new ArrayList<>();

        if (cid != 0){
            sb.append(" and cid = ? ");
            parames.add(cid);
        }
        if (!StringUtils.isEmpty(rname)&&!"null".equalsIgnoreCase(rname)){
            sb.append(" and rname like ? ");
            parames.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");
        sql = sb.toString();

        parames.add(start);
        parames.add(rows);

        return template.query(sql,new BeanPropertyRowMapper<>(Route.class),parames.toArray());
    }

    /**
     * 根据rid查询route对象
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
    /**
     * 可根据rid,rname,price查询route对象
     * @param rid
     * @param rname
     * @param lprice
     * @param hprice
     * @return
     */
    @Override
    public Route findByMsg(int rid, String rname, int lprice, int hprice) {

        Route route = null;
        try {
            String sql = "select * from tab_route where rid = ? and price > ? and price < ? ";
            StringBuilder sb = new StringBuilder(sql);
            List<Object> parames = new ArrayList<>();
            parames.add(rid);
            parames.add(lprice);
            parames.add(hprice);
            if (!StringUtils.isEmpty(rname)&&!"null".equalsIgnoreCase(rname)){
                sb.append(" and rname like ? ");
                parames.add("%"+rname+"%");
            }
            sql = sb.toString();
            route = template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),parames.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public List<Route> findByDate() {
        String sql = "select * from tab_route order by rdate desc limit 4";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }


}
