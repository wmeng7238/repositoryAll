package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.StringUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public Boolean regist(User user) {
        User u = dao.findByUsername(user.getUsername());
        if (u!=null){
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        dao.save(user);
        String text = "点击激活,<a href='http://localhost:80/travel/user/active?code="+user.getCode()+"'>【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),text,"点我激活");
        return true;
    }

    /**
     * 修改激活状态
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        if (!StringUtils.isEmpty(code)){
            User user = dao.findByCode(code);
            if (user!=null){
                dao.updateStatus(user);
                return true;
            }
        }
        return false;
    }

    /**
     * 用户登录查询
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        User u=dao.findUserByUsernameAndPassword(username,password);
        return u;
    }
}
