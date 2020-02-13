package com.tensquare.qa.interceptor;

import cn.hutool.core.util.StrUtil;
import com.tensquare.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: Durian
 * Date: 2020/2/13 15:31
 * Description: JWT权限验证拦截
 */
@Component
public class JwtInterceptor implements HandlerInterceptor
{
    private final JwtUtils jwtUtils;

    @Autowired
    public JwtInterceptor(JwtUtils jwtUtils)
    {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        /*
         * 只处理token信息，对具体业务逻辑不做判断
         */
        // 虽然所有路径都拦截，但是对于未登录进行查看操作时，token不存在，直接放行了
        // 登录状态token存在，检查合法性正确后放行
        final String header = request.getHeader("Authorization");
        if (!StrUtil.isBlank(header) && header.startsWith("Tensquare_"))
        {
            final String token = header.substring(10);
            try
            {
                Claims claims = jwtUtils.parseJWT(token);
                if (!StrUtil.isBlank((CharSequence) claims.get("roles")))
                {
                    request.setAttribute("roles", claims.get("roles"));
                }
            } catch (Exception e)
            {
                throw new RuntimeException("身份验证失败!");
            }
        }
        return true;
    }
}
