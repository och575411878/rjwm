package com.och.rjwm.filter;

import com.alibaba.fastjson.JSON;
import com.och.rjwm.common.BaseContext;
import com.och.rjwm.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        String requestURI = request1.getRequestURI();
        String[] pass_address = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        for(String str: pass_address){
            if(PATH_MATCHER.match(str,requestURI)){
                chain.doFilter(request,response);
                return;
            }
        }
        if(request1.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request1.getSession().getAttribute("user"));

            Long userId = (Long) request1.getSession().getAttribute("user");
            // TODO:是不是还没写线程变量的逻辑
            BaseContext.setCurrentId(userId);

            chain.doFilter(request,response);
            return;
        }
        if(request1.getSession().getAttribute("employee")!=null){
            chain.doFilter(request,response);
        }else{
            // TODO: 方便接口测试我直接放行了.
            request1.getSession().setAttribute("user",1L);
            BaseContext.setCurrentId(1L);
            chain.doFilter(request,response);
//            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        }
    }
}
