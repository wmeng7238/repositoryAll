package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    Boolean regist(User user);

    /**
     * 修改激活状态
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 用户登录查询
     * @param user
     * @return
     */
    User login(User user);
}
