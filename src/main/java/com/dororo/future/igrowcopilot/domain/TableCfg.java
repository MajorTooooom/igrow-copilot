package com.dororo.future.igrowcopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableCfg {
    private Integer id;

    private Integer userId;

    private String tableCfgName;

    private String connUrl;

    private String connUserName;

    private String connPassword;

    private String tableSchema;

    private String tableName;

    private String domainName;

    private String domainZnName;

    private String createTime;

    private String updateTime;
}