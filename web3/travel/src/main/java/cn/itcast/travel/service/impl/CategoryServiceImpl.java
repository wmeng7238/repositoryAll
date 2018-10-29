package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();

    /**
     * 查询所有分类
     *
     * @return
     */
    @Override
    public List<Category> findAll() {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
        } catch (Exception e) {
            e.printStackTrace();
            List<Category> list = dao.findAll();
            return list;
        }
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> list = null;
        if (categorys == null || categorys.size() == 0) {
            list = dao.findAll();
            for (Category category : list) {
                jedis.zadd("category", category.getCid(), category.getCname()); } } else {
            list = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                list.add(category); } }
                JedisUtil.close(jedis);
        return list;

    }
}
