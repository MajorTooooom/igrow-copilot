package com.dororo.future.igrowcopilot;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

public class JustTests {
    @Test
    public void test() {
        String url = "jdbc:mysql://101.33.232.242:3333/information_schema?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowMultiQueries=true";
        String[] split = url.split(":");
        String ip = StrUtil.subAfter(split[2], "//", false);
        String port = StrUtil.subBefore(split[3], "/", false);
        System.out.println(ip);
        System.out.println(port);
    }
}
