package com.dororo.future.igrowcopilot.domain;

import com.dororo.future.igrowcopilot.domain.common.ColumnMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnCfg extends ColumnMetadata {
    // 入库需要
    private Integer id;
    // 绑定用户
    private Integer userId;
    // 绑定表配置
    private Integer tableCfgId;
    // ==================================================================
    // 数据库列期望转换的Java类字段名
    private String javaName;
    // 数据库列类型对应的Java类字段类型包名,eg:java.util.Date
    private String javaType;
    // 数据库列类型对应的Java类字段类型名称,eg:Date
    private String javaTypeClassName;
    // 用于Swagger文档生成的字段注释
    private String columnSwaggerComment;
    // 用于javax.validation生成的字段注释
    private String columnValidationComment;
    // 是否需要反引号包裹,当字段名为数据库关键字时需要反引号包裹
    private Boolean requireBackQuote;
}