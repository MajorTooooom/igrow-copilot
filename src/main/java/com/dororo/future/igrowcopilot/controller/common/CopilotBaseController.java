package com.dororo.future.igrowcopilot.controller.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

public class CopilotBaseController {
    protected <T> T appointUserIdByHeader(T obj) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String xUserId = request.getHeader("gen-sys-user-id");
            BeanUtil.setFieldValue(obj, "userId", Convert.toInt(xUserId, null));
            return obj;
        }
        return obj;
    }

    protected void startPage() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Integer pageNum = Optional.ofNullable(request).filter(Objects::nonNull).map(s -> s.getParameter("pageNum")).map(q -> Convert.toInt(q, -1)).filter(h -> h > 0).orElse(1);
            Integer pageSize = Optional.ofNullable(request).filter(Objects::nonNull).map(s -> s.getParameter("pageSize")).map(q -> Convert.toInt(q, -1)).filter(h -> h > 0).orElse(10);
            // TODO orderBy防止sql注入
            PageHelper.startPage(pageNum, pageSize);
        }
    }


    protected Integer getCurrentUserIdWithException() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Integer userId = Optional.ofNullable(attributes)
                .map(ServletRequestAttributes::getRequest)
                .map(request -> request.getHeader("gen-sys-user-id"))
                .map(Convert::toInt)
                .filter(Objects::nonNull)
                .orElseThrow(() -> new RuntimeException("获取用户ID失败"));
        return userId;
    }
}
