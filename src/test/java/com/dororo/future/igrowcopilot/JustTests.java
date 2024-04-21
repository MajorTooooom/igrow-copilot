package com.dororo.future.igrowcopilot;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class JustTests {
    @Test
    public void test() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (int i = 0; i < 10000; i++) {
            long id = snowflake.nextId();
            System.out.println(StrUtil.format("id: {}", id));
        }
    }
}
