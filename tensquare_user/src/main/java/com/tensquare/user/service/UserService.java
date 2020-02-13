package com.tensquare.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.StrUtil;
import com.tensquare.utils.IdWorker;
import com.tensquare.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService
{

    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder encoder;
    private final UserDao userDao;
    private final RabbitTemplate rabbitTemplate;
    private final IdWorker idWorker;
    private final HttpServletRequest request;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    public UserService(JwtUtils jwtUtils, UserDao userDao, RabbitTemplate rabbitTemplate, IdWorker idWorker, BCryptPasswordEncoder encoder, HttpServletRequest request)
    {
        this.jwtUtils = jwtUtils;
        this.userDao = userDao;
        this.rabbitTemplate = rabbitTemplate;
        this.idWorker = idWorker;
        this.encoder = encoder;
        this.request = request;
    }

    /**
     * 登录
     */
    public User findByNickNameAndPassword(String nickname, String password)
    {
        User user = userDao.findByNickname(nickname);
        // BCrypt验证
        if (user != null && encoder.matches(password, user.getPassword()))
        {
            return user;
        } else
        {
            return null;
        }
    }

    /**
     * 根据昵称查询是否存在用户
     */
    public User findByNickName(String nickname)
    {
        return userDao.findByNickname(nickname);
    }

    /**
     * 发送短信验证码到redis和rabbitMQ
     */
    public void sendSms(String mobile)
    {
        Random random = new Random();
        int max = 999999;//最大数
        int min = 100000;//最小数
        int code = random.nextInt(max);//随机生成
        if (code < min)
        {
            code += min;
        }
        LOGGER.info(mobile + "收到验证码是：" + code);
        // 讲验证码放入redis，10分钟过期
        redisTemplate.opsForValue().set("smsmobile_" + mobile, String.valueOf(code), 5, TimeUnit.MINUTES);
        // 将验证码和手机号发到rabbitMQ中
        Map<String, String> map = new HashMap<>(2);
        map.put("mobile", mobile);
        map.put("code", String.valueOf(code));
        rabbitTemplate.convertAndSend("sms", map);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll()
    {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size)
    {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap)
    {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id)
    {
        return userDao.findById(id).get();
    }

    /**
     * 注册
     *
     * @param userInfo
     */
    public boolean add(User userInfo, String code)
    {
        User user = findByNickName(userInfo.getNickname());
        if (user != null)
        {
            return false;
        }
        // 判断验证码
        String syscode = (String) redisTemplate.opsForValue().get("smsmobile_" + userInfo.getMobile());
        if (code == null)
        {
            throw new RuntimeException("验证码不能为空");
        } else if (!Objects.equals(syscode, code))
        {
            throw new RuntimeException("验证码错误");
        }
        String newPassword = encoder.encode(userInfo.getPassword());
        userInfo.setPassword(newPassword);
        userInfo.setId(String.valueOf(idWorker.nextId()));
        userInfo.setFanscount(0);
        userInfo.setFollowcount(0);
        userInfo.setOnline(0L);
        userInfo.setRegdate(new Date());
        userInfo.setUpdatedate(new Date());
        userInfo.setLastdate(new Date());
        userDao.save(userInfo);
        redisTemplate.delete("mobile_" + userInfo.getMobile());
        return true;
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user)
    {
        userDao.save(user);
    }

    /**
     * 删除
     *
     */
    public void deleteById(String id)
    {
        String roles = (String) request.getAttribute("roles");
        if (StrUtil.isBlank(roles) || !Objects.equals(roles, "admin"))
        {
            throw new RuntimeException("权限不足");
        }
        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap)
    {

        return (Specification<User>) (root, query, cb) ->
        {
            List<Predicate> predicateList = new ArrayList<>();
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id")))
            {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 手机号码
            if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile")))
            {
                predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password")))
            {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname")))
            {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
            }
            // 性别
            if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex")))
            {
                predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
            }
            // 头像
            if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar")))
            {
                predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
            }
            // E-Mail
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email")))
            {
                predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
            }
            // 兴趣
            if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest")))
            {
                predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
            }
            // 个性
            if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality")))
            {
                predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
