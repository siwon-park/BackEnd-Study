package com.example.springjwt.filter;

import javax.servlet.*;
import java.io.IOException;

public class Myfilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response); // chain.doFilter를 하지 않으면 필터에 걸리고 나서 프로그램이 종료됨
    }
}
