package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    /**
     * 根据rid查询路线被收藏次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql,Integer.class,rid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values ( ? , ? , ? )";
        template.update(sql,rid,new Date(),uid);
    }

    /**
     * 根据uid查询rid集合
     * @param uid
     * @param start
     * @param rows
     * @return
     */
    @Override
    public List<Integer> findRidByUid(int uid,int start,int rows) {
        List<Integer> list = null;
        try {
            String sql = "select rid from tab_favorite where uid = ? limit ? , ?";
            list = template.queryForList(sql,Integer.class, uid,start,rows);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据uid查询用户收藏路线条数
     * @param uid
     * @return
     */
    @Override
    public int findCountByUid(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ?";
        return template.queryForObject(sql,Integer.class,uid);
    }
    /**
     * 查询被收藏过的路线以及被收藏过的次数
     * @return
     */
    @Override
    public List<Route> findRidAndCount() {
        String sql = "select rid,COUNT(*) count from tab_favorite GROUP BY rid ORDER BY COUNT(*) DESC";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }
    /**
     * 按收藏次数查询前四个路线
     * @return
     */
    @Override
    public List<Integer> findRidByCount() {
        String sql = "select rid count from tab_favorite GROUP BY rid ORDER BY COUNT(*) DESC limit 4";
        return template.queryForList(sql,Integer.class);
    }

}
