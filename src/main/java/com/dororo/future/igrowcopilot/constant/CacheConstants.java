package com.dororo.future.igrowcopilot.constant;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

public class CacheConstants {
    // 同时需要再`spring.cache.cache-names`中配置
    public static final String TABLE_CFG_OPTIONS = "tableCfgOptions";

    // 当前支持ftl和vm两种模板格式
    public static final String[] SUPPORTED_FORMATS_EXT_NAME = {"ftl", "vm"};

    // 当前雪花算法的workerId和dataCenterId
    public static final Integer SNOWFLAKE_WORKER_ID = 1;
    public static final Integer SNOWFLAKE_DATA_CENTER_ID = 1;
}
