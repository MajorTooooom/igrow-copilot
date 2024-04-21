package com.dororo.future.igrowcopilot.util;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;

public class FilterUtils {
    private static final List<String> staticResourceExtNames = Arrays.asList(StrUtil.split("css,ico,svg,ttf,js,woff2,html,eot,map,woff", StrUtil.COMMA));

    /**
     * 如果是"/"或者静态资源
     */
    public static boolean filter(String requestURI) {
        // 静态资源不需要校验用户ID
        for (String extName : staticResourceExtNames) {
            if (StrUtil.endWithIgnoreCase(requestURI, StrUtil.format(".{}", extName))) {
                return true;
            }
        }
        if (StrUtil.equals(requestURI, "/")) {
            return true;
        }
        return false;
    }
}
