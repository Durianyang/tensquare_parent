package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Durian
 * Date: 2020/2/14 15:39
 * Description:  在过滤器中接收header，转发给微服务
 * 因为在zuul中 Authorization,Cookie,Set-Cooki 敏感信息默认不转发
 * 或者在yml中配置
 * sensitive-headers:
 *   - Cookie, Set-Cookie, Authorization
 */
@Component
public class WebFilter extends ZuulFilter
{
    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型
     * pre 可以在请求被路由之前调用
     * route 在路由请求时候被调用
     * post 在route和error过滤器之后被调用
     * error 处理请求时发生错误时被调用
     *
     * @return 过滤器类型
     */
    @Override
    public String filterType()
    {
        // 前置过滤器
        return "pre";
    }

    /**
     * filterOrder ：通过int值来定义过滤器的执行顺序
     *
     * @return 优先级
     */
    @Override
    public int filterOrder()
    {
        // 优先级为0，数字越大，优先级越低
        return 0;
    }

    /**
     * 返回一个boolean类型来判断该过滤器是否要执行
     *
     * @return 是否过滤
     */
    @Override
    public boolean shouldFilter()
    {
        // 是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException
    {
        // 向header中添加鉴权信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        if (authorization != null)
        {
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }
        return null;
    }
}
