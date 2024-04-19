package com.dororo.future.igrowcopilot.constant;

public class CommonConstants {
    // 当前支持ftl和vm两种模板格式
    public static final String[] SUPPORTED_FORMATS_EXT_NAME = {"ftl", "vm"};

    // 当前雪花算法的workerId和dataCenterId
    public static final Integer SNOWFLAKE_WORKER_ID = 1;
    public static final Integer SNOWFLAKE_DATA_CENTER_ID = 1;

    // 目录名称     
    public static final String NZMB = "内置模板";
    public static final String NZMB_RESULT = "内置模板渲染结果";
    public static final String UPLOAD = "上传模板";
    public static final String UPLOAD_RESULT = "上传模板渲染结果";
}
