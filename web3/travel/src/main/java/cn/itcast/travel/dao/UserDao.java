package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 添加用户
     * @param user
     */
    void save(User user);

    /**
     * 根据激活码查询用户信息
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 修改激活状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    User findUserByUsernameAndPassword(String username, String password);
}
