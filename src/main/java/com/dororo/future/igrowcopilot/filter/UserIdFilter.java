package com.dororo.future.igrowcopilot.filter;

import cn.hutool.core.util.StrUtil;
import com.dororo.future.igrowcopilot.domain.GenSysUser;
import com.dororo.future.igrowcopilot.service.GenSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OptionalDataException;
import java.util.Optional;

@Slf4j
@Component
public class UserIdFilter extends OncePerRequestFilter {
    @Resource
    private GenSysUserService genSysUserService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        // 登陆相关接口不需要校验用户ID
        if (StrUtil.startWith(requestURI, "/login")) {
            return true;
        }
        return false;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String xUserId = request.getHeader("gen-sys-user-id");
        GenSysUser genSysUser = Optional.ofNullable(xUserId)
                .filter(StrUtil::isNotEmpty)
                .map(Integer::parseInt)
                .map(genSysUserService::selectByPrimaryKey)
                .filter(s -> s != null)
                .orElse(null);
        if (genSysUser == null) {
            throw new RuntimeException("用户不存在或未登录");
        }
        filterChain.doFilter(request, response);
    }
}
