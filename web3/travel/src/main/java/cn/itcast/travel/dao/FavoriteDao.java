package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid,int uid);

    /**
     * 根据rid查询收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);

    /**
     * 根据uid查询rid
     * @param uid
     * @param start
     * @param rows
     * @return
     */
    List<Integer> findRidByUid(int uid,int start,int rows);

    /**
     * 根据uid查询用户收藏路线条数
     * @param uid
     * @return
     */
    int findCountByUid(int uid);

    /**
     * 查询被收藏过的路线以及被收藏过的次数
     * @return
     */
    List<Route> findRidAndCount();

    /**
     * 按收藏次数查询前四个路线
     * @return
     */
    List<Integer> findRidByCount();

}
