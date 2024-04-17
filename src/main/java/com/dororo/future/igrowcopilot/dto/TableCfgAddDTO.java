package com.dororo.future.igrowcopilot.dto;

import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableCfgAddDTO {
    @NotNull(message = "用户ID不能为空")
    private Integer userId;
    @NotBlank(message = "配置名不能为空")
    private String tableCfgName;
    @NotBlank(message = "连接URL不能为空")
    private String connUrl;
    @NotBlank(message = "连接用户名不能为空")
    private String connUserName;
    @NotBlank(message = "连接密码不能为空")
    private String connPassword;
    @NotBlank(message = "表所属的数据库名称不能为空")
    private String tableSchema;
    @NotBlank(message = "表名不能为空")
    private String tableName;
    @NotBlank(message = "实体类名不能为空")
    private String domainName;
    @NotBlank(message = "实体类中文名不能为空")
    private String domainZnName;
    // ========================================
    @NotEmpty(message = "列信息不能为空")
    private List<ColumnCfg> columnCfgs;
}
