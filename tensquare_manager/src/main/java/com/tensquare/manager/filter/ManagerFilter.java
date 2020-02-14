package com.tensquare.manager.filter;

import cn.hutool.core.util.StrUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tensquare.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Author: Durian
 * Date: 2020/2/14 16:04
 * Description: 后台管理需要验证用户角色权限,
 * 后台管理系统必须拥有正确的身份才能进行操作
 * 所以直接在网关做了权限验证
 */
@Component
public class ManagerFilter extends ZuulFilter
{
    private final JwtUtils jwtUtils;

    @Autowired
    public ManagerFilter(JwtUtils jwtUtils)
    {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String filterType()
    {
        return "pre";
    }

    @Override
    public int filterOrder()
    {
        return 0;
    }

    @Override
    public boolean shouldFilter()
    {
        RequestContext requestContext = RequestContext.getCurrentContext();
        return requestContext.sendZuulResponse();
    }

    @Override
    public Object run()
    {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        // 放行OPTIONS请求方式,网关内部的分发方法
        if (Objects.equals(request.getMethod(), "OPTIONS"))
        {
            return null;
        }
        // 后台登录放行
        String url = request.getRequestURL().toString();
        if (url.indexOf("/admin/login") > 0)
        {
            return null;
        }
        String header = request.getHeader("Authorization");
        if (!StrUtil.isBlank(header) && header.startsWith("Tensquare_"))
        {
            String token = header.substring(10);
            try
            {
                Claims claims = jwtUtils.parseJWT(token);
                String roles = (String) claims.get("roles");
                if (Objects.equals(roles, "admin"))
                {
                    // 放行
                    return null;
                }

            } catch (Exception e)
            {
                e.printStackTrace();
                // 停止
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(403);
                requestContext.setResponseBody("权限不足");
                requestContext.getResponse().setContentType("test/html;charset=utf-8");
            }
        }
        // 如果token不存在或者不是admin
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("test/html;charset=utf-8");
        return null;
    }
}
